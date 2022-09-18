package com.example.securityptpal.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.securityptpal.LogoutAccount;
import com.example.securityptpal.R;
import com.example.securityptpal.division.ExitPermissionActivity;

public class AkunUtama extends AppCompatActivity {

    private CardView cvExitPermit;
    private ImageView imgSignOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_utama);
        imgSignOut = findViewById(R.id.sign_out_main_account);
        cvExitPermit = findViewById(R.id.card_exit_permission_main);

        cvExitPermit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AkunUtama.this, UtamaDataEmployee.class));
            }
        });
        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAccount.logout(AkunUtama.this);
            }
        });
    }
}