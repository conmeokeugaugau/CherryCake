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

public class ItemRcmAdapter extends RecyclerView.Adapter<ItemRcmAdapter.ItemViewHolder> {
    private List<ItemRcm> aListItemRCM;

    public ItemRcmAdapter(List<ItemRcm> aListItemRCM) {
        this.aListItemRCM = aListItemRCM;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemrcm_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemRcm item = aListItemRCM.get(position);
        if (item == null)
            return;
        Glide.with(holder.aitrcmpict.getContext()).load(item.getItrcmpict()).into(holder.aitrcmpict);
        holder.aitrcmname.setText(item.getItrcmname());
        holder.aitrcmprice.setText(item.getItrcmprice());
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
    public void add(ItemRcm itemRcm) {
        aListItemRCM.add(itemRcm);
        notifyDataSetChanged();
    }
}
