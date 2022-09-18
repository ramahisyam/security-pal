package com.example.securityptpal.division;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class DetailExitActivity extends AppCompatActivity {

    Spinner spinner;
    private TextView base, name, nip, division, date, necessity, place, timeout, timeback, status;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_exit);

        base = findViewById(R.id.div_exit_permit_base);
        name = findViewById(R.id.div_exit_permit_name);
        nip = findViewById(R.id.div_exit_permit_nip);
        division = findViewById(R.id.div_exit_permit_division);
        date = findViewById(R.id.div_exit_permit_date);
        necessity = findViewById(R.id.div_exit_permit_necessity);
        place = findViewById(R.id.div_exit_permit_place);
        timeout = findViewById(R.id.div_exit_permit_timeout);
        timeback = findViewById(R.id.div_exit_permit_timeback);

        PermissionEmployee permissionEmployee = getIntent().getParcelableExtra("EXIT_PERMIT");
        base.setText(permissionEmployee.getBase());
        name.setText(permissionEmployee.getName());
        nip.setText(permissionEmployee.getNip());
        division.setText(permissionEmployee.getDivision());
        date.setText(permissionEmployee.getDate());
        necessity.setText(permissionEmployee.getNecessity());
        place.setText(permissionEmployee.getPlace());
        timeout.setText(permissionEmployee.getTimeout());
        timeback.setText(permissionEmployee.getTimeback());
    }
}