package com.example.cherrycake;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GIoHangActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<MyCartModel> cartModelList;
    MyCartAdapter cartAdapter;


    // biến chứa tổng giá tiền của cart
    int totalBill;
    int totalQuantity;
    TextView AllAmount;

    Button btnThanhToan;
    TextView btnBack;
    TextView btnHome;


    //firestore
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);

        // ánh xạ
        AllAmount = findViewById(R.id.tvCartTotalPrice);
        btnBack = (TextView) findViewById(R.id.iconCartBack);
        btnHome = (TextView) findViewById(R.id.iconCartHome);
        btnThanhToan = (Button) findViewById(R.id.btnCartThanhToan);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(GIoHangActivity.this, HomeActivity.class);
//                startActivity(i);
                finish();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(GIoHangActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();


        // get dữ liệu tổng giá tiền từ cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mMessageReceiver, new IntentFilter("MyTotalAmount"));
//
//        // get dữ liệu tổng sản phẩm từ cart adapter
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(qMessageReceiver, new IntentFilter("MyTotalQuantity"));

        //set dữ liệu
        recyclerView = findViewById(R.id.rvCart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartModelList = new ArrayList<>();
        cartAdapter = new MyCartAdapter(this, cartModelList);
        recyclerView.setAdapter(cartAdapter);


//        totalQuantity = getIntent().getStringExtra("totalQuantity");

        firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc :task.getResult().getDocuments()){
                                MyCartModel myCartModel = doc.toObject(MyCartModel.class);
                                cartModelList.add(myCartModel);
                                cartAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(GIoHangActivity.this, ThanhToanActivity.class);
                i.putExtra("totalPriceFromCart", totalBill);
                i.putExtra("totalQuantityFromCart", totalQuantity);
                startActivity(i);
            }
        });
    }

    // hàm nhận tổng giá tiền từ cart adapter
    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            totalBill = intent.getIntExtra("totalAmount", 0);
            String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalBill);
            AllAmount.setText(totalGia + " VNĐ");
        }
    };

    // hàm nhận tổng sản phẩm từ cart adapter
    public BroadcastReceiver qMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            totalQuantity = intent.getIntExtra("totalQuantity",1);
        }
    };
}