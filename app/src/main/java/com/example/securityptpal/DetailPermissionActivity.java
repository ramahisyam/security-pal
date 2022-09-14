package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.securityptpal.model.PermissionEmployee;

public class DetailPermissionActivity extends AppCompatActivity {

    private TextView base, name, nip, division, date, necessity, place, timeout, timeback, status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_permission);
        base = findViewById(R.id.permit_base);
        name = findViewById(R.id.permit_name);
        nip = findViewById(R.id.permit_nip);
        division = findViewById(R.id.permit_division);
        date = findViewById(R.id.permit_date);
        necessity = findViewById(R.id.permit_necessity);
        place = findViewById(R.id.permit_place);
        timeout = findViewById(R.id.permit_timeout);
        timeback = findViewById(R.id.permit_timeback);
        status = findViewById(R.id.permit_status);

        PermissionEmployee permissionEmployee = getIntent().getParcelableExtra("permission");
        base.setText(permissionEmployee.getBase());
        name.setText(permissionEmployee.getName());
        nip.setText(permissionEmployee.getNip());
        division.setText(permissionEmployee.getDivision());
        date.setText(permissionEmployee.getDate());
        necessity.setText(permissionEmployee.getNecessity());
        place.setText(permissionEmployee.getPlace());
        timeout.setText(permissionEmployee.getTimeout());
        timeback.setText(permissionEmployee.getTimeback());
        status.setText(permissionEmployee.getStatus());
    }
}