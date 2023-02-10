package com.example.securityptpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.EmployeeSubcon;
import com.example.securityptpal.model.Subcon;

import java.util.List;

public class SubconEmployeeAdapter extends RecyclerView.Adapter<SubconEmployeeAdapter.SubconEmployeeViewHolder>{
    private Context context;
    private List<EmployeeSubcon> list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

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
