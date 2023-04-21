package com.example.cherrycake;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class nhapgmailactivity extends AppCompatActivity {
    private EditText edtmail, edtmk;
    private Button btntt;
    private FirebaseAuth auth;
    private TextView layoutdn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhapgmailactivity);

        edtmail = findViewById(R.id.edtemail);
        edtmk = findViewById(R.id.edtmkdangki);
        btntt = findViewById(R.id.bnttieptheo);
        layoutdn = findViewById(R.id.tvdntronggmail);
        dangkiListener();



//        nhapmaillistener();

        btntt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                String stremail =  edtmail.getText().toString();
                String strpass = edtmk.getText().toString();
                if(TextUtils.isEmpty(stremail)){
                    Toast.makeText(nhapgmailactivity.this,"Vui lòng nhập gmail",Toast.LENGTH_SHORT).show();
                    edtmail.requestFocus();
                } else if (TextUtils.isEmpty(strpass)) {
                    Toast.makeText(nhapgmailactivity.this,"Vui lòng nhập mật khẩu",Toast.LENGTH_SHORT).show();
                    edtmk.requestFocus();
                } else {
                    auth.createUserWithEmailAndPassword(stremail, strpass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(nhapgmailactivity.this, HomeActivity.class);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(nhapgmailactivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

//                auth.createUserWithEmailAndPassword(stremail,strpass).addOnCompleteListener(this,)

                }
            }
        });
    }
    private void dangkiListener() {
        layoutdn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(nhapgmailactivity.this, MaincuaYenActivity.class);
                startActivity(intent);
            }
        });
    }
//    private void nhapmaillistener() {
//        btntt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onClickTiepTheo();
//
//            }
//        });
//    }
//
//
//    private void onClickTiepTheo() {
//
//        FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.createUserWithEmailAndPassword(stremail,strpass)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            Intent intent = new Intent(nhapgmailactivity.this,otpactivity.class);
//                            startActivity(intent);
//                            finishAffinity();
//                        }
//                        else {
//                            Toast.makeText(nhapgmailactivity.this, "Gmail không hợp lệ", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }) ;
//
//
//    }
}