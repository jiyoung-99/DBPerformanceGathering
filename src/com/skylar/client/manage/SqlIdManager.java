package com.skylar.client.manage;


import com.skylar.client.connection.OracleConnectionPool;
import com.skylar.util.dbQuery.ClientQuery;
import com.skylar.util.dbconn.OracleCloser;
import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.dbconn.ConnectionVO;
import com.skylar.util.vo.message.MessageVO;
import com.skylar.util.vo.message.SqlIdVO;
import com.skylar.util.vo.message.SqlTextVO;

import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * 클라이언트에서 SQL_ID를 요청했을 때 오라클에서 가져온다.
 *
 * @author jiyoung lim
 */
public class SqlIdManager {

    private ObjectInputStream ois;
    private ObjectOutputStream oos;

    public SqlIdManager(ObjectInputStream ois, ObjectOutputStream oos) {
        this.ois = ois;
        this.oos = oos;
    }

    public void run() throws IOException {

        MyLogger myLogger = LoggerFactory.getLogger(SqlIdManager.class.getSimpleName());

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        List<SqlTextVO> sqlTextVOS = new ArrayList<>();

        try {
            MessageVO getMessageVO = (MessageVO) ois.readObject();
            List<SqlIdVO> sqlIdVOList = getMessageVO.getData();
            for (SqlIdVO vo : sqlIdVOList) {
                OracleConnectionPool pool = OracleConnectionPool.getInstance();
                ConnectionVO usingConnection = pool.getConnection();
                conn = usingConnection.getConn();
                pstmt = conn.prepareStatement(ClientQuery.SELECT_SQL_TEXT);
                String sqlId = vo.getSqlId();
                pstmt.setString(1, sqlId);
                pool.returnConnection(usingConnection);
                String partitionKey = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
                rs = pstmt.executeQuery();
                while (rs.next()) {
                    SqlTextVO sqlTextVO = new SqlTextVO();
                    sqlTextVO.setPartitionKey(Integer.parseInt(partitionKey));
                    sqlTextVO.setSqlId(rs.getString("sqlId"));
                    sqlTextVO.setPiece(rs.getInt("piece"));
                    sqlTextVO.setSqlText(rs.getString("sqlText"));
                    sqlTextVO.setUuid(vo.getUuid());
                    sqlTextVO.setTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                    sqlTextVOS.add(sqlTextVO);
                }

                MessageVO sendMessageVO = new MessageVO("SqlTextHandler", sqlTextVOS);
                oos.writeObject(sendMessageVO);
                oos.flush();

            }

        } catch (SQLException | ClassNotFoundException e) {
            myLogger.error(e.getMessage());
        } finally {
            OracleCloser.oracleCloseAll(rs, pstmt);
        }

    }
}
