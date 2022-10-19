package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;
import com.zolad.zoominimageview.ZoomInImageView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Barang extends AppCompatActivity {

    private String[] PERMISSIONS;
    Button monitoring, btnPicture, submitGoods;
    Spinner spinner, spinner2;
    ZoomInImageView imageView;
    TextView txtDate, txtDevice, txtLatitude, txtLongitude, txtLocation, txtDepartGoods;
    Build build;
    String information;
    FusedLocationProviderClient fusedLocationProviderClient;
    EditText edtNameGoods, edtNoHPGoods, edtPICGoods, edtItemGoods;
    String name, phone, pic, division, department, goodsname, typesgoods, date, device, latitude, longitude, location;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barang);

        spinner = findViewById(R.id.spinner);
        spinner2 = findViewById(R.id.spinner2);
        monitoring = findViewById(R.id.gotoMonitoring);
        btnPicture = findViewById(R.id.buttonPictureBarang);
        imageView = findViewById(R.id.imageViewBarang);
        txtDate = findViewById(R.id.txtDateBarang);
        txtDevice = findViewById(R.id.txtDeviceBarang);
        txtLatitude = (TextView) findViewById(R.id.txtLatitudeBarang);
        txtLongitude = (TextView) findViewById(R.id.txtLongitudeBarang);
        txtLocation = (TextView) findViewById(R.id.txtLocationBarang);
        txtDepartGoods = findViewById(R.id.txtDepartGoods);
        submitGoods = findViewById(R.id.submitGoods);
        edtNameGoods = findViewById(R.id.edtNameGoods);
        edtNoHPGoods = findViewById(R.id.edtNoHPGoods);
        edtPICGoods = findViewById(R.id.edtPICGoods);
        edtItemGoods = findViewById(R.id.edtItemGoods);

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

        ArrayList<String> numberList1 = new ArrayList<>();

        numberList1.add("Heavy Goods");
        numberList1.add("Light Goods");

        monitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMonitoringBarang();
            }
        });

        spinner.setAdapter(new ArrayAdapter<>(Barang.this,
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
                if (i == 0 || i == 1 || i == 2|| i == 3|| i == 4|| i == 5){
                    txtDepartGoods.setText("Production Directorate");
                }else if (i == 6 || i == 7 || i == 8 || i == 9){
                    txtDepartGoods.setText("Marketing Directorate");
                }
                else if (i == 10 || i == 11 || i == 12 || i == 13 || i == 14){
                    txtDepartGoods.setText("Directorate of Finance, Risk Management & HR");
                }
                else if (i == 15 || i == 16){
                    txtDepartGoods.setText("SEVP Transformation Management");
                }
                else if (i == 17){
                    txtDepartGoods.setText("SEVP Technology & Naval System");
                }
                else if (i == 18 || i == 19 || i == 20 || i == 21){
                    txtDepartGoods.setText("-");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinner2.setAdapter(new ArrayAdapter<>(Barang.this,
                android.R.layout.simple_spinner_dropdown_item, numberList1));

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
                if (!hasPermissions(Barang.this,PERMISSIONS)){
                    ActivityCompat.requestPermissions(Barang.this,PERMISSIONS,2);
                }
            }
        });

        submitGoods.setOnClickListener(view -> {
            try{
                if (TextUtils.isEmpty(edtNameGoods.getText().toString()) || TextUtils.isEmpty(edtNoHPGoods.getText().toString()) || TextUtils.isEmpty(edtPICGoods.getText().toString()) || TextUtils.isEmpty(edtPICGoods.getText().toString()) || TextUtils.isEmpty(edtItemGoods.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                    Alerter.create(Barang.this)
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
                    name = edtNameGoods.getText().toString();
                    phone = edtNoHPGoods.getText().toString();
                    pic = edtPICGoods.getText().toString();
                    division = spinner.getSelectedItem().toString();
                    pic = edtPICGoods.getText().toString();
                    department = txtDepartGoods.getText().toString();
                    goodsname = edtItemGoods.getText().toString();
                    typesgoods = spinner2.getSelectedItem().toString();
                    date = txtDate.getText().toString();
                    device = txtDevice.getText().toString();
                    latitude = txtLatitude.getText().toString();
                    longitude = txtLongitude.getText().toString();
                    location = txtLocation.getText().toString();

                    progressDialog = new ProgressDialog(Barang.this);
                    progressDialog.show();
                    progressDialog.setContentView(R.layout.progress_dialog);
                    progressDialog.getWindow().setBackgroundDrawableResource(
                            android.R.color.transparent
                    );
                    Thread timer = new Thread(){
                        @Override
                        public void run() {
                            try {
                                sleep(2000);
                                Intent intent = new Intent(getApplicationContext(),Barang.class);
                                startActivity(intent);
                                progressDialog.dismiss();
                                finish();
                                super.run();
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    };
                    timer.start();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //do something
                            StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                        }
                    }, 2000 );
                }
            }
            catch (Exception e){
                e.printStackTrace();
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
                        Geocoder geocoder = new Geocoder(Barang.this, Locale.getDefault());

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

    public void openMonitoringBarang() {
        Intent intent = new Intent(this, MonitoringBarang.class);
        startActivity(intent);
    }

    private void saveinfo(){
        information = build.MODEL;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 2){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(this, "Calling Permissions is granted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 2);
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
        if(requestCode == 2 && resultCode == RESULT_OK && data != null) {
            Bitmap captureImage = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(captureImage);
            Date dateCurrent = Calendar.getInstance().getTime();
            txtDate.setText(dateCurrent.toString());
            txtDevice.setText(information);
            StyleableToast.makeText(getApplicationContext(), "Image has been Captured!", Toast.LENGTH_SHORT, R.style.result).show();
            getLocation();
        }else {
            StyleableToast.makeText(getApplicationContext(), "No Image Captured!", Toast.LENGTH_SHORT, R.style.warning).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}