package com.example.securityptpal.employee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class DetailPermissionActivity extends AppCompatActivity {

    Spinner spinner;
    private TextView base, name, nip, division, date, necessity, place, timeout, timeback, status;
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

        ArrayList<String> numberList = new ArrayList<>();

        numberList.add("Accepted");
        numberList.add("Pending");
        numberList.add("Rejected");

//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                if (i == 0){
////                    Toast.makeText(getApplicationContext(),
////                            "Please Select Division",Toast.LENGTH_SHORT).show();
////                    textView.setText("");
////                }else{
////                    String sNumber = adapterView.getItemAtPosition(i).toString();
////                    textView.setText(sNumber);
////                }
//                String sNumber = adapterView.getItemAtPosition(i).toString();
////                textView.setText(sNumber);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
    }
}