package com.example.securityptpal.main;

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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.Login;
import com.example.securityptpal.LogoutAccount;
import com.example.securityptpal.Preferences;
import com.example.securityptpal.R;
import com.example.securityptpal.adapter.PermissionEmployeeAdapter;
import com.example.securityptpal.division.AkunDivisi;
import com.example.securityptpal.division.DetailExitActivity;
import com.example.securityptpal.division.ExitPermissionActivity;
import com.example.securityptpal.employee.DetailPermissionActivity;
import com.example.securityptpal.employee.Employee;
import com.example.securityptpal.employee.MonitoringEmployee;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.util.ArrayList;
import java.util.List;

public class UtamaDataEmployee extends AppCompatActivity implements PermissionEmployeeAdapter.OnPermitListener{

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<PermissionEmployee> list = new ArrayList<>();
    private PermissionEmployeeAdapter permissionEmployeeAdapter;
    private SearchView searchView;
    private Intent intent;
    private ImageView imgSignOut;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_employee);
        recyclerView = findViewById(R.id.rv_main_exit_permit);
        searchView = findViewById(R.id.main_search_permission);
        imgSignOut = findViewById(R.id.security_sign_out_permission);
        userID = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userID);
        documentReference.addSnapshotListener(UtamaDataEmployee.this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.getString("role").equals("security")) {
                    imgSignOut.setVisibility(View.VISIBLE);
                    imgSignOut.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            LogoutAccount.logout(UtamaDataEmployee.this);
                        }
                    });
                }
            }
        });
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showAllData();
                return false;
            }
        });

        permissionEmployeeAdapter = new PermissionEmployeeAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(permissionEmployeeAdapter);

        showAllData();
    }

    private void showAllData() {
        db.collection("permission_employee")
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
                            Toast.makeText(UtamaDataEmployee.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UtamaDataEmployee.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
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
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(permissionEmployee);
                            }
                            permissionEmployeeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(UtamaDataEmployee.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UtamaDataEmployee.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onPermitClick(int position) {
        intent = new Intent(UtamaDataEmployee.this, DetailExitActivity.class);
        intent.putExtra("EXIT_PERMIT", list.get(position));
        startActivity(intent);
    }
}