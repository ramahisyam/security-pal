package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

public class Login extends AppCompatActivity {
    EditText username, nip;
    ImageView arrow;
    Switch active;
    String correct_username = "karyawan@gmail.com";
    String correct_NIP = "12345678";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = findViewById(R.id.username);
        nip = findViewById(R.id.nip);
        arrow = findViewById(R.id.arrow);
        arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //validate inputs
                    if (TextUtils.isEmpty(username.getText().toString()) ||
                            TextUtils.isEmpty(nip.getText().toString())){
                        Alerter.create(Login.this)
                                .setTitle("Empty Data Provided!")
                                .setText("Please fill the data correctly")
                                .setIcon(R.drawable.ic_priority)
                                .setBackgroundColorRes(android.R.color.holo_orange_light)
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
                    }
                    else
                    if(username.getText().toString().equals(correct_username)){
                        //check password
                        if (nip.getText().toString().equals(correct_NIP)){
                            StyleableToast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_SHORT,R.style.logsuccess).show();
                            openEmployee();
                            username.setText("");
                            nip.setText("");
                        }
                        else {
                            Alerter.create(Login.this)
                                    .setTitle("Login Failed!")
                                    .setText("Invalid Username or Password")
                                    .setIcon(R.drawable.ic_clear)
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
                        }
                    }
                    else {
                        Alerter.create(Login.this)
                                .setTitle("Login Failed!")
                                .setText("Invalid Username or Password")
                                .setIcon(R.drawable.ic_clear)
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
                    }
                }
            }
        );
    }

    public void openEmployee() {
        Intent intent = new Intent(this, Employee.class);
        startActivity(intent);
    }
}