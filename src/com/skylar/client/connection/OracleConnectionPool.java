package com.skylar.client.connection;

import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.db.OracleDBVO;
import com.skylar.util.vo.dbconn.ConnectionVO;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Oracle SQL
 * Connection Pool
 * 풀링 기법
 * - 미리 여러 개의 데이터 베이스 커넥션을 생성해서 보관한다.
 * - 커넥션이 필요할 때마다 커넥션 풀에서 하나씩 꺼내서 사용하고 사용이 끝나면 다시 커넥션을 풀로 보내 보관한다.
 * - 커넥션 풀이 데이터베이스의 연결과 해제를 직접 관리한다.
 **/
public class OracleConnectionPool {

    private static Stack<ConnectionVO> connections = new Stack<>();             //기본 생성 커넥션
    private static List<ConnectionVO> usedConnections = new ArrayList<>();    //사용한 커넥션 모아두는 맵
    private static final int MAX_CONNECTIONS = 20;                        //최대 갯수
    private static final int MIN_IDLE_CONNECTIONS = 10;                   //최소로 유지되는 갯수
    private static OracleConnectionPool instance = null;
    /**
     * max -> 제일 많이 나오는 것
     * idle -> 최소 유지 갯수
     * 일정한 시간이 지나면 key, value에서 idle 로 ㄴ옴
     */

    public MyLogger myLogger = LoggerFactory.getLogger(OracleConnectionPool.class.getSimpleName());

    //singleton
    static public OracleConnectionPool getInstance() {
        if (instance == null) {
            synchronized (OracleConnectionPool.class) {
                if (instance == null) {
                    instance = new OracleConnectionPool();
                }
            }
        }
        return instance;
    }

    public OracleConnectionPool() {
        init();
    }

    public void init() {
        try {
            Class.forName(OracleDBVO.ORACLE_DRIVER);
            for (int i = 0; i < MIN_IDLE_CONNECTIONS; i++) {
                Connection conn = DriverManager.getConnection(OracleDBVO.ORACLE_URL, OracleDBVO.ORACLE_USER, OracleDBVO.ORACLE_PASSWORD);
                LocalDateTime now = LocalDateTime.now();
                connections.add(new ConnectionVO(now, conn));
            }
        } catch (ClassNotFoundException e) {
            myLogger.error("init connectPool ClassNotFoundException: " + e.getMessage());
        } catch (SQLException e) {
            myLogger.error("init connectPool SQLException: " +e.getMessage());
        }
    }

    /**
     * 풀에서 커넥션을 한다.
     * 스레드의 실행을 제어하는 기술을 스레드 동기화(thread synchronization)라고 부른다.
     * 방법 1 : synchronized로 동기화 블럭 지정
     * 방법 2 : wait()-notify(), notifyall() 메서드로 스레드 실행순서를 제어
     **/
    public synchronized ConnectionVO getConnection() {

        //사이즈가 0일 때
        if (connections.size() == 0) {
            //사용한 양이 맥스 사이즈가 되지 않으면 늘리고 만약 맥스 사이즈보다 크면 기다린다.
            if(usedConnections.size() <= MAX_CONNECTIONS) {
                try {
                    LocalDateTime now = LocalDateTime.now();
                    Connection conn = DriverManager.getConnection(OracleDBVO.ORACLE_URL, OracleDBVO.ORACLE_USER, OracleDBVO.ORACLE_PASSWORD);
                    connections.add(new ConnectionVO(now, conn));
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
        ConnectionVO usingConnection = connections.pop();
        usedConnections.add(usingConnection);

        return usingConnection;
    }

    /**
     * 커넥션을 풀로 돌려준다.
     **/
    public synchronized void returnConnection(ConnectionVO usingConnection) {
        LocalDateTime now = LocalDateTime.now();
        usingConnection.setTime(now);
        connections.add(usingConnection);
        this.notifyAll();
        organizeConnection();
    }

    //일정한 시간 내 실행
    public void organizeConnection(){
        LocalDateTime now = LocalDateTime.now();
        while(connections.size() > MIN_IDLE_CONNECTIONS) {
            ConnectionVO connectionVO = connections.pop();
            try {
                connectionVO.getConn().close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

    }

}
