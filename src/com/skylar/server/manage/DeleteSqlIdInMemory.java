package com.skylar.server.manage;

import com.skylar.util.vo.manage.SqlIdInMemoryVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import static com.skylar.server.Server.sqlIdInMemory;

public class DeleteSqlIdInMemory extends TimerTask {


    /**
     * 매 시간 정각마다 실행된다.
     * 하루가 지나면 삭제된다.
     */
    @Override
    public void run() {

        Date now = new Date();
        List<String> sqlIds = new ArrayList<>();

        for (SqlIdInMemoryVO vo : sqlIdInMemory) {
            int compare = now.compareTo(vo.getTime());
            if (compare > 1) {
                sqlIds.add(vo.getSqlId());
            }
        }

        deleteSqlIdInMemory(sqlIds);
    }

    /**
     * 메모리에 있는 sql id 삭제하는 메소드
     *
     * @param sqlIds
     */
    public void deleteSqlIdInMemory(List<String> sqlIds) {

        for (String sqlid : sqlIds) {
            sqlIdInMemory.remove(sqlid);
        }

    }
}
