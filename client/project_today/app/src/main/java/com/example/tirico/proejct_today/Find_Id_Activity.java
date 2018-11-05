package com.example.tirico.proejct_today;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Find_Id_Activity extends AppCompatActivity {
    static public Handler handler;
    static public Message hdmsg;
    static public Message_Code code = new Message_Code();
    server_communication sc;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        final TextView tv_email = (TextView) findViewById(R.id.find_id_email);
        final TextView tv_birth = (TextView) findViewById(R.id.find_id_birth);

        Button btn_find_id = (Button) findViewById(R.id.btnFindId);
        Button btn_update_pwd = (Button) findViewById(R.id.btnUpdatePwd);
        Button btn_cancel = (Button) findViewById(R.id.btnCancel);

        btn_find_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = tv_email.getText().toString();
                String birth = tv_birth.getText().toString();
                if(birth.contains("-")) {
                    sc = new server_communication(email, birth);
                    sc.start();
                } else {
                    hdmsg = handler.obtainMessage();
                    hdmsg.what = code.Format_Error;
                    handler.sendMessage(hdmsg);
                }
            }
        });

        btn_update_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Find_Pwd_Activity.class);
                startActivity(intent);
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(hdmsg.what == code.Format_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Id_Activity.this);
                    builder.setMessage("생년월일을 형식에 맞게 입력해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.find_id_Ok) {
                    String id = sc.getID();
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Id_Activity.this);
                    builder.setMessage("아이디 : " + id + " 입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Server_Connection_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Id_Activity.this);
                    builder.setMessage("등록되지 않은 정보입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        };
    }
}
