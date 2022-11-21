package com.example.securityptpal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class CheckUp implements Parcelable {
    private String id;
    private String name;
    private String nip;
    private String division;
    private String department;
    private String status;
    private String date;
    private String type;
    private String others;

    public CheckUp() {
    }

    public CheckUp(String id, String name, String nip, String division, String department, String status, String date, String type, String others) {
        this.id = id;
        this.name = name;
        this.nip = nip;
        this.division = division;
        this.department = department;
        this.status = status;
        this.date = date;
        this.type = type;
        this.others = others;
    }

    protected CheckUp(Parcel in) {
        id = in.readString();
        name = in.readString();
        nip = in.readString();
        division = in.readString();
        department = in.readString();
        status = in.readString();
        date = in.readString();
        type = in.readString();
        others = in.readString();
    }

    public static final Creator<CheckUp> CREATOR = new Creator<CheckUp>() {
        @Override
        public CheckUp createFromParcel(Parcel in) {
            return new CheckUp(in);
        }

        @Override
        public CheckUp[] newArray(int size) {
            return new CheckUp[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOthers() {
        return others;
    }

    public void setOthers(String others) {
        this.others = others;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(nip);
        parcel.writeString(division);
        parcel.writeString(department);
        parcel.writeString(status);
        parcel.writeString(date);
        parcel.writeString(type);
        parcel.writeString(others);
    }
}
