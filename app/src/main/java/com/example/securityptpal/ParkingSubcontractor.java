package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.EmployeeSubcon;
import com.example.securityptpal.model.Guest;
import com.example.securityptpal.model.MasukPS;
import com.example.securityptpal.model.ParkSub;
import com.example.securityptpal.model.Subcon;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ParkingSubcontractor extends AppCompatActivity {

    Button scanParksub;
    TextView inputMasuk, inputKeluar, tvMasuk, tvKeluar;
    private int counter1, counter2;
    String masuk, keluar;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbRef;
    AlertDialog dialog;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_subcontractor);
        progressDialog = new ProgressDialog(ParkingSubcontractor.this);
        mDatabase = FirebaseDatabase.getInstance("https://project-kp-ff0b3-default-rtdb.asia-southeast1.firebasedatabase.app/");
        dbRef = mDatabase.getReference().child("permission_parksub");

        inputMasuk = (TextView) findViewById(R.id.masuk);
        inputKeluar = (TextView) findViewById(R.id.keluar);
        tvMasuk = (TextView) findViewById(R.id.tvmasuk);
        tvKeluar = (TextView) findViewById(R.id.tvkeluar);
        scanParksub = findViewById(R.id.scanParksub);
//        resetParkSub = findViewById(R.id.resetParkSub);

        scanParksub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(ParkingSubcontractor.this);
                integrator.setCaptureActivity(CaptureAct.class);
                integrator.setOrientationLocked(false);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
                integrator.setPrompt("Scanning Code");
                integrator.initiateScan();
            }
        });

        initCounter1();
        initCounter2();

        masuk = inputMasuk.getText().toString();
        keluar = inputKeluar.getText().toString();
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                inputMasuk.setText(snapshot.child("masuk").getValue().toString());
                inputKeluar.setText(snapshot.child("keluar").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//        ParkSub parkSub = new ParkSub(
//                db.collection("permission_parksub").document().getId(),
//                masuk,
//                keluar
//        );


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

    private void addData(int counter1){
        AlertDialog.Builder builder = new AlertDialog.Builder(ParkingSubcontractor.this);
        builder.setTitle("Add Data");
        View view = getLayoutInflater().inflate(R.layout.add_data_parksub, null);

        Date dateCurrent = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd/HH:mm:ss");
        String dateString = dateFormat.format(dateCurrent);

        EditText edtName = (EditText) view.findViewById(R.id.name_parksub);
        EditText edtNopol = (EditText) view.findViewById(R.id.nopol_parksub);
        TextView edtDate = (TextView) view.findViewById(R.id.tanggal_parksub);
        TextView edtQueue = (TextView) view.findViewById(R.id.urutan_parksub);

        edtDate.setText(dateString);
        edtQueue.setText(String.valueOf(counter1));

        Button btnSubmit = view.findViewById(R.id.btn_add_parksub);

        btnSubmit.setOnClickListener(view1 -> {
            if (TextUtils.isEmpty(edtName.getText().toString()) || TextUtils.isEmpty(edtNopol.getText().toString()) || TextUtils.isEmpty(edtDate.getText().toString()) || TextUtils.isEmpty(edtQueue.getText().toString())){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                Alerter.create(ParkingSubcontractor.this)
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
            } else {
                MasukPS masukPS = new MasukPS(
                  db.collection("masuk_parksub").document().getId(),
                  edtName.getText().toString(),
                  edtNopol.getText().toString(),
                        edtDate.getText().toString(),
                        String.valueOf(counter1)
                );
                progressDialog.setTitle("Loading");
                progressDialog.setMessage("Sending data...");
                progressDialog.show();
                db.collection("masuk_parksub")
                        .add(masukPS)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(ParkingSubcontractor.this, "Success adding employee", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ParkingSubcontractor.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.dismiss();
                progressDialog.dismiss();
                inputMasuk.setText(counter1 + "");

                db.collection("permission_parksub").document("20220208")
                        .update(
                                "masuk", String.valueOf(counter1),
                                "keluar", "1"
                        )
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getApplicationContext(), "Success update", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //                StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                    }
                });

            }
        });

        builder.setView(view);
        dialog = builder.create();
        dialog.show();
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
//                                inputMasuk.setText(counter1 + "");
//                                sDialog.dismissWithAnimation();
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
//                                        StyleableToast.makeText(getApplicationContext(),"Data Send Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
//                                    }
//                                }).addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        StyleableToast.makeText(getApplicationContext(),"Data Send Failed!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
//                                    }
//                                });
                                addData(counter1);
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            } else {
                StyleableToast.makeText(getApplicationContext(), "No Scan Results!", Toast.LENGTH_SHORT, R.style.warning).show();
            }
        }
    }
}