package com.exem.server.handler.statHandler;

import com.exem.server.connection.PostgreConnectionPool;
import com.exem.server.handler.StatHandler;
import com.exem.util.dbQuery.ServerQuery;
import com.exem.util.dbconn.PostgresCloser;
import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.dbconn.ConnectionVO;
import com.exem.util.vo.manage.SqlIdInMemoryVO;
import com.exem.util.vo.message.SqlTextVO;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.List;

import static com.exem.server.Server.*;


public class SqlTextHandler extends StatHandler<SqlTextVO> {

    @Override
    public void apply(List<SqlTextVO> valueObject) {
        insertSqlText(valueObject);
    }

    public void insertSqlText(List<SqlTextVO> valueObject) {

        MyLogger myLogger = LoggerFactory.getLogger(SqlTextHandler.class.getSimpleName());

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String uuid = null;

        try {
            PostgreConnectionPool pool = PostgreConnectionPool.getInstance();
            ConnectionVO usingConnection = pool.getConnection();
            conn = usingConnection.getConn();
            pstmt = conn.prepareStatement(ServerQuery.INSERT_SQL_TEXT);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss");
            SqlIdInMemoryVO sqlIdInMemoryVO = new SqlIdInMemoryVO();
            for (SqlTextVO vo : valueObject) {
                uuid = vo.getUuid();
                sqlIdInMemoryVO.setSqlId(vo.getSqlId());;
                sqlIdInMemoryVO.setTime(Timestamp.valueOf(vo.getTime()));
                pstmt.setInt(1, vo.getPartitionKey());
                pstmt.setString(2, vo.getSqlId());
                pstmt.setInt(3, vo.getPiece());
                pstmt.setString(4, vo.getSqlText());
                pstmt.setString(5, vo.getTime());
                pstmt.executeUpdate();
            }
            sqlIdInMemory.add(sqlIdInMemoryVO);
            sqlIdTimeoutMap.remove(uuid);
            pool.returnConnection(usingConnection);
        } catch (SQLException e) {
            myLogger.error(e.getMessage());
        }finally {
            PostgresCloser.postgresCloseAll(rs, pstmt);
        }
    }


}
