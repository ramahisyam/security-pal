package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

public class Visitor extends AppCompatActivity {

    Button bt_visitor;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        bt_visitor = findViewById(R.id.gotoMonitoring);
        spinner = findViewById(R.id.spinner);

        bt_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMonitoringVisitor();
            }
        });

        ArrayList<String> numberList = new ArrayList<>();

        numberList.add("Company Strategic Planning");
        numberList.add("Design");
        numberList.add("Merchant Ship");
        numberList.add("Warship");
        numberList.add("Submarine");
        numberList.add("Marketing and Sales of Bangkap");
        numberList.add("Maintenance and Repair");
        numberList.add("General Engineering");
        numberList.add("Recumalable Sales");
        numberList.add("Quality Assurance");
        numberList.add("Supply Chain");
        numberList.add("Treasury");
        numberList.add("Accountancy");
        numberList.add("Information Technology");
        numberList.add("HCM and Command Media");
        numberList.add("Area");

        spinner.setAdapter(new ArrayAdapter<>(Visitor.this,
                android.R.layout.simple_spinner_dropdown_item, numberList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sNumber = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void openMonitoringVisitor() {
        Intent intent = new Intent(this, MonitoringVisitor.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}