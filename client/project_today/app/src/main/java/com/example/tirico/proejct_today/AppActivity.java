package com.example.tirico.proejct_today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class AppActivity extends AppCompatActivity {
    static String uid;
    static String last_login;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        TextView tv_user = (TextView) findViewById(R.id.tv_user_info);
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        last_login = intent.getStringExtra("last_login");

        tv_user.setText(uid + " 님 환영합니다.\n" + "마지막 로그인 : " + last_login);
    }
}
