package com.example.securityptpal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Guest implements Parcelable {
    private String id;
    private String name;
    private String company;
    private String phone;
    private String division;
    private String pic;
    private String necessity;
    private String date;
    private String timeIn;
    private String timeOut;

    public Guest() {
    }

    public Guest(String id, String name, String company, String phone, String division, String pic, String necessity, String date, String timeIn, String timeOut) {
        this.id = id;
        this.name = name;
        this.company = company;
        this.phone = phone;
        this.division = division;
        this.pic = pic;
        this.necessity = necessity;
        this.date = date;
        this.timeIn = timeIn;
        this.timeOut = timeOut;
    }

    protected Guest(Parcel in) {
        id = in.readString();
        name = in.readString();
        company = in.readString();
        phone = in.readString();
        division = in.readString();
        pic = in.readString();
        necessity = in.readString();
        date = in.readString();
        timeIn = in.readString();
        timeOut = in.readString();
    }

    public static final Creator<Guest> CREATOR = new Creator<Guest>() {
        @Override
        public Guest createFromParcel(Parcel in) {
            return new Guest(in);
        }

        @Override
        public Guest[] newArray(int size) {
            return new Guest[size];
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

    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(String timeIn) {
        this.timeIn = timeIn;
    }

    public String getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(String timeOut) {
        this.timeOut = timeOut;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(company);
        parcel.writeString(phone);
        parcel.writeString(division);
        parcel.writeString(pic);
        parcel.writeString(necessity);
        parcel.writeString(date);
        parcel.writeString(timeIn);
        parcel.writeString(timeOut);
    }
}
