package com.trochun.lab2;

import java.io.Serializable;

public class DataModel implements Serializable {
    private String from;
    private String to;
    private boolean morning;

    public DataModel(String from, String to, boolean morning) {
        this.from = from;
        this.to = to;
        this.morning = morning;
    }

    public DataModel() {
    }


    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public boolean isMorning() {
        return morning;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void setMorning(boolean morning) {
        this.morning = morning;
    }
}
