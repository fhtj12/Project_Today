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
import android.widget.EditText;

public class Update_Pwd_Activity extends AppCompatActivity {
    public static Handler handler;
    public static Message hdmsg;
    Message_Code code = new Message_Code();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_pwd);

        Intent intent = new Intent(getApplicationContext(), Find_Pwd_Activity.class);
        final String id = intent.getStringExtra("id");

        final EditText et_pwd = (EditText) findViewById(R.id.update_pwd_new_pwd);
        final EditText et_new_pwd = (EditText) findViewById(R.id.update_pwd_confirm_pwd);

        Button btn_confirm = (Button) findViewById(R.id.btnUpdatePwd);
        Button btn_cancel = (Button) findViewById(R.id.btnCancel);

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pwd = et_pwd.getText().toString();
                String pwd_confirm = et_new_pwd.getText().toString();
                if(pwd.length() > 0 && pwd_confirm.length() > 0) {
                    if(pwd.equals(pwd_confirm)) {
                        // 비밀번호 변경 DB 처리
                        server_communication sc = new server_communication(id, pwd, 3);
                        sc.start();
                    } else {
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = code.update_pwd_incorrect_Error;
                        handler.sendMessage(hdmsg);
                    }
                } else {
                    hdmsg = handler.obtainMessage();
                    hdmsg.what = code.update_pwd_empty_info_Error;
                    handler.sendMessage(hdmsg);
                }
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
            public void handleMessage(Message hdmsg) {
                if(hdmsg.what == code.update_pwd_Ok) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_Pwd_Activity.this);
                    builder.setMessage("비밀번호가 변경되었습니다. 다시 로그인 해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                } else if(hdmsg.what == code.update_pwd_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_Pwd_Activity.this);
                    builder.setMessage("비밀번호를 변경하던 도중 문제가 발생했습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.update_pwd_incorrect_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_Pwd_Activity.this);
                    builder.setMessage("비밀번호가 서로 다릅니다. 다시 확인해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Server_Connection_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_Pwd_Activity.this);
                    builder.setMessage("서버와의 연결이 끊어졌습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.update_pwd_empty_info_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_Pwd_Activity.this);
                    builder.setMessage("입력하지 않은 항목이 있습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else{
                    // undefined error
                    AlertDialog.Builder builder = new AlertDialog.Builder(Update_Pwd_Activity.this);
                    builder.setMessage("확인되지 않은 에러가 발생했습니다. 다시 시도해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        };
    }
}
