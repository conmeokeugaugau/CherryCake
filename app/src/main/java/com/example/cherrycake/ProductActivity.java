package com.example.cherrycake;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProductActivity extends AppCompatActivity implements ProductGridAdapter.ProductCallback{

    RecyclerView rvListC;
    ArrayList<Product> lstProduct;
    ProductGridAdapter productGridAdapter;

    DrawerLayout mdrawMenuPro;

    FloatingActionButton mFloatButtonPro;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        rvListC = findViewById(R.id.rvGrid);
        LoadData();
        productGridAdapter = new ProductGridAdapter(lstProduct, this);
        GridLayoutManager gridLayoutManager= new GridLayoutManager(this,2);
        rvListC.setAdapter(productGridAdapter);
        rvListC.setLayoutManager(gridLayoutManager);


        // load menu
        mdrawMenuPro = findViewById(R.id.drawmenuPro);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawMenuPro, R.string.nav_menu_op, R.string.nav_menu_cl);
        mdrawMenuPro.addDrawerListener(toggle);
        toggle.syncState();

        mFloatButtonPro = findViewById(R.id.btnFloatingProduct);
        mFloatButtonPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdrawMenuPro.openDrawer(GravityCompat.START);
            }
        });



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
        Intent i = new Intent(this, DetailActivity.class);
        i.putExtra("ten",ten);
        i.putExtra("mota",mota);
        i.putExtra("gia",gia);
        i.putExtra("anh",anh);
        startActivity(i);
    }
}