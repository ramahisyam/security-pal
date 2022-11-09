package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.adapter.MainEmployeePermitAdapter;
import com.example.securityptpal.adapter.MainGoodsPermitAdapter;
import com.example.securityptpal.adapter.OnPermitListener;
import com.example.securityptpal.main.AkunUtama;
import com.example.securityptpal.main.DetailExitPermissionActivity;
import com.example.securityptpal.main.EditExitPermitActivity;
import com.example.securityptpal.main.EditGoodsPermitActivity;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class UtamaDataBarang extends AppCompatActivity implements OnPermitListener {

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Barang> list = new ArrayList<>();
    private MainGoodsPermitAdapter mainGoodsPermitAdapter;
    private SearchView searchView;
    private Intent intent;
    private ImageView imgSignOut;
    private String userID;
    private ProgressDialog progressDialog;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    DrawerLayout drawerLayout;
    ImageView btMenu;
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_barang);
        recyclerView = findViewById(R.id.rv_main_goods_permit);
        searchView = findViewById(R.id.main_search_goods);
        mSwipeRefreshLayout = findViewById(R.id.refresh_main_goods_permit);
        progressDialog = new ProgressDialog(UtamaDataBarang.this);
//        fab = (FloatingActionButton) findViewById(R.id.main_employee_fab);
//        fab1 = (FloatingActionButton) findViewById(R.id.main_employee_fab1);
//        fab2 = (FloatingActionButton) findViewById(R.id.main_employee_fab2);

//        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
//        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

//        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
//        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
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
                return false;
            }
        });

        mainGoodsPermitAdapter = new MainGoodsPermitAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(mainGoodsPermitAdapter);
        mainGoodsPermitAdapter.setDialog(new MainGoodsPermitAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(UtamaDataBarang.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intentEdit = new Intent(getApplicationContext(), EditGoodsPermitActivity.class);
                                intentEdit.putExtra("MAIN_EDIT_GOODS_PERMIT", list.get(pos));
                                startActivity(intentEdit);
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
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                showAllData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void deleteData(String id) {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Deleting data...");
        progressDialog.show();
        db.collection("goods_permit").document(id)
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

    private void showAllData() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("goods_permit")
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Barang barang = new Barang(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("phone"),
                                        document.getString("pic"),
                                        document.getString("division"),
                                        document.getString("department"),
                                        document.getString("goods_name"),
                                        document.getString("type"),
                                        document.getString("img"),
                                        document.getString("date"),
                                        document.getString("device"),
                                        document.getString("latitude"),
                                        document.getString("longitude"),
                                        document.getString("location")
                                );
                                list.add(barang);
                            }
                            mainGoodsPermitAdapter.notifyDataSetChanged();
                        } else {
                            StyleableToast.makeText(getApplicationContext(),"Load Data Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UtamaDataBarang.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void searchData(String name) {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("goods_permit")
                .whereEqualTo("goods_name", name)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Barang barang = new Barang(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("phone"),
                                        document.getString("pic"),
                                        document.getString("division"),
                                        document.getString("department"),
                                        document.getString("goods_name"),
                                        document.getString("type"),
                                        document.getString("img"),
                                        document.getString("date"),
                                        document.getString("device"),
                                        document.getString("latitude"),
                                        document.getString("longitude"),
                                        document.getString("location")
                                );
                                list.add(barang);
                            }
                            mainGoodsPermitAdapter.notifyDataSetChanged();
                        } else {
                            StyleableToast.makeText(getApplicationContext(),"Load Data Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        StyleableToast.makeText(getApplicationContext(),"Data Not Found!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
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
        AkunUtama.redirectActivity(this, AkunUtama.class);
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
    public void onPermitClick(int position) {
        intent = new Intent(UtamaDataBarang.this, DetailBarangActivity.class);
        intent.putExtra("MAIN_GOODS_PERMIT", list.get(position));
        startActivity(intent);
    }
}