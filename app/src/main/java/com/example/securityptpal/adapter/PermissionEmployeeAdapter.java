package com.example.securityptpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;

import java.util.List;

public class PermissionEmployeeAdapter extends RecyclerView.Adapter<PermissionEmployeeAdapter.MyViewHolder> {
    private Context context;
    private List<PermissionEmployee> list;

    public PermissionEmployeeAdapter(Context context, List<PermissionEmployee> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee_permission, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.base.setText(list.get(position).getBase());
        holder.name.setText(list.get(position).getName());
        holder.nip.setText(list.get(position).getNip());
        if (list.get(position).getStatus().equals("accepted")){
            holder.status.setText(list.get(position).getStatus());
            holder.status.setTextColor(holder.status.getResources().getColor(R.color.main_green_color));
        } else if (list.get(position).getStatus().equals("pending")) {
            holder.status.setText(list.get(position).getStatus());
            holder.status.setTextColor(holder.status.getResources().getColor(R.color.main_orange_color));
        } else {
            holder.status.setText(list.get(position).getStatus());
            holder.status.setTextColor(holder.status.getResources().getColor(R.color.cardColorRed));
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView base, name, nip, status;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            base = itemView.findViewById(R.id.base_permission);
            name = itemView.findViewById(R.id.employee_name);
            nip = itemView.findViewById(R.id.employee_nip);
            status = itemView.findViewById(R.id.permission_status);
        }
    }
}
