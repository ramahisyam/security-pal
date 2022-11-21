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

import java.util.List;

public class MainGoodsPermitAdapter extends RecyclerView.Adapter<MainGoodsPermitAdapter.MainGoodsViewHolder>{
    private Context context;
    private List<Barang> list;
    private OnPermitListener mOnPermitListener;
    private Dialog dialog;

    public interface Dialog {
        void onClick(int pos);
    }

    public void setDialog (MainGoodsPermitAdapter.Dialog dialog) {
        this.dialog = dialog;
    }

    public MainGoodsPermitAdapter(Context context, List<Barang> list, OnPermitListener mOnPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
    }

    @NonNull
    @Override
    public MainGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main_goods_permission, parent, false);
        return new MainGoodsPermitAdapter.MainGoodsViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MainGoodsViewHolder holder, int position) {
        holder.name.setText(list.get(position).getGoods_name());
        holder.userName.setText(list.get(position).getName());
        holder.date.setText(list.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MainGoodsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, userName, date;
        ImageView options;
        OnPermitListener onPermitListener;
        public MainGoodsViewHolder(@NonNull View itemView, OnPermitListener onPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.main_goods_name);
            userName = itemView.findViewById(R.id.main_goods_user_name);
            date = itemView.findViewById(R.id.main_goods_date);
            options = itemView.findViewById(R.id.main_goods_settings);
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
