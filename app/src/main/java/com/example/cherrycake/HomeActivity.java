package com.example.cherrycake;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class HomeActivity extends AppCompatActivity implements ProductAdapter.ProductCallback, ProductFavouriteAdapter.ProductCallback {
    DrawerLayout mdrawLo;

    RecyclerView rvListC,rvListFavouriteC;
    ArrayList<Product> lstProduct;
    ProductAdapter productAdapter;
    ProductFavouriteAdapter productFavouriteAdapter;

    ImageButton mimgbtImage, mimgbtaboutus, mimgbtfind, mimgbtcart, mimgbtdirect, mimgbtphone;

    LinearLayout mcake, mcandy, mcupcake, mcroissant;
    ImageView mcakeimg, mcandyimg, mcupcakeimg, mcroissantimg;
    TextView mcaketv, mcandytv, mcupcaketv, mcroissanttv;

    TextView mtvaboutus,mtvViewAll1, mtvViewAll2;
    RecyclerView mrcvFavouItem, mrcvRcmItem;
    ItemFavouAdapter mItemFavouAdapter;
    ItemRcmAdapter mItemRcmAdapter;
    List<ItemFavou> itcakefv, itcandyfv, itcupcakefv, itcroissfv;
    List<ItemRcm> itcakercm, itcandyrcm, itcupcakercm, itcroissrcm;
    FirebaseFirestore firestoreHome;

    MeowBottomNavigation btnav;
    int selectedMenu = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        rvListC = findViewById(R.id.rcvRcmItem);
        rvListFavouriteC = findViewById(R.id.rcvFavouItem);

        getSupportActionBar().hide();

        //Menu
        mdrawLo = findViewById(R.id.drawLo);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mdrawLo, R.string.nav_menu_op,R.string.nav_menu_cl);
        mdrawLo.addDrawerListener(toggle);
        toggle.syncState();
        mimgbtImage = findViewById(R.id.imgbtImage);
        mimgbtImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdrawLo.openDrawer(GravityCompat.START);
            }
        });


        mimgbtfind = findViewById(R.id.imgbtfind);
        mimgbtcart = findViewById(R.id.imgbtcart);
        mimgbtfind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        //Trỏ tới giỏ hàng
        mimgbtcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cartintent = new Intent(HomeActivity.this, GIoHangActivity.class);
                startActivity(cartintent);
            }
        });

        //Liên hệ
        mimgbtdirect = findViewById(R.id.imgbtdirect);
        mimgbtphone = findViewById(R.id.imgbtphone);
        mimgbtaboutus = findViewById(R.id.imgbtaboutus);
        mtvaboutus = findViewById(R.id.tvaboutus);
        mimgbtdirect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://maps.app.goo.gl/QZDpT5coQrjGzQFw6";
                Intent map = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(map);
            }
        });
        mimgbtphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent callPhone = new Intent((Intent.ACTION_DIAL));
                callPhone.setData(Uri.parse("tel: 0948369673"));
                startActivity(callPhone);
            }
        });
        //Nhấp vào test để trỏ tới giỏ hàng
        mtvaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToAboutUs();
            }
        });
        //Nhấp vào icon để trỏ tới giỏ hàng
        mimgbtaboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                directToAboutUs();
            }
        });


        mcake = findViewById(R.id.cake);
        mcandy = findViewById(R.id.candy);
        mcupcake = findViewById(R.id.cupcake);
        mcroissant = findViewById(R.id.croissant);

        mcakeimg = findViewById(R.id.cakeimg);
        mcandyimg = findViewById(R.id.candyimg);
        mcupcakeimg = findViewById(R.id.cupcakeimg);
        mcroissantimg = findViewById(R.id.croissantimg);

        mcaketv = findViewById(R.id.caketv);
        mcandytv = findViewById(R.id.candytv);
        mcupcaketv = findViewById(R.id.cupcaketv);
        mcroissanttv = findViewById(R.id.croissanttv);


        LinearLayoutManager horizontalLayoutItManagerIT1 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mrcvFavouItem = findViewById(R.id.rcvFavouItem);
        mrcvFavouItem.setLayoutManager(horizontalLayoutItManagerIT1);
        LinearLayoutManager horizontalLayoutItManagerIT2 = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        mrcvRcmItem = findViewById(R.id.rcvRcmItem);
        mrcvRcmItem.setLayoutManager(horizontalLayoutItManagerIT2);

        //Vừa vào

        loadItemHCakeData();


        mcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm nếu mặc định for u đã được chọn hay chưa
                if(selectedMenu != 1){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcandytv.setVisibility(View.GONE);
                    mcupcaketv.setVisibility(View.GONE);
                    mcroissanttv.setVisibility(View.GONE);

                    mcandyimg.setImageResource(R.drawable.candy);
                    mcupcakeimg.setImageResource(R.drawable.cupcake);
                    mcroissantimg.setImageResource(R.drawable.croissant);

                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));

                    //Chọn menu cake
                    mcaketv.setVisibility(View.VISIBLE);
                    mcakeimg.setImageResource(R.drawable.cakeselected);
                    mcake.setBackgroundResource(R.drawable.roundcake);

                    //Tạo hiệu ứng cho cake
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcake.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 1;

                    //Load sản phẩm Cake
                    loadItemHCakeData();
                }
            }
        });
        mcandy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMenu != 2){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcaketv.setVisibility(View.GONE);
                    mcupcaketv.setVisibility(View.GONE);
                    mcroissanttv.setVisibility(View.GONE);

                    mcakeimg.setImageResource(R.drawable.cake);
                    mcupcakeimg.setImageResource(R.drawable.cupcake);
                    mcroissantimg.setImageResource(R.drawable.croissant);

//                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);


                    //Chọn menu candy
                    mcandytv.setVisibility(View.VISIBLE);
                    mcandyimg.setImageResource(R.drawable.candyselected);
                    mcandy.setBackgroundResource(R.drawable.roundcandy);

                    //Tạo hiệu ứng cho candy
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcandy.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 2;

                    //Load sản phẩm Candy
                    loadItemHCandyData();
                }
            }
        });
        mcupcake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMenu != 3){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcaketv.setVisibility(View.GONE);
                    mcandytv.setVisibility(View.GONE);
                    mcroissanttv.setVisibility(View.GONE);

                    mcakeimg.setImageResource(R.drawable.cake);
                    mcandyimg.setImageResource(R.drawable.candy);
                    mcroissantimg.setImageResource(R.drawable.croissant);

//                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcroissant.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
                    mcroissant.setBackgroundResource(R.drawable.roundnotselect);
                    //Chọn menu candy
                    mcupcaketv.setVisibility(View.VISIBLE);
                    mcupcakeimg.setImageResource(R.drawable.cupcakeselected);
                    mcupcake.setBackgroundResource(R.drawable.roundcupcake);

                    //Tạo hiệu ứng cho candy
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcupcake.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 3;

                    //Load sản phẩm cupcake
                    loadItemHCupCData();
                }
            }
        });

        //load data recommend
        LoadDataRecommend();
        productAdapter = new ProductAdapter(lstProduct,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        rvListC.setAdapter(productAdapter);
        rvListC.setLayoutManager(linearLayoutManager);

        //load data favourite
        LoadDataFavourite();
        productFavouriteAdapter = new ProductFavouriteAdapter(lstProduct, this);
        rvListFavouriteC.setAdapter(productFavouriteAdapter);
        //rvListFavouriteC.setLayoutManager(linearLayoutManager);
        //

        mcroissant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedMenu != 4){

                    //Bỏ chọn các menu còn lại ngoài trừ for u (làm thay đổi dạng)
                    mcaketv.setVisibility(View.GONE);
                    mcandytv.setVisibility(View.GONE);
                    mcupcaketv.setVisibility(View.GONE);

                    mcakeimg.setImageResource(R.drawable.cake);
                    mcandyimg.setImageResource(R.drawable.candy);
                    mcupcakeimg.setImageResource(R.drawable.cupcake);

//                    mcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcandy.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
//                    mcupcake.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.bgroundnotselect));
                    mcake.setBackgroundResource(R.drawable.roundnotselect);
                    mcandy.setBackgroundResource(R.drawable.roundnotselect);
                    mcupcake.setBackgroundResource(R.drawable.roundnotselect);
                    //Chọn menu candy
                    mcroissanttv.setVisibility(View.VISIBLE);
                    mcroissantimg.setImageResource(R.drawable.croissantselected);
                    mcroissant.setBackgroundResource(R.drawable.roundcroissant);

                    //Tạo hiệu ứng cho candy
                    ScaleAnimation scaleAnimation = new ScaleAnimation(0.8f, 1.0f, 1f, 1f,
                            Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
                    scaleAnimation.setDuration(200);
                    scaleAnimation.setFillAfter(true);
                    mcroissant.startAnimation(scaleAnimation);

                    //Đặt lại biến = 1 khi chọn (ý là khi thằng khác (giả dụ biến = 2 candy) chọn mình thì khúc cuối
                    //sau khi thực hiện cho candy xong thì biến sẽ thành 1, lúc đó ko thể chọn lại candy
                    selectedMenu = 4;

                    //Load sản phẩm croissant
                    loadItemHCroissData();
                }
            }
        });

        //MeoBottomNavigation
        btnav = findViewById(R.id.btnav);
        btnav.show(1,true );
        btnav.add(new MeowBottomNavigation.Model(1, R.drawable.btnavhome));
        btnav.add(new MeowBottomNavigation.Model(2, R.drawable.btnavgrid));
        btnav.add(new MeowBottomNavigation.Model(3, R.drawable.btnavheart));
        btnav.add(new MeowBottomNavigation.Model(4, R.drawable.btnavuserinf));

        btnav.getId();
        btnav.setOnClickMenuListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                switch (model.getId()){
                    case 1:
//                        Intent homeintent = new Intent(MainActivity.this,);
//                        startActivity(homeintent);
                        break;
                    case 2:
                        Intent categoryintent = new Intent(HomeActivity.this,ProductActivity.class);
                        startActivity(categoryintent);
                        break;
                    case 3:
//                        Intent favouritelistintent = new Intent(MainActivity.this, );
//                        startActivity(favouritelistintent);
                        break;
                    case 4:
                        mdrawLo.openDrawer(GravityCompat.START);
                        break;
                }
                return null;
            }
        });
        btnav.setOnShowListener(new Function1<MeowBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(MeowBottomNavigation.Model model) {

                return null;
            }
        });


        mtvViewAll1 = findViewById(R.id.tvViewAll1);
        mtvViewAll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductActivity.class);
                startActivity(i);
            }
        });
        mtvViewAll2 = findViewById(R.id.tvViewAll2);
        mtvViewAll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this,ProductActivity.class);
                startActivity(i);
            }
        });

        //Demo


