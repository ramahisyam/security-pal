package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;

import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.Subcon;

public class DetailSubconActivity extends AppCompatActivity {
    TextView company, phone, necessity, division, department, startDate, finishDate;
    ProgressDialog progressDialog;
    Subcon subcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subcon);

        progressDialog = new ProgressDialog(DetailSubconActivity.this);
        company = findViewById(R.id.detail_company_subcon);
        phone = findViewById(R.id.detail_phone_subcon);
        necessity = findViewById(R.id.detail_necessity_subcon);
        division = findViewById(R.id.detail_division_subcon);
        department = findViewById(R.id.detail_department_subcon);
        startDate = findViewById(R.id.detail_start_date_subcon);
        finishDate = findViewById(R.id.detail_finish_date_subcon);

        subcon = getIntent().getParcelableExtra("SUBCON_DETAIL");

        company.setText(subcon.getCompany());
        phone.setText(subcon.getPhone());
        necessity.setText(subcon.getNecessity());
        division.setText(subcon.getDivision());
        department.setText(subcon.getDepartment());
        startDate.setText(subcon.getStartDate());
        finishDate.setText(subcon.getFinishDate());
    }
}