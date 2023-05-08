package com.example.cherrycake;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ItemRcmAdapter extends RecyclerView.Adapter<ItemRcmAdapter.ItemViewHolder> {
    private ArrayList<Product> aListItemRCM;
    private onClickItem onClickItemListener;

    public ItemRcmAdapter(ArrayList<Product> aListItemRCM, onClickItem itemListener ) {
        this.aListItemRCM = aListItemRCM;
        this.onClickItemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrcm_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Product item = aListItemRCM.get(position);
        if (item == null)
            return;
//        Glide.with(context).load(item.getItfvpict()).into(holder.aitfvpict);
        holder.aitrcmname.setText(item.getName());
        holder.aitrcmprice.setText(item.getPrice()+"");
        Glide.with(holder.aitrcmpict.getContext()).load(item.getImage()).into(holder.aitrcmpict);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickToDetail(item.getName(),item.getPrice(),item.getDescription(),item.getImage());
            }
        });
    }
    public interface onClickItem{
        void onClickToDetail(String name, int price, String description, String picture);
    }
    @Override
    public int getItemCount() {
        if(aListItemRCM != null){
            return aListItemRCM.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView aitrcmname, aitrcmprice;
        private ImageView aitrcmpict;
        private ImageButton aitrcmadd;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            aitrcmname = itemView.findViewById(R.id.itrcmName);
            aitrcmprice = itemView.findViewById(R.id.itrcmPrice);
            aitrcmpict = itemView.findViewById(R.id.itrcmPict);
            aitrcmadd = itemView.findViewById(R.id.itrcmAdd);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            aitrcmadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    public void add(Product itemRcm) {
        aListItemRCM.add(itemRcm);
        notifyDataSetChanged();
    }
}
