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
import android.widget.Toast;

public class Account_Create_Activity extends AppCompatActivity {
    boolean check = false;
    static public Handler handler;
    static public Message hdmsg;
    static public Message_Code code = new Message_Code();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_create);

        final TextView tv_id = (TextView) findViewById(R.id.new_id);
        final TextView tv_pwd = (TextView) findViewById(R.id.new_pwd);
        final TextView tv_email = (TextView) findViewById(R.id.email);
        final TextView tv_address = (TextView) findViewById(R.id.address);
        final TextView tv_birth = (TextView) findViewById(R.id.birth);

        Button btn_check = (Button) findViewById(R.id.btnCheck);
        Button btn_next = (Button) findViewById(R.id.btnNext);
        Button btn_cancel = (Button) findViewById(R.id.btnCancel);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id_tmp = tv_id.getText().toString();
                server_communication sc = new server_communication(id_tmp, 1);
                sc.start();
            }
        });
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(check) {
                    String id = tv_id.getText().toString();
                    String pwd = tv_pwd.getText().toString();
                    String address = tv_address.getText().toString();
                    String email = tv_email.getText().toString();
                    String birth = tv_birth.getText().toString();
                    if(birth.contains("-")) {

                    } else {
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = code.Format_Error;
                        handler.sendMessage(hdmsg);
                    }
                    server_communication sc = new server_communication(id, pwd, address, email, birth);
                    sc.start();
                } else {
                    Toast.makeText(getApplicationContext(), "아이디 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        handler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                if(hdmsg.what == code.Create_Duplicate) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Create_Activity.this);
                    builder.setMessage("중복된 아이디입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                    check = false;
                } else if(hdmsg.what == code.Create_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Create_Activity.this);
                    builder.setMessage("가입할 수 없는 정보입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                    check = false;
                } else if(hdmsg.what == code.Server_Connection_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Create_Activity.this);
                    builder.setMessage("서버와 연결이 끊어졌습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                    check = false;
                } else if(hdmsg.what == code.Create_Ok) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Create_Activity.this);
                    builder.setMessage("가입이 완료되었습니다. 이제 권한 설정을 해주십시오.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.find_id_Ok) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Account_Create_Activity.this);
                    builder.setMessage("사용할 수 있는 아이디입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                    check = true;
                }
            }
        };
    }
}
