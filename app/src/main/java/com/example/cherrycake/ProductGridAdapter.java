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

import java.util.ArrayList;

public class ProductGridAdapter extends RecyclerView.Adapter<ProductGridAdapter.ProductViewHolder> {
    ArrayList<Product> lstProduct;
    Context context;
    ProductCallback productCallBack;


    public ProductGridAdapter(ArrayList<Product> lstProduct, ProductCallback productCallBack) {
        this.lstProduct = lstProduct;
        this.productCallBack = productCallBack;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Nạp layout cho View biểu diễn phần tử user
        View userView = inflater.inflate(R.layout.layoutitemgrid, parent, false);
        //
        ProductViewHolder viewHolder = new ProductViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // lấy item của dữ liệu
        Product item = lstProduct.get(position);
        // gán item  của view
        holder.textViewNameProduct.setText(item.getName());
        holder.textViewPriceProduct.setText(item.getPrice()+"");
        Glide.with(context).load(item.getImage())
                .into(holder.imageViewProduct);
        // lấy sự kiện
        holder.itemView.setOnClickListener(view -> productCallBack.onItemClick(item.getName(),item.getPrice(),item.getDescription(),item.getImage()));
    }

    @Override
    public int getItemCount() {
        return lstProduct.size();
    }

    class ProductViewHolder extends RecyclerView.ViewHolder{
        private TextView textViewNameProduct, textViewPriceProduct, textViewDescriptionProduct;
        private ImageView imageViewProduct;
        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewNameProduct = itemView.findViewById(R.id.edtTenSP);
            textViewPriceProduct = itemView.findViewById(R.id.edtGiaSP);
            imageViewProduct = itemView.findViewById(R.id.ivAvatar);
        }
    }

    public interface ProductCallback {
        void onItemClick(String ten,int gia,String mota,String anh);
    }
    public void add(Product product) {
        lstProduct.add(product);
        notifyDataSetChanged();
    }
}
