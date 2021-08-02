package com.skylar.util.vo.message;

import java.io.Serializable;

/**
 * TABLE : ORA_SQL_STAT
 */
public class SQLStatVO extends ValueObject implements Serializable {

    private Integer partitionKey;
    private Integer dbId;
    private String time;
    private String sqlAddr;
    private Long sqlHash;
    private String sqlId;
    private Long sqlPlanHash;
    private String userName;
    private String program;
    private String module;
    private String action;
    private String machine;
    private String osUser;
    private Integer elapsedTime;
    private Integer cpuTime;
    private Integer waitTime;
    private Integer logicalReads;
    private Integer physicalReads;
    private Integer redoSize;
    private Integer executionCount;
    private Integer sortDisk;
    private Integer sortRows;
    private Integer tableFetchByRowId;
    private Integer tableFetchContinuedByRowId;
    private Integer tableScanBlocksGotten;
    private Integer tableScanRowsGotten;

    public SQLStatVO() {

    }

    public SQLStatVO(Integer partitionKey, Integer dbId, String time, String sqlAddr, Long sqlHash, String sqlId, Long sqlPlanHash, String userName, String program, String module, String action, String machine, String osUser, Integer elapsedTime, Integer cpuTime, Integer waitTime, Integer logicalReads, Integer physicalReads, Integer redoSize, Integer executionCount, Integer sortDisk, Integer sortRows, Integer tableFetchByRowId, Integer tableFetchContinuedByRowId, Integer tableScanBlocksGotten, Integer tableScanRowsGotten) {
        this.partitionKey = partitionKey;
        this.dbId = dbId;
        this.time = time;
        this.sqlAddr = sqlAddr;
        this.sqlHash = sqlHash;
        this.sqlId = sqlId;
        this.sqlPlanHash = sqlPlanHash;
        this.userName = userName;
        this.program = program;
        this.module = module;
        this.action = action;
        this.machine = machine;
        this.osUser = osUser;
        this.elapsedTime = elapsedTime;
        this.cpuTime = cpuTime;
        this.waitTime = waitTime;
        this.logicalReads = logicalReads;
        this.physicalReads = physicalReads;
        this.redoSize = redoSize;
        this.executionCount = executionCount;
        this.sortDisk = sortDisk;
        this.sortRows = sortRows;
        this.tableFetchByRowId = tableFetchByRowId;
        this.tableFetchContinuedByRowId = tableFetchContinuedByRowId;
        this.tableScanBlocksGotten = tableScanBlocksGotten;
        this.tableScanRowsGotten = tableScanRowsGotten;
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

    public String getSqlAddr() {
        return sqlAddr;
    }

    public Long getSqlHash() {
        return sqlHash;
    }

    public String getSqlId() {
        return sqlId;
    }

    public Long getSqlPlanHash() {
        return sqlPlanHash;
    }

    public String getUserName() {
        return userName;
    }

    public String getProgram() {
        return program;
    }

    public String getModule() {
        return module;
    }

    public String getAction() {
        return action;
    }

    public String getMachine() {
        return machine;
    }

    public String getOsUser() {
        return osUser;
    }

    public Integer getElapsedTime() {
        return elapsedTime;
    }

    public Integer getCpuTime() {
        return cpuTime;
    }

    public Integer getWaitTime() {
        return waitTime;
    }

    public Integer getLogicalReads() {
        return logicalReads;
    }

    public Integer getPhysicalReads() {
        return physicalReads;
    }

    public Integer getRedoSize() {
        return redoSize;
    }

    public Integer getExecutionCount() {
        return executionCount;
    }

    public Integer getSortDisk() {
        return sortDisk;
    }

    public Integer getSortRows() {
        return sortRows;
    }

    public Integer getTableFetchByRowId() {
        return tableFetchByRowId;
    }

    public Integer getTableFetchContinuedByRowId() {
        return tableFetchContinuedByRowId;
    }

    public Integer getTableScanBlocksGotten() {
        return tableScanBlocksGotten;
    }

    public Integer getTableScanRowsGotten() {
        return tableScanRowsGotten;
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

    public void setSqlAddr(String sqlAddr) {
        this.sqlAddr = sqlAddr;
    }

    public void setSqlHash(Long sqlHash) {
        this.sqlHash = sqlHash;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    public void setSqlPlanHash(Long sqlPlanHash) {
        this.sqlPlanHash = sqlPlanHash;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setMachine(String machine) {
        this.machine = machine;
    }

    public void setOsUser(String osUser) {
        this.osUser = osUser;
    }

    public void setElapsedTime(Integer elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public void setCpuTime(Integer cpuTime) {
        this.cpuTime = cpuTime;
    }

    public void setWaitTime(Integer waitTime) {
        this.waitTime = waitTime;
    }

    public void setLogicalReads(Integer logicalReads) {
        this.logicalReads = logicalReads;
    }

    public void setPhysicalReads(Integer physicalReads) {
        this.physicalReads = physicalReads;
    }

    public void setRedoSize(Integer redoSize) {
        this.redoSize = redoSize;
    }

    public void setExecutionCount(Integer executionCount) {
        this.executionCount = executionCount;
    }

    public void setSortDisk(Integer sortDisk) {
        this.sortDisk = sortDisk;
    }

    public void setSortRows(Integer sortRows) {
        this.sortRows = sortRows;
    }

    public void setTableFetchByRowId(Integer tableFetchByRowId) {
        this.tableFetchByRowId = tableFetchByRowId;
    }

    public void setTableFetchContinuedByRowId(Integer tableFetchContinuedByRowId) {
        this.tableFetchContinuedByRowId = tableFetchContinuedByRowId;
    }

    public void setTableScanBlocksGotten(Integer tableScanBlocksGotten) {
        this.tableScanBlocksGotten = tableScanBlocksGotten;
    }

    public void setTableScanRowsGotten(Integer tableScanRowsGotten) {
        this.tableScanRowsGotten = tableScanRowsGotten;
    }

    @Override
    public String toString() {
        return "SQLStatVO{" +
                "partitionKey=" + partitionKey +
                ", dbId=" + dbId +
                ", time=" + time +
                ", sqlAddr='" + sqlAddr + '\'' +
                ", sqlHash=" + sqlHash +
                ", sqlId='" + sqlId + '\'' +
                ", sqlPlanHash=" + sqlPlanHash +
                ", userName='" + userName + '\'' +
                ", program='" + program + '\'' +
                ", module='" + module + '\'' +
                ", action='" + action + '\'' +
                ", machine='" + machine + '\'' +
                ", osUser='" + osUser + '\'' +
                ", elapsedTime=" + elapsedTime +
                ", cpuTime=" + cpuTime +
                ", waitTime=" + waitTime +
                ", logicalReads=" + logicalReads +
                ", physicalReads=" + physicalReads +
                ", redoSize=" + redoSize +
                ", executionCount=" + executionCount +
                ", sortDisk=" + sortDisk +
                ", sortRows=" + sortRows +
                ", tableFetchByRowId=" + tableFetchByRowId +
                ", tableFetchContinuedByRowId=" + tableFetchContinuedByRowId +
                ", tableScanBlocksGotten=" + tableScanBlocksGotten +
                ", tableScanRowsGotten=" + tableScanRowsGotten +
                '}';
    }
}
