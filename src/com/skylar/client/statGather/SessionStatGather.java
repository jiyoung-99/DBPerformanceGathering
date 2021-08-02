package com.skylar.client.statGather;


import com.skylar.client.connection.OracleConnectionPool;
import com.skylar.util.dbQuery.ClientQuery;
import com.skylar.util.dbconn.OracleCloser;
import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.dbconn.ConnectionVO;
import com.skylar.util.vo.message.SessionStatVO;
import com.skylar.util.vo.message.MessageVO;

import java.io.ObjectOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



/**
 * SESSION STAT 정보를 오라클에서 가져온 다음 서버로 보낸다.
 * @author jiyoung lim
 */
public class SessionStatGather {

    private ObjectOutputStream oos;

    public SessionStatGather(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void run() {

        MyLogger myLogger = LoggerFactory.getLogger(SessionStatGather.class.getSimpleName());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        List<SessionStatVO> sessionStatVOS = new ArrayList<SessionStatVO>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;

        try {

            OracleConnectionPool pool = OracleConnectionPool.getInstance();
            ConnectionVO usingConnection = pool.getConnection();
            conn = usingConnection.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(ClientQuery.SELECT_SESSION_STAT);
            pool.returnConnection(usingConnection);
            String partitionKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

            while (rs.next()) {
                SessionStatVO sessionStatVO = new SessionStatVO();
                sessionStatVO.setsId(rs.getInt("sid"));
                sessionStatVO.setPartitionKey(Integer.parseInt(partitionKey));
                sessionStatVO.setDbId(0001);
                sessionStatVO.setTime(simpleDateFormat.format(now));
                sessionStatVO.setLogonTime(rs.getString("logonTime"));
                sessionStatVO.setSerial(rs.getInt("serial"));
                sessionStatVO.setStatus(rs.getString("status"));
                sessionStatVO.setStatus(rs.getString("status"));
                sessionStatVO.setTaddr(rs.getString("taddr"));
                sessionStatVO.setRowWaitFile(rs.getInt("rowWaitFile"));
                sessionStatVO.setRowWaitBlock(rs.getInt("rowWaitBlock"));
                sessionStatVO.setRowWaitRow(rs.getInt("rowWaitRow"));
                sessionStatVO.setRowWaitObject(rs.getInt("rowWaitObject"));
                sessionStatVO.setSchemaName(rs.getString("schemaName"));
                sessionStatVO.setModule(rs.getString("module"));
                sessionStatVO.setAction(rs.getString("action"));
                sessionStatVO.setClientInfo(rs.getString("clientInfo"));
                sessionStatVO.setCommandType(rs.getInt("commandType"));
                sessionStatVO.setSqlAddr(rs.getString("sqlAddr"));
                sessionStatVO.setSqlHash(rs.getLong("sqlHash"));
                sessionStatVO.setSqlId(rs.getString("sqlId"));
                sessionStatVO.setPrevSqlAddr(rs.getString("prevSqlAddr"));
                sessionStatVO.setPrevSqlHash(rs.getLong("prevSqlHash"));
                sessionStatVO.setSqlId(rs.getString("prevSqlId"));
                sessionStatVOS.add(sessionStatVO);
            }

            MessageVO sendMessageVO = new MessageVO("SessionStatHandler", sessionStatVOS);
            oos.writeObject(sendMessageVO);
            oos.flush();

        } catch (Exception e) {
            myLogger.error(e.getMessage());
        } finally {
            OracleCloser.oracleCloseAll(rs, stmt);
        }

    }
}
