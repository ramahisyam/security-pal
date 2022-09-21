package com.example.securityptpal.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailPermissionActivity extends AppCompatActivity {

    private TextView base, name, nip, division, date, necessity, place, timeout, timeback, division_approve, center_approve;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
        division_approve = findViewById(R.id.division_approval_status);
        center_approve = findViewById(R.id.center_approval);

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
        if (permissionEmployee.getDivision_approval().equals("Pending")){
            division_approve.setText(permissionEmployee.getDivision_approval());
            division_approve.setTextColor(division_approve.getResources().getColor(R.color.main_orange_color));
        } else if (permissionEmployee.getDivision_approval().equals("Accepted")){
            division_approve.setText(permissionEmployee.getDivision_approval());
            division_approve.setTextColor(division_approve.getResources().getColor(R.color.main_green_color));
        } else {
            division_approve.setText(permissionEmployee.getDivision_approval());
            division_approve.setTextColor(division_approve.getResources().getColor(R.color.cardColorRed));
        }

        if (permissionEmployee.getCenter_approval().equals("Pending")){
            center_approve.setText(permissionEmployee.getCenter_approval());
            center_approve.setTextColor(center_approve.getResources().getColor(R.color.main_orange_color));
        } else if (permissionEmployee.getCenter_approval().equals("Accepted")){
            center_approve.setText(permissionEmployee.getCenter_approval());
            center_approve.setTextColor(center_approve.getResources().getColor(R.color.main_green_color));
        } else {
            center_approve.setText(permissionEmployee.getCenter_approval());
            center_approve.setTextColor(center_approve.getResources().getColor(R.color.cardColorRed));
        }
    }
}