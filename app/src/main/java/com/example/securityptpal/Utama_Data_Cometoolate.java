package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.adapter.LatePermissionAdapter;
import com.example.securityptpal.adapter.PermissionEmployeeAdapter;
import com.example.securityptpal.main.DetailExitPermissionActivity;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.example.securityptpal.model.PermissionEmployee;
import com.example.securityptpal.model.PermissionLate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Utama_Data_Cometoolate extends AppCompatActivity implements LatePermissionAdapter.OnLateListener{

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<PermissionLate> list = new ArrayList<>();
    private LatePermissionAdapter latePermissionAdapter;
    private SearchView searchView;
    private Intent intent;
    private ImageView imgSignOut;
    private String userID;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_cometoolate);
        recyclerView = findViewById(R.id.rv_main_late_permit);
        searchView = findViewById(R.id.search_late_permission);
        imgSignOut = findViewById(R.id.sign_out_permission_late);
        progressDialog = new ProgressDialog(Utama_Data_Cometoolate.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting data...");
//        userID = mAuth.getCurrentUser().getUid();

        latePermissionAdapter = new LatePermissionAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(latePermissionAdapter);
    }

    private void showAllData() {
        db.collection("permission_late")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PermissionLate permissionLate = new PermissionLate(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("nip"),
                                        document.getString("division"),
                                        document.getString("reason"),
                                        document.getString("img"),
                                        document.getString("date"),
                                        document.getString("device"),
                                        document.getString("latitude"),
                                        document.getString("longitude"),
                                        document.getString("location")
                                );
                                list.add(permissionLate);
                            }
                            latePermissionAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(Utama_Data_Cometoolate.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Utama_Data_Cometoolate.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        showAllData();
    }

    @Override
    public void onLateClick(int position) {
        intent = new Intent(Utama_Data_Cometoolate.this, UtamaCometoolate.class);
        intent.putExtra("MAIN_LATE_PERMIT", list.get(position));
        startActivity(intent);
    }
}