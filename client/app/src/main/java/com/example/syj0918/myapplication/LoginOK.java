package com.example.syj0918.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoginOK extends AppCompatActivity {
    TextView textView;
    Button btnLogout;

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
        setContentView(R.layout.activity_login_ok);
        handler = new Handler(){
            @Override
            public void handleMessage(Message hdmsg) {
                if(hdmsg.what == 1111){
                    progressDialog = new ProgressDialog(LoginOK.this);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setMessage("로그아웃 중...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            send = new SendThread(socket, "END");
                            send.start(); // 소켓 종료시키는 문장 보내기
                            finish();
                            progressDialog.dismiss();
                        }
                    }, 200);
                }
            }
        };
        textView = (TextView) findViewById(R.id.textView);
        btnLogout = (Button) findViewById(R.id.btnLogout);

        Intent intent = getIntent();
        textView.setText(intent.getStringExtra("Name") + "님 반갑습니다!");

        // 연결
        client = new SocketClient(ServerInfo.ip, ServerInfo.port); //이클립스 서버 ip
        client.start();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send = new SendThread(socket, "LOGOUT");
                send.start();
                finish();
            }
        });
    }

    // 소켓생성 스레드
    class SocketClient extends Thread {
        String ip;
        int port;

        public SocketClient(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run() {

            try {
                // 연결후 바로 ReceiveThread 시작
                socket = new Socket(ip, port);
                receive = new ReceiveThread(socket);
                receive.start();

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
                    String stok[] = msg.split("/"); //개행문자 제거
                    if (stok[0].equals("LOGOUT_OK")) {
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = 1111;
                        handler.sendMessage(hdmsg);
                        break; // 로그아웃 하므로 스레드를 종료시킨다.
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
                output = new PrintWriter(socket.getOutputStream(), true);
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
