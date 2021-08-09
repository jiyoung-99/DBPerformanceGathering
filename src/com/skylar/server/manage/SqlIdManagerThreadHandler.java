package com.exem.server.manage;


import com.exem.util.logger.LoggerFactory;
import com.exem.util.logger.MyLogger;
import com.exem.util.vo.message.MessageVO;
import com.exem.util.vo.message.SqlIdVO;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.exem.server.Server.*;


public class SqlIdManagerThreadHandler implements Runnable {

    public ObjectOutputStream oos;

    public SqlIdManagerThreadHandler(ObjectOutputStream oos) {
        this.oos = oos;
    }

    @Override
    public void run() {

        MyLogger myLogger = LoggerFactory.getLogger(SqlIdManagerThreadHandler.class.getSimpleName());

        while (true) {
            try {
                Thread.sleep(300);
                MessageVO<SqlIdVO> messageVO = new MessageVO<SqlIdVO>();
                List<SqlIdVO> sqlIdVOList = new ArrayList<>();
                for (int i = 0; i < sqlIdRequestQueue.size(); i++) {
                    String sqlId = sqlIdRequestQueue.poll();
                    SqlIdVO sqlIdVO = new SqlIdVO();
                    LocalDateTime now = LocalDateTime.now();
                    String uuid = UUID.randomUUID().toString();
                    sqlIdVO.setSqlId(sqlId);
                    sqlIdVO.setVoTime(now);
                    sqlIdVO.setUuid(uuid);
                    sqlIdVOList.add(sqlIdVO);
                }
                messageVO.setData(sqlIdVOList);
                oos.writeObject(messageVO);
                oos.flush();

            } catch (InterruptedException | IOException e) {
                myLogger.error(e.getMessage());
            }

        }

    }
}

