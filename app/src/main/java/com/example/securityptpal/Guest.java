package com.example.securityptpal;

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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.Locale;

public class Guest extends AppCompatActivity {
    ImageView calGuest, img_timeout, img_timein;
    EditText edtDate, edtTimeout, edtTimein;
    Button monitoring;
    DatePickerDialog.OnDateSetListener setListener;
    int hour, minute;

//    DrawerLayout drawerLayout;
//    ImageView btMenu;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);

        calGuest = findViewById(R.id.calGuest);
        edtDate = findViewById(R.id.edtDateGuest);
        img_timeout = findViewById(R.id.img_timeout);
        img_timein = findViewById(R.id.img_timein);
        edtTimeout = findViewById(R.id.edtTimeoutGuest);
        edtTimein = findViewById(R.id.edtTimeinGuest);
//        drawerLayout = findViewById(R.id.drawer_layout);
//        btMenu = findViewById(R.id.bt_menu);
        spinner = findViewById(R.id.spinner);
        monitoring = findViewById(R.id.gotoMonitoring);

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        final int year = calendar.get(java.util.Calendar.YEAR);
        final int month = calendar.get(java.util.Calendar.MONTH);
        final int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        ArrayList<String> numberList = new ArrayList<>();

//        numberList.add("");
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

        calGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Guest.this, new DatePickerDialog.OnDateSetListener() {
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
                openMonitoringGuest();
            }
        });

//        btMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.END);
//            }
//        });

        spinner.setAdapter(new ArrayAdapter<>(Guest.this,
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                edtTimein.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
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
                edtTimeout.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,onTimeSetListener,hour,minute,true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }

    public void openMonitoringGuest() {
        Intent intent = new Intent(this, MonitoringGuest.class);
        startActivity(intent);
    }

//    public void ClickLogo(View view){
//        closeDrawer(drawerLayout);
//    }
//
//    public static void closeDrawer(DrawerLayout drawerLayout){
//        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
//            drawerLayout.closeDrawer(GravityCompat.END);
//        }
//    }
//
//    public void ClickFillData(View view){
//        redirectActivity(this, Guest.class);
//    }
//
//    public void ClickMonitoring(View view){
//        redirectActivity(this, MonitoringGuest.class);
//    }
//
//    public static void redirectActivity(Activity activity, Class aClass){
//        Intent intent = new Intent(activity,aClass);
//
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//        activity.startActivity(intent);
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}