package com.example.cherrycake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    // Khai báo //
    TextView tvNameDetailC,tvPriceDetailC,tvDescriptionC,tvQuantityC;
    ImageView ivImageDetailC;
    int count = 0;
    ImageButton imageButtonBack, imageButtonShare;
    CheckBox checkBoxFavourite;
    private CollectionReference favouriteList = FirebaseFirestore.getInstance().collection("FAVOURITES");
    Product product = new Product();
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide(); // Ẩn ActionBar //

        // Ánh xạ //
        tvNameDetailC = findViewById(R.id.tvNameDetail);
        tvPriceDetailC = findViewById(R.id.tvPriceDetail);
        tvDescriptionC = findViewById(R.id.tvDetail);

        ivImageDetailC = findViewById(R.id.ivImageDetail);

        tvQuantityC = (TextView) findViewById(R.id.tvQuantity);

        imageButtonBack = findViewById(R.id.ibBack);
        imageButtonShare = findViewById(R.id.ibShare);

        checkBoxFavourite = findViewById(R.id.btnFavourite);

        // Load dữ liệu //


        //load user

        //load tên
        String ten = getIntent().getStringExtra("ten");
        tvNameDetailC.setText(ten);
        product.setName(ten);

        //load giá ban đầu (giá trị mặc định là 1)
        changePrice(1);

        //load mô tả
        String mota = getIntent().getStringExtra("mota");
        tvDescriptionC.setText(mota);
        product.setDescription(mota);

        //load ảnh
        String anh = getIntent().getStringExtra("anh");

        Glide.with(this).load(anh).into(ivImageDetailC);
        product.setImage(anh);
        //


        //nút back
        imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DetailActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
        //

        //nút share
        imageButtonShare.setOnClickListener(view -> {ShareApp(DetailActivity.this,ten);});


        // Favourite //

        // Check PRODUCTS có trùng với FAVOURITES bằng cách so sánh Image //
        // Nếu có dữ liệu trong đây thì tiến hành checkbox                //
        // Không có thì tiếp tục xuống dưới                               //
        favouriteList.whereEqualTo("image",anh)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                            checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                    if(b) {
                                        Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                                        checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
                                        removeFavourite(anh);
                                    } else {
                                        Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                                        checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                                        addFavourite(ten,1,mota,anh);
                                    }
                                }
                            });
                        }

                    }
                });
        //


        //nút favourite

        checkBoxFavourite.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
                    checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite2);
                    addFavourite(ten,1,mota,anh);
                    //
                } else {
                    Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
                    checkBoxFavourite.setBackgroundResource(R.drawable.ic_favourite);
                    removeFavourite(anh);
                    //
                }
            }
        });

    }



    // Thêm vào danh sách yêu thích //
    public void addFavourite(String ten,int gia,String mota,String anh) {
        product = new Product(null,ten,mota,null,anh,gia);
        favouriteList.add(product);
    }
    // Xóa khỏi danh sách yêu thích //
    public void removeFavourite(String anh) {
        favouriteList.whereEqualTo("image",anh)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String documentId = "";

                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            documentId = documentSnapshot.getId();
                        }
                        favouriteList.document(documentId).delete();
                    }
                });

    }
    // Thay đổi đơn vị tiền tệ và update giá
    public void changePrice(int quantity) {
        int gia = quantity * getIntent().getIntExtra("gia",0);
        String giatien = NumberFormat.getNumberInstance(Locale.US).format(gia);
        tvPriceDetailC.setText(giatien + " VNĐ");
        product.setPrice(getIntent().getIntExtra("gia",0));
    }

    // Tăng số lượng //
    public void increment(View v) {
        count++;
        changePrice(count);
        tvQuantityC.setText("" + count);
    }

    // Giảm số lượng //
    public void decrement(View v) {
        // Nếu nhập số lượng nhỏ hơn bằng 1 thì set mặt định số lượng bằng 1
        if (count <= 1) {
            count = 1;
            changePrice(count);
        } else {
            count--;
            changePrice(count);
        }
        tvQuantityC.setText("" + count);
    }

    // Share app
    private void ShareApp(Context context,String ten) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT,ten);
        i.setType("text/plain");
        context.startActivity(i);
    }
}
