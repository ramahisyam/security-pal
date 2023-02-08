package com.example.securityptpal.model;

import android.os.Parcel;
import android.os.Parcelable;

public class EmployeeSubcon implements Parcelable {
    private String id;
    private String name;
    private String age;

    public EmployeeSubcon() {
    }

    public EmployeeSubcon(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    protected EmployeeSubcon(Parcel in) {
        id = in.readString();
        name = in.readString();
        age = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(name);
        parcel.writeString(age);
    }
}
