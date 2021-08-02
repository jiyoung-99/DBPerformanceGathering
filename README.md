# 2021년 6월 교육 과정
[교육과정(클릭)](https://docs.google.com/spreadsheets/d/1AW5OVvkaDmxweTJmoqVJ82FryK9lK1BZ1zWQIjb_vkY/edit#gid=100932392)

![prj흐름도__3_](/uploads/0332e77fdce3def0bb53d8eb94756e16/prj흐름도__3_.jpg)

-----------------
# 1주차
* [X] 자바 기초 
* [X] 소켓 통신 
* [X] git flow 
* [X] 환경설정 및 필요한 프로그램 설치

-----------------
# 2주차
* [X] Oracle 성능 지표 수집(SQL, Session, DB Stat) 
* [X] TCP/IP 프로토콜을 이용한 Blocking 방식의 소켓 서버 구현
* [X] 성능 데이터를 수집하여 서버로 전송하는 클라이언트 구현
* [X] 현재는 오브젝트 안에 있는 변수들 각각을 정의 하지 않음, 우선 랜덤값을 넣어서 처리


#### 구현 내용
* 오라클에서 클라이언트 클래스를 통해 정보를 수집
* 클라이언트에서 소켓 통신으로 오브젝을 갖고 온 다음 서버에 넘겨준다.
* 서버에서 넘겨온 정보를 Postgres에 insert(일부만)

#### 코드 수정 요청 사항
* 클라이언트 VO는 정보를 가져오기만 하기 때문에 setter은 필요 없어 삭제 요청            ---> 클라이언트, 서버에서 똑같은 변수들을 쓰고 있기 때문에 각각의 오브젝트를 합침
* DBStatVO에 카멜 케이스로 되어있지 않은 변수명을 카멜 케이스로 변경                    ---> 변경 완료
* try블럭 안에서 예외가 발생되었을 시라도 close()처리 할 수 있게 finally 추가  ---> 확인 후에 변경 완료 예정
* OracleConnector에서 드라이버가 로드되지 않았을 시에 정확한 에러 메시지가 나오지 않으므로 예외를 외부로 노출 ---> e.printStackTrace() 추가
* 쿼리는 전역으로 사용 ---> 쿼리는 변경될 일이 없기 때문에 쿼리를 담은 클래스 생성       
* Client 어플리케이션을 동작할 때 3가지 수집을 수행해야 하는데 현재는 main함수가 각각 있으므로 클라이언트의 시작점을 만들고 이후 각각의 역할 수행 ---> ClientMain 생성 후 처리

2021-07-01 팀 리뷰 후 추가

* 그래들 의존성을 추가하였으면 그래들 구조에 맞게 변경 요청
* sleep(3000)<<--1개로 변경
* 만약에 jdbc드라이버가 안될 시에는 서버 종료 되게 구현 요청
* 디버그 이용하고 최대한 sout 자제 
* 구현 먼저 하고 코드 정리 하기

-----------------
# 3주차
* [X] 리팩토링 - 리플렉션을 이용하여 Db Stat, Session stat, Sql Stat 에 대한 처리부를 리팩토링한다.
* [X] 요청 응답 구현(1-1) - 서버에서 전달받은 SQL데이터 중에서 기존에 알고있던 SQL_ID가 아니면 클라이언트에 sql 텍스트를 요청해서 수집한다. 여기서 sql id, sql text는 테이블로도 갖고 있어야 하고 메모리에도 저장이 되어있어야 한다.
* [X] 요청 응답 구현 (1-2) - 만약 일정한 시간 내에 클라이언트에서 sql text 응답이 오지 않으면 에러 메시지를 받게 처리한다.
* [X] Postgres 트리거 기반 파티셔닝 구현 - Db stat, Session Stat, Sql stat 을 위한 파티셔닝을 구현한다. 만약 7월 5일자로 데이터를 받게 된다면 7월 3일 이전 테이블은 삭제, 7월 3일 ~  7월 8일 테이블까지 구현을 한다. 

#### 구현 내용
* insert마다 실행되는 트리거를 만들고 트리거 함수를 만들어서 하나의 데이터가 들어갈 때마다 -2일 ~ +3일의 파티셔닝 테이블을 만들고(기존에 없다면) -8일 ~ -3일까지의 파티셔닝 테이블은 삭제한다.
* 기존 테이블을 상속 받은 파티셔닝 테이블 안에 데이터를 넣는다.
``` sql
-- db Trigger function to split out between the various child partition tables
CREATE OR REPLACE FUNCTION create_db_stat_partition_measurement()
RETURNS TRIGGER AS $$
DECLARE
    partition_date TEXT;        --insert 기준 날짜
    partition TEXT;             --table 이름
    delete_partition TEXT;
    start_date INTEGER;            --시작
    end_date INTEGER;              --종료
    i_date DATE;                    --루프 도는 것 안에 날짜
    i_date_text TEXT;     --루프 도는 것 안에 날짜 텍스트
    delete_date DATE;       --삭제 기준일
BEGIN
    partition_date := to_char(NEW.time, 'YYMMDD');
    start_date := to_number(to_char((NEW.time - INTERVAL '2 day'), 'J'), '999999999');
    end_date := to_number(to_char((NEW.time + INTERVAL '3 day'), 'J'), '999999999');
    FOR i IN start_date..end_date LOOP
        i_date := to_date(to_char(i, '99999999999999'), 'J');
        i_date_text := to_char(i_date, 'YYMMDD');
        partition := TG_RELNAME || '_' || i_date_text;
        delete_date := i_date - INTERVAL '6 day';
        delete_partition := TG_RELNAME || '_' || to_char(delete_date, 'YYMMDD');
        IF NOT EXISTS(SELECT tablename  FROM pg_tables WHERE tablename  = partition) THEN
        RAISE NOTICE 'A partition has been created %', partition;
            EXECUTE 'CREATE TABLE ' || partition || '(check( time >= DATE '' ' || i_date || ''' AND time <= DATE ''' || i_date + INTERVAL '1 day' || ''')) INHERITS  (' || TG_RELNAME || ');';
        END IF;
        IF EXISTS(SELECT tablename FROM pg_tables WHERE tablename = delete_partition) THEN
            EXECUTE 'DROP TABLE ' || delete_partition || ';';
        END IF;
    END LOOP;
    partition := TG_RELNAME || '_' || partition_date;
    EXECUTE 'INSERT INTO '||partition || ' SELECT ( '|| TG_RELNAME || ' ' || quote_literal(NEW) || ').* ;';
RETURN NULL;
END $$
LANGUAGE plpgsql
;
```
* 리플렉션을 이용해서 key값을 서버에 있는 class값을 준 다음에 if, else문을 이용하지 않고 key값의 클래스에서 메소드를 실행해서 각각의 데이터를 인서트 한다. 

#### 코드 수정 요청 사항
* executeUpdate 오류 수정 요청 -> 수정 완료
* 메소드 호출마다 객체를 생성하고 있는데 이것을 한번만 생성하는 것으로 변경  ---> 변경 완료
* dbstat, sessionstat, sqlstat 에서 한 개의 수집이 지연되는 경우에 다른 것들에게 영향을 줌 --> 타임아웃 넣음,  해결 방법 생각 중
* A스레드와 A큐를 만들어서 새로운 sql_id 들어오면 sql_id를 A큐에 넣고 A스레드를 통해 꺼내온다. 여기서 유니크한 pk를 만들어서 맵에 키로 넣고 밸류는 sql_id, 요청시간을 넣는다. 클라이언트에 요청을 보내 클라이언트가 요청을 받으면
* 요청을 확인하여서 타입을 확인하고 sql을 찾는 핸들러를 통해 sql정보를 찾은 다음에 다시 서버로 전송해준다.

-----------------
# 4주차
* [X] DB Stat 데이터를 메모리에서 1분 서머리 하여 저장
* [X] DB Stat 1분 서머리 데이터 기반 1시간, 1일 서머리 프로시저 작성
* [X] 메모리에서 서머리 수행시 java8 의 스트림을 활용해 구현

#### 구현 내용

* 프로시저(10min 테이블 생성)
```sql
-- create 10 min , 1분데이터가 10번 들어올 때마다 insert
CREATE OR REPLACE FUNCTION insert_ora_db_stat_10min()
RETURNS trigger
AS $$
DECLARE
    all_count INTEGER;
    count INTEGER;
BEGIN
    RAISE NOTICE 'count_function';
    all_count := (628*10);
    count := (select count(*) from ora_db_stat);
    RAISE NOTICE 'count_function() % ', count%all_count;
    IF(count%all_count) = 0 THEN
        INSERT INTO ora_db_stat_10min (TIME, STAT_ID, MAX_VALUE, MIN_VALUE, AVG_VALUE)
            select
                max(time) as time,
                stat_id,
                max(max_value) as max_value,
                min(min_value) as min_value,
                avg(avg_value) as avg_value
            from
                    (select *
                    from ora_db_stat
                    where time > (select now() - interval '10 min ')) sub
            group by stat_id;
    END IF;
RETURN NULL;
END; $$
LANGUAGE plpgsql;
```

#### 코드 수정 요청 사항
