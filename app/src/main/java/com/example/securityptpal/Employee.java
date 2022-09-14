package com.example.securityptpal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
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

import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Employee extends AppCompatActivity {
    ImageView Calendar, img_timeout, img_timeback, imgSignOut;
    EditText edtBase, edtName, edtNip, edtDate, edtNecessity, edtPlace, edtTimeout, edtTimeback;
    DatePickerDialog.OnDateSetListener setListener;
    Spinner spinner;
    int hour, minute;
    String base, name, nip, division, date, necessity, place, timeout, timeback;
    FirebaseFirestore db;
    Button btnSendEmployee, btnMonitoring;

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
        db = FirebaseFirestore.getInstance();

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

        spinner.setAdapter(new ArrayAdapter<>(Employee.this,
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

                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

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
                        base,
                        name,
                        nip,
                        division,
                        date,
                        necessity,
                        place,
                        timeout,
                        timeback,
                        "pending"
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