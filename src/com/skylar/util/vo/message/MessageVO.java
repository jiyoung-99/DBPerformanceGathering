package com.skylar.util.vo.message;

import java.io.Serializable;
import java.util.List;

public class MessageVO<T extends ValueObject> implements Serializable {

    private String type;
    private List<T> data;

    public MessageVO() {
    }

    public MessageVO(String type, List<T> data) {
        this.type = type;
        this.data = data;
    }

    public MessageVO(String type, List<T> data, String uuid) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "MessageVO{" +
                "type='" + type + '\'' +
                ", data=" + data +
                '}';
    }
}
