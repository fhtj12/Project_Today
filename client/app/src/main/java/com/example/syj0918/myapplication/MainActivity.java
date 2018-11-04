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
import android.widget.TextView;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {
    EditText id, password;
    Button btnLogin, btnCreateUser;
    String res_str, user_id, userName, uid, last_login;

    //소켓관련변수들
    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    Socket socket;

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

        handler = new Handler(){
            @Override
            public void handleMessage(Message hdmsg) {
                if(hdmsg.what == 1111){
                    progressDialog = new ProgressDialog(MainActivity.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("로그인 중...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //send = new SendThread(socket, "END");
                            //send.start(); // 소켓 종료시키는 문장 보내기
                            Intent intent = new Intent(getApplicationContext(), LoginOK.class);
                            intent.putExtra("Name", uid);
                            startActivity(intent);
                            progressDialog.dismiss();
                        }
                    }, 200);
                } else if(hdmsg.what == 2222){
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

                //SendThread 시작
                //String userdata = "login" + "/" + inputid + "/" + inputpassword;

                if (inputid.equals("")) {
                    Toast.makeText(MainActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputpassword.equals("")) {
                    Toast.makeText(MainActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 연결
                //client = new SocketClient(ServerInfo.ip, ServerInfo.port, userdata); //라즈베리파이 ip
                //client.start();
                //user_id = id.getText().toString();
                server_communication sc = new server_communication(inputid, inputpassword);
                sc.start();
                //시작후 edittext 초기화
                id.setText("");
                password.setText("");
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
        }

        @Override
        public void run() {

            try {
                // 연결후 바로 ReceiveThread 시작
                socket = new Socket(ip, port);
                receive = new ReceiveThread(socket);
                receive.start();
                send = new SendThread(socket, msg);
                send.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //서버로부터 값을 받는 스레드
    class ReceiveThread extends Thread {
        private Socket sock = null;
        BufferedReader input;

        public ReceiveThread(Socket socket) {
            this.sock = socket;
            try {
                input = new BufferedReader(new InputStreamReader(sock.getInputStream()));
            } catch (Exception e) {
            }
        }

        public void run() {
            try {
                while (input != null) {
                    String msg = input.readLine();
                    String stok[] = msg.split("/");
                    if (stok[0].equals("USER_NAME")) {
                        userName = stok[1];
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = 1111;
                        handler.sendMessage(hdmsg);
                        break;
                    } else if(stok[0].equals("LOGIN_FAILED")){
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = 2222;
                        handler.sendMessage(hdmsg);
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //서버로 보내는 스레드
    class SendThread extends Thread {
        Socket socket;
        String msg;
        PrintWriter output;

        public SendThread(Socket socket, String msg) {
            this.socket = socket;
            this.msg = msg;
            try {
                output = new PrintWriter(socket.getOutputStream(),true);
            } catch (Exception e) {
            }
        }

        public void run() {
            try {
                // 메세지 전송부
                if (output != null) {
                    if (msg != null) {
                        output.println(msg);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
