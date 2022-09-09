package com.example.securityptpal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class MonitoringGuest extends AppCompatActivity {

//    DrawerLayout drawerLayout;
//    ImageView btMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_guest);

//        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        btMenu = (ImageView) findViewById(R.id.bt_menu);
//
//        btMenu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawerLayout.openDrawer(GravityCompat.END);
//            }
//        });
    }
//    public void ClickLogo(View view){
//        Guest.closeDrawer(drawerLayout);
//    }
//
//    public void ClickFillData(View view){
//        Guest.redirectActivity(this, Guest.class);
//    }
//
//    public void ClickMonitoring(View view){
//        Guest.redirectActivity(this, MonitoringGuest.class);
//    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Guest.class));
        finish();
    }
}