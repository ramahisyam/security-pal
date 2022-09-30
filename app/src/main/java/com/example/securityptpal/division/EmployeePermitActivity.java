package com.example.securityptpal.division;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.securityptpal.R;

public class EmployeePermitActivity extends AppCompatActivity {

    private String EXTRA;
    private CardView cvLatePermission, cvExitPermission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_permit);
        cvExitPermission = findViewById(R.id.card_exit_permission);
        cvLatePermission = findViewById(R.id.card_late_permission);

        Bundle extras = getIntent().getExtras();
        EXTRA = extras.getString(Intent.EXTRA_TEXT);
        cvExitPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EmployeePermitActivity.this, ExitPermissionActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, EXTRA);
                startActivity(intent);
            }
        });

        cvLatePermission.setOnClickListener(view -> {
            Intent intent = new Intent(EmployeePermitActivity.this, DivisionLatePermitActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, EXTRA);
            startActivity(intent);
        });
    }
}