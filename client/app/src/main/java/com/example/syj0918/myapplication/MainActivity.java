package com.example.syj0918.myapplication;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText id, password;
    Button btnLogin, btnCreateUser;
    String user_id, userName;

    Handler handler;
    Message hdmsg;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id = (EditText) findViewById(R.id.inputId);
        password = (EditText) findViewById(R.id.inputPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCreateUser = (Button) findViewById(R.id.createUser);

        id.setText("aaa");
        password.setText("1111");

        handler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                if (hdmsg.what == 1111) {
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("로그인 중...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), LoginOK.class);
                            intent.putExtra("Name", userName);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                    }, 200);
                } else if (hdmsg.what == 2222) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("존재하지 않은 아이디입니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        };
        //로그인 버튼 클릭 이벤트
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String inputid = id.getText().toString();
                String inputpassword = password.getText().toString();

                if (inputid.equals("")) {
                    Toast.makeText(MainActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputpassword.equals("")) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                user_id = id.getText().toString();
                //시작후 edittext 초기화
                id.setText("");
                password.setText("");

                String ret = login(inputid, inputpassword);
                if(ret.contains("ok")) {
                    Toast.makeText(MainActivity.this, ret, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), LoginOK.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    Toast.makeText(MainActivity.this, ret, Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

        // 회원가입 버튼 클릭 이벤트
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public static String login(String id, String pwd) {
        String ret = null;
        String url = "fhtj12.iptime.org:9503/" + "login?" + "id=" + id + "&pwd=" + pwd;
        json_parsing jp = new json_parsing();

        try {
            URL conn = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) conn.openConnection();

            httpURLConnection.setRequestMethod("Post");
            httpURLConnection.setRequestProperty("Accept-Charset", "UTF-8");
            httpURLConnection.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

            InputStream in = new BufferedInputStream(httpURLConnection.getInputStream());
            String str = in.toString();
            ret = jp.json_parsing_login(str);
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return e.toString();
        }

        return ret;
    }

}
