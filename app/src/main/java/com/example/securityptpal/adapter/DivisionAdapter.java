package com.example.securityptpal.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.Division;
import com.example.securityptpal.model.PermissionLate;

import java.util.List;

public class DivisionAdapter extends RecyclerView.Adapter<DivisionAdapter.DivisionViewHolder> {

    private Context context;
    private List<Division> list;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (Dialog dialog) {
        this.dialog = dialog;
    }

    public DivisionAdapter(Context context, List<Division> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public DivisionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_division_list, parent, false);
        return new DivisionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DivisionViewHolder holder, int position) {
        holder.name.setText(list.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DivisionViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView settings;
        public DivisionViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.division_name);
            settings = itemView.findViewById(R.id.division_settings);
            settings.setOnClickListener(view -> {
                if (dialog != null){
                    dialog.onClick(getLayoutPosition());
                }
            });
        }
    }
}
