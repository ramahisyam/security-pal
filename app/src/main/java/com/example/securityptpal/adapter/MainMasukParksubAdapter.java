package com.example.securityptpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.MasukPS;

import java.util.List;

public class MainMasukParksubAdapter extends RecyclerView.Adapter<MainMasukParksubAdapter.MainMasukParksubViewHolder>{
    private Context context;
    private List<MasukPS> list;
    private OnPermitListener mOnPermitListener;

    public MainMasukParksubAdapter(Context context, List<MasukPS> list, OnPermitListener mOnPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
    }

    @NonNull
    @Override
    public MainMasukParksubAdapter.MainMasukParksubViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_masuk_parksub, parent, false);
        return new MainMasukParksubAdapter.MainMasukParksubViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainMasukParksubAdapter.MainMasukParksubViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.nopol.setText(list.get(position).getNopol());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainMasukParksubViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, nopol, date;
        OnPermitListener onPermitListener;
        public MainMasukParksubViewHolder(@NonNull View itemView, OnPermitListener onPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.main_parksub_employee_name);
            nopol = itemView.findViewById(R.id.main_parksub_employee_nopol);
            date = itemView.findViewById(R.id.main_parksub_employee_date);

            this.onPermitListener = onPermitListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPermitListener.onPermitClick(getAdapterPosition());
        }
    }
}
