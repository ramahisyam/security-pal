package com.example.securityptpal.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.model.Division;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EditDivisionActivity extends AppCompatActivity {

    EditText edtName, edtDepartment;
    Button btnSubmit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    Division mDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_division);
        edtName = findViewById(R.id.name_division_edit);
        edtDepartment = findViewById(R.id.department_division_edit);
        btnSubmit = findViewById(R.id.btn_edit_division);
        progressDialog = new ProgressDialog(EditDivisionActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        mDivision = getIntent().getParcelableExtra("DIVISION_EDIT");
        String data = "";
        for (String department : mDivision.getDepartment()) {
            data += "," + department;
        }
        edtName.setText(mDivision.getName());
        edtDepartment.setText(data);

        btnSubmit.setOnClickListener(view -> {
//            saveData(
//                    db.collection("division").document().getId(),
//                    edtName.getText().toString()
//            );
        });
    }

    private void saveData(String id, String name, String department) {
        progressDialog.show();

//        Division division = new Division(
//                id,
//                name
//        );
//        db.collection("division").document(mDivision.getId())
//                .set(division)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(EditDivisionActivity.this, "Berhasil diperbarui", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(EditDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                });

//        db.collection("division").document(mDivision.getId())
//                .set(division)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        Toast.makeText(EditDivisionActivity.this, "Berhasil diperbarui", Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                        finish();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(EditDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
//                    }
//                });
    }
}