package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.example.securityptpal.main.EditGuestPermitActivity;
import com.example.securityptpal.model.CheckUp;
import com.example.securityptpal.model.Division;
import com.example.securityptpal.model.Guest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class EditCheckupPermitActivity extends AppCompatActivity {
    ImageView dateCheckup;
    EditText edtDateCheckup, edtNameCheckup, edtNIPCheckup, edtOthersCheckup;
    Button submit;
    DatePickerDialog.OnDateSetListener setListener;
    ArrayList<String> divisionList, departmentList;
    Spinner divSpinner, depSpinner, sttsSpinner, typeSpinner;
    private ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ArrayAdapter divSpinnerAdapter, depSpinnerAdapter;
    private List<Division> departments;
    TextView txtDiv, txtDep, txtStts, txtType;
    CheckUp checkUp;
    String divItem, depItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_checkup_permit);
        txtDiv = findViewById(R.id.edit_division_checkup);
        txtDep = findViewById(R.id.edit_department_checkup);
        txtStts = findViewById(R.id.edit_stts_checkup);
        txtType = findViewById(R.id.edit_type_checkup);
        divSpinner = findViewById(R.id.edit_checkup_div_spinner);
        depSpinner = findViewById(R.id.edit_checkup_dep_spinner);
        sttsSpinner = findViewById(R.id.edit_checkup_stts_spinner);
        typeSpinner = findViewById(R.id.edit_checkup_type_spinner);
        edtDateCheckup = findViewById(R.id.edtDateCheckup);
        edtNameCheckup = findViewById(R.id.edtNameCheckup);
        edtNIPCheckup = findViewById(R.id.edtNIPCheckup);
        edtOthersCheckup = findViewById(R.id.edtOthersCheckup);
        submit = findViewById(R.id.edit_submit_visitor);
        dateCheckup = findViewById(R.id.dateCheckup);
        progressDialog = new ProgressDialog(EditCheckupPermitActivity.this);

        checkUp = getIntent().getParcelableExtra("MAIN_EDIT_CHECKUP_PERMIT");
        edtDateCheckup.setText(checkUp.getDate());
        edtNameCheckup.setText(checkUp.getName());
        edtNIPCheckup.setText(checkUp.getNip());
        edtOthersCheckup.setText(checkUp.getOthers());
        txtDiv.setText(checkUp.getDivision());
        txtDep.setText(checkUp.getDepartment());
        txtStts.setText(checkUp.getStatus());
        txtType.setText(checkUp.getType());

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
                depSpinnerAdapter = new ArrayAdapter<>(EditCheckupPermitActivity.this,
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

        ArrayList<String> statusList = new ArrayList<>();

        statusList.add("PKWT");
        statusList.add("PKWTT");

        sttsSpinner.setAdapter(new ArrayAdapter<>(EditCheckupPermitActivity.this,
                android.R.layout.simple_spinner_dropdown_item, statusList));

        sttsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        typeSpinner.setAdapter(new ArrayAdapter<>(EditCheckupPermitActivity.this,
                android.R.layout.simple_spinner_dropdown_item, typeList));

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        dateCheckup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditCheckupPermitActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month + 1;
                        String date = day+"-"+month+"-"+year;
                        edtDateCheckup.setText(date);
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
        db.collection("permission_checkup").document(checkUp.getId())
                .update(
                        "name", edtNameCheckup.getText().toString(),
                        "nip", edtNIPCheckup.getText().toString(),
                        "division", txtDiv.getText().toString(),
                        "department", txtDep.getText().toString(),
                        "status", txtStts.getText().toString(),
                        "date", edtDateCheckup.getText().toString(),
                        "type", txtType.getText().toString(),
                        "others", edtOthersCheckup.getText().toString()
                )
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(EditCheckupPermitActivity.this, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditCheckupPermitActivity.this, "Gagal mengubah data", Toast.LENGTH_SHORT).show();
                    }
                });
        finish();
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
                Toast.makeText(EditCheckupPermitActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}