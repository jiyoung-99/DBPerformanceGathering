package com.exem.server.handler.statHandler;

import com.exem.server.connection.PostgreConnectionPool;
import com.exem.server.handler.StatHandler;
import com.exem.util.dbQuery.ServerQuery;
import com.exem.util.dbconn.PostgresCloser;
import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.dbconn.ConnectionVO;
import com.exem.util.vo.manage.SqlIdInMemoryVO;
import com.exem.util.vo.message.SessionStatVO;

import java.sql.*;
import java.util.*;

import static com.exem.server.Server.*;


public class SessionStatHandler extends StatHandler<SessionStatVO> {

    @Override
    public void apply(List<SessionStatVO> valueObject) {
        insertSessionStat(valueObject);
        /**
         * 2주차 : sql id 가 들어오면 메모리에 있는 sql id 중 없는 것이 있는지 확인한다.
         */
        checkSqlIdInMemory(valueObject);
    }

    /**
     * sql id 중에 sql_text 테이블에 저장 되지 않는 것이 있는지 찾는 메소드
     * 찾으면 static sqlIdQueue 에 넣어준다.
     */
    public void checkSqlIdInMemory(List<SessionStatVO> valueObject) {
        List<SessionStatVO> sessionStatVOS = valueObject;
        List<String> sqlIdInMemoryList = new ArrayList<>();
        //우선 메모리에 저장되어 있는  sql id를  뽑아서 리스트에 넣는다.
        for (SqlIdInMemoryVO sqlIdQueueVO : sqlIdInMemory) {
            sqlIdInMemoryList.add(sqlIdQueueVO.getSqlId());
        }
        checkSqlIdInMemory2(sessionStatVOS, sqlIdInMemoryList);
    }

    public void checkSqlIdInMemory2(List<SessionStatVO> sessionStatVOS, List<String> sqlIdInMemoryList) {
        for (SessionStatVO vo : sessionStatVOS) {
            //메모리에 저장되어있는리스트에 새로 들어온 아이디가 있고, null 값이 아니면 sqlIdrequestqueue 에 요청 sqlid 를 넣는다.
            if ((!(sqlIdInMemoryList.contains(vo.getSqlId()))) && (vo.getSqlId() != null)) {
                sqlIdRequestQueue.add(vo.getSqlId());
            }
        }
    }

    /**
     * session_stat 테이블에 가져온 정보를 insert 하는 메소드
     */
    public int insertSessionStat(List<SessionStatVO> ValueObject) {

        MyLogger myLogger = LoggerFactory.getLogger(SessionStatHandler.class.getSimpleName());

        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            List<SessionStatVO> sessionStatVOS = ValueObject;
            PostgreConnectionPool pool = PostgreConnectionPool.getInstance();
            ConnectionVO usingConnection = pool.getConnection();
            conn = usingConnection.getConn();
            pstmt = conn.prepareStatement(ServerQuery.INSERT_SESSION_STAT);

            for (SessionStatVO vo : sessionStatVOS) {

                pstmt.setInt(1, vo.getPartitionKey());
                pstmt.setInt(2, vo.getDbId());
                pstmt.setString(3, vo.getTime());
                pstmt.setInt(4, vo.getsId());
                pstmt.setString(5, vo.getLogonTime());
                pstmt.setInt(6, vo.getSerial());
                pstmt.setString(7, vo.getStatus());
                pstmt.setString(8, vo.getTaddr());
                pstmt.setInt(9, vo.getRowWaitFile());
                pstmt.setInt(10, vo.getRowWaitBlock());
                pstmt.setInt(11, vo.getRowWaitRow());
                pstmt.setInt(12, vo.getRowWaitObject());
                pstmt.setString(13, vo.getSchemaName());
                pstmt.setString(14, vo.getModule());
                pstmt.setString(15, vo.getAction());
                pstmt.setString(16, vo.getClientInfo());
                pstmt.setInt(17, vo.getCommandType());
                pstmt.setString(18, vo.getSqlAddr());
                pstmt.setLong(19, vo.getSqlHash());
                pstmt.setString(20, vo.getSqlId());
                pstmt.setString(21, vo.getPrevSqlAddr());
                pstmt.setLong(22, vo.getPrevSqlHash());
                pstmt.setString(23, vo.getSqlId());
                pstmt.executeUpdate();
            }
            pool.returnConnection(usingConnection);
        } catch (SQLException e) {
            myLogger.error(e.getMessage());
        } finally {
            PostgresCloser.postgresCloseAll(rs, pstmt);
        }

        return result;
    }

}
