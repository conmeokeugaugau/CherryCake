package com.example.cherrycake;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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


public class ProductFragmentActivity extends Fragment implements ProductGridAdapter.ProductCallback{
    RecyclerView rvListC;
    ArrayList<Product> lstProduct;
    ProductGridAdapter productGridAdapter;
    DrawerLayout mdrawMenuPro;
    FloatingActionButton mFloatButtonPro;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_product_activity, container, false);
        rvListC = v.findViewById(R.id.rvGrid);
        LoadData();
        productGridAdapter = new ProductGridAdapter(lstProduct, this);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(ProductFragmentActivity.this.getActivity(),2);
        rvListC.setAdapter(productGridAdapter);
        rvListC.setLayoutManager(gridLayoutManager);


        // load menu
        Menu();

        mFloatButtonPro = v.findViewById(R.id.btnFloatingProduct);
        mFloatButtonPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdrawMenuPro.openDrawer(GravityCompat.START);
            }
        });
        return v;
    }
    void LoadData() {
        lstProduct =new ArrayList<>();
        FirebaseFirestore.getInstance()
                .collection("PRODUCTS")
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
        Intent i = new Intent(ProductFragmentActivity.this.getActivity(), DetailActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
    void Menu(){
        mdrawMenuPro = v.findViewById(R.id.drawmenuPro);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(ProductFragmentActivity.this.getActivity(), mdrawMenuPro, R.string.nav_menu_op, R.string.nav_menu_cl);
        mdrawMenuPro.addDrawerListener(toggle);
        toggle.syncState();
    }
}