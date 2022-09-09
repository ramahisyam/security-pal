package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity {
    boolean isPressed = false;
    ImageView orang, barang;
    DrawerLayout drawerLayout;
    ImageView btMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        orang = findViewById(R.id.orang);
        barang = findViewById(R.id.barang);
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        orang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });
        barang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Barang.class);
                startActivity(intent);
            }
        });
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.END)){
            drawerLayout.closeDrawer(GravityCompat.END);
        }
    }

    public void ClickMain(View view){
        redirectActivity(this, MainActivity.class);
        finish();
    }

    public void ClickFaq(View view){
        redirectActivity(this, FAQ.class);
    }

    public void ClickAboutus(View view){
        redirectActivity(this, AboutUs.class);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    public void ClickExit(View view){
        exit(this);
    }

    public static void exit(Activity activity){
        new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("EXIT")
                .setContentText("Are you sure want to exit ?")
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        activity.finishAffinity();
                        System.exit(0);
                    }
                })
                .setCancelButton("CANCEL", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    @Override
    public void onBackPressed(){
        if (isPressed){
            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("EXIT")
                    .setContentText("Are you sure want to exit ?")
                    .setConfirmText("OK")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            MainActivity.this.finishAffinity();
                            System.exit(0);
                        }
                    })
                    .setCancelButton("CANCEL", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();
        }else {
            StyleableToast.makeText(getApplicationContext(), "Please click back again to Exit!", Toast.LENGTH_SHORT, R.style.warning).show();
            isPressed = true;
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                isPressed = false;
            }
        };
        new Handler().postDelayed(runnable,2000);
    }
}