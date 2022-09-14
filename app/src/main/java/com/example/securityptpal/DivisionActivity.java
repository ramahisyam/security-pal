package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DivisionActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE_DESIGN = "DESIGN";
    public static final String EXTRA_MESSAGE_HCM = "HCM";
    private ImageView imgSignOut;
    private CardView cvDesignDiv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_division);
        imgSignOut = findViewById(R.id.sign_out_main_account);
        cvDesignDiv = findViewById(R.id.design_division);

        cvDesignDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DivisionActivity.this, EmployeePermitActivity.class);
                intent.putExtra(Intent.EXTRA_TEXT, EXTRA_MESSAGE_DESIGN);
                startActivity(intent);
            }
        });

        imgSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogoutAccount.logout(DivisionActivity.this);
            }
        });
    }
}