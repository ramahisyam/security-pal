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
    private OnPermitLongClick mOnPermitLongClick;

    public SubconEmployeeAdapter(Context context, List<EmployeeSubcon> list, OnPermitListener mOnPermitListener, OnPermitLongClick mOnPermitLongClick) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
        this.mOnPermitLongClick = mOnPermitLongClick;
    }

    @NonNull
    @Override
    public SubconEmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subcon_employee, parent, false);
        return new SubconEmployeeAdapter.SubconEmployeeViewHolder(itemView, mOnPermitListener, mOnPermitLongClick);
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

    public class SubconEmployeeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView name, nip;
        OnPermitListener onPermitListener;
        OnPermitLongClick onPermitLongClick;
        public SubconEmployeeViewHolder(@NonNull View itemView, OnPermitListener onPermitListener, OnPermitLongClick onPermitLongClick) {
            super(itemView);
            name = itemView.findViewById(R.id.subcon_employee_name);
            nip = itemView.findViewById(R.id.subcon_employee_nip);

            this.onPermitListener = onPermitListener;
            this.onPermitLongClick = onPermitLongClick;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPermitListener.onPermitClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            if (onPermitLongClick != null) {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION){
                    onPermitLongClick.onLongCLickListener(pos);
                }
            }
            return true;
        }
    }
}
