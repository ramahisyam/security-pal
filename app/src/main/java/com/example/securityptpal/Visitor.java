package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Visitor extends AppCompatActivity {

    Button bt_visitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        bt_visitor = findViewById(R.id.gotoMonitoring);

        bt_visitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openMonitoringVisitor();
            }
        });
    }

    private void openMonitoringVisitor() {
        Intent intent = new Intent(this, MonitoringVisitor.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity2.class));
        finish();
    }
}