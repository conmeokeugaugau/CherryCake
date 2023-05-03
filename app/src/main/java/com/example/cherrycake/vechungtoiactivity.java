package com.example.cherrycake;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class vechungtoiactivity extends AppCompatActivity {
    private Button mbtntrangchu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vechungtoiactivity);

        mbtntrangchu = findViewById(R.id.btntrangchu);
        trovetrangchu();

    }

    private void trovetrangchu() {
        mbtntrangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(vechungtoiactivity.this, HomeActivity.class);
                startActivity(intent);

            }
        });
    }
}