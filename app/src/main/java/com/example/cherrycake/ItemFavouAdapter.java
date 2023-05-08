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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ItemFavouAdapter extends RecyclerView.Adapter<ItemFavouAdapter.ItemViewHolder>{
    private ArrayList<Product> aListItemFavou;
    private onClickItem onClickItemListener;

    private FirebaseFirestore firestore;
    private FirebaseAuth firebaseAuth;
    private String quantity = "1";


    public ItemFavouAdapter(ArrayList<Product> aListItemFavou, onClickItem itemListener) {
        this.aListItemFavou = aListItemFavou;
        this.onClickItemListener = itemListener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemfavou_main,parent,false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        firestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();


        Product item = aListItemFavou.get(position);
        if (item == null)
            return;
//        Glide.with(context).load(item.getItfvpict()).into(holder.aitfvpict);
        holder.aitfvname.setText(item.getName());
        holder.aitfvprice.setText(item.getPrice()+"");
        Glide.with(holder.aitfvpict.getContext()).load(item.getImage()).into(holder.aitfvpict);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickToDetail(item.getName(),item.getPrice(),item.getDescription(),item.getImage());
            }
        });
        holder.aitfvadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> cartMap = new HashMap<>();
                cartMap.put("name", holder.aitfvname.getText().toString());
                cartMap.put("price", holder.aitfvprice.getText().toString());
                cartMap.put("totalQuantity", quantity);
//                    Glide.with(holder.aitfvpict.getContext()).load(item.getImage()).into(holder.aitfvpict);
//                    cartMap.put("anh", aitfvpict.getContext().toString());
                cartMap.put("anh", item.getImage());
                CollectionReference CartcollectionReference = firestore.collection("AddToCart").document(firebaseAuth.getCurrentUser().getUid()).collection("User");
                CartcollectionReference.add(cartMap);
            }
        });
    }
    public interface onClickItem{
        void onClickToDetail(String name, int price, String description, String picture);
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
        private FirebaseFirestore firestore;
        private FirebaseAuth firebaseAuth;
        private String userID, quantity, totalQuantity;
//        final HashMap<String,Object> cartMap = new HashMap<>();
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            aitfvname = itemView.findViewById(R.id.itfvname);
            aitfvprice = itemView.findViewById(R.id.itfvprice);
            aitfvpict = itemView.findViewById(R.id.itfvpict);
            aitfvadd = itemView.findViewById(R.id.itfvadd);

            firestore = FirebaseFirestore.getInstance();
            firebaseAuth = FirebaseAuth.getInstance();
            userID = firebaseAuth.getCurrentUser().getUid();

            quantity = "1";
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
//            aitfvadd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Map<String, Object> cartMap = new HashMap<>();
//                    cartMap.put("name", aitfvname.getText().toString());
//                    cartMap.put("price", aitfvprice.getText().toString());
//                    cartMap.put("totalQuantity", quantity);
////                    Glide.with(holder.aitfvpict.getContext()).load(item.getImage()).into(holder.aitfvpict);
//                    cartMap.put("anh", aitfvpict.getContext().toString());
//                    CollectionReference CartcollectionReference = firestore.collection("AddToCart").document(userID).collection("User");
//                    CartcollectionReference.add(cartMap);
//                }
//            });
        }
    }
    public void add(Product itemFavou) {
        aListItemFavou.add(itemFavou);
        notifyDataSetChanged();
    }
}
