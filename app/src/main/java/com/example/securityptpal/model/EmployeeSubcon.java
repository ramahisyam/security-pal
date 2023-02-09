package com.example.securityptpal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeSubcon implements Parcelable {
    private String id;
    private String name;
    private String age;
    private String phone;
    private String nip;
    private String address;
    private String img;

    public EmployeeSubcon() {
    }

    public EmployeeSubcon(String id, String name, String age, String phone, String nip, String address, String img) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.phone = phone;
        this.nip = nip;
        this.address = address;
        this.img = img;
    }

    protected EmployeeSubcon(Parcel in) {
        id = in.readString();
        name = in.readString();
        age = in.readString();
        phone = in.readString();
        nip = in.readString();
        address = in.readString();
        img = in.readString();
    }

    public static final Creator<EmployeeSubcon> CREATOR = new Creator<EmployeeSubcon>() {
        @Override
        public EmployeeSubcon createFromParcel(Parcel in) {
            return new EmployeeSubcon(in);
        }

        @Override
        public EmployeeSubcon[] newArray(int size) {
            return new EmployeeSubcon[size];
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(age);
        parcel.writeString(phone);
        parcel.writeString(nip);
        parcel.writeString(address);
        parcel.writeString(img);
    }
}
