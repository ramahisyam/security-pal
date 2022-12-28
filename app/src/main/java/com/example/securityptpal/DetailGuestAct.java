package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailGuestAct extends AppCompatActivity {
    private TextView name, company, phone, division, department, pic, necessity, date, timeIn, timeOut, division_approve, center_approve;
    ImageView qrGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_guest);

        name = findViewById(R.id.detail_name_guest);
        company = findViewById(R.id.detail_company_guest);
        phone = findViewById(R.id.detail_phone_guest);
        division = findViewById(R.id.detail_division_guest);
        department = findViewById(R.id.detail_department_guest);
        pic = findViewById(R.id.detail_pic_guest);
        necessity = findViewById(R.id.detail_necessity_guest);
        date = findViewById(R.id.detail_date_guest);
        timeIn = findViewById(R.id.detail_timein_guest);
        timeOut = findViewById(R.id.detail_timeout_guest);
        division_approve = findViewById(R.id.main_division_approval_status);
        center_approve = findViewById(R.id.main_center_approval);
    }
}