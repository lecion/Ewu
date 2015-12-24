package com.yliec.ewu.api.entity;

import com.yliec.ewu.api.entity.element.Status;

/**
 * Created by Lecion on 12/24/15.
 */
public class BaseEntity<Data> {
    Status status;

    Data data;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}
