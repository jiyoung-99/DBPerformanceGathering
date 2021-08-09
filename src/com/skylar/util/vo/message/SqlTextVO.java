package com.exem.util.vo.message;

import java.io.Serializable;


public class SqlTextVO extends ValueObject implements Serializable {

    private Integer partitionKey;
    private String sqlId;
    private Integer piece;
    private String sqlText;
    private String uuid;
    private String time;

    public SqlTextVO() {
    }

    public SqlTextVO(Integer partitionKey, String sqlId, Integer piece, String sqlText, String uuid, String time) {
        this.partitionKey = partitionKey;
        this.sqlId = sqlId;
        this.piece = piece;
        this.sqlText = sqlText;
        this.uuid = uuid;
        this.time = time;
    }

    public Integer getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(Integer partitionKey) {
        this.partitionKey = partitionKey;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public Integer getPiece() {
        return piece;
    }

    public void setPiece(Integer piece) {
        this.piece = piece;
    }

    public String getSqlText() {
        return sqlText;
    }

    public void setSqlText(String sqlText) {
        this.sqlText = sqlText;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "SqlTextVO{" +
                "partitionKey=" + partitionKey +
                ", sqlId='" + sqlId + '\'' +
                ", piece=" + piece +
                ", sqlText='" + sqlText + '\'' +
                ", uuid='" + uuid + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
