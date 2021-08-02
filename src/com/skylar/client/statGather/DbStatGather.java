package com.skylar.client.statGather;


import com.skylar.client.connection.OracleConnectionPool;
import com.skylar.util.dbQuery.ClientQuery;
import com.skylar.util.dbconn.OracleCloser;
import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.dbconn.ConnectionVO;
import com.skylar.util.vo.message.DBStatVO;
import com.skylar.util.vo.message.MessageVO;

import java.io.ObjectOutputStream;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



/**
 * DB STAT 정보를 오라클에서 가져온 다음 서버로 보낸다.
 * @author jiyoung lim
 */
public class DbStatGather {

    private ObjectOutputStream oos;

    public DbStatGather(ObjectOutputStream oos) {
        this.oos = oos;
    }

    public void run() {

        MyLogger myLogger = LoggerFactory.getLogger(DbStatGather.class.getSimpleName());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
        List<DBStatVO> dbStatVOS = new ArrayList<DBStatVO>();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            OracleConnectionPool pool = OracleConnectionPool.getInstance();
            ConnectionVO usingConnection = pool.getConnection();
            conn = usingConnection.getConn();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(ClientQuery.SELECT_DB_STAT);
            pool.returnConnection(usingConnection);
            String partitionKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

            while (rs.next()) {
                DBStatVO dbStatVO = new DBStatVO();
                dbStatVO.setPartitionKey(Integer.parseInt(partitionKey));
                dbStatVO.setDbId(0001);
                dbStatVO.setValue(rs.getLong("value"));
                dbStatVO.setTime(simpleDateFormat.format(now));
                dbStatVO.setStatId(rs.getLong("statId"));
                dbStatVOS.add(dbStatVO);
            }
            MessageVO sendMessageVO = new MessageVO("DbStatHandler", dbStatVOS);
            oos.writeObject(sendMessageVO);
            oos.flush();

        } catch (Exception e) {
            myLogger.error(e.getMessage());
        } finally {
            OracleCloser.oracleCloseAll(rs, stmt);
        }
    }
}

