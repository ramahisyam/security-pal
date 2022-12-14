package com.example.securityptpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.PermissionEmployee;
import com.example.securityptpal.model.Visitor;

import java.util.List;

public class MainVisitorPermitAdapter extends RecyclerView.Adapter<MainVisitorPermitAdapter.MainPermitViewHolder>{
    private Context context;
    private List<Visitor> list;
    private OnPermitListener mOnPermitListener;
    private MainVisitorPermitAdapter.Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (MainVisitorPermitAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public MainVisitorPermitAdapter(Context context, List<Visitor> list, OnPermitListener onPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = onPermitListener;
    }

    @NonNull
    @Override
    public MainPermitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_visitor_permission, parent, false);
        return new MainVisitorPermitAdapter.MainPermitViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainVisitorPermitAdapter.MainPermitViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.company.setText(list.get(position).getCompany());
        holder.date.setText(list.get(position).getDate());
        if (list.get(position).getDivision_approval().equals("Accepted") && list.get(position).getCenter_approval().equals("Accepted")){
            holder.status.setText("Accepted");
            holder.status.setTextColor(holder.status.getResources().getColor(R.color.main_green_color));
        } else if (list.get(position).getDivision_approval().equals("Rejected") || list.get(position).getCenter_approval().equals("Rejected")) {
            holder.status.setText("Rejected");
            holder.status.setTextColor(holder.status.getResources().getColor(R.color.cardColorRed));
        } else {
            holder.status.setText("Pending");
            holder.status.setTextColor(holder.status.getResources().getColor(R.color.main_orange_color));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainPermitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, company, status, date;
        ImageView options;
        OnPermitListener onPermitListener;
        public MainPermitViewHolder(@NonNull View itemView, OnPermitListener onPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.main_visitor_name);
            company = itemView.findViewById(R.id.main_visitor_company);
            status = itemView.findViewById(R.id.main_visitor_status);
            date = itemView.findViewById(R.id.main_visitor_date);
            options = itemView.findViewById(R.id.main_visitor_settings);
            options.setOnClickListener(view -> {
                if (dialog != null){
                    dialog.onClick(getLayoutPosition());
                }
            });
            this.onPermitListener = onPermitListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPermitListener.onPermitClick(getAdapterPosition());
        }
    }
}
