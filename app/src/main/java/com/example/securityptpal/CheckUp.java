package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.securityptpal.employee.Employee;

import java.util.ArrayList;

public class CheckUp extends AppCompatActivity {

    Spinner spinner, spinner2, spinner3;
    TextView edtDepart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up);
        edtDepart = findViewById(R.id.edtDepart);
        spinner = findViewById(R.id.spinner_division_checkup);
        spinner2 = findViewById(R.id.spinner_status_checkup);
        spinner3 = findViewById(R.id.spinner_type_checkup);

        ArrayList<String> numberList = new ArrayList<>();

        numberList.add("General Engineering");
        numberList.add("Merchant Ship");
        numberList.add("Warship");
        numberList.add("Submarine");
        numberList.add("Maintenance & Repair");
        numberList.add("Production Management Office");
        numberList.add("Ship Marketing & Sales");
        numberList.add("Recumalable Sales");
        numberList.add("Supply Chain");
        numberList.add("Area & K3LH");
        numberList.add("Company Strategic Planning");
        numberList.add("Treasury");
        numberList.add("Accountancy");
        numberList.add("Human Capital Management");
        numberList.add("Risk");
        numberList.add("Office of The Board");
        numberList.add("Legal");
        numberList.add("Technology & Quality Assurance");
        numberList.add("Company Secretary");
        numberList.add("Internal Control Unit");
        numberList.add("Information Technology");
        numberList.add("Design");

        spinner.setAdapter(new ArrayAdapter<>(CheckUp.this,
                android.R.layout.simple_spinner_dropdown_item, numberList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0){
//                    Toast.makeText(getApplicationContext(),
//                            "Please Select Division",Toast.LENGTH_SHORT).show();
//                    textView.setText("");
//                }else{
//                    String sNumber = adapterView.getItemAtPosition(i).toString();
//                    textView.setText(sNumber);
//                }
                String sNumber = adapterView.getItemAtPosition(i).toString();
//                textView.setText(sNumber);
                if (i == 0 || i == 1 || i == 2|| i == 3|| i == 4|| i == 5){
                    edtDepart.setText("Production Directorate");
                }else if (i == 6 || i == 7 || i == 8 || i == 9){
                    edtDepart.setText("Marketing Directorate");
                }
                else if (i == 10 || i == 11 || i == 12 || i == 13 || i == 14){
                    edtDepart.setText("Directorate of Finance, Risk Management & HR");
                }
                else if (i == 15 || i == 16){
                    edtDepart.setText("SEVP Transformation Management");
                }
                else if (i == 17){
                    edtDepart.setText("SEVP Technology & Naval System");
                }
                else if (i == 18 || i == 19 || i == 20 || i == 21){
                    edtDepart.setText("-");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> statusList = new ArrayList<>();

        statusList.add("PKWT");
        statusList.add("PKWTT");

        spinner2.setAdapter(new ArrayAdapter<>(CheckUp.this,
                android.R.layout.simple_spinner_dropdown_item, statusList));

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0){
//                    Toast.makeText(getApplicationContext(),
//                            "Please Select Division",Toast.LENGTH_SHORT).show();
//                    textView.setText("");
//                }else{
//                    String sNumber = adapterView.getItemAtPosition(i).toString();
//                    textView.setText(sNumber);
//                }
                String sNumber = adapterView.getItemAtPosition(i).toString();
//                textView.setText(sNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayList<String> typeList = new ArrayList<>();

        typeList.add("Blood Pressure Check");
        typeList.add("Cholesterol and Blood Sugar Check");
        typeList.add("Blood Test");
        typeList.add("Heart Check");
        typeList.add("Eye Examination");

        spinner3.setAdapter(new ArrayAdapter<>(CheckUp.this,
                android.R.layout.simple_spinner_dropdown_item, typeList));

        spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0){
//                    Toast.makeText(getApplicationContext(),
//                            "Please Select Division",Toast.LENGTH_SHORT).show();
//                    textView.setText("");
//                }else{
//                    String sNumber = adapterView.getItemAtPosition(i).toString();
//                    textView.setText(sNumber);
//                }
                String sNumber = adapterView.getItemAtPosition(i).toString();
//                textView.setText(sNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}