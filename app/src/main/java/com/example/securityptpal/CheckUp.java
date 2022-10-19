package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securityptpal.employee.Employee;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.util.ArrayList;

public class CheckUp extends AppCompatActivity {

    Spinner spinner, spinner2, spinner3;
    Button submitCheckup;
    TextView edtDepartCheckup;
    ProgressDialog progressDialog;
    String name, nip, division, department, status, date, type, others;
    EditText edtDateCheckup, edtNameCheckup, edtNIPCheckup, edtOthersCheckup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_up);
        edtDepartCheckup = findViewById(R.id.edtDepartCheckup);
        spinner = findViewById(R.id.spinner_division_checkup);
        spinner2 = findViewById(R.id.spinner_status_checkup);
        spinner3 = findViewById(R.id.spinner_type_checkup);
        edtDateCheckup = findViewById(R.id.edtDateCheckup);
        edtNameCheckup = findViewById(R.id.edtNameCheckup);
        edtNIPCheckup = findViewById(R.id.edtNIPCheckup);
        edtOthersCheckup = findViewById(R.id.edtOthersCheckup);
        submitCheckup = findViewById(R.id.submitCheckup);

        submitCheckup.setOnClickListener(view -> {
            try{
                if (TextUtils.isEmpty(edtNameCheckup.getText().toString()) || TextUtils.isEmpty(edtNIPCheckup.getText().toString()) || TextUtils.isEmpty(edtDepartCheckup.getText().toString()) || TextUtils.isEmpty(edtDateCheckup.getText().toString()) || TextUtils.isEmpty(edtOthersCheckup.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                    Alerter.create(CheckUp.this)
                            .setTitle("Add Data Failed!")
                            .setText("Please fill all the data")
                            .setIcon(R.drawable.ic_close)
                            .setBackgroundColorRes(android.R.color.holo_red_dark)
                            .setDuration(2000)
                            .enableSwipeToDismiss()
                            .enableProgress(true)
                            .setProgressColorRes(R.color.design_default_color_primary)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setOnShowListener(new OnShowAlertListener() {
                                @Override
                                public void onShow() {

                                }
                            })
                            .setOnHideListener(new OnHideAlertListener() {
                                @Override
                                public void onHide() {

                                }
                            })
                            .show();
                }else{
                    name = edtNameCheckup.getText().toString();
                    nip = edtNIPCheckup.getText().toString();
                    division = spinner.getSelectedItem().toString();
                    department = edtDepartCheckup.getText().toString();
                    status = spinner2.getSelectedItem().toString();
                    date = edtDateCheckup.getText().toString();
                    type = spinner3.getSelectedItem().toString();
                    others = edtOthersCheckup.getText().toString();

                    progressDialog = new ProgressDialog(CheckUp.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );
                    Thread timer = new Thread(){
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                                Intent intent = new Intent(getApplicationContext(),CheckUp.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                                super.run();
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    timer.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //do something
                            StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                        }
                    }, 2000 );
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });

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
                    edtDepartCheckup.setText("Production Directorate");
                }else if (i == 6 || i == 7 || i == 8 || i == 9){
                    edtDepartCheckup.setText("Marketing Directorate");
                }
                else if (i == 10 || i == 11 || i == 12 || i == 13 || i == 14){
                    edtDepartCheckup.setText("Directorate of Finance, Risk Management & HR");
                }
                else if (i == 15 || i == 16){
                    edtDepartCheckup.setText("SEVP Transformation Management");
                }
                else if (i == 17){
                    edtDepartCheckup.setText("SEVP Technology & Naval System");
                }
                else if (i == 18 || i == 19 || i == 20 || i == 21){
                    edtDepartCheckup.setText("-");
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