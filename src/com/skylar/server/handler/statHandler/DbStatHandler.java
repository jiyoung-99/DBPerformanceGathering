package com.exem.server.handler.statHandler;

import com.exem.server.connection.PostgreConnectionPool;
import com.exem.server.handler.StatHandler;
import com.exem.util.alarm.WebhookSender;
import com.exem.util.alarm.enums.AlarmLevel;
import com.exem.util.alarm.enums.ConnectColor;
import com.exem.util.alarm.enums.WebhookUrl;
import com.exem.util.alarm.form.ConnectInfo;
import com.exem.util.dbQuery.ServerQuery;
import com.exem.util.vo.dbconn.ConnectionVO;
import com.exem.util.vo.message.DBStatVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.exem.server.Server.dbStatInMemory;
import static com.exem.server.Server.prevDbStatInMemory;

public class DbStatHandler extends StatHandler<DBStatVO> {

    @Override
    public void apply(List<DBStatVO> valueObject) {
        insertDBStatList(valueObject);
    }

    public void insertDBStatList(List<DBStatVO> valueObject) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        List<DBStatVO> dbStatVOS = valueObject;
        for (DBStatVO vo : dbStatVOS) {

            String statId = String.valueOf(vo.getStatId());     //현재 아이디
            Long nowValue = vo.getValue();                      //현재 아이디의 밸류
            /**
             * prevDbStatInMemory 변수에 있는 statid의 밸류와 현재 들어온 밸류를 비교한다.
             */
            Long prevValue = prevDbStatInMemory.get(statId);
            /**
             * null 일 시 (처음 서버 시작) 이전 데이터를 저장하는 prevDbStatInMemory 와 dbStatInMemory에다가 저장한다.
             * 총 628개
             */
            if (prevValue == null) {
                LinkedList<Long> nullValueList = new LinkedList<>(Arrays.asList(0L));
                prevDbStatInMemory.put(statId, nowValue);
                dbStatInMemory.put(statId, nullValueList);
                /**
                 * 그 이후 부터는 예전 값 - 현재 값을 계산하여서 값의 차를 리스트로 차곡 차곡 dbStatInMemory에 넣는다.
                 * 계산 한 다음에는 현재 값을 prevDbStatInMemory에 넣어서 그 다음 값을 대비한다.
                 */
            } else {
                Long minusValue = nowValue - prevValue;             //메모리에 있는것과 현재 밸류의 차이
                if(minusValue > 10000000) {
                    insertDbAlarmHistory(vo.getStatId(), AlarmLevel.warning.getLevelName(), minusValue);
                    createAlarm(vo.getStatId(), AlarmLevel.warning.getLevelName(), minusValue);
                }else if(minusValue > 1000000) {
                    insertDbAlarmHistory(vo.getStatId(), AlarmLevel.critical.getLevelName(), minusValue);
                    createAlarm(vo.getStatId(), AlarmLevel.critical.getLevelName(), minusValue);
                }
                prevDbStatInMemory.put(statId, nowValue);
                LinkedList<Long> valueList = dbStatInMemory.get(statId);        //현재 아이디 중 메모리에 있는 것 중에 밸류
                valueList.addLast((minusValue));
                dbStatInMemory.put(statId, valueList);
            }

        }
    }

    /**
     * 디비 알람 히스토리 테이블에 인서트 하는 메소드
     * @param statId
     * @param level
     * @param minusValue
     */
    public void insertDbAlarmHistory(Long statId, String level, Long minusValue) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        PostgreConnectionPool pool = PostgreConnectionPool.getInstance();
        ConnectionVO usingConnection = pool.getConnection();
        conn = usingConnection.getConn();
        String partitionKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try {
            pstmt = conn.prepareStatement(ServerQuery.INSERT_DB_ALARM_HISTORY);
            pstmt.setInt(1, Integer.parseInt(partitionKey)); //partition_key
            pstmt.setInt(2, 1); //db_id
            pstmt.setString(3, now);
            pstmt.setString(4, level);//alert_type
            pstmt.setLong(5, statId);     //  stat_id
            pstmt.setLong(6, minusValue);     //   value
            pstmt.executeUpdate();
            pool.returnConnection(usingConnection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 알람을 보내주는 메소드, 테이블을 먼저 조회하고 해당 날짜의 테이블에 동일한 알람의 갯수가 두 개 이상이면
     * 더이상 알람을 보내지 않는다.(하루에 한번만 전송)
     * @param statId
     * @param level
     * @param minusValue
     */
    public void createAlarm(Long statId, String level, Long minusValue) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        PostgreConnectionPool pool = PostgreConnectionPool.getInstance();
        ConnectionVO usingConnection = pool.getConnection();
        conn = usingConnection.getConn();
        try {
            pstmt = conn.prepareStatement(ServerQuery.SELECT_DB_ALARM_HISTORY);
            pstmt.setLong(1, statId);
            pstmt.setString(2, level);
            String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            pstmt.setString(3, today);
            pstmt.setString(4, today);
            ResultSet rs1 = pstmt.executeQuery();
            if(rs1.next()) {
                if(rs1.getInt("count") < 2) {
                    new WebhookSender(WebhookUrl.JANDI_URL.getUrl()).sendMessage("[[CRITICAL 발생]]", ConnectColor.CRICTICAL.getColor(), new ConnectInfo(statId.toString(), "value"+minusValue));

                }
            }
            pool.returnConnection(usingConnection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

}
