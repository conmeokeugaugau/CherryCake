package com.example.cherrycake;

import android.content.Intent;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class FavouriteFragmentActivity extends Fragment implements ProductGridAdapter.ProductCallback{
    RecyclerView rvListC;
    ArrayList<Product> lstProduct;
    ProductGridAdapter productGridAdapter;
    View v;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_product_activity, container, false);
        LoadData();
        return v;
    }
    void LoadData() {
        lstProduct =new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("FAVOURITES")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> dsList = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot ds:dsList) {
                            Product product = ds.toObject(Product.class);
                            productGridAdapter.add(product);
                        }
                    }
                });
    }
    @Override
    public void onItemClick(String ten, int gia, String mota, String anh) {
        Intent i = new Intent(FavouriteFragmentActivity.this.getActivity(), DetailActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
}