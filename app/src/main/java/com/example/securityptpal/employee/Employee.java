package com.example.securityptpal.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.securityptpal.CometooLate;
import com.example.securityptpal.LogoutAccount;
import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Employee extends AppCompatActivity {
    ImageView Calendar, img_timeout, img_timeback, imgSignOut;
    EditText edtBase, edtName, edtNip, edtDate, edtNecessity, edtPlace, edtTimeout, edtTimeback;
    TextView txtDepart;
    DatePickerDialog.OnDateSetListener setListener;
    Spinner spinner, spinner2;
    int hour, minute;
    String base, name, nip, division, date, necessity, place, timeout, timeback;
    FirebaseFirestore db;
    Button btnSendEmployee, btnMonitoring;
    private ArrayAdapter spinnerAdapter;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        txtDepart = findViewById(R.id.txtDepart);
        Calendar = findViewById(R.id.calendar);
        edtDate = (EditText) findViewById(R.id.edtDate);
        img_timeout = findViewById(R.id.img_timeout);
        img_timeback = findViewById(R.id.img_timeback);
        imgSignOut = findViewById(R.id.sign_out_permission);
        edtTimeout = (EditText) findViewById(R.id.edtTimeout);
        edtTimeback = (EditText) findViewById(R.id.edtTimeback);
        edtBase = (EditText) findViewById(R.id.edtBase);
        edtName = (EditText) findViewById(R.id.edtName);
        edtNip = (EditText) findViewById(R.id.edtNip);
        edtNecessity = (EditText) findViewById(R.id.edtNecessity);
        edtPlace = (EditText) findViewById(R.id.edtPlace);
        btnSendEmployee = findViewById(R.id.btn_send_employee);
        btnMonitoring = findViewById(R.id.gotoMonitoring);
        spinner = findViewById(R.id.spinner_division_employee);
        spinner2 = findViewById(R.id.spinner_status_employee);

        db = FirebaseFirestore.getInstance();

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
        numberList.add("Risk Management");
        numberList.add("Office of The Board");
        numberList.add("Legal");
        numberList.add("Technology & Quality Assurance");
        numberList.add("Company Secretary");
        numberList.add("Internal Control Unit");
        numberList.add("Information Technology");
        numberList.add("Design");

        ArrayList<String> statusList = new ArrayList<>();

        statusList.add("PKWT");
        statusList.add("PKWTT");

        spinner2.setAdapter(new ArrayAdapter<>(Employee.this,
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


        spinnerAdapter = new ArrayAdapter<>(Employee.this,
                android.R.layout.simple_spinner_dropdown_item, numberList);
        spinner.setAdapter(spinnerAdapter);

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

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        final int year = calendar.get(java.util.Calendar.YEAR);
        final int month = calendar.get(java.util.Calendar.MONTH);
        final int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        Calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Employee.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        calendar.set(year, month, day);

                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

                        String dateString = dateFormat.format(calendar.getTime());
                        edtDate.setText(dateString);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnSendEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try{
                    if (TextUtils.isEmpty(edtBase.getText().toString()) || TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtNip.getText().toString()) || TextUtils.isEmpty(edtDate.getText().toString()) || TextUtils.isEmpty(edtNecessity.getText().toString()) || TextUtils.isEmpty(edtPlace.getText().toString()) || TextUtils.isEmpty(edtTimeout.getText().toString()) || TextUtils.isEmpty(edtTimeback.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                        Alerter.create(Employee.this)
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
                        base = edtBase.getText().toString();
                        name = edtName.getText().toString();
                        nip = edtNip.getText().toString();
                        date = edtDate.getText().toString();
                        necessity = edtNecessity.getText().toString();
                        place = edtPlace.getText().toString();
                        timeout = edtTimeout.getText().toString();
                        timeback = edtTimeback.getText().toString();
                        division = spinner.getSelectedItem().toString();

                        progressDialog = new ProgressDialog(Employee.this);
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
                                    Intent intent = new Intent(getApplicationContext(),Employee.class);
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
                        PermissionEmployee permissionEmployee = new PermissionEmployee(
                                db.collection("permission_employee").document().getId(),
                                base,
                                name,
                                nip,
                                division,
                                date,
                                necessity,
                                place,
                                timeout,
                                timeback,
                                "Pending",
                                "Pending"
                        );
                        db.collection("permission_employee").add(permissionEmployee).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                //StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        btnMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Employee.this, MonitoringEmployee.class));
            }
        });

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAccount.logout(Employee.this);
            }
        });
    }

    public void pop(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                edtTimeout.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void pop2(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                edtTimeback.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}