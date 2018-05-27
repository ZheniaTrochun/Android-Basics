package com.trochun.lab3;

import android.os.Parcel;
import android.os.Parcelable;

public class DataModel implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(from);
        dest.writeString(to);
        dest.writeInt(morning ? 1: 0);
    }

    private DataModel(Parcel src) {
        this.from = src.readString();
        this.to = src.readString();
        this.morning = src.readInt() == 1;
    }

    public static final Parcelable.Creator<DataModel> CREATOR =
            new Parcelable.Creator<DataModel>() {
        public DataModel createFromParcel(Parcel in) {
            return new DataModel(in);
        }

        public DataModel[] newArray(int size) {
            return new DataModel[size];
        }
    };
}
