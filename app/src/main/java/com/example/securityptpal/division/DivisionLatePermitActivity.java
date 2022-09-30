package com.example.securityptpal.division;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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

import com.example.securityptpal.R;
import com.example.securityptpal.UtamaCometoolate;
import com.example.securityptpal.Utama_Data_Cometoolate;
import com.example.securityptpal.adapter.LatePermissionAdapter;
import com.example.securityptpal.model.PermissionLate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

public class DivisionLatePermitActivity extends AppCompatActivity implements LatePermissionAdapter.OnLateListener{

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private List<PermissionLate> list = new ArrayList<>();
    private LatePermissionAdapter latePermissionAdapter;
    private SearchView searchView;
    private Intent intent;
    private String userID;
    private ProgressDialog progressDialog;
    private String EXTRA;
    FloatingActionButton fab, fab1, fab2;
    Animation fabOpen, fabClose, rotateForward, rotateBackward;

    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division_late_permit);
        recyclerView = findViewById(R.id.rv_div_late_permit);
        searchView = findViewById(R.id.div_search_late_permission);
        progressDialog = new ProgressDialog(DivisionLatePermitActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting data...");

        latePermissionAdapter = new LatePermissionAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(latePermissionAdapter);

        Bundle extras = getIntent().getExtras();
        EXTRA = extras.getString(Intent.EXTRA_TEXT);

        fab = (FloatingActionButton) findViewById(R.id.div_late_fab);
        fab1 = (FloatingActionButton) findViewById(R.id.div_late_fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.div_late_fab2);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.fab_close);

        rotateForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
        rotateBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_backward);

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateFab();
            }
        });

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions, 1);
                    } else {
                        importData();
                    }
                } else {
                    importData();
                }
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String foldername = "Import Excel";
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(Uri.parse(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + foldername), "*/*");
                startActivity(intent);
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

    private void showDataDivision(String division) {
        db.collection("permission_late")
                .whereEqualTo("division", division)
                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PermissionLate permissionLate = new PermissionLate(
                                        document.getId(),
                                        document.getString("name"),
                                        document.getString("nip"),
                                        document.getString("division"),
                                        document.getString("reason"),
                                        document.getString("img"),
                                        document.getString("date"),
                                        document.getString("device"),
                                        document.getString("latitude"),
                                        document.getString("longitude"),
                                        document.getString("location")
                                );
                                list.add(permissionLate);
                            }
                            latePermissionAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(DivisionLatePermitActivity.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DivisionLatePermitActivity.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }

    private void importData(){
        if(list.size()>0){
            createXlFile();
        } else {
            Toast.makeText(this, "list are empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void createXlFile() {
        Workbook wb = new HSSFWorkbook();
        Cell cell = null;

        Sheet sheet = null;
        sheet = wb.createSheet("Demo Excel Sheet");

        Row row = sheet.createRow(0);

        cell = row.createCell(0);
        cell.setCellValue("Id");

        cell = row.createCell(1);
        cell.setCellValue("Name");

        cell = row.createCell(2);
        cell.setCellValue("Nip");

        cell = row.createCell(3);
        cell.setCellValue("Division");

        cell = row.createCell(4);
        cell.setCellValue("Reason");

        cell = row.createCell(5);
        cell.setCellValue("Image url");

        cell = row.createCell(6);
        cell.setCellValue("Date");

        cell = row.createCell(7);
        cell.setCellValue("Device");

        cell = row.createCell(8);
        cell.setCellValue("Latitude");

        cell = row.createCell(9);
        cell.setCellValue("Longitude");

        cell = row.createCell(10);
        cell.setCellValue("Location");

        sheet.setColumnWidth(0, (30 * 200));
        sheet.setColumnWidth(1, (30 * 200));
        sheet.setColumnWidth(2, (30 * 200));
        sheet.setColumnWidth(3, (30 * 200));
        sheet.setColumnWidth(4, (30 * 200));
        sheet.setColumnWidth(5, (30 * 200));
        sheet.setColumnWidth(6, (30 * 200));
        sheet.setColumnWidth(7, (30 * 200));
        sheet.setColumnWidth(8, (30 * 200));
        sheet.setColumnWidth(9, (30 * 200));
        sheet.setColumnWidth(10, (30 * 200));

        for (int i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(list.get(i).getId());

            cell = row1.createCell(1);
            cell.setCellValue((list.get(i).getName()));

            cell = row1.createCell(2);
            cell.setCellValue(list.get(i).getNip());

            cell = row1.createCell(3);
            cell.setCellValue(list.get(i).getDivision());

            cell = row1.createCell(4);
            cell.setCellValue(list.get(i).getReason());

            cell = row1.createCell(5);
            cell.setCellValue(list.get(i).getImg());

            cell = row1.createCell(6);
            cell.setCellValue(list.get(i).getDate());

            cell = row1.createCell(7);
            cell.setCellValue(list.get(i).getDevice());

            cell = row1.createCell(8);
            cell.setCellValue(list.get(i).getLatitude());

            cell = row1.createCell(9);
            cell.setCellValue(list.get(i).getLongitude());

            cell = row1.createCell(10);
            cell.setCellValue(list.get(i).getLocation());

        }

        String folderName = "Import Excel";
        String fileName = "Late Permission_" + EXTRA + "_" + System.currentTimeMillis() + ".xls";
        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + folderName + File.separator + fileName;

        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator + folderName);
        if (!file.exists()) {
            file.mkdirs();
        }

        FileOutputStream outputStream = null;

        try {
            outputStream = new FileOutputStream(path);
            wb.write(outputStream);
            // ShareViaEmail(file.getParentFile().getName(),file.getName());
            Toast.makeText(getApplicationContext(), "Excel Created in " + path, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();

            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
            try {
                outputStream.close();
            } catch (Exception ex) {
                ex.printStackTrace();

            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        showDataDivision(EXTRA);
    }

    @Override
    public void onLateClick(int position) {
        intent = new Intent(DivisionLatePermitActivity.this, UtamaCometoolate.class);
        intent.putExtra("MAIN_LATE_PERMIT", list.get(position));
        startActivity(intent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            importData();
        } else {
            Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
        }
    }
}