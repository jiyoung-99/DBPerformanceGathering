package com.exem.server.connection;

import com.exem.server.handler.InputStreamServerHandler;
import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.db.OracleDBVO;
import com.exem.util.vo.db.PostgreDBVO;
import com.exem.util.vo.dbconn.ConnectionVO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;


/**
 * Postgres SQL
 * Connection Pool
 */
public class PostgreConnectionPool {

    private static LinkedList<ConnectionVO> connections = new LinkedList<>();           //기본 생성 커넥션
    private static LinkedList<ConnectionVO> usedConnections = new LinkedList<>();    //사용한 커넥션 모아두는 맵
    private static final int MAX_CONNECTIONS = 20;                            //최대 갯수
    private static final int MIN_IDLE_CONNECTIONS = 10;                       //최소 유지 갯수
    private static PostgreConnectionPool instance = null;

    public MyLogger myLogger = LoggerFactory.getLogger(PostgreConnectionPool.class.getSimpleName());

    static public PostgreConnectionPool getInstance() {
        if (instance == null) {
            synchronized(PostgreConnectionPool.class) {
                if (instance == null) {
                    instance = new PostgreConnectionPool();
                }
            }
        }
        return instance;
    }

    public PostgreConnectionPool() { init();}

    public void init() {

        try {
            Class.forName(PostgreDBVO.POSTGRES_DRIVER);
            for (int i = 0; i < MIN_IDLE_CONNECTIONS; i++) {
                Connection conn = DriverManager.getConnection(PostgreDBVO.POSTGRES_URL, PostgreDBVO.POSTGRES_USER, PostgreDBVO.POSTGRES_PASSWORD);
                LocalDateTime now = LocalDateTime.now();
                connections.addLast(new ConnectionVO(now, conn));
            }
        } catch (ClassNotFoundException e) {
            myLogger.error("init connectPool ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            myLogger.error("init connectPoo SQLExceptionl: " + e.getMessage());
        }
    }

    /**
     * 풀에서 커넥션을 한다.
     **/
    public synchronized ConnectionVO getConnection() {
        //사이즈가 0일 때
        if (connections.size() == 0) {
            //사용한 양이 맥스 사이즈가 되지 않으면 늘리고 만약 맥스 사이즈보다 크면 기다린다.
            if(usedConnections.size() <= MAX_CONNECTIONS) {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    Connection conn = DriverManager.getConnection(OracleDBVO.ORACLE_URL, OracleDBVO.ORACLE_USER, OracleDBVO.ORACLE_PASSWORD);
                    connections.addLast(new ConnectionVO(now, conn));
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                // max 까지 썼으면 waiting
            }else {
                while (connections.size() == 0) {
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        myLogger.error(e.getMessage());
                    }
                }
            }
        }
        ConnectionVO usingConnection = connections.pollLast();
        usedConnections.addLast(usingConnection);

        return usingConnection;
    }

    /**
     * 커넥션을 풀로 돌려준다.
     **/
    public synchronized void returnConnection(ConnectionVO usingConnection) {
        LocalDateTime now = LocalDateTime.now();
        usingConnection.setTime(now);
        connections.addLast(usingConnection);
        this.notifyAll();
        organizeConnection();
    }


    //일정한 시간 내 실행
    public void organizeConnection() {
        LocalDateTime now = LocalDateTime.now();
        while(connections.size() > MIN_IDLE_CONNECTIONS) {
            ConnectionVO connectionVO = connections.pollFirst();
            try {
                connectionVO.getConn().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }
}
