package com.exem.util.vo.message;

import java.io.Serializable;

/**
 * TABLE : ORA_DB_STAT
 *
 * 숫자로 받아오면 크기가 커질 시 오류 가능성 있어서 웬만한 것들은 String으로 설정함
 */
public class DBStatVO extends ValueObject implements Serializable {

    private Integer partitionKey;
    private Integer dbId;
    private String time;
    private Long statId;
    private Long value;
    private Long minusValue;

    public DBStatVO() {
    }

    public DBStatVO(Integer partitionKey, Integer dbId, String time, Long statId, Long value, Long minusValue) {
        this.partitionKey = partitionKey;
        this.dbId = dbId;
        this.time = time;
        this.statId = statId;
        this.value = value;
        this.minusValue = minusValue;
    }

    public Integer getPartitionKey() {
        return partitionKey;
    }

    public void setPartitionKey(Integer partitionKey) {
        this.partitionKey = partitionKey;
    }

    public Integer getDbId() {
        return dbId;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Long getStatId() {
        return statId;
    }

    public void setStatId(Long statId) {
        this.statId = statId;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Long getMinusValue() {
        return minusValue;
    }

    public void setMinusValue(Long minusValue) {
        this.minusValue = minusValue;
    }

    @Override
    public String toString() {
        return "DBStatVO{" +
                "partitionKey=" + partitionKey +
                ", dbId=" + dbId +
                ", time='" + time + '\'' +
                ", statId=" + statId +
                ", value=" + value +
                ", minusValue=" + minusValue +
                '}';
    }
}