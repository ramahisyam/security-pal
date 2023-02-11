package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.securityptpal.model.EmployeeSubcon;
import com.example.securityptpal.model.Subcon;
import com.zolad.zoominimageview.ZoomInImageView;

public class DetailSubconEmployeeActivity extends AppCompatActivity {

    EmployeeSubcon employeeSubcon;
    TextView name, phone, nip, age, address, startDate, finishDate, company;
    ZoomInImageView photo;
    Subcon subcon;
    ConstraintLayout download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_subcon_employee);

        name = findViewById(R.id.detail_name_employee_subcon);
        phone = findViewById(R.id.detail_phone_employee_subcon);
        nip = findViewById(R.id.detail_nip_employee_subcon);
        age = findViewById(R.id.detail_age_employee_subcon);
        address = findViewById(R.id.detail_address_employee_subcon);
        company = findViewById(R.id.detail_company_employee_subcon);
        startDate = findViewById(R.id.detail_start_date_employee_subcon);
        finishDate = findViewById(R.id.detail_finish_date_employee_subcon);
        photo = findViewById(R.id.img_employee_subcon);
        download = findViewById(R.id.downloadidcard);

        employeeSubcon = getIntent().getParcelableExtra("SUBCON_EMPLOYEE_DETAIL");
        subcon = getIntent().getParcelableExtra("SUBCON_DATA");
        name.setText(employeeSubcon.getName());
        phone.setText(employeeSubcon.getPhone());
        nip.setText(employeeSubcon.getNip());
        age.setText(employeeSubcon.getAge());
        address.setText(employeeSubcon.getAddress());
        company.setText(subcon.getCompany());
        startDate.setText(subcon.getStartDate());
        finishDate.setText(subcon.getFinishDate());
        Glide.with(this).load(employeeSubcon.getImg()).placeholder(R.drawable.pict).into(photo);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailSubconEmployeeActivity.this, IdcardDetailSubcon.class);
                intent.putExtra("IDCARD1", employeeSubcon);
                intent.putExtra("IDCARD2", subcon);
                startActivity(intent);
            }
        });
    }
}