//        itfv.add(new ItemFavou(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itfv.add(new ItemFavou(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itfv.add(new ItemFavou(R.drawable.pictdemo,"Hoa cúc","30.000đ"));






    }


    //code load dữ liệu item recommend
    void LoadDataRecommend() {
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
                            productAdapter.add(product);
                        }
                    }
                });
    }

    //code load dữ liệu item favourite
    void LoadDataFavourite() {
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
                            productFavouriteAdapter.add(product);
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

    //

    void loadItemHCakeData(){
        itcakefv = new ArrayList<>();
        firestoreHome = FirebaseFirestore.getInstance();
        CollectionReference itHCakeFVreference = firestoreHome.collection("HomeItems")
                .document("M6WkKNU1hDOSdzc12iO8").collection("CakeFV");
        itHCakeFVreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemFavou itemFavou = doc.toObject(ItemFavou.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcakefv);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);


//        itcakercm = new ArrayList<>();
//        itcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        mItemRcmAdapter = new ItemRcmAdapter(itcakercm);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
        itcakercm = new ArrayList<>();
        CollectionReference itHCakeRCMreference = firestoreHome.collection("HomeItems")
                .document("M6WkKNU1hDOSdzc12iO8").collection("CakeRCM");
        itHCakeRCMreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemRcm itemRcm = doc.toObject(ItemRcm.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcakercm);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    protected void loadItemHCandyData(){
        itcandyfv = new ArrayList<>();
        firestoreHome = FirebaseFirestore.getInstance();
        CollectionReference itHCandyFVreference = firestoreHome.collection("HomeItems")
                .document("yqIZEyVjLilrTDN0sBKh").collection("CandyFV");
        itHCandyFVreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemFavou itemFavou = doc.toObject(ItemFavou.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcandyfv);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);

        itcandyrcm = new ArrayList<>();
        CollectionReference itHCandyRCMreference = firestoreHome.collection("HomeItems")
                .document("yqIZEyVjLilrTDN0sBKh").collection("CandyRCM");
        itHCandyRCMreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemRcm itemRcm = doc.toObject(ItemRcm.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcandyrcm);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//        itcandyrcm = new ArrayList<>();
//        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcandyrcm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        mItemRcmAdapter = new ItemRcmAdapter(itcandyrcm);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    protected void loadItemHCupCData(){
        itcupcakefv = new ArrayList<>();
        firestoreHome = FirebaseFirestore.getInstance();
        CollectionReference itHCupCFVreference = firestoreHome.collection("HomeItems")
                .document("yqIZEyVjLilrTDN0sBKh").collection("CupcakeFV");
        itHCupCFVreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemFavou itemFavou = doc.toObject(ItemFavou.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcandyfv);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);

        itcupcakercm = new ArrayList<>();
        CollectionReference itHCupCRCMreference = firestoreHome.collection("HomeItems")
                .document("yqIZEyVjLilrTDN0sBKh").collection("CupcakeRCM");
        itHCupCRCMreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemRcm itemRcm = doc.toObject(ItemRcm.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcupcakercm);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
