package com.example.securityptpal;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.securityptpal.employee.Employee;
import com.example.securityptpal.model.PermissionEmployee;
import com.example.securityptpal.model.PermissionLate;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;
import com.zolad.zoominimageview.ZoomInImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CometooLate extends AppCompatActivity {

    private String[] PERMISSIONS;
    Button monitoring, btnPicture, btnSubmit;
    EditText edtName, edtNip, edtReason;
    Spinner spinner, spinner2;
    ZoomInImageView imageView;
    TextView txtDate, txtDevice, txtLatitude, txtLongitude, txtLocation, txtDepart;
    Build build;
    String device, name, nip, division, reason, image, date, latitude, longitude, location;
    FusedLocationProviderClient fusedLocationProviderClient;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cometoo_late);

        spinner = findViewById(R.id.division_late);
        spinner2 = findViewById(R.id.status_late);
        monitoring = findViewById(R.id.late_monitoring);
        btnPicture = findViewById(R.id.buttonPicture);
        btnSubmit = (Button) findViewById(R.id.submitLate);
        imageView = findViewById(R.id.late_evidence);
        edtName = findViewById(R.id.late_name);
        edtNip = findViewById(R.id.late_nip);
        edtReason = findViewById(R.id.late_reason);
        txtDate = findViewById(R.id.late_date);
        txtDevice = findViewById(R.id.late_device);
        txtLatitude = (TextView) findViewById(R.id.late_latitude);
        txtLongitude = (TextView) findViewById(R.id.late_longitude);
        txtLocation = (TextView) findViewById(R.id.late_location);
        txtDepart = findViewById(R.id.txtDepartlate);

        PERMISSIONS = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        ArrayList<String> divisionList = new ArrayList<>();

        divisionList.add("General Engineering");
        divisionList.add("Merchant Ship");
        divisionList.add("Warship");
        divisionList.add("Submarine");
        divisionList.add("Maintenance & Repair");
        divisionList.add("Production Management Office");
        divisionList.add("Ship Marketing & Sales");
        divisionList.add("Recumalable Sales");
        divisionList.add("Supply Chain");
        divisionList.add("Area & K3LH");
        divisionList.add("Company Strategic Planning");
        divisionList.add("Treasury");
        divisionList.add("Accountancy");
        divisionList.add("Human Capital Management");
        divisionList.add("Risk");
        divisionList.add("Office of The Board");
        divisionList.add("Legal");
        divisionList.add("Technology & Quality Assurance");
        divisionList.add("Company Secretary");
        divisionList.add("Internal Control Unit");
        divisionList.add("Information Technology");
        divisionList.add("Design");

        ArrayList<String> statusList = new ArrayList<>();

        statusList.add("PKWT");
        statusList.add("PKWTT");

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMonitoringCometooLate();
            }
        });

        spinner.setAdapter(new ArrayAdapter<>(CometooLate.this,
                android.R.layout.simple_spinner_dropdown_item, divisionList));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0){
//                    Toast.makeText(getApplicationContext(),
//                            "Please Select Division",Toast.LENGTH_SHORT).show();
//                    textView.setText("");
//                }else{
//                    String sNumber = adapterView.getItemAtPosition(i).toString();
//                    textView.setText(sNumber);
//                }
                String sNumber = adapterView.getItemAtPosition(i).toString();
//                textView.setText(sNumber);
                if (i == 0 || i == 1 || i == 2|| i == 3|| i == 4|| i == 5){
                    txtDepart.setText("Production Directorate");
                }else if (i == 6 || i == 7 || i == 8 || i == 9){
                    txtDepart.setText("Marketing Directorate");
                }
                else if (i == 10 || i == 11 || i == 12 || i == 13 || i == 14){
                    txtDepart.setText("Directorate of Finance, Risk Management & HR");
                }
                else if (i == 15 || i == 16){
                    txtDepart.setText("SEVP Transformation Management");
                }
                else if (i == 17){
                    txtDepart.setText("SEVP Technology & Naval System");
                }
                else if (i == 18 || i == 19 || i == 20 || i == 21){
                    txtDepart.setText("-");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setAdapter(new ArrayAdapter<>(CometooLate.this,
                android.R.layout.simple_spinner_dropdown_item, statusList));

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                if (i == 0){
//                    Toast.makeText(getApplicationContext(),
//                            "Please Select Division",Toast.LENGTH_SHORT).show();
//                    textView.setText("");
//                }else{
//                    String sNumber = adapterView.getItemAtPosition(i).toString();
//                    textView.setText(sNumber);
//                }
                String sNumber = adapterView.getItemAtPosition(i).toString();
//                textView.setText(sNumber);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hasPermissions(CometooLate.this,PERMISSIONS)){
                    ActivityCompat.requestPermissions(CometooLate.this,PERMISSIONS,1);
                }
            }
        });

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        saveinfo();

        btnSubmit.setOnClickListener(view -> {
            try{
                if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtNip.getText().toString()) || TextUtils.isEmpty(edtReason.getText().toString()) || TextUtils.isEmpty(txtDate.getText().toString()) || TextUtils.isEmpty(txtDevice.getText().toString()) || TextUtils.isEmpty(txtLatitude.getText().toString()) || TextUtils.isEmpty(txtLongitude.getText().toString()) || TextUtils.isEmpty(txtLocation.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                    Alerter.create(CometooLate.this)
                            .setTitle("Add Data Failed!")
                            .setText("Please fill all the data")
                            .setIcon(R.drawable.ic_close)
                            .setBackgroundColorRes(android.R.color.holo_red_dark)
                            .setDuration(2000)
                            .enableSwipeToDismiss()
                            .enableProgress(true)
                            .setProgressColorRes(R.color.design_default_color_primary)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setOnShowListener(new OnShowAlertListener() {
                                @Override
                                public void onShow() {

                                }
                            })
                            .setOnHideListener(new OnHideAlertListener() {
                                @Override
                                public void onHide() {

                                }
                            })
                            .show();
                }else{
                    name = edtName.getText().toString();
                    nip = edtNip.getText().toString();
                    division = spinner.getSelectedItem().toString();
                    reason = edtReason.getText().toString();
                    date = txtDate.getText().toString();
                    device = txtDevice.getText().toString();
                    latitude = txtLatitude.getText().toString();
                    longitude = txtLongitude.getText().toString();
                    location = txtLocation.getText().toString();
                    upload(name, nip, division, reason, date, device, latitude, longitude, location);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    private boolean hasPermissions(Context context, String... PERMISSIONS){
        if (context != null && PERMISSIONS != null){
            for (String permission: PERMISSIONS){
                if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return false;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();

                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(CometooLate.this, Locale.getDefault());

                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        txtLatitude.setText(Html.fromHtml("<font color = '000000'></font>" + addresses.get(0).getLatitude()));
                        txtLongitude.setText(Html.fromHtml("<font color = '000000'></font>" + addresses.get(0).getLongitude()));
                        txtLocation.setText(Html.fromHtml("<font color = '000000'></font>" + addresses.get(0).getAddressLine(0)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void openMonitoringCometooLate() {
        Intent intent = new Intent(this, MonitoringComeTooLate.class);
        startActivity(intent);
    }

    private void saveinfo(){
        device = build.MODEL;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Calling Permissions is granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 1);
            }else {
                Toast.makeText(this, "You don't have permission to access camera!", Toast.LENGTH_SHORT).show();
            }
            if (grantResults[1] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "SMS Permissions is granted", Toast.LENGTH_SHORT).show();
                getLocation();
            }else {
                Toast.makeText(this, "You don't have permission to access location!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null) {
            final Bundle extras = data.getExtras();
            Thread thread = new Thread(() -> {
                Bitmap bitmap = (Bitmap) extras.get("data");
                imageView.post(() -> {
                    imageView.setImageBitmap(bitmap);
                });
            });
            thread.start();
            Date dateCurrent = Calendar.getInstance().getTime();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
            String dateString = dateFormat.format(dateCurrent);
            txtDate.setText(dateString);
            txtDevice.setText(device);
            getLocation();
            StyleableToast.makeText(getApplicationContext(), "Image has been Captured!", Toast.LENGTH_SHORT, R.style.result).show();
        }else {
            StyleableToast.makeText(getApplicationContext(), "No Image Captured!", Toast.LENGTH_SHORT, R.style.warning).show();
        }
    }

    private void upload(String name, String nip, String division, String reason, String date, String device, String latitude, String longitude, String location) {
        imageView.setDrawingCacheEnabled(true);
        imageView.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        StorageReference storageRef = storage.getReference("late_permission").child("IMG" + new Date().getTime()+".jpeg");
        UploadTask uploadTask = storageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(CometooLate.this, exception.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                if (taskSnapshot.getMetadata() != null) {
                    if (taskSnapshot.getMetadata().getReference() != null) {
                        taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                                saveData(
                                        name,
                                        nip,
                                        division,
                                        reason,
                                        date,
                                        device,
                                        latitude,
                                        longitude,
                                        location,
                                        task.getResult().toString()
                                );
                            }
                        });
                    }
                }
            }
        });
    }

    private void saveData(String name, String nip, String division, String reason, String date, String device, String latitude, String longitude, String location, String img) {
        PermissionLate permissionLate = new PermissionLate(
                db.collection("permission_late").document().getId(),
                name,
                nip,
                division,
                reason,
                img,
                date,
                device,
                latitude,
                longitude,
                location
        );

        db.collection("permission_late").add(permissionLate).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                edtName.setText("");
                edtNip.setText("");
                edtReason.setText("");
                imageView.setImageResource(R.drawable.pict);
                txtDepart.setText("");
                txtDate.setText("Date");
                txtDevice.setText("Phone");
                txtLatitude.setText("Latitude");
                txtLongitude.setText("Longitude");
                txtLocation.setText("Location");
                Toast.makeText(CometooLate.this, "Berhasil submit", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}