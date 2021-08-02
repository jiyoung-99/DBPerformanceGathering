package com.skylar.server.handler.statHandler;

import com.skylar.server.connection.PostgreConnectionPool;
import com.skylar.server.handler.StatHandler;
import com.skylar.util.dbQuery.ServerQuery;
import com.skylar.util.dbconn.PostgresCloser;
import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.dbconn.ConnectionVO;
import com.skylar.util.vo.manage.SqlIdInMemoryVO;
import com.skylar.util.vo.message.SQLStatVO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.skylar.server.Server.*;

public class SqlStatHandler extends StatHandler<SQLStatVO> {

    @Override
    public void apply(List<SQLStatVO> valueObject) {
        insertSqlStat(valueObject);
        checkSqlIdInMemory(valueObject);
    }

    /**
     * sql id 중에 sql_text 테이블에 저장 되지 않는 것이 있는지 찾는 메소드
     * 찾으면 static sqlIdQueue 에 넣어준다.
     *
     */
    public void checkSqlIdInMemory(List<SQLStatVO> valueObject) {
        List<SQLStatVO> sqlStatVOS = valueObject;
        List<String> sqlIdInMemoryList = new ArrayList<>();
        for (SqlIdInMemoryVO sqlIdQueueVO : sqlIdInMemory) {
            sqlIdInMemoryList.add(sqlIdQueueVO.getSqlId());
        }
        checkSqlIdInMemory2(sqlStatVOS, sqlIdInMemoryList);
    }

    public void checkSqlIdInMemory2(List<SQLStatVO> sqlStatVOS, List<String> sqlIdInMemoryList) {
        for (SQLStatVO vo : sqlStatVOS) {
            if (!(sqlIdInMemoryList.contains(vo.getSqlId()))) {
                sqlIdRequestQueue.add(vo.getSqlId());
            }
        }
    }


    public int insertSqlStat(List<SQLStatVO> valueObject) {

        MyLogger myLogger = LoggerFactory.getLogger(SqlStatHandler.class.getSimpleName());

        int result = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            List<SQLStatVO> sqlStatVOS = valueObject;
            PostgreConnectionPool pool = PostgreConnectionPool.getInstance();
            ConnectionVO usingConnection = pool.getConnection();
            conn = usingConnection.getConn();
            pstmt = conn.prepareStatement(ServerQuery.INSERT_SQL_STAT);

            for (SQLStatVO vo : sqlStatVOS) {
                pstmt.setInt(1, vo.getPartitionKey());
                pstmt.setInt(2, vo.getDbId());
                pstmt.setString(3, vo.getTime());
                pstmt.setString(4, vo.getSqlAddr());
                pstmt.setLong(5, vo.getSqlHash());
                pstmt.setString(6, vo.getSqlId());
                pstmt.setLong(7, vo.getSqlPlanHash());
                pstmt.setString(8, vo.getUserName());
                pstmt.setString(9, vo.getProgram());
                pstmt.setString(10, vo.getModule());
                pstmt.setString(11, vo.getAction());
                pstmt.setString(12, vo.getMachine());
                pstmt.setString(13, vo.getOsUser());
                pstmt.setInt(14, vo.getElapsedTime());
                pstmt.setInt(15, vo.getCpuTime());
                pstmt.setInt(16, vo.getWaitTime());
                pstmt.setInt(17, vo.getLogicalReads());
                pstmt.setInt(18, vo.getPhysicalReads());
                pstmt.setInt(19, vo.getRedoSize());
                pstmt.setInt(20, vo.getExecutionCount());
                pstmt.setInt(21, vo.getSortDisk());
                pstmt.setInt(22, vo.getSortRows());
                pstmt.setInt(23, vo.getTableFetchByRowId());
                pstmt.setInt(24, vo.getTableFetchByRowId());
                pstmt.setInt(25, vo.getTableScanBlocksGotten());
                pstmt.setInt(26, vo.getTableScanRowsGotten());
                result = pstmt.executeUpdate();
            }
            pool.returnConnection(usingConnection);
        } catch (SQLException e) {
            myLogger.error(e.getMessage());
        }finally {
            PostgresCloser.postgresCloseAll(rs,pstmt);
        }

        return result;
    }

}
