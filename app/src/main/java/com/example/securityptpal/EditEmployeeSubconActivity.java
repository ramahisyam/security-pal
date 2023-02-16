package com.example.securityptpal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.securityptpal.model.EmployeeSubcon;
import com.example.securityptpal.model.Subcon;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.muddzdev.styleabletoast.StyleableToast;
import com.zolad.zoominimageview.ZoomInImageView;

import java.util.Date;
import java.util.HashMap;

public class EditEmployeeSubconActivity extends AppCompatActivity {
    private EditText name, age, phone, nip, address;
    private Button save, changeImage;
    private EmployeeSubcon employeeSubcon;
    private ZoomInImageView photo;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
    Subcon subcon;
    int PICK_IMAGE_CODE = 200;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_subcon);

        name = findViewById(R.id.edit_subcon_employee_name);
        age = findViewById(R.id.edit_subcon_employee_age);
        phone = findViewById(R.id.edit_subcon_employee_phone);
        nip = findViewById(R.id.edit_subcon_employee_nip);
        address = findViewById(R.id.edit_subcon_employee_address);
        save = findViewById(R.id.submit_edit_subcon_employee);
        photo = findViewById(R.id.edit_photo_employee_subcon);
        changeImage = findViewById(R.id.edit_employee_photo);

        employeeSubcon = getIntent().getParcelableExtra("EDIT_SUBCON_EMPLOYEE_PERMIT");
        subcon = getIntent().getParcelableExtra("SUBCON_DATA_EDIT");

        name.setText(employeeSubcon.getName());
        age.setText(employeeSubcon.getAge());
        phone.setText(employeeSubcon.getPhone());
        nip.setText(employeeSubcon.getNip());
        address.setText(employeeSubcon.getAddress());
        Glide.with(this).load(employeeSubcon.getImg()).placeholder(R.drawable.pict).into(photo);

        changeImage.setOnClickListener(view1 -> {
            Intent intentImage = new Intent();
            intentImage.setType("image/*");
            intentImage.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intentImage, PICK_IMAGE_CODE);
        });

        save.setOnClickListener(view -> {
            db.collection("subcontractor").document(subcon.getId()).collection("employee").document(employeeSubcon.getId())
                    .update(
                            "name", name.getText().toString(),
                            "age", age.getText().toString(),
                            "phone", phone.getText().toString(),
                            "nip", nip.getText().toString(),
                            "address", address.getText().toString()
                    )
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
//                            StorageReference storageReference = firebaseStorage.getReference("subcon_employee").child(employeeSubcon.getImg());
//                            storageReference.putFile(selectedImage).addOnCompleteListener(task -> {
//                                if (task.isSuccessful()) {
//                                    storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                        @Override
//                                        public void onSuccess(Uri uri) {
//                                            Toast.makeText(EditEmployeeSubconActivity.this, "Change Image Success", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//                                } else {
//                                    Toast.makeText(EditEmployeeSubconActivity.this, "Gagal mengubah image", Toast.LENGTH_SHORT).show();
//                                }
//                            });
                            StyleableToast.makeText(getApplicationContext(),"Data Edited Successfully!", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            StyleableToast.makeText(getApplicationContext(),"Data Failed to Edit!", Toast.LENGTH_SHORT,R.style.resultfailed).show();
                        }
                    });
            finish();
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (data != null) {
//            if (data.getData() != null) {
//                Uri uri = data.getData(); // filepath
//                FirebaseStorage storage = FirebaseStorage.getInstance();
//                long time = new Date().getTime();
//                StorageReference reference = storage.getReference().child("subcon_employee").child(employeeSubcon.getImg());
//                reference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                                @Override
//                                public void onSuccess(Uri uri) {
//                                    String filePath = uri.toString();
//                                    HashMap<String, Object> obj = new HashMap<>();
//                                    obj.put("img", filePath);
//                                    db.collection("subcontractor").document(subcon.getId()).collection("employee").document(employeeSubcon.getId())
//                                            .update(
//                                                    "name", name.getText().toString(),
//                                                    "age", age.getText().toString(),
//                                                    "phone", phone.getText().toString(),
//                                                    "nip", nip.getText().toString(),
//                                                    "address", address.getText().toString()
//                                            );
//                                }
//                            });
//                        }
//                    }
//                });
//                photo.setImageURI(data.getData());
//                selectedImage = data.getData();
//            }
//        }
//    }
}