package com.skylar.util.vo.message;


import java.io.Serializable;

/**
 * TABLE : ORA_SESSION_STAT
 */
public class SessionStatVO extends ValueObject implements Serializable {

    private Integer partitionKey;
    private Integer dbId;
    private String time;
    private Integer sId;
    private String logonTime;
    private Integer serial;
    private String status;
    private String taddr;
    private Integer rowWaitFile;
    private Integer rowWaitBlock;
    private Integer rowWaitRow;
    private Integer rowWaitObject;
    private String schemaName;
    private String module;
    private String action;
    private String clientInfo;
    private Integer commandType;
    private String sqlAddr;
    private Long sqlHash;
    private String sqlId;
    private String prevSqlAddr;
    private Long prevSqlHash;
    private String prevSqlId;

    public SessionStatVO() {
    }

    public SessionStatVO(Integer partitionKey, Integer dbId, String time, Integer sId, String logonTime, Integer serial, String status, String taddr, Integer rowWaitFile, Integer rowWaitBlock, Integer rowWaitRow, Integer rowWaitObject, String schemaName, String module, String action, String clientInfo, Integer commandType, String sqlAddr, Long sqlHash, String sqlId, String prevSqlAddr, Long prevSqlHash, String prevSqlId) {
        this.partitionKey = partitionKey;
        this.dbId = dbId;
        this.time = time;
        this.sId = sId;
        this.logonTime = logonTime;
        this.serial = serial;
        this.status = status;
        this.taddr = taddr;
        this.rowWaitFile = rowWaitFile;
        this.rowWaitBlock = rowWaitBlock;
        this.rowWaitRow = rowWaitRow;
        this.rowWaitObject = rowWaitObject;
        this.schemaName = schemaName;
        this.module = module;
        this.action = action;
        this.clientInfo = clientInfo;
        this.commandType = commandType;
        this.sqlAddr = sqlAddr;
        this.sqlHash = sqlHash;
        this.sqlId = sqlId;
        this.prevSqlAddr = prevSqlAddr;
        this.prevSqlHash = prevSqlHash;
        this.prevSqlId = prevSqlId;
    }

    public Integer getPartitionKey() {
        return partitionKey;
    }

    public Integer getDbId() {
        return dbId;
    }

    public String getTime() {
        return time;
    }

    public Integer getsId() {
        return sId;
    }

    public String getLogonTime() {
        return logonTime;
    }

    public Integer getSerial() {
        return serial;
    }

    public String getStatus() {
        return status;
    }

    public String getTaddr() {
        return taddr;
    }

    public Integer getRowWaitFile() {
        return rowWaitFile;
    }

    public Integer getRowWaitBlock() {
        return rowWaitBlock;
    }

    public Integer getRowWaitRow() {
        return rowWaitRow;
    }

    public Integer getRowWaitObject() {
        return rowWaitObject;
    }

    public String getSchemaName() {
        return schemaName;
    }

    public String getModule() {
        return module;
    }

    public String getAction() {
        return action;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public Integer getCommandType() {
        return commandType;
    }

    public String getSqlAddr() {
        return sqlAddr;
    }

    public Long getSqlHash() {
        return sqlHash;
    }

    public String getSqlId() {
        return sqlId;
    }

    public String getPrevSqlAddr() {
        return prevSqlAddr;
    }

    public Long getPrevSqlHash() {
        return prevSqlHash;
    }

    public String getPrevSqlId() {
        return prevSqlId;
    }

    public void setPartitionKey(Integer partitionKey) {
        this.partitionKey = partitionKey;
    }

    public void setDbId(Integer dbId) {
        this.dbId = dbId;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public void setLogonTime(String logonTime) {
        this.logonTime = logonTime;
    }

    public void setSerial(Integer serial) {
        this.serial = serial;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTaddr(String taddr) {
        this.taddr = taddr;
    }

    public void setRowWaitFile(Integer rowWaitFile) {
        this.rowWaitFile = rowWaitFile;
    }

    public void setRowWaitBlock(Integer rowWaitBlock) {
        this.rowWaitBlock = rowWaitBlock;
    }

    public void setRowWaitRow(Integer rowWaitRow) {
        this.rowWaitRow = rowWaitRow;
    }

    public void setRowWaitObject(Integer rowWaitObject) {
        this.rowWaitObject = rowWaitObject;
    }

    public void setSchemaName(String schemaName) {
        this.schemaName = schemaName;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public void setCommandType(Integer commandType) {
        this.commandType = commandType;
    }

    public void setSqlAddr(String sqlAddr) {
        this.sqlAddr = sqlAddr;
    }

    public void setSqlHash(Long sqlHash) {
        this.sqlHash = sqlHash;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public void setPrevSqlAddr(String prevSqlAddr) {
        this.prevSqlAddr = prevSqlAddr;
    }

    public void setPrevSqlHash(Long prevSqlHash) {
        this.prevSqlHash = prevSqlHash;
    }

    public void setPrevSqlId(String prevSqlId) {
        this.prevSqlId = prevSqlId;
    }

    @Override
    public String toString() {
        return "SessionStatVO{" +
                "partitionKey=" + partitionKey +
                ", dbId=" + dbId +
                ", time=" + time +
                ", sId=" + sId +
                ", logonTime=" + logonTime +
                ", serial=" + serial +
                ", status=" + status +
                ", taddr=" + taddr +
                ", rowWaitFile=" + rowWaitFile +
                ", rowWaitBlock=" + rowWaitBlock +
                ", rowWaitRow=" + rowWaitRow +
                ", rowWaitObject=" + rowWaitObject +
                ", schemaName='" + schemaName + '\'' +
                ", module='" + module + '\'' +
                ", action='" + action + '\'' +
                ", clientInfo='" + clientInfo + '\'' +
                ", commandType=" + commandType +
                ", sqlAddr='" + sqlAddr + '\'' +
                ", sqlHash=" + sqlHash +
                ", sqlId='" + sqlId + '\'' +
                ", prevSqlAddr='" + prevSqlAddr + '\'' +
                ", prevSqlHash=" + prevSqlHash +
                ", prevSqlId='" + prevSqlId + '\'' +
                '}';
    }
}











