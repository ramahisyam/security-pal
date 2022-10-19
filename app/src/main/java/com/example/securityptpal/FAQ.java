package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.securityptpal.main.AkunUtama;

import java.util.ArrayList;
import java.util.List;

public class FAQ extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<DataModel> mList;
    private ItemAdapter adapter;
    DrawerLayout drawerLayout;
    ImageView btMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.END);
            }
        });

        recyclerView = findViewById(R.id.main_recyclervie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();

        //list1
        List<String> nestedList1 = new ArrayList<>();
        nestedList1.add("After you send permission and confirmation, the application will send your approved permit application containing the QR Code. At this stage, you can be sure that your permit has been approved");

        List<String> nestedList2 = new ArrayList<>();
        nestedList2.add("Employee -> Monitoring");
        nestedList2.add("Subcontractor -> Monitoring");
        nestedList2.add("Guest -> Monitoring -> Enter the permission code that you received when you did permission");

        List<String> nestedList3 = new ArrayList<>();
        nestedList3.add("Screenshot QR code is not allowed to be used as a valid document at the time of scanning");

        List<String> nestedList4 = new ArrayList<>();
        nestedList4.add(" ");

        List<String> nestedList5 = new ArrayList<>();
        nestedList5.add("Remember Login");
        nestedList5.add("QRCode Scanner");
        nestedList5.add("Zoom In Image");
        nestedList5.add("Update Data from List Employee Subcon");
        nestedList5.add("Delete Data from List Employee Subcon");
        nestedList5.add("About Us");
        nestedList5.add("FAQ");

        List<String> nestedList6 = new ArrayList<>();
        nestedList6.add(" ");

        mList.add(new DataModel(nestedList1 , "How to know the permit has been approved ?"));
        mList.add(new DataModel( nestedList2,"How to check permission status ?"));
        mList.add(new DataModel( nestedList3,"QR Code screenshot be used for scanning ?"));
        mList.add(new DataModel( nestedList4,"How to make permission changes ?"));
        mList.add(new DataModel( nestedList5,"Application Features"));
        mList.add(new DataModel(nestedList6,"Warning !"));

        adapter = new ItemAdapter(mList);
        recyclerView.setAdapter(adapter);
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
        AkunUtama.redirectActivity(this, MainActivity.class);
    }

    public void ClickFaq(View view){
        AkunUtama.redirectActivity(this, FAQ.class);
        finish();
    }

    public void ClickAboutus(View view){
        AkunUtama.redirectActivity(this, AboutUs.class);
    }

    public void ClickExit(View view){
        AkunUtama.exit(this);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }
}