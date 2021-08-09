package com.exem.client.statGather;


import com.exem.client.connection.OracleConnectionPool;
import com.exem.util.dbQuery.ClientQuery;
import com.exem.util.dbconn.OracleCloser;
import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.dbconn.ConnectionVO;
import com.exem.util.vo.message.SQLStatVO;
import com.exem.util.vo.message.MessageVO;

import java.io.ObjectOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


/**
 * Sql Stat 을 오라클에서 가져온다.
 * 주기적으로 정보를 갖고 온 다음 서버에 전송한다.
 *
 * @author jiyoung lim
 */
public class SqlStatGather {

    private ObjectOutputStream oos;

    public SqlStatGather(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void run() {

        MyLogger myLogger = LoggerFactory.getLogger(SqlStatGather.class.getSimpleName());

        List<SQLStatVO> sqlStatVOS = new ArrayList<SQLStatVO>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");

        try {

            OracleConnectionPool pool = OracleConnectionPool.getInstance();
            ConnectionVO usingConnection = pool.getConnection();
            conn = usingConnection.getConn();
            stmt = conn.createStatement();
            stmt.setQueryTimeout(10);
            rs = stmt.executeQuery(ClientQuery.SELECT_SQL_STAT);
            pool.returnConnection(usingConnection);
            String partitionKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

            while (rs.next()) {
                SQLStatVO sqlStatVO = new SQLStatVO();
                sqlStatVO.setPartitionKey(Integer.parseInt(partitionKey));
                sqlStatVO.setDbId(00);
                sqlStatVO.setTime(simpleDateFormat.format(now));
                sqlStatVO.setSqlAddr(rs.getString("sqlAddr"));
                sqlStatVO.setSqlHash(rs.getLong("sqlHash"));
                sqlStatVO.setSqlId(rs.getString("sqlId"));
                sqlStatVO.setSqlPlanHash(rs.getLong("sqlPlanHash"));
                sqlStatVO.setUserName(rs.getString("userName"));
                sqlStatVO.setProgram(rs.getString("program"));
                sqlStatVO.setModule(rs.getString("module"));
                sqlStatVO.setAction(rs.getString("action"));
                sqlStatVO.setMachine(rs.getString("machine"));
                sqlStatVO.setOsUser(rs.getString("osUser"));
                sqlStatVO.setElapsedTime(rs.getInt("elapsedTime"));
                sqlStatVO.setCpuTime(rs.getInt("cpuTime"));
                sqlStatVO.setWaitTime(rs.getInt("waitTime"));
                sqlStatVO.setLogicalReads(rs.getInt("logicalReads"));
                sqlStatVO.setPhysicalReads(rs.getInt("physicalReads"));
                sqlStatVO.setRedoSize(rs.getInt("redoSize"));
                sqlStatVO.setExecutionCount(rs.getInt("executionCount"));
                sqlStatVO.setSortDisk(rs.getInt("sortDisk"));
                sqlStatVO.setSortRows(rs.getInt("sortRows"));
                sqlStatVO.setTableFetchByRowId(rs.getInt("tableFetchByRowId"));
                sqlStatVO.setTableFetchContinuedByRowId(rs.getInt("tableFetchContinuedByRowId"));
                sqlStatVO.setTableScanBlocksGotten(rs.getInt("tableScanBlocksGotten"));
                sqlStatVO.setTableScanRowsGotten(rs.getInt("tableScanRowsGotten"));

                sqlStatVOS.add(sqlStatVO);
            }

            MessageVO sendMessageVO = new MessageVO("SqlStatHandler", sqlStatVOS);

            oos.writeObject(sendMessageVO);
            oos.flush();
        } catch (Exception e) {
            myLogger.error(e.getMessage());
        } finally {
            OracleCloser.oracleCloseAll(rs, stmt, pstmt);
        }

    }


}



