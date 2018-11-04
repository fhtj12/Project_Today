package com.example.tirico.proejct_today;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String uid;
    public static String last_login;
    public static Handler handler;
    public static Message hdmsg;


    server_communication sc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView tv_id, tv_pwd;
        tv_id = (TextView) findViewById(R.id.input_id);
        tv_pwd = (TextView) findViewById(R.id.input_pwd);

        Button btn_login, btn_create, btn_find;
        btn_login = (Button) findViewById(R.id.btnLogin);
        btn_create = (Button) findViewById(R.id.createUser);
        btn_find = (Button) findViewById(R.id.find_id);

        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Account_Create_Activity.class);
                startActivity(intent);
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = tv_id.getText().toString();
                String pwd = tv_pwd.getText().toString();
                if(id.equals("")) {
                    Toast.makeText(MainActivity.this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                } else if(pwd.equals("")) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                sc = new server_communication(id, pwd);
                sc.start();
            }
        });

        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find_Id_Activity.class);
                startActivity(intent);
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                Message_Code code = new Message_Code();
                if(hdmsg.what == code.Login_Ok) {
                    uid = sc.getUID();
                    last_login = sc.getLast_login();
                    Intent intent = new Intent(getApplicationContext(), AppActivity.class);
                    intent.putExtra("uid", uid);
                    intent.putExtra("last_login", last_login);
                    startActivity(intent);
                } else if(hdmsg.what == code.Login_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("아이디가 존재하지 않거나 틀린 비밀번호입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Server_Connection_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("서버 연결 실패입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Create_URL_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("URL 생성 에러입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        };
    }
}
