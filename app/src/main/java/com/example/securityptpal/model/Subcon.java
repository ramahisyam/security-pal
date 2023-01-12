package com.example.securityptpal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Subcon implements Parcelable {
    private String id;
    private String company;
    private String phone;
    private String necessity;
    private String division;
    private String department;
    private String startDate;
    private String finishDate;
    private String userID;

    public Subcon() {
    }

    public Subcon(String id, String company, String phone, String necessity, String division, String department, String startDate, String finishDate, String userID) {
        this.id = id;
        this.company = company;
        this.phone = phone;
        this.necessity = necessity;
        this.division = division;
        this.department = department;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.userID = userID;
    }

    protected Subcon(Parcel in) {
        id = in.readString();
        company = in.readString();
        phone = in.readString();
        necessity = in.readString();
        division = in.readString();
        department = in.readString();
        startDate = in.readString();
        finishDate = in.readString();
        userID = in.readString();
    }

    public static final Creator<Subcon> CREATOR = new Creator<Subcon>() {
        @Override
        public Subcon createFromParcel(Parcel in) {
            return new Subcon(in);
        }

        @Override
        public Subcon[] newArray(int size) {
            return new Subcon[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(company);
        parcel.writeString(phone);
        parcel.writeString(necessity);
        parcel.writeString(division);
        parcel.writeString(department);
        parcel.writeString(startDate);
        parcel.writeString(finishDate);
        parcel.writeString(userID);
    }
}
