package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.adapter.PermissionEmployeeAdapter;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MonitoringEmployee extends AppCompatActivity implements PermissionEmployeeAdapter.OnPermitListener {

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<PermissionEmployee> list = new ArrayList<>();
    private PermissionEmployeeAdapter permissionEmployeeAdapter;
    private SearchView searchView;
    private ImageView imgSignOut;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_employee);
        recyclerView = findViewById(R.id.rv_monitoring_employee);
        searchView = findViewById(R.id.search_permission);
        imgSignOut = findViewById(R.id.sign_out_monitoring_permission);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);
                return false;
            }
        });

        permissionEmployeeAdapter = new PermissionEmployeeAdapter(getApplicationContext(), list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(permissionEmployeeAdapter);

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAccount.logout(MonitoringEmployee.this);
            }
        });
    }

    private void searchData(String nip) {
        db.collection("permission_employee")
                .whereEqualTo("nip", nip)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PermissionEmployee permissionEmployee = new PermissionEmployee(
                                        document.getString("base"),
                                        document.getString("name"),
                                        document.getString("nip"),
                                        document.getString("division"),
                                        document.getString("date"),
                                        document.getString("necessity"),
                                        document.getString("place"),
                                        document.getString("timeout"),
                                        document.getString("timeback"),
                                        document.getString("status")
                                );
                                intent = new Intent(MonitoringEmployee.this, DetailPermissionActivity.class);
                                intent.putExtra("permission", permissionEmployee);
                                list.add(permissionEmployee);
                            }
                            permissionEmployeeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(MonitoringEmployee.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MonitoringEmployee.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPermitClick(int position) {
        startActivity(intent);
    }
}