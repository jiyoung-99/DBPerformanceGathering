package com.exem.util.vo.message;

import java.io.Serializable;
import java.time.LocalDateTime;

public class SqlIdVO extends ValueObject implements Serializable {

    private String sqlId;
    private String uuid;

    public SqlIdVO() {
    }

    public SqlIdVO(String sqlId, String uuid) {
        this.sqlId = sqlId;
        this.uuid = uuid;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "SqlIdVO{" +
                "sqlId='" + sqlId + '\'' +
                ", uuid='" + uuid + '\'' +
                ", serverId=" + serverId +
                ", voTime=" + voTime +
                '}';
    }
}
