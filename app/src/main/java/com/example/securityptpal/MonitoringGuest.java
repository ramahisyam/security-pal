package com.example.securityptpal;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.adapter.GuestAdapter;
import com.example.securityptpal.adapter.LatePermissionAdapter;
import com.example.securityptpal.model.Guest;
import com.example.securityptpal.model.PermissionLate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MonitoringGuest extends AppCompatActivity implements GuestAdapter.OnGuestListener{

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Guest> list = new ArrayList<>();
    private GuestAdapter guestAdapter;
    private SearchView searchView;
    private ProgressDialog progressDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_guest);
        recyclerView = findViewById(R.id.rv_monitoring_guest);
        progressDialog = new ProgressDialog(MonitoringGuest.this);
        searchView = findViewById(R.id.search_guest);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
//                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchData(newText);

                return false;
            }
        });

        guestAdapter = new GuestAdapter(getApplicationContext(), list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(guestAdapter);
        showAllData();
    }

    private void searchData(String name) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("permission_guest")
                .whereEqualTo("name", name)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Guest guest = new Guest(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("company"),
                                        document.getString("phone"),
                                        document.getString("division"),
                                        document.getString("department"),
                                        document.getString("pic"),
                                        document.getString("necessity"),
                                        document.getString("date"),
                                        document.getString("timeIn"),
                                        document.getString("timeOut"),
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(guest);
                            }
                            guestAdapter.notifyDataSetChanged();
                            progressDialog.hide();
                        } else {
                            Toast.makeText(MonitoringGuest.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MonitoringGuest.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
                    }
                });
    }

    private void showAllData(){
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog2);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("permission_guest")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Guest guest = new Guest(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("company"),
                                        document.getString("phone"),
                                        document.getString("division"),
                                        document.getString("department"),
                                        document.getString("pic"),
                                        document.getString("necessity"),
                                        document.getString("date"),
                                        document.getString("timeIn"),
                                        document.getString("timeOut"),
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(guest);
                            }
                            guestAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(MonitoringGuest.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MonitoringGuest.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onGuestClick(int position) {
        Intent intent = new Intent(MonitoringGuest.this, DetailGuestPermitActivity.class);
        intent.putExtra("MAIN_GUEST_PERMIT", list.get(position));
        startActivity(intent);
    }
}