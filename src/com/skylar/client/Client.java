package com.skylar.client;


import com.skylar.client.manage.SqlIdManager;
import com.skylar.client.statGather.DbStatGather;
import com.skylar.client.statGather.SessionStatGather;
import com.skylar.client.statGather.SqlStatGather;
import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.LoggerLevel;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.socket.SocketVO;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;



/**
 * :::::::Client Main 함수:::::::
 * <p>
 * -DbStatGather
 * -SessionStatGather
 * -SqlStatGather
 * -SqlIdManager
 * 위 클래스에서 정보를 받아서 서버에게 주는
 * 클라이언트 메인 함수
 *
 * @author jiyoung lim
 */
public class Client {

    public Client() {

        LoggerFactory.setLoggerLevel(LoggerLevel.TRACE);
        MyLogger myLogger = LoggerFactory.getLogger(Client.class.getSimpleName());

        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {

            socket = new Socket();
            socket.connect(new InetSocketAddress(SocketVO.HOST_NAME, SocketVO.SOCKET_PORT));
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            DbStatGather dbStatGather = new DbStatGather(oos);
            SessionStatGather sessionStatGather = new SessionStatGather(oos);
            SqlStatGather sqlStatGather = new SqlStatGather(oos);
            SqlIdManager sqlIdManage = new SqlIdManager(ois, oos);
            myLogger.trace("START CLIENT !!");

            while (true) {
                Thread.sleep(3000);
                dbStatGather.run();
                sessionStatGather.run();
                sqlStatGather.run();
                sqlIdManage.run();
            }
        } catch (Exception e) {
            myLogger.error(e.getMessage());
        } finally {
            try {
                oos.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
            try {
                ois.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
            try {
                socket.close();
            } catch (IOException e) {
                myLogger.error(e.getMessage());
            }
        }

    }

    /**
     * MAIN
     */
    public static void main(String[] args) {
        new Client();
    }
}
