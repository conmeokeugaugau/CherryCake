package com.example.cherrycake;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {

    Context context;
    List<MyCartModel> list;

    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    private FirebaseAuth auth = FirebaseAuth.getInstance();

    int totalAmount = 0;

    int totalQuantity;

    public MyCartAdapter(Context context, List<MyCartModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override // hàm này dược sử dụng để tạo ra các View hiển thị
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // nạp layout cho view biểu diễn phần từ gio hang item
        View userView = inflater.inflate(R.layout.cart_item,parent,false);
        //
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override     // pthuc này dùng để gán dữ liệu cho View
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MyCartModel item = list.get(position);
//        Glide.with(holder.ivBanh.getContext()).load(item.getImage()).into(holder.ivBanh);
//        Glide.with(context).load(item.getAnh()).into(holder.ivBanh);
        Glide.with(context).load(item.getAnh()).into(holder.ivBanh);
        holder.name.setText(item.getName());
        holder.price.setText(item.getPrice()+"");
        holder.totalQuantity.setText(item.getTotalQuantity());

        // total mount gủi đến to cart activity
        totalAmount = totalAmount + list.get(position).getPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount", totalAmount);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

        //total quantity gửi đến to cart activity
        totalQuantity = totalQuantity + Integer.parseInt(list.get(position).getTotalQuantity());
        Intent i = new Intent("MyTotalQuantity");
        i.putExtra("totalQuantity", totalQuantity);
        LocalBroadcastManager.getInstance(context).sendBroadcast(i);

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
            ivBanh = itemView.findViewById(R.id.ivCartItem);
            name = itemView.findViewById(R.id.tvCartNameItem);
            price = itemView.findViewById(R.id.tvCartItemPrice);
            totalQuantity = itemView.findViewById(R.id.tvCartSoLuong);
        }
    }
}
