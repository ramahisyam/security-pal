package com.example.securityptpal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Employee extends AppCompatActivity {
    ImageView Calendar, img_timeout, img_timeback;
    EditText edtDate, edtTimeout, edtTimeback;
    Button monitoring;
    DatePickerDialog.OnDateSetListener setListener;
    int hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee);

        Calendar = findViewById(R.id.calendar);
        edtDate = findViewById(R.id.edtDate);
        img_timeout = findViewById(R.id.img_timeout);
        img_timeback = findViewById(R.id.img_timeback);
        edtTimeout = findViewById(R.id.edtTimeout);
        edtTimeback = findViewById(R.id.edtTimeback);
        monitoring = findViewById(R.id.gotoMonitoring);

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
                        month = month + 1;
                        String date = day+"-"+month+"-"+year;
                        edtDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMonitoringEmployee();
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

    public static void logout(Activity activity){
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("LOGOUT")
                .setContentText("Are you sure want to logout ?")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        activity.startActivity(new Intent(activity,MainActivity2.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        activity.finish();
                    }
                })
                .setCancelButton("CANCEL", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed(){
        logout(this);
    }

    public void openMonitoringEmployee() {
        Intent intent = new Intent(this, MonitoringEmployee.class);
        startActivity(intent);
    }
}