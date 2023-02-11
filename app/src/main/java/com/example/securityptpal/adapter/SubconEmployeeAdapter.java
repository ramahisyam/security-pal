package com.example.securityptpal.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.DetailSubconActivity;
import com.example.securityptpal.IdcardDetailSubcon;
import com.example.securityptpal.MonitoringSubcontractor;
import com.example.securityptpal.R;
import com.example.securityptpal.model.EmployeeSubcon;
import com.example.securityptpal.model.Subcon;

import java.util.ArrayList;
import java.util.List;

public class SubconEmployeeAdapter extends RecyclerView.Adapter<SubconEmployeeAdapter.SubconEmployeeViewHolder>{
    private Context context;
    private List<EmployeeSubcon> list;
    Subcon subcon;
    private OnPermitListener mOnPermitListener;

    public SubconEmployeeAdapter(Context context, List<EmployeeSubcon> list, OnPermitListener mOnPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
    }

    @NonNull
    @Override
    public SubconEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subcon_employee, parent, false);
        return new SubconEmployeeAdapter.SubconEmployeeViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubconEmployeeViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.nip.setText(list.get(position).getNip());
//        holder.age.setText(list.get(position).getName());
//        holder.address.setText(list.get(position).getNip());
//        holder.phone.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    public class SubconEmployeeViewHolder extends RecyclerView.ViewHolder {
//        TextView name, nip, age, address, phone;
//        ConstraintLayout download;
//        public SubconEmployeeViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.subcon_employee_name);
//            nip = itemView.findViewById(R.id.subcon_employee_nip);
//            age = itemView.findViewById(R.id.subcon_employee_age);
//            address = itemView.findViewById(R.id.subcon_employee_address);
//            phone = itemView.findViewById(R.id.subcon_employee_phone);
//            download = itemView.findViewById(R.id.downloadidcard);
//
//            download.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(context, IdcardDetailSubcon.class);
//                    intent.putExtra("SUBCON_IDCARD", list.get(getAdapterPosition()));
//                    context.startActivity(intent);
//                }
//            });

    public class SubconEmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, nip;
        OnPermitListener onPermitListener;
        public SubconEmployeeViewHolder(@NonNull View itemView, OnPermitListener onPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.subcon_employee_name);
            nip = itemView.findViewById(R.id.subcon_employee_nip);

            this.onPermitListener = onPermitListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPermitListener.onPermitClick(getAdapterPosition());
        }
    }
}
