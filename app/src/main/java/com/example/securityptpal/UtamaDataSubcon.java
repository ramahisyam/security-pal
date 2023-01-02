package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.securityptpal.main.AkunUtama;
import com.example.securityptpal.main.MainDivisionActivity;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UtamaDataSubcon extends AppCompatActivity {
    DrawerLayout drawerLayout;
    ImageView btMenu;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_subcon);

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
}