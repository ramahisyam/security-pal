package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.securityptpal.main.AkunUtama;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.example.securityptpal.model.PermissionLate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.muddzdev.styleabletoast.StyleableToast;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtamaDataBarang extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ImageView btMenu;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utama_data_barang);
        drawerLayout = findViewById(R.id.drawer_layout);
        btMenu = findViewById(R.id.bt_menu);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.main_goods_fab);
        fab1 = (FloatingActionButton) findViewById(R.id.main_goods_fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.main_goods_fab2);

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

//        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
//        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
//        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");
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

//    private void importData(){
//        if(list.size()>0){
//            createXlFile();
//        } else {
//            Toast.makeText(this, "list are empty", Toast.LENGTH_SHORT).show();
//        }
//    }

//    private void createXlFile() {
//        Workbook wb = new HSSFWorkbook();
//        Cell cell = null;
//
//        Sheet sheet = null;
//        sheet = wb.createSheet("Demo Excel Sheet");
//
//        Row row = sheet.createRow(0);
//
//        cell = row.createCell(0);
//        cell.setCellValue("Id");
//
//        cell = row.createCell(1);
//        cell.setCellValue("Name");
//
//        cell = row.createCell(2);
//        cell.setCellValue("Phone");
//
//        cell = row.createCell(3);
//        cell.setCellValue("PIC");
//
//        cell = row.createCell(4);
//        cell.setCellValue("Division");
//
//        cell = row.createCell(5);
//        cell.setCellValue("Department");
//
//        cell = row.createCell(6);
//        cell.setCellValue("Goods Name");
//
//        cell = row.createCell(7);
//        cell.setCellValue("Types Goods");
//
//        cell = row.createCell(8);
//        cell.setCellValue("Image Url");
//
//        cell = row.createCell(9);
//        cell.setCellValue("Date");
//
//        cell = row.createCell(10);
//        cell.setCellValue("Phone");
//
//        cell = row.createCell(11);
//        cell.setCellValue("Latitude");
//
//        cell = row.createCell(12);
//        cell.setCellValue("Longitude");
//
//        cell = row.createCell(13);
//        cell.setCellValue("Location");
//
//        sheet.setColumnWidth(0, (30 * 200));
//        sheet.setColumnWidth(1, (30 * 200));
//        sheet.setColumnWidth(2, (30 * 200));
//        sheet.setColumnWidth(3, (30 * 200));
//        sheet.setColumnWidth(4, (30 * 200));
//        sheet.setColumnWidth(5, (30 * 200));
//        sheet.setColumnWidth(6, (30 * 200));
//        sheet.setColumnWidth(7, (30 * 200));
//        sheet.setColumnWidth(8, (30 * 200));
//        sheet.setColumnWidth(9, (30 * 200));
//        sheet.setColumnWidth(10, (30 * 200));
//        sheet.setColumnWidth(11, (30 * 200));
//        sheet.setColumnWidth(12, (30 * 200));
//        sheet.setColumnWidth(13, (30 * 200));
//
//        for (int i = 0; i < list.size(); i++) {
//            Row row1 = sheet.createRow(i + 1);
//
//            cell = row1.createCell(0);
//            cell.setCellValue(list.get(i).getId());
//
//            cell = row1.createCell(1);
//            cell.setCellValue((list.get(i).getName()));
//
//            cell = row1.createCell(2);
//            cell.setCellValue(list.get(i).getPhone());
//
//            cell = row1.createCell(3);
//            cell.setCellValue(list.get(i).getPIC());
//
//            cell = row1.createCell(4);
//            cell.setCellValue(list.get(i).getReason());
//
//            cell = row1.createCell(5);
//            cell.setCellValue(list.get(i).getImg());
//
//            cell = row1.createCell(6);
//            cell.setCellValue(list.get(i).getDate());
//
//            cell = row1.createCell(7);
//            cell.setCellValue(list.get(i).getDevice());
//
//            cell = row1.createCell(8);
//            cell.setCellValue(list.get(i).getLatitude());
//
//            cell = row1.createCell(9);
//            cell.setCellValue(list.get(i).getLongitude());
//
//            cell = row1.createCell(10);
//            cell.setCellValue(list.get(i).getLocation());
//
//            cell = row1.createCell(11);
//            cell.setCellValue(list.get(i).getLocation());
//
//            cell = row1.createCell(12);
//            cell.setCellValue(list.get(i).getLocation());
//
//            cell = row1.createCell(13);
//            cell.setCellValue(list.get(i).getLocation());
//        }
//
//        String folderName = "Import Excel";
//        String fileName = "Goods Permission_" + System.currentTimeMillis() + ".xls";
//        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + folderName + File.separator + fileName;
//
//        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + folderName);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        FileOutputStream outputStream = null;
//
//        try {
//            outputStream = new FileOutputStream(path);
//            wb.write(outputStream);
//            // ShareViaEmail(file.getParentFile().getName(),file.getName());
//            StyleableToast.makeText(getApplicationContext(), "Excel Created in " + path, Toast.LENGTH_SHORT, R.style.logsuccess).show();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//            StyleableToast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG, R.style.warning).show();
//            try {
//                outputStream.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//
//            }
//        }
//    }

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
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, AkunUtama.class));
        finish();
    }
}