package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class EmployeePermitActivity extends AppCompatActivity {

    private String EXTRA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_permit);

        Bundle extras = getIntent().getExtras();
        EXTRA = extras.getString(Intent.EXTRA_TEXT);

        if (EXTRA.equals("DESIGN")){
            FragmentManager fragmentManager = getSupportFragmentManager();
            EmployeePermitFragment fragment = new EmployeePermitFragment();
            fragmentManager.beginTransaction().replace(R.id.container_fragment, fragment).commit();
        } else {
            Toast.makeText(this, "Test", Toast.LENGTH_SHORT).show();
        }
    }
}