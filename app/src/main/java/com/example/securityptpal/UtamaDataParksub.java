package com.example.securityptpal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.adapter.MainMasukParksubAdapter;
import com.example.securityptpal.adapter.OnPermitListener;
import com.example.securityptpal.main.AkunUtama;
import com.example.securityptpal.main.MainDivisionActivity;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.example.securityptpal.model.MasukPS;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class UtamaDataParksub extends AppCompatActivity implements OnPermitListener {
    DrawerLayout drawerLayout;
    ImageView btMenu;
    private RecyclerView recyclerView;
    private Button btnRekapMasuk, btnRekapKeluar;
    private ProgressDialog progressDialog;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<MasukPS> list = new ArrayList<>();
    private MainMasukParksubAdapter masukParksubAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_parksub);

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);
        btnRekapMasuk = findViewById(R.id.rekap_masuk);
        btnRekapKeluar = findViewById(R.id.rekap_keluar);
        progressDialog = new ProgressDialog(UtamaDataParksub.this);
        recyclerView = findViewById(R.id.rv_parksub);

        masukParksubAdapter = new MainMasukParksubAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(masukParksubAdapter);

        btnRekapMasuk.setOnClickListener(view -> {
            showAllDataMasuk();
        });

        btnRekapKeluar.setOnClickListener(view -> {
            showAllDataKeluar();
        });

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    private void showAllDataMasuk() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("masuk_parksub").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                if (value != null) {
                    for (QueryDocumentSnapshot document : value) {
                        MasukPS masukPS = new MasukPS(
                                document.getId(),
                                document.getString("name"),
                                document.getString("nopol"),
                                document.getString("date"),
                                document.getString("queue")
                        );
                        list.add(masukPS);
                    }
                    masukParksubAdapter.notifyDataSetChanged();
                } else {
                    StyleableToast.makeText(UtamaDataParksub.this,"Load Data Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                }
                progressDialog.dismiss();
            }
        });
    }

    private void showAllDataKeluar() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("keluar_parksub").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                list.clear();
                if (value != null) {
                    for (QueryDocumentSnapshot document : value) {
                        MasukPS masukPS = new MasukPS(
                                document.getId(),
                                document.getString("name"),
                                document.getString("nopol"),
                                document.getString("date"),
                                document.getString("queue")
                        );
                        list.add(masukPS);
                    }
                    masukParksubAdapter.notifyDataSetChanged();
                } else {
                    StyleableToast.makeText(UtamaDataParksub.this,"Load Data Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                }
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AkunUtama.class));
        finish();
    }

    @Override
    public void onPermitClick(int position) {

    }
}