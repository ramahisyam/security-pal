package com.example.securityptpal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.util.ArrayList;
import java.util.Locale;

public class Guest extends AppCompatActivity {
    ImageView calGuest, img_timeout, img_timein;
    EditText edtDate, edtTimeout, edtTimein, edtNameGuest, edtCompanyGuest, edtNoHPGuest, edtPICGuest, edtNecessityGuest;
    TextView txtDepart;
    Button monitoring, submitGuest;
    DatePickerDialog.OnDateSetListener setListener;
    String name, company, phone, department, pic, necessity, date, timein, timeout, division;
    int hour, minute;
    ProgressDialog progressDialog;

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
        edtNameGuest = findViewById(R.id.edtNameGuest);
        edtCompanyGuest = findViewById(R.id.edtCompanyGuest);
        edtNoHPGuest = findViewById(R.id.edtNoHPGuest);
        edtPICGuest = findViewById(R.id.edtPICGuest);
        edtNecessityGuest = findViewById(R.id.edtNecessityGuest);
//        drawerLayout = findViewById(R.id.drawer_layout);
//        btMenu = findViewById(R.id.bt_menu);
        spinner = findViewById(R.id.spinner);
        monitoring = findViewById(R.id.gotoMonitoring);
        txtDepart = findViewById(R.id.txtdepartGuest);
        submitGuest = findViewById(R.id.submitGuest);

        submitGuest.setOnClickListener(view -> {
            try{
                if (TextUtils.isEmpty(edtNameGuest.getText().toString()) || TextUtils.isEmpty(edtCompanyGuest.getText().toString()) || TextUtils.isEmpty(edtNoHPGuest.getText().toString()) || TextUtils.isEmpty(edtPICGuest.getText().toString()) || TextUtils.isEmpty(edtNecessityGuest.getText().toString()) || TextUtils.isEmpty(edtDate.getText().toString()) || TextUtils.isEmpty(edtTimein.getText().toString()) || TextUtils.isEmpty(edtTimeout.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                    Alerter.create(Guest.this)
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
                    name = edtNameGuest.getText().toString();
                    company = edtCompanyGuest.getText().toString();
                    phone = edtNoHPGuest.getText().toString();
                    division = spinner.getSelectedItem().toString();
                    department = txtDepart.getText().toString();
                    pic = edtPICGuest.getText().toString();
                    necessity = edtNecessityGuest.getText().toString();
                    date = edtDate.getText().toString();
                    timein = edtTimein.getText().toString();
                    timeout = edtTimeout.getText().toString();

                    progressDialog = new ProgressDialog(Guest.this);
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
                                Intent intent = new Intent(getApplicationContext(),Guest.class);
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