package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.Guest;
import com.zolad.zoominimageview.ZoomInImageView;

public class DetailGuestPermitActivity extends AppCompatActivity {

    TextView name, company, phone, division, department, pic, necessity, date, timeIn, timeOut;
    ProgressDialog progressDialog;
    Guest guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_guest_permit);
        progressDialog = new ProgressDialog(DetailGuestPermitActivity.this);
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

        guest = getIntent().getParcelableExtra("MAIN_GUEST_PERMIT");

        name.setText(guest.getName());
        company.setText(guest.getCompany());
        phone.setText(guest.getPhone());
        division.setText(guest.getDivision());
        department.setText(guest.getDepartment());
        pic.setText(guest.getPic());
        necessity.setText(guest.getNecessity());
        date.setText(guest.getDate());
        timeIn.setText(guest.getTimeIn());
        timeOut.setText(guest.getTimeOut());
    }
}