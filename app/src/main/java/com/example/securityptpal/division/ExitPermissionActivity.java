package com.example.securityptpal.division;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.Toast;

import com.example.securityptpal.R;
import com.example.securityptpal.adapter.PermissionEmployeeAdapter;
import com.example.securityptpal.employee.DetailPermissionActivity;
import com.example.securityptpal.main.UtamaDataEmployee;
import com.example.securityptpal.model.PermissionEmployee;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
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

public class ExitPermissionActivity extends AppCompatActivity implements PermissionEmployeeAdapter.OnPermitListener {

    private RecyclerView recyclerView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private List<PermissionEmployee> list = new ArrayList<>();
    private PermissionEmployeeAdapter permissionEmployeeAdapter;
    private SearchView searchView;
    private Intent intent;
    private String EXTRA;
    String userID;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;
    private Button btnExport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit_permission);
        recyclerView = findViewById(R.id.rv_exit_permission);
        searchView = findViewById(R.id.search_employee_permit);
        btnExport = findViewById(R.id.div_export_excel);
        progressDialog = new ProgressDialog(ExitPermissionActivity.this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Getting data...");

        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchData(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                showDataDivision(EXTRA);
                return false;
            }
        });

        permissionEmployeeAdapter = new PermissionEmployeeAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        RecyclerView.ItemDecoration decoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setAdapter(permissionEmployeeAdapter);

        Bundle extras = getIntent().getExtras();
        EXTRA = extras.getString(Intent.EXTRA_TEXT);

        System.setProperty("org.apache.poi.javax.xml.stream.XMLInputFactory", "com.fasterxml.aalto.stax.InputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLOutputFactory", "com.fasterxml.aalto.stax.OutputFactoryImpl");
        System.setProperty("org.apache.poi.javax.xml.stream.XMLEventFactory", "com.fasterxml.aalto.stax.EventFactoryImpl");

        btnExport.setOnClickListener(view -> {
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
        });
    }

    private void showDataDivision(String division) {
        db.collection("permission_employee")
                .whereEqualTo("division", division)
//                .orderBy("date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PermissionEmployee permissionEmployee = new PermissionEmployee(
                                        document.getId(),
                                        document.getString("base"),
                                        document.getString("name"),
                                        document.getString("nip"),
                                        document.getString("division"),
                                        document.getString("date"),
                                        document.getString("necessity"),
                                        document.getString("place"),
                                        document.getString("timeout"),
                                        document.getString("timeback"),
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(permissionEmployee);
                            }
                            permissionEmployeeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ExitPermissionActivity.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ExitPermissionActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
    }
    private void searchData(String nip) {
        db.collection("permission_employee")
                .whereEqualTo("nip", nip)
                .whereEqualTo("division", "design")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        list.clear();
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PermissionEmployee permissionEmployee = new PermissionEmployee(
                                        document.getId(),
                                        document.getString("base"),
                                        document.getString("name"),
                                        document.getString("nip"),
                                        document.getString("division"),
                                        document.getString("date"),
                                        document.getString("necessity"),
                                        document.getString("place"),
                                        document.getString("timeout"),
                                        document.getString("timeback"),
                                        document.getString("division_approval"),
                                        document.getString("center_approval")
                                );
                                list.add(permissionEmployee);
                            }
                            permissionEmployeeAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(ExitPermissionActivity.this, "data gagal dimuat", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ExitPermissionActivity.this, "data tidak ditemukan", Toast.LENGTH_SHORT).show();
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
        cell.setCellValue("Base");

        cell = row.createCell(2);
        cell.setCellValue("Name");

        cell = row.createCell(3);
        cell.setCellValue("Nip");

        cell = row.createCell(4);
        cell.setCellValue("Division");

        cell = row.createCell(5);
        cell.setCellValue("Date");

        cell = row.createCell(6);
        cell.setCellValue("Necessity");

        cell = row.createCell(7);
        cell.setCellValue("Place");

        cell = row.createCell(8);
        cell.setCellValue("Time Out");

        cell = row.createCell(9);
        cell.setCellValue("Time Back");

        cell = row.createCell(10);
        cell.setCellValue("Division Approval");

        cell = row.createCell(11);
        cell.setCellValue("Center Approval");

        for (int i = 0; i < list.size(); i++) {
            Row row1 = sheet.createRow(i + 1);

            cell = row1.createCell(0);
            cell.setCellValue(list.get(i).getId());

            cell = row1.createCell(1);
            cell.setCellValue((list.get(i).getBase()));

            cell = row1.createCell(2);
            cell.setCellValue(list.get(i).getName());

            cell = row1.createCell(3);
            cell.setCellValue(list.get(i).getNip());

            cell = row1.createCell(4);
            cell.setCellValue(list.get(i).getDivision());

            cell = row1.createCell(5);
            cell.setCellValue(list.get(i).getDate());

            cell = row1.createCell(6);
            cell.setCellValue(list.get(i).getNecessity());

            cell = row1.createCell(7);
            cell.setCellValue(list.get(i).getPlace());

            cell = row1.createCell(8);
            cell.setCellValue(list.get(i).getTimeout());

            cell = row1.createCell(9);
            cell.setCellValue(list.get(i).getTimeback());

            cell = row1.createCell(10);
            cell.setCellValue(list.get(i).getDivision_approval());

            cell = row1.createCell(11);
            cell.setCellValue(list.get(i).getCenter_approval());

        }

        String folderName = "Import Excel";
        String fileName = "Exit Permission_"+ EXTRA + System.currentTimeMillis() + ".xls";
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
    public void onPermitClick(int position) {
        intent = new Intent(ExitPermissionActivity.this, DetailExitActivity.class);
        intent.putExtra("EXIT_PERMIT", list.get(position));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressDialog.show();
        showDataDivision(EXTRA);
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