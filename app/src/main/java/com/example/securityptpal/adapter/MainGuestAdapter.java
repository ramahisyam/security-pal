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
import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.Guest;

import java.util.List;

public class MainGuestAdapter extends RecyclerView.Adapter<MainGuestAdapter.MainGuestViewHolder>{
    private Context context;
    private List<Guest> list;
    private OnPermitListener mOnPermitListener;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (Dialog dialog) {
        this.dialog = dialog;
    }

    public MainGuestAdapter(Context context, List<Guest> list, OnPermitListener mOnPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
    }

    @NonNull
    @Override
    public MainGuestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_guest_permit, parent, false);
        return new MainGuestAdapter.MainGuestViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainGuestViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.phone.setText(list.get(position).getPhone());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainGuestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, phone, date;
        ImageView options;
        OnPermitListener onPermitListener;
        public MainGuestViewHolder(@NonNull View itemView, OnPermitListener mOnPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.main_guest_name);
            phone = itemView.findViewById(R.id.main_guest_phone);
            date = itemView.findViewById(R.id.main_guest_date);
            options = itemView.findViewById(R.id.main_guest_settings);
            options.setOnClickListener(view -> {
                if (dialog != null){
                    dialog.onClick(getLayoutPosition());
                }
            });
            this.onPermitListener = mOnPermitListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onPermitListener.onPermitClick(getAdapterPosition());
        }
    }
}
