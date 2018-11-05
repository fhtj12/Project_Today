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

public class Find_Pwd_Activity extends AppCompatActivity {
    public static Handler handler;
    public static Message hdmsg;
    Message_Code code = new Message_Code();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pwd);

        final EditText et_id = (EditText) findViewById(R.id.find_pwd_id);
        final EditText et_email = (EditText) findViewById(R.id.find_pwd_email);
        final EditText et_birth = (EditText) findViewById(R.id.find_pwd_birth);

        Button btn_update = (Button) findViewById(R.id.btnFindPwd);
        Button btn_cancel = (Button) findViewById(R.id.btnCancel);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = et_id.getText().toString();
                String email = et_email.getText().toString();
                String birth = et_birth.getText().toString();
                if(id.length() > 0 && email.length() > 0) {
                    if(birth.contains("-")) {
                        server_communication sc = new server_communication(id, email, 2);
                        sc.start();
                    } else {
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = code.Format_Error;
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
                if(hdmsg.what == code.find_pwd_Ok) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Pwd_Activity.this);
                    builder.setMessage("정보가 확인되었습니다. 비밀번호를 변경해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                    Intent intent = new Intent(getApplicationContext(), Update_Pwd_Activity.class);
                    String id = et_id.getText().toString();
                    intent.putExtra("id", id);
                    startActivity(intent);
                } else if(hdmsg.what == code.find_pwd_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Pwd_Activity.this);
                    builder.setMessage("사용자 정보가 일치하지 않습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Server_Connection_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Pwd_Activity.this);
                    builder.setMessage("서버와의 연결이 끊어졌습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Format_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Pwd_Activity.this);
                    builder.setMessage("생년월일을 형식에 맞게 입력해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.update_pwd_empty_info_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Pwd_Activity.this);
                    builder.setMessage("입력하지 않은 항목이 있습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Find_Pwd_Activity.this);
                    builder.setMessage("확인되지 않은 에러가 발생했습니다. 다시 시도해주세요.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        };
    }
}
