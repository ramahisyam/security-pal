package com.example.securityptpal.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.securityptpal.R;
import com.example.securityptpal.model.Barang;
import com.example.securityptpal.model.Subcon;

import java.util.List;

public class SubconAdapter extends RecyclerView.Adapter<SubconAdapter.SubconViewHolder>{
    private Context context;
    private List<Subcon> list;
    private OnPermitListener mOnPermitListener;

    public SubconAdapter(Context context, List<Subcon> list, OnPermitListener mOnPermitListener) {
        this.context = context;
        this.list = list;
        this.mOnPermitListener = mOnPermitListener;
    }

    @NonNull
    @Override
    public SubconViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_subcon_list, parent, false);
        return new SubconAdapter.SubconViewHolder(itemView, mOnPermitListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SubconViewHolder holder, int position) {
        holder.name.setText(list.get(position).getCompany());
        holder.start.setText(list.get(position).getStartDate());
        holder.end.setText(list.get(position).getFinishDate());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class SubconViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name, start, end;
        OnPermitListener onPermitListener;
        public SubconViewHolder(@NonNull View itemView, OnPermitListener onPermitListener) {
            super(itemView);
            name = itemView.findViewById(R.id.company_subcon);
            start = itemView.findViewById(R.id.start_subcon);
            end = itemView.findViewById(R.id.end_subcon);

            this.onPermitListener = onPermitListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
