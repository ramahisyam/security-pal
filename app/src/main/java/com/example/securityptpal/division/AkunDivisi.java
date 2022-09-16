package com.example.securityptpal.division;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.securityptpal.LogoutAccount;
import com.example.securityptpal.R;

public class AkunDivisi extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_DESIGN = "design";
    public static final String EXTRA_MESSAGE_IT = "Information Technology";
    private ImageView imgSignOut;
    private CardView cvDesignDiv, cvITDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_akun_divisi);
        imgSignOut = findViewById(R.id.sign_out_division_account);
        cvDesignDiv = findViewById(R.id.design_division);
        cvITDiv = findViewById(R.id.it_division);

        cvITDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunDivisi.this, EmployeePermitActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, EXTRA_MESSAGE_IT);
                startActivity(intent);
            }
        });

        cvDesignDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AkunDivisi.this, EmployeePermitActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, EXTRA_MESSAGE_DESIGN);
                startActivity(intent);
            }
        });

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAccount.logout(AkunDivisi.this);
            }
        });
    }
}