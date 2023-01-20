package com.example.securityptpal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.example.securityptpal.adapter.MainSubconAdapter;
import com.example.securityptpal.adapter.OnPermitListener;
import com.example.securityptpal.adapter.OnPermitLongClick;
import com.example.securityptpal.adapter.SubconAdapter;
import com.example.securityptpal.main.AkunUtama;
import com.example.securityptpal.main.EditExitPermitActivity;
import com.example.securityptpal.main.EditSubconPermitActivity;
import com.example.securityptpal.main.MainDivisionActivity;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.example.securityptpal.model.Subcon;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.muddzdev.styleabletoast.StyleableToast;

import java.util.ArrayList;
import java.util.List;

public class UtamaDataSubcon extends AppCompatActivity implements OnPermitListener, OnPermitLongClick {
    DrawerLayout drawerLayout;
    ImageView btMenu;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<Subcon> list = new ArrayList<>();
    private MainSubconAdapter mainSubconAdapter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_subcon);
        recyclerView = findViewById(R.id.rv_main_subcon);
        progressDialog = new ProgressDialog(UtamaDataSubcon.this);

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        fab = (FloatingActionButton) findViewById(R.id.main_sub_fab);
        fab1 = (FloatingActionButton) findViewById(R.id.main_sub_fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.main_sub_fab2);

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

        mainSubconAdapter = new MainSubconAdapter(this, list, this, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(mainSubconAdapter);

        showAllData();
    }

    private void showAllData() {
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog1);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        db.collection("subcontractor").addSnapshotListener(new EventListener<QuerySnapshot>() {
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
                    mainSubconAdapter.notifyDataSetChanged();
                } else {
                    StyleableToast.makeText(getApplicationContext(),"Load Data Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                }
                progressDialog.dismiss();
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

    @Override
    public void onLongCLickListener(int pos) {
        final CharSequence[] dialogItem = {"Edit", "Delete"};
        AlertDialog.Builder dialog = new AlertDialog.Builder(UtamaDataSubcon.this);
        dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
//                                editData(list, pos);
                        Intent intentEdit = new Intent(getApplicationContext(), EditSubconPermitActivity.class);
                        intentEdit.putExtra("MAIN_EDIT_SUBCON_PERMIT", list.get(pos));
                        startActivity(intentEdit);
//                                Toast.makeText(UtamaDataEmployee.this, "coming soon", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
//                        deleteData(list.get(pos).getId());
                        break;
                }
            }
        });
        dialog.show();
    }
}