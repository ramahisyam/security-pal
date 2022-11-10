package com.example.securityptpal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.muddzdev.styleabletoast.StyleableToast;
import com.tapadoo.alerter.Alerter;
import com.tapadoo.alerter.OnHideAlertListener;
import com.tapadoo.alerter.OnShowAlertListener;

public class Login2 extends AppCompatActivity {
    TextView reg ,forget;
    EditText em, pass;
    Button login;
    String email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mAuth = FirebaseAuth.getInstance();
        forget = findViewById(R.id.forgetpass);
        reg = findViewById(R.id.register);
        em = findViewById(R.id.username);
        pass = findViewById(R.id.nip);
        login = findViewById(R.id.arrow);
        login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cekLogin();
                }
            }
        );
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForget();
            }
        });
    }

    public void openForget() {
        Intent intent = new Intent(this, ForgetPassword.class);
        startActivity(intent);
    }

    public void openRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    private void cekLogin() {
        email = em.getText().toString();
        password = pass.getText().toString();

        try{
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
//                        StyleableToast.makeText(getApplicationContext(), "Please fill all the data!!!", Toast.LENGTH_SHORT, R.style.resultfailed).show();
                Alerter.create(Login2.this)
                        .setTitle("Login Failed!")
                        .setText("Please fill email and password")
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
                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    StyleableToast.makeText(getApplicationContext(),"Login Success", Toast.LENGTH_LONG,R.style.logsuccess).show();
                                    startActivity(new Intent(Login2.this,Subcontractor.class));
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Alerter.create(Login2.this)
                                            .setTitle("Login Failed!")
                                            .setText("Error ! " + task.getException().getMessage())
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
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}