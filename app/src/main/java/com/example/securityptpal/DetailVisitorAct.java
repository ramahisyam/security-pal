package com.example.securityptpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.securityptpal.employee.DetailPermissionActivity;
import com.example.securityptpal.model.PermissionEmployee;
import com.example.securityptpal.model.Visitor;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DetailVisitorAct extends AppCompatActivity {
    private TextView name, company, phone, division, department, pic, necessity, date, timein, timeout, division_approve, center_approve;
    ImageView qrVisitor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_visitor);

        name = findViewById(R.id.name);
        company = findViewById(R.id.company);
        phone = findViewById(R.id.phone);
        division = findViewById(R.id.division);
        department = findViewById(R.id.department);
        pic = findViewById(R.id.pic);
        necessity = findViewById(R.id.necessity);
        date = findViewById(R.id.date);
        timein = findViewById(R.id.timein);
        timeout = findViewById(R.id.timeout);
        division_approve = findViewById(R.id.division_approval_status);
        center_approve = findViewById(R.id.center_approval);
        qrVisitor = findViewById(R.id.qrVisitor);

        qrVisitor.setEnabled(false);

        qrVisitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailVisitorAct.this, ShowQR.class));
            }
        });

        Visitor visitor = getIntent().getParcelableExtra("permission");
        name.setText(visitor.getName());
        company.setText(visitor.getCompany());
        phone.setText(visitor.getPhone());
        division.setText(visitor.getDivision());
        department.setText(visitor.getDepartment());
        pic.setText(visitor.getPic());
        necessity.setText(visitor.getNecessity());
        date.setText(visitor.getDate());
        timein.setText(visitor.getTimein());
        timeout.setText(visitor.getTimeout());

        if (visitor.getDivision_approval().equals("Pending")){
            division_approve.setText(visitor.getDivision_approval());
            division_approve.setTextColor(division_approve.getResources().getColor(R.color.main_orange_color));
            division_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(DetailVisitorAct.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("PENDING")
                            .setContentText("Please wait the permission status")
                            .setConfirmText("Close")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        } else if (visitor.getDivision_approval().equals("Accepted")){
            division_approve.setText(visitor.getDivision_approval());
            division_approve.setTextColor(division_approve.getResources().getColor(R.color.main_green_color));
            division_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(DetailVisitorAct.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("ACCEPTED")
                            .setContentText("Please continue to security permission")
                            .setConfirmText("Close")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        } else {
            division_approve.setText(visitor.getDivision_approval());
            division_approve.setTextColor(division_approve.getResources().getColor(R.color.cardColorRed));
            division_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(DetailVisitorAct.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("REJECTED")
                            .setContentText("Sorry you can't continue to security permission")
                            .setConfirmText("Close")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        }

        if (visitor.getCenter_approval().equals("Pending")){
            center_approve.setText(visitor.getCenter_approval());
            center_approve.setTextColor(center_approve.getResources().getColor(R.color.main_orange_color));
            center_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(DetailVisitorAct.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("PENDING")
                            .setContentText("Please wait")
                            .setConfirmText("Close")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        } else if (visitor.getCenter_approval().equals("Accepted")){
            center_approve.setText(visitor.getCenter_approval());
            center_approve.setTextColor(center_approve.getResources().getColor(R.color.main_green_color));
            center_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(DetailVisitorAct.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("ACCEPTED")
                            .setContentText("Congratulations")
                            .setConfirmText("Close")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        } else {
            center_approve.setText(visitor.getCenter_approval());
            center_approve.setTextColor(center_approve.getResources().getColor(R.color.cardColorRed));
            center_approve.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    new SweetAlertDialog(DetailVisitorAct.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("REJECTED")
                            .setContentText("Contact your department")
                            .setConfirmText("Close")
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.dismissWithAnimation();
                                }
                            })
                            .show();
                }
            });
        }

        if (visitor.getDivision_approval().equals("Accepted") && visitor.getCenter_approval().equals("Accepted")){
            qrVisitor.setEnabled(true);
        }
    }
}