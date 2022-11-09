package com.example.securityptpal.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.adapter.DivisionAdapter;
import com.example.securityptpal.model.Division;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainDivisionActivity extends AppCompatActivity implements DivisionAdapter.OnDivisionListener {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Division> list = new ArrayList<>();
    private DivisionAdapter divisionAdapter;
    private ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton fab;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_division);
        recyclerView = findViewById(R.id.rv_main_division);
        searchView = findViewById(R.id.main_search_division);
        fab = findViewById(R.id.fab_add_division);
        progressDialog = new ProgressDialog(MainDivisionActivity.this);

        divisionAdapter = new DivisionAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(divisionAdapter);

        fab.setOnClickListener(view -> {
            showAddDialog();
        });

        divisionAdapter.setDialog(new DivisionAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Add Department", "Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainDivisionActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                addDepartment(list.get(pos).getId());
                                break;
                            case 1:
                                editData(list, pos);
                                break;
                            case 2:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    private void showAllData() {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting data...");
        progressDialog.show();
        db.collection("division")
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        list.clear();
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            Division mDivision = documentSnapshot.toObject(Division.class);

                            Division division = new Division(
                                    documentSnapshot.getId(),
                                    documentSnapshot.getString("name"),
                                    mDivision.getDepartment()
                            );
                            list.add(division);
                        }
                        divisionAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void deleteData(String id){
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Deleting data...");
        progressDialog.show();
        db.collection("division").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data gagal di hapus!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        onStart();
                    }
                });
    }

    private void editData(List<Division> divisions, int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainDivisionActivity.this);
        builder.setTitle("Edit Division");
        View view = getLayoutInflater().inflate(R.layout.editor_division_dialog, null);

        EditText edtName = view.findViewById(R.id.name_division);
        Button btnSubmit = view.findViewById(R.id.btn_add_division);

        edtName.setText(divisions.get(pos).getName());

        btnSubmit.setOnClickListener(view1 -> {
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Sending data...");
            progressDialog.show();
            db.collection("division").document(divisions.get(pos).getId())
                    .update("name", edtName.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainDivisionActivity.this, "Berhasil mengubah data", Toast.LENGTH_SHORT).show();
                            onStart();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainDivisionActivity.this, "Gagal mengubah data", Toast.LENGTH_SHORT).show();
                        }
                    });
            dialog.dismiss();
            onStart();
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void addDepartment(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainDivisionActivity.this);
        builder.setTitle("Add Department");
        View view = getLayoutInflater().inflate(R.layout.editor_division_dialog, null);

        EditText edtName = view.findViewById(R.id.name_division);
        Button btnSubmit = view.findViewById(R.id.btn_add_division);

        btnSubmit.setOnClickListener(view1 -> {
            progressDialog.setTitle("Loading");
            progressDialog.setMessage("Sending data...");
            progressDialog.show();

            db.collection("division").document(id)
                    .update("department", FieldValue.arrayUnion(edtName.getText().toString()))
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainDivisionActivity.this, "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });
            dialog.dismiss();
            onStart();
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void showAddDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainDivisionActivity.this);
        builder.setTitle("Add Division");
        View view = getLayoutInflater().inflate(R.layout.editor_division_dialog, null);

        EditText edtName = view.findViewById(R.id.name_division);
        Button btnSubmit = view.findViewById(R.id.btn_add_division);

        btnSubmit.setOnClickListener(view1 -> {
            saveData(
                    db.collection("division").document().getId(),
                    edtName.getText().toString()
            );
            dialog.dismiss();
            onStart();
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    private void saveData(String id, String name) {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Sending data...");
        progressDialog.show();

        List<String> department = new ArrayList<>();

        Division division = new Division(
                id,
                name,
                department
        );

        db.collection("division").add(division)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(MainDivisionActivity.this, "Data berhasil dikirim", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainDivisionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        showAllData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showAllData();
    }

    @Override
    public void onDivisionClick(int position) {
        Intent intent = new Intent(MainDivisionActivity.this, DetailDivisionActivity.class);
        intent.putExtra("division", list.get(position));
        startActivity(intent);
    }
}