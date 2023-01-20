package com.example.securityptpal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.securityptpal.adapter.MonitoringGoodsPermitAdapter;
import com.example.securityptpal.adapter.OnPermitListener;
import com.example.securityptpal.adapter.OnPermitLongClick;
import com.example.securityptpal.adapter.SubconAdapter;
import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.PermissionEmployee;
import com.example.securityptpal.model.Subcon;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class MonitoringSubcontractor extends AppCompatActivity implements OnPermitListener, OnPermitLongClick {
    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Subcon> list = new ArrayList<>();
    private SubconAdapter subconAdapter;
    private ProgressDialog progressDialog;
//    private SearchView searchView;
    String userID;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_subcontractor);
        recyclerView = findViewById(R.id.rv_monitoring_subcon);
//        searchView = findViewById(R.id.search_goods);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Subcontractor.class));
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
        Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
    }
}