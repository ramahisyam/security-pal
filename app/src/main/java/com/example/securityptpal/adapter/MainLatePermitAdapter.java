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
import com.example.securityptpal.model.PermissionLate;

import java.util.List;

public class MainLatePermitAdapter extends RecyclerView.Adapter<MainLatePermitAdapter.MainLatePermitViewHolder> {
    private Context context;
    private List<PermissionLate> list;
    private OnPermitListener mOnPermitListener;
    private MainLatePermitAdapter.Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (MainLatePermitAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public MainLatePermitAdapter(Context context, List<PermissionLate> list, OnPermitListener mOnPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
    }

    @NonNull
    @Override
    public MainLatePermitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_late_permit, parent, false);
        return new MainLatePermitAdapter.MainLatePermitViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainLatePermitViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
        holder.nip.setText(list.get(position).getNip());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainLatePermitViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, nip, date;
        ImageView options;
        OnPermitListener onPermitListener;

        public MainLatePermitViewHolder(@NonNull View itemView, OnPermitListener onPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.main_late_employee_name);
            nip = itemView.findViewById(R.id.main_late_employee_nip);
            date = itemView.findViewById(R.id.main_late_employee_date);
            options = itemView.findViewById(R.id.main_late_settings);
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