//        itcupcakercm = new ArrayList<>();
//        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo2,"Hoa hồng","40.000đ"));
//        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        itcupcakercm.add(new ItemRcm(R.drawable.pictdemo,"Hoa cúc","30.000đ"));
//        mItemRcmAdapter = new ItemRcmAdapter(itcupcakercm);
//        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }
    protected void loadItemHCroissData(){
        itcroissfv = new ArrayList<>();
        firestoreHome = FirebaseFirestore.getInstance();
        CollectionReference itHCroissFVreference = firestoreHome.collection("HomeItems")
                .document("yqIZEyVjLilrTDN0sBKh").collection("CroissantFV");
        itHCroissFVreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemFavou itemFavou = doc.toObject(ItemFavou.class);
                    mItemFavouAdapter.add(itemFavou);
                }
            }
        });
        mItemFavouAdapter = new ItemFavouAdapter(itcroissfv);
        mrcvFavouItem.setAdapter(mItemFavouAdapter);

        itcroissrcm = new ArrayList<>();
        CollectionReference itHCCroissRCMreference = firestoreHome.collection("HomeItems")
                .document("yqIZEyVjLilrTDN0sBKh").collection("CroissantRCM");
        itHCCroissRCMreference.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                for(DocumentSnapshot doc:list)
                {
                    ItemRcm itemRcm = doc.toObject(ItemRcm.class);
                    mItemRcmAdapter.add(itemRcm);
                }
            }
        });
        mItemRcmAdapter = new ItemRcmAdapter(itcroissrcm);
        mrcvRcmItem.setAdapter(mItemRcmAdapter);
    }

    //Đến trang giới thiệu
    public void directToAboutUs(){
        Intent toAboutUs = new Intent(HomeActivity.this, vechungtoiactivity.class);
        startActivity(toAboutUs);
    }
}