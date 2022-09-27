package com.example.securityptpal.employee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.securityptpal.LogoutAccount;
import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class Employee extends AppCompatActivity {
    ImageView Calendar, img_timeout, img_timeback, imgSignOut;
    EditText edtBase, edtName, edtNip, edtDate, edtNecessity, edtPlace, edtTimeout, edtTimeback;
    DatePickerDialog.OnDateSetListener setListener;
    Spinner spinner;
    int hour, minute;
    String base, name, nip, division, date, necessity, place, timeout, timeback;
    FirebaseFirestore db;
    Button btnSendEmployee, btnMonitoring;
    private ArrayAdapter spinnerAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

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
        progressDialog = new ProgressDialog(Employee.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");
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
        numberList.add("Risk");
        numberList.add("Office of The Board");
        numberList.add("Legal");
        numberList.add("Technology & Quality Assurance");
        numberList.add("Company Secretary");
        numberList.add("Internal Control Unit");
        numberList.add("Information Technology");
        numberList.add("Design");

        spinnerAdapter = new ArrayAdapter<>(Employee.this,
                android.R.layout.simple_spinner_dropdown_item, numberList);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String sNumber = adapterView.getItemAtPosition(i).toString();
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
                progressDialog.show();
                base = edtBase.getText().toString();
                name = edtName.getText().toString();
                nip = edtNip.getText().toString();
                date = edtDate.getText().toString();
                necessity = edtNecessity.getText().toString();
                place = edtPlace.getText().toString();
                timeout = edtTimeout.getText().toString();
                timeback = edtTimeback.getText().toString();
                division = spinner.getSelectedItem().toString();

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
                        Toast.makeText(Employee.this, "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                        edtBase.setText("");
                        edtName.setText("");
                        edtNip.setText("");
                        edtDate.setText("");
                        edtNecessity.setText("");
                        edtPlace.setText("");
                        edtTimeout.setText("");
                        edtTimeback.setText("");
                        progressDialog.dismiss();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Employee.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
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