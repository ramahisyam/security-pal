package com.example.securityptpal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.muddzdev.styleabletoast.StyleableToast;
import com.zolad.zoominimageview.ZoomInImageView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CometooLate extends AppCompatActivity {

    private String[] PERMISSIONS;
    Button monitoring, btnPicture;
    Spinner spinner;
    ZoomInImageView imageView;
    TextView txtDate, txtDevice, txtLatitude, txtLongitude, txtLocation;
    Build build;
    String information;
    FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cometoo_late);

        spinner = findViewById(R.id.spinner);
        monitoring = findViewById(R.id.gotoMonitoring);
        btnPicture = findViewById(R.id.buttonPicture);
        imageView = findViewById(R.id.imageViewLate);
        txtDate = findViewById(R.id.txtDateLate);
        txtDevice = findViewById(R.id.txtDeviceLate);
        txtLatitude = (TextView) findViewById(R.id.txtLatitude);
        txtLongitude = (TextView) findViewById(R.id.txtLongitude);
        txtLocation = (TextView) findViewById(R.id.txtLocation);

        PERMISSIONS = new String[]{
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION
        };

        ArrayList<String> numberList = new ArrayList<>();

        numberList.add("General Engineering");
        numberList.add("Merchant Ship");
        numberList.add("Warship");
        numberList.add("Submarine");
        numberList.add("Maintenance & Repair");
        numberList.add("Production Management Office");
        numberList.add("Ship Marketing & Sales");
        numberList.add("Recumalable Sales");
        numberList.add("Supply Chain");
        numberList.add("Area & K3LH");
        numberList.add("Company Strategic Planning");
        numberList.add("Treasury");
        numberList.add("Accountancy");
        numberList.add("Human Capital Management");
        numberList.add("Risk");
        numberList.add("Office of The Board");
        numberList.add("Legal");
        numberList.add("Technology & Quality Assurance");
        numberList.add("Company Secretary");
        numberList.add("Internal Control Unit");
        numberList.add("Information Technology");
        numberList.add("Design");

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMonitoringCometooLate();
            }
        });

        spinner.setAdapter(new ArrayAdapter<>(CometooLate.this,
                android.R.layout.simple_spinner_dropdown_item, numberList));

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
        information = build.MODEL;
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
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
            Date dateCurrent = Calendar.getInstance().getTime();
            txtDate.setText(dateCurrent.toString());
            txtDevice.setText(information);
            StyleableToast.makeText(getApplicationContext(), "Image has been Captured!", Toast.LENGTH_SHORT, R.style.result).show();
        }else {
            StyleableToast.makeText(getApplicationContext(), "No Image Captured!", Toast.LENGTH_SHORT, R.style.warning).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}