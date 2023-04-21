package com.example.cherrycake;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MaincuaYenActivity extends AppCompatActivity {
    private TextView layoutdk;
    private EditText medtmail, medtpass;
    private Button mbtndangnhap;
    private FirebaseAuth auth;
    private ImageButton mmenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maincuayen);
        layoutdk = findViewById(R.id.tvdk);
        mmenu = findViewById(R.id.menu);

        getSupportActionBar().hide();
        dangkiListener();
        dangnhaplistener();
        menu();
//        onStart();

    }

    // này là kiểu tài khoản đã đăng nhập rồi thì vô app không cần đăng nhập lại nữa á
    // nhưng mà chưa có được nên từ từ
//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(MainActivity.this, otpactivity.class);
//            startActivity(intent);
//        }
//    }

    private void dangkiListener() {
        layoutdk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaincuaYenActivity.this, nhapgmailactivity.class);
                startActivity(intent);
            }
        });
    }
    private void menu() {
        mmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MaincuaYenActivity.this, vechungtoiactivity.class);
                startActivity(intent);
            }
        });
    }
    private void dangnhaplistener(){
        medtmail = findViewById(R.id.edtemaillogin);
        medtpass = findViewById(R.id.edtpass);
        mbtndangnhap = findViewById(R.id.btndangnhap);
        mbtndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                String stremail =  medtmail.getText().toString();
                String strpass = medtpass.getText().toString();
                if(TextUtils.isEmpty(stremail)){
                    Toast.makeText(MaincuaYenActivity.this,"Vui lòng nhập gmail",Toast.LENGTH_SHORT).show();
                    medtmail.requestFocus();
                } else if (TextUtils.isEmpty(strpass)) {
                    Toast.makeText(MaincuaYenActivity.this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
                    medtpass.requestFocus();
                } else {
                    auth.signInWithEmailAndPassword(stremail, strpass)
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(MaincuaYenActivity.this, HomeActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(MaincuaYenActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });
                }
            }
        });
    }


}