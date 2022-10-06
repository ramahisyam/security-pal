package com.example.securityptpal.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.employee.Employee;
import com.example.securityptpal.model.Division;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDivisionActivity extends AppCompatActivity {

    EditText edtName;
    Button btnSubmit;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ProgressDialog progressDialog;
    Division mDivision;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_division);
        edtName = findViewById(R.id.name_division);
        btnSubmit = findViewById(R.id.btn_add_division);
        progressDialog = new ProgressDialog(AddDivisionActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Menyimpan...");

        mDivision = getIntent().getParcelableExtra("DIVISION_EDIT");
        if (mDivision != null) {
            edtName.setText(mDivision.getName());
        }

        btnSubmit.setOnClickListener(view -> {
            saveData(
                    db.collection("division").document().getId(),
                    edtName.getText().toString()
            );
        });
    }

    private void saveData(String id, String name) {
        progressDialog.show();

        Division division = new Division(
                id,
                name
        );

        if (mDivision.getId().length()>0) { //edit data
            db.collection("division").document(mDivision.getId())
                    .set(division)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(AddDivisionActivity.this, "Berhasil diperbarui", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        } else { //add data
            db.collection("division").add(division)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(AddDivisionActivity.this, "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
        }
    }
}