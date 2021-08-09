package com.exem.server.manage;

import com.exem.server.connection.PostgreConnectionPool;
import com.exem.util.dbQuery.ServerQuery;
import com.exem.util.dbconn.PostgresCloser;
import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.dbconn.ConnectionVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.exem.server.Server.dbStatInMemory;


public class InsertDbStatInDb extends Thread {

    public void run() {

        MyLogger myLogger = LoggerFactory.getLogger(InsertDbStatInDb.class.getSimpleName());

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Random random = new Random();
            Connection conn = null;
            CallableStatement cstmt = null;
            PreparedStatement pstmt = null;
            ResultSet rs = null;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            PostgreConnectionPool pool = PostgreConnectionPool.getInstance();
            /**
             * 1 hour + 10 min 처리 (01분에 처리)
             * 01분에 직전 시간에 쌓아두었던 10분 데이터들의 써머리를 1hour테이블에 인서트하고
             * 직전 10분 간의 1분 써머리 데이터를 10분 써머리 데이터 테이블에 insert 한다.
             */
            if (now.getMinute() % 60 == 1) {
                LocalDateTime startDateTime = now.minusMinutes(1);
                String startDateTimeText = startDateTime.format(formatter);
                try {
                    ConnectionVO usingConnection = pool.getConnection();
                    conn = usingConnection.getConn();
                    cstmt = conn.prepareCall(ServerQuery.INSERT_DB_STAT_1HOUR);
                    cstmt.setString(1, startDateTimeText);
                    cstmt.execute();
                    pool.returnConnection(usingConnection);
                } catch (SQLException e) {
                    myLogger.error(e.getMessage());
                } finally {
                    PostgresCloser.postgresCloseAll(rs, cstmt);
                }

            }

            /**
             * 10min 처리
             */
            if (now.getMinute() % 10 == 1) {
                LocalDateTime startDateTime = now.minusMinutes(1);
                String startDateTimeText = startDateTime.format(formatter);
                try {
                    ConnectionVO usingConnection = pool.getConnection();
                    conn = usingConnection.getConn();
                    cstmt = conn.prepareCall(ServerQuery.INSERT_DB_STAT_10MIN);
                    cstmt.setString(1, startDateTimeText);
                    cstmt.execute();
                    pool.returnConnection(usingConnection);
                } catch (SQLException e) {
                    myLogger.error(e.getMessage());
                } finally {
                    PostgresCloser.postgresCloseAll(rs, cstmt);
                }
            }

            /**
             * 1분마다 데이터를 db_ora_stat 테이블에 인서트
             */
            for (Map.Entry<String, LinkedList<Long>> entry : dbStatInMemory.entrySet()) {
                String dbStatId = entry.getKey();
                LinkedList<Long> dbStatValues = entry.getValue();
                LongSummaryStatistics summary = getSummaryStatistics(dbStatValues);
                String partitionKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
//                String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                try {
                    ConnectionVO usingConnection = pool.getConnection();
                    conn = usingConnection.getConn();
                    pstmt = conn.prepareStatement(ServerQuery.INSERT_DB_STAT);
                    pstmt.setInt(1, Integer.parseInt(partitionKey));
                    pstmt.setInt(2, random.nextInt(100));
                    pstmt.setString(3, now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    pstmt.setLong(4, Long.parseLong(dbStatId)); //statid
                    pstmt.setLong(5, (long) summary.getAverage()); //avg
                    pstmt.setLong(6, (long) summary.getMin());  //min
                    pstmt.setLong(7, (long) summary.getMax());  //max
                    pstmt.executeUpdate();
                    pool.returnConnection(usingConnection);
                } catch (SQLException e) {
                    myLogger.error(e.getMessage());
                } finally {
                    PostgresCloser.postgresCloseAll(rs, cstmt);
                }
                dbStatInMemory.put(dbStatId, new LinkedList<Long>(Arrays.asList(dbStatValues.getLast())));
            }

        }
    }

    /**
     * summary statistic 계산
     * 요구사항 java8 스트림 이용
     * @param list
     * @return
     */
    public LongSummaryStatistics getSummaryStatistics(LinkedList<Long> list) {
        LongSummaryStatistics summaryStatistics = list.stream().mapToLong((x) -> x).summaryStatistics();
        return summaryStatistics;
    }
}
