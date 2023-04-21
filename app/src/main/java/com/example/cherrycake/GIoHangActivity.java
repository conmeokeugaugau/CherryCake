package com.example.cherrycake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GIoHangActivity extends AppCompatActivity {
    TextView tviconCartBack, getTviconCartHome, tvThanhToan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        tviconCartBack = findViewById(R.id.iconCartBack);
        getTviconCartHome = findViewById(R.id.iconCartHome);
        tvThanhToan = findViewById(R.id.btnCartThanhToan);

        tviconCartBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GIoHangActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        tvThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GIoHangActivity.this, ThanhToanActivity.class);
                startActivity(i);
            }
        });

        getTviconCartHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GIoHangActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });
    }
}