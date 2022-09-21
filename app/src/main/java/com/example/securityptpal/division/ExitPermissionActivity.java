package com.example.securityptpal.division;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.adapter.PermissionEmployeeAdapter;
import com.example.securityptpal.employee.DetailPermissionActivity;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ExitPermissionActivity extends AppCompatActivity implements PermissionEmployeeAdapter.OnPermitListener {

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<PermissionEmployee> list = new ArrayList<>();
    private PermissionEmployeeAdapter permissionEmployeeAdapter;
    private SearchView searchView;
    private Intent intent;
    private String EXTRA;
    String userID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_permission);
        recyclerView = findViewById(R.id.rv_exit_permission);
        searchView = findViewById(R.id.search_employee_permit);

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showDataDivision(EXTRA);
                return false;
            }
        });

        permissionEmployeeAdapter = new PermissionEmployeeAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(permissionEmployeeAdapter);

        Bundle extras = getIntent().getExtras();
        EXTRA = extras.getString(Intent.EXTRA_TEXT);

        showDataDivision(EXTRA);
    }

    private void showDataDivision(String division) {
        db.collection("permission_employee")
                .whereEqualTo("division", division)
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
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(permissionEmployee);
                            }
                            permissionEmployeeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ExitPermissionActivity.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ExitPermissionActivity.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void searchData(String nip) {
        db.collection("permission_employee")
                .whereEqualTo("nip", nip)
                .whereEqualTo("division", "design")
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
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(permissionEmployee);
                            }
                            permissionEmployeeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ExitPermissionActivity.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ExitPermissionActivity.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onPermitClick(int position) {
        intent = new Intent(ExitPermissionActivity.this, DetailExitActivity.class);
//        db.collection("permission_employee").document().getId()
        intent.putExtra("EXIT_PERMIT", list.get(position));
        startActivity(intent);
    }
}