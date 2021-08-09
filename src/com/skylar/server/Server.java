package com.exem.server;


import com.exem.server.connection.PostgreConnectionPool;
import com.exem.server.handler.InputStreamServerHandler;
import com.exem.server.manage.*;
import com.exem.util.alarm.WebhookSender;
import com.exem.util.alarm.enums.ConnectColor;
import com.exem.util.alarm.enums.WebhookUrl;
import com.exem.util.alarm.form.ConnectInfo;
import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.LoggerLevel;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.manage.SqlIdInMemoryVO;
import com.exem.util.vo.socket.SocketVO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;


/**
 * 서버
 * <p>
 * 클라이언트에서 오라클 성능 데이터를 수집하여서
 * postgre sql 안에 저장한다.
 */
public class Server {

    //sql id 를 계속 넣음
    public static Queue<String> sqlIdRequestQueue = new LinkedList<>();
    // 메모리에 있는 sql id
    public static Queue<SqlIdInMemoryVO> sqlIdInMemory = new LinkedList<>();
    // sql id timeout 난 것을 저장하는 맵
    public static Map<String, SqlIdInMemoryVO> sqlIdTimeoutMap = new HashMap<>();
    // db stat 정보에서 예전 값을 기억하는 맵
    public static Map<String, Long> prevDbStatInMemory = new HashMap<>();
    //db stat 정보에서 값들의 차를 기억하는 맵
    public static Map<String, LinkedList<Long>> dbStatInMemory = new HashMap<>();

    // main 함수에서 실행되는 메소드
    public Server() {


        LoggerFactory.setLoggerLevel(LoggerLevel.TRACE);
        MyLogger myLogger = LoggerFactory.getLogger(Server.class.getSimpleName());

        ServerSocket serverSocket = null;
        Socket socket = null;
        ObjectInputStream ois = null;
        ObjectOutputStream oos = null;

        /**
         * 2주차 : 메모리에 sql id 저장하는 클래스
         */
        //기본 sql_id 를 현재 시간과 함께 메모리에 저장한다.
        InsertSqlIdInMemory sqlIdInMemoryClass = new InsertSqlIdInMemory();
        sqlIdInMemoryClass.insertSqlIdInQueue();

        //sql id 를 정각마다 삭제하는 타이머
        Timer deleteSqlIdTimer = new Timer();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        DeleteSqlIdInMemory deleteSqlIdInMemory = new DeleteSqlIdInMemory();
        deleteSqlIdTimer.schedule(deleteSqlIdInMemory, calendar.getTime());
        //타임아웃 된 sql id 를 보여주는 타이머, 10초
        Timer getSqlIdTimeoutTimer = new Timer();
        SqlIdTimeoutManager sqlIdTimeoutManager = new SqlIdTimeoutManager();
        getSqlIdTimeoutTimer.schedule(sqlIdTimeoutManager, 10000, 10000);

        /**
         * STEP 1
         *
         * 다수의 클라이언트에서 받은 정보들을
         * 순서대로 처리한다.
         */
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(SocketVO.HOST_NAME, SocketVO.SOCKET_PORT));
            myLogger.trace("START SERVER!!");

            /**
             * STEP 2
             *
             * 소켓으로 받은 데이터를 쓰레드 풀을 이용해서 순차적으로 처리한다.
             */
            while (true) {
                socket = serverSocket.accept();
                ois = new ObjectInputStream(socket.getInputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                InputStreamServerHandler handler = new InputStreamServerHandler(ois);
                new Thread(handler).start();
                //기존 테이블에 저장이 되어있지 않은 Sql id를 찾아 정보를 갖고오는 핸들러
                SqlIdManagerThreadHandler sqlIdManageThreadHandler = new SqlIdManagerThreadHandler(oos);
                new Thread(sqlIdManageThreadHandler).start();
                //db stat insert into 10min
                InsertDbStatInDb insertDbStatInDbThread = new InsertDbStatInDb();
                insertDbStatInDbThread.start();
            }
        } catch (Exception e) {
            myLogger.error(e.getMessage());
        } finally {
            try {
                ois.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
            try {
                oos.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
            try {
                socket.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
            try {
                serverSocket.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
        }

    }

    public static void main(String[] args) {
        new Server();
    }

}
