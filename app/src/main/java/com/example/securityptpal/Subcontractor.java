package com.example.securityptpal;

import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.main.AkunUtama;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Subcontractor extends AppCompatActivity {
    ImageView calStart, calFinish, btAdd,btReset, imgSignOut;
    EditText edtStart, edtFinish, editText;
    Button monitoring;
    DatePickerDialog.OnDateSetListener setListener;
    RecyclerView recyclerView;
    TextView txtDepart;

    List<MainData> dataList = new ArrayList<>();
    LinearLayoutManager linearLayoutManager;
    RoomDB database;
    MainAdapter adapter;

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcontractor);

        calStart = findViewById(R.id.calStartSubcon);
        calFinish = findViewById(R.id.calFinishSubcon);
        edtStart = findViewById(R.id.edtstartSubcon);
        edtFinish = findViewById(R.id.edtFinishSubcon);
        spinner = findViewById(R.id.spinner);
        monitoring = findViewById(R.id.gotoMonitoring);

        editText = findViewById(R.id.edit_text);
        btAdd = findViewById(R.id.bt_add);
        btReset = findViewById(R.id.bt_reset);
        recyclerView = findViewById(R.id.recycler_view);
        txtDepart = findViewById(R.id.txtdepartSubcon);
        imgSignOut = findViewById(R.id.sign_out_subcon);

        database = RoomDB.getInstance(this);
        dataList = database.mainDao().getAll();
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MainAdapter(Subcontractor.this,dataList);
        recyclerView.setAdapter(adapter);

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sText = editText.getText().toString().trim();
                if (!sText.equals("")){
                    MainData data = new MainData();
                    data.setText(sText);
                    database.mainDao().insert(data);
                    editText.setText("");
                    dataList.clear();
                    dataList.addAll(database.mainDao().getAll());
                    adapter.notifyDataSetChanged();
                }
            }
        });

        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.mainDao().reset(dataList);
                dataList.clear();
                dataList.addAll(database.mainDao().getAll());
                adapter.notifyDataSetChanged();
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

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMonitoringSubcontractor();
            }
        });

        calStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Subcontractor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"-"+month+"-"+year;
                        edtStart.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        calFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Subcontractor.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"-"+month+"-"+year;
                        edtFinish.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        spinner.setAdapter(new ArrayAdapter<>(Subcontractor.this,
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

        imgSignOut.setOnClickListener(view -> {
            LogoutAccount.logout(Subcontractor.this);
        });
    }

    public void openMonitoringSubcontractor() {
        Intent intent = new Intent(this, MonitoringSubcontractor.class);
        startActivity(intent);
    }
}