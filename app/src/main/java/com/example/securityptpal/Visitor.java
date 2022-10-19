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

import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.util.ArrayList;

public class Visitor extends AppCompatActivity {

    Button bt_visitor, submitVisitor;
    Spinner spinner;
    TextView txtDepart, edtDepartVisitor;
    ProgressDialog progressDialog;
    EditText edtNameVisitor, edtCompanyVisitor, edtNoHPVisitor, edtPICVisitor, edtNecessityVisitor, edtDateVisitor, edtTimeoutVisitor, edtTimeinVisitor;
    String name, company, phone, division, department, pic, necessity, date, timein, timeout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        bt_visitor = findViewById(R.id.gotoMonitoring);
        spinner = findViewById(R.id.spinner);
        txtDepart = findViewById(R.id.edtDepartVisitor);
        submitVisitor = findViewById(R.id.submitVisitor);
        edtNameVisitor = findViewById(R.id.edtNameVisitor);
        edtCompanyVisitor = findViewById(R.id.edtCompanyVisitor);
        edtNoHPVisitor = findViewById(R.id.edtNoHPVisitor);
        edtDepartVisitor = findViewById(R.id.edtDepartVisitor);
        edtPICVisitor = findViewById(R.id.edtPICVisitor);
        edtNecessityVisitor = findViewById(R.id.edtNecessityVisitor);
        edtDateVisitor = findViewById(R.id.edtDateVisitor);
        edtTimeoutVisitor = findViewById(R.id.edtTimeoutVisitor);
        edtTimeinVisitor = findViewById(R.id.edtTimeinVisitor);

        bt_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMonitoringVisitor();
            }
        });

        submitVisitor.setOnClickListener(view -> {
            try{
                if (TextUtils.isEmpty(edtNameVisitor.getText().toString()) || TextUtils.isEmpty(edtCompanyVisitor.getText().toString()) || TextUtils.isEmpty(edtNoHPVisitor.getText().toString()) || TextUtils.isEmpty(edtPICVisitor.getText().toString()) || TextUtils.isEmpty(edtNecessityVisitor.getText().toString()) || TextUtils.isEmpty(edtDateVisitor.getText().toString()) || TextUtils.isEmpty(edtTimeoutVisitor.getText().toString()) || TextUtils.isEmpty(edtTimeinVisitor.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                    Alerter.create(Visitor.this)
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
                    name = edtNameVisitor.getText().toString();
                    company = edtCompanyVisitor.getText().toString();
                    phone = edtNoHPVisitor.getText().toString();
                    division = spinner.getSelectedItem().toString();
                    department = edtDepartVisitor.getText().toString();
                    pic = edtPICVisitor.getText().toString();
                    necessity = edtNecessityVisitor.getText().toString();
                    date = edtDateVisitor.getText().toString();
                    timein = edtTimeoutVisitor.getText().toString();
                    timeout = edtTimeinVisitor.getText().toString();

                    progressDialog = new ProgressDialog(Visitor.this);
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
                                Intent intent = new Intent(getApplicationContext(),Visitor.class);
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

        spinner.setAdapter(new ArrayAdapter<>(Visitor.this,
                android.R.layout.simple_spinner_dropdown_item, numberList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sNumber = adapterView.getItemAtPosition(i).toString();
                if (i == 0 || i == 1 || i == 2|| i == 3|| i == 4|| i == 5){
                    txtDepart.setText("Production Directorate");
                }else if (i == 6 || i == 7 || i == 8 || i == 9){
                    txtDepart.setText("Marketing Directorate");
                }
                else if (i == 10 || i == 11 || i == 12 || i == 13 || i == 14){
                    txtDepart.setText("Directorate of Finance, Risk Management & HR");
                }
                else if (i == 15 || i == 16){
                    txtDepart.setText("SEVP Transformation Management");
                }
                else if (i == 17){
                    txtDepart.setText("SEVP Technology & Naval System");
                }
                else if (i == 18 || i == 19 || i == 20 || i == 21){
                    txtDepart.setText("-");
                }
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