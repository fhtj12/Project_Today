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

<<<<<<< HEAD
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
=======
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
>>>>>>> 0f18550c962790e1b3bfaaeb8fb3eb87cca02a49

public class MainActivity extends AppCompatActivity {
    EditText id, password;
    Button btnLogin, btnCreateUser;
    String res_str, user_id, userName, uid, last_login;

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

        //id.setText("aaa");
        //password.setText("1111");

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
<<<<<<< HEAD
=======
                            //send = new SendThread(socket, "END");
                            //send.start(); // 소켓 종료시키는 문장 보내기
>>>>>>> 0f18550c962790e1b3bfaaeb8fb3eb87cca02a49
                            Intent intent = new Intent(getApplicationContext(), LoginOK.class);
                            intent.putExtra("Name", uid);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                    }, 200);
                } else if (hdmsg.what == 2222) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("아이디가 존재하지 않거나 틀린 비밀번호입니다.");
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

<<<<<<< HEAD
=======
                //SendThread 시작
                //String userdata = "login" + "/" + inputid + "/" + inputpassword;

>>>>>>> 0f18550c962790e1b3bfaaeb8fb3eb87cca02a49
                if (inputid.equals("")) {
                    Toast.makeText(MainActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputpassword.equals("")) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

<<<<<<< HEAD
                user_id = id.getText().toString();
=======
                // 연결
                //client = new SocketClient(ServerInfo.ip, ServerInfo.port, userdata); //라즈베리파이 ip
                //client.start();
                //user_id = id.getText().toString();
                server_communication sc = new server_communication(inputid, inputpassword);
                sc.start();
>>>>>>> 0f18550c962790e1b3bfaaeb8fb3eb87cca02a49
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

<<<<<<< HEAD
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
=======
    // 서버 통신 스레드(rest)
    class server_communication extends Thread {
        final String basic_url = "fhtj12.iptime.org:9503/";
        private String param;

        public server_communication(String id, String pwd) {
            this.param = "login?id=" + id + "&pwd=" + pwd;
        }

        // 스레드 구동
        @Override
        public void run() {
            if(param == null) {
                return;
            }
            URL url = generate_url(basic_url + param);
            String result = http_request(url);
            if(result == null) {
                hdmsg = handler.obtainMessage();
                hdmsg.what = 2222;
                handler.sendMessage(hdmsg);
            } else {
                res_str = result;
                hdmsg = handler.obtainMessage();
                hdmsg.what = 1111;
                handler.sendMessage(hdmsg);
            }
        }

        // url 생성 메소드
        private URL generate_url(String str_url) {
            URL url = null;
            try {
                url = new URL(str_url);
            } catch (MalformedURLException e) {
                // URL 작성 오류
                return null;
            }
            return url;
        }

        // http 연결 메소드
        private String http_request(URL url) {
            String ret = null;
            if(url == null) {
                return null;
            }
            HttpURLConnection conn = null;
            try {
                conn = (HttpURLConnection)url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(10000);
                conn.connect(); // 실제 커넥션 실행
                if(conn.getResponseCode() != 200) {
                    return null;
                } else {
                    // 응답받은 json 파싱
                    json_manager jm = new json_manager(conn.getInputStream());
                    ret = jm.getAllString();
                }
            } catch (IOException e) {
                return null;
            } finally {
                if(conn != null) {
                    conn.disconnect();
                }
            }
            return ret;
        }
    }

    // json 파싱
    class json_manager {
        private InputStream res;
        public json_manager(InputStream res) {
            this.res = res;
        }
        public json_manager() {}
        public String getAllString() {
            String ret = null;

            try {
                InputStreamReader inputStreamReader = new InputStreamReader(this.res, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                bufferedReader.close();
                ret = sb.toString().trim();
            } catch (IOException e) {
                return null;
            }

            return ret;
        }
        public String getFunc(String str) {
            String func = null;
            return func;
        }
        public String getUID(String str) {
            String uid = null;
            return uid;
        }
        public String getLastLogin(String str) {
            String last_login = null;
            return last_login;
        }
    }

    // 소켓생성 스레드
    class SocketClient extends Thread {
        String ip;
        int port;
        String msg;

        public SocketClient(String ip, int port, String msg) {
            this.ip = ip;
            this.port = port;
            this.msg = msg;
>>>>>>> 0f18550c962790e1b3bfaaeb8fb3eb87cca02a49
        }

        return ret;
    }

}
