package com.example.securityptpal;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

//Define table name
@Entity(tableName = "table_name")
public class MainData implements Serializable {
    //creat id column
    @PrimaryKey(autoGenerate = true)
    private int ID;

    //creat text column
    @ColumnInfo(name = "text")
    private String text;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
