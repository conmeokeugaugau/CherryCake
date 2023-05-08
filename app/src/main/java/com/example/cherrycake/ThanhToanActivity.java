package com.example.cherrycake;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ThanhToanActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<ThanhToanModel> thanhToanModelList;
    ThanhToanAdapter thanhToanAdapter;

    TextView tvToTalPriceOfPay;

    Button btnMuaHang;
    int totalPrice;
    int totalQuantity;

    // firebase
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        auth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnMuaHang = findViewById(R.id.btnPay);
        tvToTalPriceOfPay = findViewById(R.id.tvPriceOfPayFooter);

        // load và nhận dữ liệu tổng tiền từ cart activity
        totalPrice = getIntent().getIntExtra("totalPriceFromCart", 0);
        // format giá
        String totalGia = NumberFormat.getNumberInstance(Locale.US).format(totalPrice);
        tvToTalPriceOfPay.setText(totalGia+ " VNĐ");

        //load và nhận dữ liệu tổng sản phẩm
        totalQuantity = getIntent().getIntExtra("totalQuantityFromCart", 1);


        recyclerView = findViewById(R.id.rvThanhToan);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        thanhToanModelList = new ArrayList<>();

        thanhToanAdapter = new ThanhToanAdapter(this, thanhToanModelList);
        recyclerView.setAdapter(thanhToanAdapter);

        firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                .collection("AddToCart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (DocumentSnapshot doc :task.getResult().getDocuments()){
                                ThanhToanModel thanhToanModel = doc.toObject(ThanhToanModel.class);
                                thanhToanModelList.add(thanhToanModel);
                                thanhToanAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });

        btnMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placedOrder();
            }
        });
    }

    private void placedOrder() {

        // các biến lưu thời gian mua hàng
        String saveCurrentDate, saveCurrentTime;
        Calendar callForDate = Calendar.getInstance();
        Calendar callForTime = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MM dd, yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(callForTime.getTime());

//        if(thanhToanModelList != null && thanhToanModelList.size() > 0){
//            for(ThanhToanModel model : thanhToanModelList){


        if(thanhToanModelList != null && thanhToanModelList.size() > 0){
            for(ThanhToanModel model : thanhToanModelList){
                Map<String, Object> infoorder = new HashMap<>();
                infoorder.put("Tên", model.getName());
                infoorder.put("Số lượng", model.getTotalQuantity());
                infoorder.put("Giá", model.getPrice());

                CollectionReference OrderInfoCollectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                        .collection("Orders").document("Order Info").collection("Order Info");
                OrderInfoCollectionReference.add(infoorder);
            }
        }

                Map<String, Object> dateInfo = new HashMap<>();
                dateInfo.put("Số lượng", totalQuantity);
                dateInfo.put("Giá",totalPrice);
                dateInfo.put("Ngày", saveCurrentDate);
                dateInfo.put("Thời gian", saveCurrentTime);
                CollectionReference OrderscollectionReference = firestore.collection("USERS").document(auth.getCurrentUser().getUid())
                                .collection("Orders");
                OrderscollectionReference.add(dateInfo).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
//                                OrderscollectionReference.add(dateInfo);

                                Intent i = new Intent(ThanhToanActivity.this, SuccessActivity.class);
                                startActivity(i);

                            }
                        });

    }
}