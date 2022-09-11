package com.example.securityptpal.model;

public class PermissionEmployee {
    private String base;
    private String name;
    private String nip;
    private String division;
    private String date;
    private String necessity;
    private String place;
    private String timeout;
    private String timeback;
    private String status;

    public PermissionEmployee() {
    }

    public PermissionEmployee(String base, String name, String nip, String division, String date, String necessity, String place, String timeout, String timeback, String status) {
        this.base = base;
        this.name = name;
        this.nip = nip;
        this.division = division;
        this.date = date;
        this.necessity = necessity;
        this.place = place;
        this.timeout = timeout;
        this.timeback = timeback;
        this.status = status;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNecessity() {
        return necessity;
    }

    public void setNecessity(String necessity) {
        this.necessity = necessity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTimeback() {
        return timeback;
    }

    public void setTimeback(String timeback) {
        this.timeback = timeback;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
