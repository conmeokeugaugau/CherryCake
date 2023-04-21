package com.example.cherrycake;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView tvNameDetailC,tvPriceDetailC,tvDescriptionC,tvQuantityC;
    ImageView ivImageDetailC;
    int count = 0;
    ImageButton imageButtonBack;
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

        toggleButtonFavourite = findViewById(R.id.btnFavourite);

        //

        String ten = getIntent().getStringExtra("ten");
        tvNameDetailC.setText(ten);
        int gia = getIntent().getIntExtra("gia",0);
        tvPriceDetailC.setText(gia + " VNĐ");
        String mota = getIntent().getStringExtra("mota");
        tvDescriptionC.setText(mota);
        String anh = getIntent().getStringExtra("anh");

        Glide.with(this).load(anh).into(ivImageDetailC);

        imageButtonBack.setOnClickListener(view -> finish());
    }

    public void increment(View v) {
        count++;
        tvQuantityC.setText("" + count);
    }

    public void decrement(View v) {
        if (count <= 1) {
            count = 1;
        } else {
            count--;
        }
        tvQuantityC.setText("" + count);
    }

    public void onToggleButton(View v){
        if(toggleButtonFavourite.isChecked()) {
            Toast.makeText(this,"Thêm vào danh sách yêu thích thành công",Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(this,"Hủy yêu thích thành công",Toast.LENGTH_SHORT).show();
        }
    }
}