package com.exem.util.vo.dbconn;

import java.sql.Connection;
import java.time.LocalDateTime;

public class ConnectionVO {

    private LocalDateTime time;
    private Connection conn;

    public ConnectionVO() {
    }

    public ConnectionVO(LocalDateTime time, Connection conn) {
        this.time = time;
        this.conn = conn;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }
}
