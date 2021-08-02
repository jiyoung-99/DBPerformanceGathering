package com.skylar.util.vo.message;

import java.time.LocalDateTime;

public class ValueObject {

    protected int serverId;
    protected LocalDateTime voTime;

    public ValueObject() {
    }

    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    public LocalDateTime getVoTime() {
        return voTime;
    }

    public void setVoTime(LocalDateTime voTime) {
        this.voTime = voTime;
    }


}
