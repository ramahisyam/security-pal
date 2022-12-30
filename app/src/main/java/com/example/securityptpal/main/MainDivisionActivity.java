package com.example.securityptpal.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.UtamaDataBarang;
import com.example.securityptpal.UtamaDataCheckup;
import com.example.securityptpal.UtamaDataGuest;
import com.example.securityptpal.UtamaDataParksub;
import com.example.securityptpal.UtamaDataSubcon;
import com.example.securityptpal.UtamaDataVisitor;
import com.example.securityptpal.Utama_Data_Cometoolate;
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
    DrawerLayout drawerLayout;
    ImageView btMenu, btnFilter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_division);
        recyclerView = findViewById(R.id.rv_main_division);
        searchView = findViewById(R.id.main_search_division);
        fab = findViewById(R.id.fab_add_division);
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
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

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMain(View view){
        AkunUtama.redirectActivity(this, AkunUtama.class);
    }

    public void ClickEmployee(View view){
        AkunUtama.redirectActivity(this, UtamaDataEmployee.class);
        finish();
    }

    public void ClickComelate(View view){
        AkunUtama.redirectActivity(this, Utama_Data_Cometoolate.class);
    }

    public void ClickCheckup(View view){ AkunUtama.redirectActivity(this, UtamaDataCheckup.class); }

    public void ClickSubcon(View view){ AkunUtama.redirectActivity(this, UtamaDataSubcon.class); }

    public void ClickParksub(View view){ AkunUtama.redirectActivity(this, UtamaDataParksub.class); }

    public void ClickGuest(View view){
        AkunUtama.redirectActivity(this, UtamaDataGuest.class);
    }

    public void ClickVisitor(View view){
        AkunUtama.redirectActivity(this, UtamaDataVisitor.class);
    }

    public void ClickGoods(View view){
        AkunUtama.redirectActivity(this, UtamaDataBarang.class);
    }

    public void ClickEdit(View view){
        AkunUtama.redirectActivity(this, MainDivisionActivity.class);
    }

    public void ClickExit(View view){
        AkunUtama.exit(this);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
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