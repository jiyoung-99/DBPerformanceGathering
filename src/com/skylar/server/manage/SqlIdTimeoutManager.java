package com.skylar.server.manage;

import com.skylar.util.logger.LoggerFactory;
import com.skylar.util.logger.MyLogger;
import com.skylar.util.vo.manage.SqlIdInMemoryVO;

import java.sql.Timestamp;
import java.util.Map;
import java.util.TimerTask;

import static com.skylar.server.Server.sqlIdTimeoutMap;

public class SqlIdTimeoutManager extends TimerTask {


    @Override
    public void run() {

        MyLogger myLogger = LoggerFactory.getLogger(SqlIdTimeoutManager.class.getSimpleName());

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String uuid = null;
        for (Map.Entry<String, SqlIdInMemoryVO> entry : sqlIdTimeoutMap.entrySet()) {
            SqlIdInMemoryVO timeoutMapValues = entry.getValue();
            Timestamp requestTime = timeoutMapValues.getTime();
            Timestamp standardTime = new Timestamp(requestTime.getTime() - 60000);
            if (requestTime.before(standardTime)) {
                uuid = entry.getKey();
                myLogger.warn("[TIME OUT 발생] UUID : " + uuid + "    SQL ID : + "+entry.getValue()+ "[요청시간] : " + entry.getValue() + "       [현재시간] : " + now);
                break;
            }

        }
        sqlIdTimeoutMap.remove(uuid);
    }
}
