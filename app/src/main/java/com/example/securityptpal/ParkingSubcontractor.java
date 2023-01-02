package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securityptpal.model.Guest;
import com.example.securityptpal.model.ParkSub;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.muddzdev.styleabletoast.StyleableToast;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ParkingSubcontractor extends AppCompatActivity {

    Button scanParksub;
    TextView inputMasuk, inputKeluar, resetParkSub;
    private int counter1, counter2;
    String masuk, keluar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_subcontractor);

        inputMasuk = findViewById(R.id.masuk);
        inputKeluar = findViewById(R.id.keluar);
        scanParksub = findViewById(R.id.scanParksub);
//        resetParkSub = findViewById(R.id.resetParkSub);

        scanParksub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(ParkingSubcontractor.this);
                integrator.setCaptureActivity(CaptureAct.class);
                integrator.setOrientationLocked(true);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning Code");
                integrator.initiateScan();
            }
        });

        initCounter1();
        initCounter2();

        masuk = inputMasuk.getText().toString();
        keluar = inputKeluar.getText().toString();

        ParkSub parkSub = new ParkSub(
                db.collection("permission_parksub").document().getId(),
                masuk,
                keluar
        );
        db.collection("permission_parksub").add(parkSub).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
//                StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
//                StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
            }
        });

//        resetParkSub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new SweetAlertDialog(ParkingSubcontractor.this, SweetAlertDialog.WARNING_TYPE)
//                        .setTitleText("RESET")
//                        .setContentText("Are you sure want to reset ?")
//                        .setConfirmText("OK")
//                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                counter1 = 0;
//                                counter2 = 0;
//                                inputMasuk.setText(counter1 + "");
//                                inputKeluar.setText(counter2 + "");
//
//                                masuk = inputMasuk.getText().toString();
//                                keluar = inputKeluar.getText().toString();
//
//                                ParkSub parkSub = new ParkSub(
//                                        db.collection("permission_parksub").document().getId(),
//                                        masuk,
//                                        keluar
//                                );
//                                db.collection("permission_parksub").add(parkSub).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                    @Override
//                                    public void onSuccess(DocumentReference documentReference) {
//                StyleableToast.makeText(getApplicationContext(),"Reset Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
////                StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
//                                    }
//                                });
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .setCancelButton("CANCEL", new SweetAlertDialog.OnSweetClickListener() {
//                            @Override
//                            public void onClick(SweetAlertDialog sDialog) {
//                                sDialog.dismissWithAnimation();
//                            }
//                        })
//                        .show();
//            }
//        });
    }

    private void initCounter1(){
        counter1=0;
//        counter2=0;
        inputMasuk.setText(counter1 + "");
//        inputKeluar.setText(counter2);
    }

    private void initCounter2(){
//        counter1=0;
        counter2=0;
//        inputMasuk.setText(counter1);
        inputKeluar.setText(counter2 + "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("SCANNING RESULT")
                        .setContentText(result.getContents())
                        .setCustomImage(R.drawable.ic_code)
                        .setConfirmText("K")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                counter2++;
                                inputKeluar.setText(counter2 + "");
                                sDialog.dismissWithAnimation();

                                masuk = inputMasuk.getText().toString();
                                keluar = inputKeluar.getText().toString();

                                ParkSub parkSub = new ParkSub(
                                        db.collection("permission_parksub").document().getId(),
                                        masuk,
                                        keluar
                                );
                                db.collection("permission_parksub").add(parkSub).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                                    }
                                });
                            }
                        })
                        .setCancelButton("M", new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                counter1++;
                                inputMasuk.setText(counter1 + "");
                                sDialog.dismissWithAnimation();

                                masuk = inputMasuk.getText().toString();
                                keluar = inputKeluar.getText().toString();

                                ParkSub parkSub = new ParkSub(
                                        db.collection("permission_parksub").document().getId(),
                                        masuk,
                                        keluar
                                );
                                db.collection("permission_parksub").add(parkSub).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                                    }
                                });
                            }
                        })
                        .show();
            } else {
                StyleableToast.makeText(getApplicationContext(), "No Scan Results!", Toast.LENGTH_SHORT, R.style.warning).show();
            }
        }
    }
}