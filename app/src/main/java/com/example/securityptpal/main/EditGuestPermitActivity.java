package com.example.securityptpal.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.securityptpal.GuestPermissionActivity;
import com.example.securityptpal.R;
import com.example.securityptpal.model.Division;
import com.example.securityptpal.model.Guest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditGuestPermitActivity extends AppCompatActivity {

    ImageView calGuest, img_timeout, img_timein;
    EditText edtDate, edtTimeout, edtTimein, edtName, edtCompany, edtPhone, edtPic, edtNecessity;
    Button monitoring, submit;
    DatePickerDialog.OnDateSetListener setListener;
    int hour, minute;
    ArrayList<String> divisionList, departmentList;
    Spinner divSpinner, depSpinner;
    private ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayAdapter divSpinnerAdapter, depSpinnerAdapter;
    private List<Division> departments;
    TextView txtDiv, txtDep;
    Guest guest;
    String divItem, depItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guest_permit);

        calGuest = findViewById(R.id.edit_cal_guest);
        edtDate = findViewById(R.id.edit_date_guest);
        edtName = findViewById(R.id.edit_name_guest);
        edtCompany = findViewById(R.id.edit_company_guest);
        edtPhone = findViewById(R.id.edit_phone_guest);
        edtPic = findViewById(R.id.edit_pic_guest);
        edtNecessity = findViewById(R.id.edit_necessity_guest);
        img_timeout = findViewById(R.id.edit_img_guest_timeout);
        img_timein = findViewById(R.id.edit_img_guest_timein);
        edtTimeout = findViewById(R.id.edit_timeout_guest);
        edtTimein = findViewById(R.id.edit_timein_guest);
        divSpinner = findViewById(R.id.edit_guest_div_spinner);
        depSpinner = findViewById(R.id.edit_guest_dep_spinner);
        monitoring = findViewById(R.id.btn_monitoring_guest);
        txtDiv = findViewById(R.id.edit_division_guest);
        txtDep = findViewById(R.id.edit_department_guest);
        submit = findViewById(R.id.edit_submit_guest);
        progressDialog = new ProgressDialog(EditGuestPermitActivity.this);

        guest = getIntent().getParcelableExtra("MAIN_EDIT_GUEST_PERMIT");
        edtName.setText(guest.getName());
        edtCompany.setText(guest.getCompany());
        edtPhone.setText(guest.getPhone());
        edtPic.setText(guest.getPic());
        edtNecessity.setText(guest.getNecessity());
        edtDate.setText(guest.getDate());
        edtTimein.setText(guest.getTimeIn());
        edtTimeout.setText(guest.getTimeOut());
        txtDiv.setText(guest.getDivision());
        txtDep.setText(guest.getDepartment());

        java.util.Calendar calendar = java.util.Calendar.getInstance();
        final int year = calendar.get(java.util.Calendar.YEAR);
        final int month = calendar.get(java.util.Calendar.MONTH);
        final int day = calendar.get(java.util.Calendar.DAY_OF_MONTH);

        getDivision();
        divisionList = new ArrayList<>();
        divSpinnerAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, divisionList);
        divSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        divSpinner.setAdapter(divSpinnerAdapter);

        departmentList = new ArrayList<>();
        divSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                divItem = divSpinner.getSelectedItem().toString();
                txtDiv.setText(divItem);
                departmentList.clear();
                for (String mDepartment : departments.get(i).getDepartment()){
                    departmentList.add(mDepartment);
                }
                depSpinnerAdapter = new ArrayAdapter<>(EditGuestPermitActivity.this,
                        android.R.layout.simple_spinner_dropdown_item, departmentList);
                depSpinner.setAdapter(depSpinnerAdapter);
                depSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        depItem = depSpinner.getSelectedItem().toString();
                        txtDep.setText(depItem);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        calGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditGuestPermitActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        
        submit.setOnClickListener(view -> {
            saveData();
        });
    }

    private void saveData() {
        db.collection("permission_guest").document(guest.getId())
                .update(
                        "name", edtName.getText().toString(),
                        "company", edtCompany.getText().toString(),
                        "phone", edtPhone.getText().toString(),
                        "division", txtDiv.getText().toString(),
                        "department", txtDep.getText().toString(),
                        "pic", edtPic.getText().toString(),
                        "necessity", edtNecessity.getText().toString(),
                        "date", edtDate.getText().toString(),
                        "timein", edtTimein.getText().toString(),
                        "timeout", edtTimeout.getText().toString()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditGuestPermitActivity.this, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditGuestPermitActivity.this, "Gagal mengubah data", Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
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

    private void getDivision() {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        db.collection("division").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                departments = queryDocumentSnapshots.toObjects(Division.class);
                progressDialog.hide();
                if (queryDocumentSnapshots.size()>0) {
                    divisionList.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        divisionList.add(doc.getString("name"));
                    }
                    divSpinnerAdapter.notifyDataSetChanged();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.hide();
                Toast.makeText(EditGuestPermitActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}