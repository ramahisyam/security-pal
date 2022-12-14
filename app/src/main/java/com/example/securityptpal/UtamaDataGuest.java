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

import com.example.securityptpal.adapter.MainGoodsPermitAdapter;
import com.example.securityptpal.adapter.MainGuestAdapter;
import com.example.securityptpal.adapter.OnPermitListener;
import com.example.securityptpal.main.AkunUtama;
import com.example.securityptpal.main.EditGoodsPermitActivity;
import com.example.securityptpal.main.EditGuestPermitActivity;
import com.example.securityptpal.main.UtamaDataEmployee;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.Guest;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class UtamaDataGuest extends AppCompatActivity implements OnPermitListener {

    DrawerLayout drawerLayout;
    ImageView btMenu;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    RecyclerView recyclerView;
    SearchView searchView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressDialog progressDialog;
    private MainGuestAdapter mainGuestAdapter;
    private List<Guest> list = new ArrayList<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_guest);

        recyclerView = findViewById(R.id.rv_main_guest_permit);
        searchView = findViewById(R.id.main_search_guest);
        mSwipeRefreshLayout = findViewById(R.id.refresh_main_guest_permit);
        progressDialog = new ProgressDialog(UtamaDataGuest.this);
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.main_guest_fab);
        fab1 = (FloatingActionButton) findViewById(R.id.main_guest_fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.main_guest_fab2);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
//                    if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
//                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
//                        requestPermissions(permissions, 1);
//                    } else {
//                        importData();
//                    }
//                } else {
//                    importData();
//                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                String foldername = "Import Excel";
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setDataAndType(Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + foldername), "*/*");
//                startActivity(intent);
            }
        });
    }

    private void animateFab(){
        if (isOpen){
            fab.startAnimation(rotateBackward);
            fab1.startAnimation(fabClose);
            fab2.startAnimation(fabClose);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isOpen=false;
        }
        else{
            fab.startAnimation(rotateForward);
            fab1.startAnimation(fabOpen);
            fab2.startAnimation(fabOpen);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isOpen=true;
        }

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

        mainGuestAdapter = new MainGuestAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(mainGuestAdapter);
        mainGuestAdapter.setDialog(new MainGuestAdapter.Dialog() {
            @Override
            public void onClick(int pos) {
                final CharSequence[] dialogItem = {"Edit", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(UtamaDataGuest.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                Intent intentEdit = new Intent(getApplicationContext(), EditGuestPermitActivity.class);
                                intentEdit.putExtra("MAIN_EDIT_GUEST_PERMIT", list.get(pos));
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
        db.collection("permission_guest").document(id)
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
                        showAllData();
                    }
                });
    }

    private void showAllData(){
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
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
                                        document.getString("timeOut")
                                );
                                list.add(guest);
                            }
                            mainGuestAdapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } else {
                            Toast.makeText(UtamaDataGuest.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UtamaDataGuest.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void searchData(String name) {
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
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
                                        document.getString("timeOut")
                                );
                                list.add(guest);
                            }
                            mainGuestAdapter.notifyDataSetChanged();
                            progressDialog.hide();
                        } else {
                            Toast.makeText(UtamaDataGuest.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                            progressDialog.hide();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UtamaDataGuest.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();
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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AkunUtama.class));
        finish();
    }

    public void onPermitClick(int position) {
        Intent intent = new Intent(UtamaDataGuest.this, DetailGuestPermitActivity.class);
        intent.putExtra("MAIN_GUEST_PERMIT", list.get(position));
        startActivity(intent);
    }
}