package com.example.cherrycake;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ThanhToanAdapter extends RecyclerView.Adapter<ThanhToanAdapter.ViewHolder>{
    Context context;
    List<ThanhToanModel> list;

    public ThanhToanAdapter(Context context, List<ThanhToanModel> list) {
        this.context = context;
        this.list = list;
    }

    public ThanhToanAdapter() {
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ thanhtoan
        View userView = inflater.inflate(R.layout.thanhtoan_item,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhToanModel item = list.get(position);
//        Glide.with(holder.ivBanh.getContext()).load(item.getImage()).into(holder.ivBanh);
        Glide.with(context).load(item.getAnh()).into(holder.ivBanh);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice()+"");
        holder.totalQuantity.setText(item.getTotalQuantity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView ivBanh;
        TextView name, price, totalQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBanh = itemView.findViewById(R.id.ivThanhToanItem);
            name = itemView.findViewById(R.id.tvNameThanhToanItem);
            price = itemView.findViewById(R.id.tvPriceThanhToanItem);
            totalQuantity = itemView.findViewById(R.id.tvQuantityThanhToanItem);
        }
    }
}
