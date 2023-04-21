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

import java.util.List;

public class ItemFavouAdapter extends RecyclerView.Adapter<ItemFavouAdapter.ItemViewHolder>{

    private List<ItemFavou> aListItemFavou;

    public ItemFavouAdapter(List<ItemFavou> aListItemFavou) {
        this.aListItemFavou = aListItemFavou;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfavou_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemFavou item = aListItemFavou.get(position);
        if (item == null)
            return;
//        Glide.with(context).load(item.getItfvpict()).into(holder.aitfvpict);
        Glide.with(holder.aitfvpict.getContext()).load(item.getItfvpict()).into(holder.aitfvpict);
        holder.aitfvname.setText(item.getItfvname());
        holder.aitfvprice.setText(item.getItfvprice());
    }

    @Override
    public int getItemCount() {
        if(aListItemFavou != null){
            return aListItemFavou.size();
        }
        return 0;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView aitfvname, aitfvprice;
        private ImageView aitfvpict;
        private ImageButton aitfvadd;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            aitfvname = itemView.findViewById(R.id.itfvname);
            aitfvprice = itemView.findViewById(R.id.itfvprice);
            aitfvpict = itemView.findViewById(R.id.itfvpict);
            aitfvadd = itemView.findViewById(R.id.itfvadd);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            aitfvadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    public void add(ItemFavou itemFavou) {
        aListItemFavou.add(itemFavou);
        notifyDataSetChanged();
    }
}
