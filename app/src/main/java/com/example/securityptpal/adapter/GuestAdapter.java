package com.example.securityptpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.Guest;
import com.example.securityptpal.model.PermissionLate;

import java.util.List;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.GuestViewHolder>{
    private Context context;
    private List<Guest> list;
    private GuestAdapter.OnGuestListener mOnGuestListener;

    public GuestAdapter(Context context, List<Guest> list, OnGuestListener mOnGuestListener) {
        this.context = context;
        this.list = list;
        this.mOnGuestListener = mOnGuestListener;
    }

    @NonNull
    @Override
    public GuestAdapter.GuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_guest_list, parent, false);
        return new GuestAdapter.GuestViewHolder(itemView, mOnGuestListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GuestAdapter.GuestViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.company.setText(list.get(position).getCompany());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class GuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, company, date;
        GuestAdapter.OnGuestListener onGuestListener;
        public GuestViewHolder(@NonNull View itemView, OnGuestListener onGuestListener) {
            super(itemView);
            name = itemView.findViewById(R.id.guest_name);
            company = itemView.findViewById(R.id.guest_company);
            date = itemView.findViewById(R.id.guest_date);
            this.onGuestListener = onGuestListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onGuestListener.onGuestClick(getAdapterPosition());
        }
    }

    public interface OnGuestListener {
        void onGuestClick(int position);
    }
}
