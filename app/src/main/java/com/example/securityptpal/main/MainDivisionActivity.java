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
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.adapter.DivisionAdapter;
import com.example.securityptpal.adapter.PermissionEmployeeAdapter;
import com.example.securityptpal.model.Division;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainDivisionActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchView searchView;
    private List<Division> list = new ArrayList<>();
    private DivisionAdapter divisionAdapter;
    private ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_division);
        recyclerView = findViewById(R.id.rv_main_division);
        searchView = findViewById(R.id.main_search_division);
        fab = findViewById(R.id.fab_add_division);
        progressDialog = new ProgressDialog(MainDivisionActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting data...");

        divisionAdapter = new DivisionAdapter(this, list);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(divisionAdapter);

        fab.setOnClickListener(view -> {
            startActivity(new Intent(this, AddDivisionActivity.class));
        });

        divisionAdapter.setDialog(new DivisionAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainDivisionActivity.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intent = new Intent(MainDivisionActivity.this, EditDivisionActivity.class);
                                intent.putExtra("DIVISION_EDIT", list.get(pos));
                                startActivity(intent);
                                break;
                            case 1:
                                deleteData(list.get(pos).getId());
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        showAllData();
    }

    private void showAllData() {
        db.collection("division")
                .orderBy("name", Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Division division = new Division(
                                        document.getId(),
                                        document.getString("name")
                                );
                                list.add(division);
                            }
                            divisionAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MainDivisionActivity.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
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
                        showAllData();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        showAllData();
    }
}