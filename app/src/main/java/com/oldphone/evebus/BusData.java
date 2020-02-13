package com.oldphone.evebus;

public class BusData {
    private Object data;
    private EveType type;

    public BusData(Object data, EveType type) {
        this.data = data;
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public EveType getType() {
        return type;
    }

    public void setType(EveType type) {
        this.type = type;
    }
}
