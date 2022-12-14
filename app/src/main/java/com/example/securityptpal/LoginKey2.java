package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

public class LoginKey2 extends AppCompatActivity {
    String key22 = "checkup";

    EditText key2;
    Button loginkey2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_key2);
        key2 = findViewById(R.id.key2);
        loginkey2 = findViewById(R.id.loginkey2);

        loginkey2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(key2.getText().toString())){
                    Alerter.create(LoginKey2.this)
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
                else {
                    if (key2.getText().toString().equals(key22)) {
                        openCheckup();
                    } else {
                        Alerter.create(LoginKey2.this)
                                .setTitle("Login Failed!")
                                .setText("Invalid Key")
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
        });
    }

    public void openCheckup() {
        Intent intent = new Intent(LoginKey2.this, CheckUpPermissionActivity.class);
        startActivity(intent);
    }
}