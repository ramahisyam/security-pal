package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.securityptpal.adapter.MonitoringGoodsPermitAdapter;
import com.example.securityptpal.adapter.OnPermitListener;
import com.example.securityptpal.adapter.OnPermitLongClick;
import com.example.securityptpal.adapter.SubconAdapter;
import com.example.securityptpal.employee.Employee;
import com.example.securityptpal.main.EditSubconPermitActivity;
import com.example.securityptpal.main.MainDivisionActivity;
import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.Guest;
import com.example.securityptpal.model.Division;
import com.example.securityptpal.model.EmployeeSubcon;
import com.example.securityptpal.model.PermissionEmployee;
import com.example.securityptpal.model.Subcon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.util.ArrayList;
import java.util.List;

public class MonitoringSubcontractor extends AppCompatActivity implements OnPermitListener, OnPermitLongClick{
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Subcon> list = new ArrayList<>();
    private SubconAdapter subconAdapter;
    private ProgressDialog progressDialog;
    private SearchView searchView;
    String userID;
    FirebaseAuth mAuth;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_subcontractor);
        recyclerView = findViewById(R.id.rv_monitoring_subcon);
        searchView = findViewById(R.id.search_subcon);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //searchData(newText);

                return false;
            }
        });
        progressDialog = new ProgressDialog(MonitoringSubcontractor.this);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();

        subconAdapter = new SubconAdapter(this, list, this, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(subconAdapter);

        showAllDataDesc();
    }

    private void searchData(String company) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("subcontractor")
                .whereEqualTo("company", company)
                .orderBy("startDate", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Subcon subcon = new Subcon(
                                        document.getId(),
                                        document.getString("company"),
                                        document.getString("phone"),
                                        document.getString("necessity"),
                                        document.getString("division"),
                                        document.getString("department"),
                                        document.getString("startDate"),
                                        document.getString("finishDate"),
                                        document.getString("userID"),
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(subcon);
                            }
                            subconAdapter.notifyDataSetChanged();
                            progressDialog.hide();
                        } else {
                            StyleableToast.makeText(getApplicationContext(), "Data Failed to Load", Toast.LENGTH_SHORT, R.style.warning).show();
                            progressDialog.hide();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StyleableToast.makeText(getApplicationContext(), "Data Not Found!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                    }
                });
    }

    private void showAllDataDesc() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("subcontractor").whereEqualTo("userID", userID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                if (value != null) {
                    for (QueryDocumentSnapshot document : value) {
                        Subcon subcon = new Subcon(
                                document.getId(),
                                document.getString("company"),
                                document.getString("phone"),
                                document.getString("necessity"),
                                document.getString("division"),
                                document.getString("department"),
                                document.getString("startDate"),
                                document.getString("finishDate"),
                                document.getString("userID"),
                                document.getString("division_approval"),
                                document.getString("center_approval")
                        );
                        list.add(subcon);
                    }
                    subconAdapter.notifyDataSetChanged();
                } else {
                    StyleableToast.makeText(getApplicationContext(),"Load Data Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void deleteData(String id) {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Deleting data...");
        progressDialog.show();
        db.collection("subcontractor").document(id)
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Data gagal di hapus!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Data berhasil di hapus!", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                        showAllDataDesc();
                    }
                });
    }

    private void addEmployee(List<Subcon> subcons, int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(MonitoringSubcontractor.this);
        builder.setTitle("Add Employee");
        View view = getLayoutInflater().inflate(R.layout.add_employee_subcon, null);

        EditText edtName = (EditText) view.findViewById(R.id.name_employee);
        EditText edtAge = (EditText) view.findViewById(R.id.age_employee);
        Button btnSubmit = view.findViewById(R.id.btn_add_employee);

        btnSubmit.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtAge.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                Alerter.create(MonitoringSubcontractor.this)
                        .setTitle("Add Data Failed!")
                        .setText("Please fill all the data")
                        .setIcon(R.drawable.ic_close)
                        .setBackgroundColorRes(android.R.color.holo_red_dark)
                        .setDuration(2000)
                        .enableSwipeToDismiss()
                        .enableProgress(true)
                        .setProgressColorRes(R.color.design_default_color_primary)
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        })
                        .setOnShowListener(new OnShowAlertListener() {
                            @Override
                            public void onShow() {

                            }
                        })
                        .setOnHideListener(new OnHideAlertListener() {
                            @Override
                            public void onHide() {

                            }
                        })
                        .show();
            } else {
                EmployeeSubcon employeeSubcon = new EmployeeSubcon(
                        db.collection("subcontractor").document(subcons.get(pos).getId()).collection("employee").document().getId(),
                        edtName.getText().toString(),
                        edtAge.getText().toString()
                );
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Sending data...");
                progressDialog.show();
                db.collection("subcontractor")
                        .document(subcons.get(pos).getId())
                        .collection("employee")
                        .add(employeeSubcon)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(MonitoringSubcontractor.this, "Success adding employee", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MonitoringSubcontractor.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                dialog.dismiss();
                progressDialog.dismiss();
            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onPermitClick(int position) {
        Intent intent = new Intent(MonitoringSubcontractor.this, DetailSubconActivity.class);
        intent.putExtra("SUBCON_DETAIL", list.get(position));
        startActivity(intent);
    }

    @Override
    public void onLongCLickListener(int pos) {
        final CharSequence[] dialogItem = {"Add Employee","Edit", "Delete"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(MonitoringSubcontractor.this);
        dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        addEmployee(list, pos);
                        break;
                    case 1:
                        Intent intentEdit = new Intent(getApplicationContext(), EditSubconPermitActivity.class);
                        intentEdit.putExtra("MAIN_EDIT_SUBCON_PERMIT", list.get(pos));
                        startActivity(intentEdit);
                        break;
                    case 2:
                        deleteData(list.get(pos).getId());
                        break;
                }
            }
        });
        dialog.show();
    }
}