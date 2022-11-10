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

public class LoginKey extends AppCompatActivity {
    String key1 = "cometoolate";

    EditText key;
    Button loginkey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_key);
        key = findViewById(R.id.key);
        loginkey = findViewById(R.id.loginkey);

        loginkey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(key.getText().toString())){
                    Alerter.create(LoginKey.this)
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
                if (key.getText().toString().equals(key1)){
                   openCometoolate();
                }else {
                    Alerter.create(LoginKey.this)
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
        });
   }

    public void openCometoolate() {
        Intent intent = new Intent(this, CometooLate.class);
        startActivity(intent);
    }
}