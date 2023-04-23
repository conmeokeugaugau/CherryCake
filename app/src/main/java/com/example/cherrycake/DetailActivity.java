package com.example.cherrycake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    TextView tvNameDetailC,tvPriceDetailC,tvDescriptionC,tvQuantityC;
    ImageView ivImageDetailC;
    int count = 0;
    ImageButton imageButtonBack, imageButtonShare;
    ToggleButton toggleButtonFavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().hide();

        tvNameDetailC = findViewById(R.id.tvNameDetail);
        tvPriceDetailC = findViewById(R.id.tvPriceDetail);
        tvDescriptionC = findViewById(R.id.tvDetail);

        ivImageDetailC = findViewById(R.id.ivImageDetail);

        tvQuantityC = (TextView) findViewById(R.id.tvQuantity);

        imageButtonBack = findViewById(R.id.ibBack);
        imageButtonShare = findViewById(R.id.ibShare);

        toggleButtonFavourite = findViewById(R.id.btnFavourite);

        //load dữ liệu

        //load tên
        String ten = getIntent().getStringExtra("ten");
        tvNameDetailC.setText(ten);

        //load giá ban đầu (giá trị mặc định là 1)
        changePrice(1);

        //load mô tả
        String mota = getIntent().getStringExtra("mota");
        tvDescriptionC.setText(mota);

        //load ảnh
        String anh = getIntent().getStringExtra("anh");

        Glide.with(this).load(anh).into(ivImageDetailC);

        //


        //nút back
        imageButtonBack.setOnClickListener(view -> finish());
        //

        //nút share
        imageButtonShare.setOnClickListener(view -> {ShareApp(DetailActivity.this,ten);});
    }

    //hàm thay đổi đơn vị tiền tệ và updat giá
    public void changePrice(int quantity) {
        int gia = quantity * getIntent().getIntExtra("gia",0);
        String giatien = NumberFormat.getNumberInstance(Locale.US).format(gia);
        tvPriceDetailC.setText(giatien + " VNĐ");
    }

    //hàm tăng số lượng
    public void increment(View v) {
        count++;
        changePrice(count);
        tvQuantityC.setText("" + count);
    }

    //hàm giảm số lượng
    public void decrement(View v) {
        //nếu nhập số lượng nhỏ hơn bằng 1 thì set mặt định số lượng bằng 1
        if (count <= 1) {
            count = 1;
            changePrice(count);
        } else {
            count--;
            changePrice(count);
        }
        tvQuantityC.setText("" + count);
    }

    //hàm bấm nút yêu thích
    public void onToggleButton(View v){
        if(toggleButtonFavourite.isChecked()) {
            Toast.makeText(DetailActivity.this,"Thêm vào danh sách yêu thích",Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(DetailActivity.this,"Xóa khỏi danh sách yêu thích",Toast.LENGTH_SHORT).show();
        };
    }

    //hàm nút share app
    private void ShareApp(Context context,String ten) {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_TEXT,ten);
        i.setType("text/plain");
        context.startActivity(i);
    }
}
