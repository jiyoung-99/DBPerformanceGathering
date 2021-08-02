package com.skylar.util.vo.manage;

import java.sql.Timestamp;

public class SqlIdInMemoryVO {

    private String sqlId;
    private Timestamp time;

    public SqlIdInMemoryVO() {
    }

    public SqlIdInMemoryVO(String sqlId, Timestamp time) {
        this.sqlId = sqlId;
        this.time = time;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SqlIdInMemoryVO{" +
                "sqlId='" + sqlId + '\'' +
                ", time=" + time +
                '}';
    }
}
