package com.example.syj0918.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignUpActivity extends AppCompatActivity {
    EditText new_id, new_pass, name, dateOfBirth, phoneNum;
    Button btnNext, btnCancel, btnCheck;
    TextView newUserData;
    int checkFlag = 0; // 사용중이면 0, 존재하지 않으면 1 => 1일 때 다음으로 넘어가도록 함
    String dialogMsg;

    //현재 날짜 구하기
    Date date = new Date(System.currentTimeMillis());
    String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
    String stokDate[] = currentDate.split("-");
    int bYear = Integer.parseInt(stokDate[0]), bMonth = Integer.parseInt(stokDate[1]) - 1, bDay = Integer.parseInt(stokDate[2]);

    //소켓관련변수들
    SocketClient client;
    ReceiveThread receive;
    SendThread send;
    Socket socket;

    Handler handler;
    Message hdmsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up_main);


        new_id = (EditText) findViewById(R.id.new_id);
        new_pass = (EditText) findViewById(R.id.new_pass);
        name = (EditText) findViewById(R.id.name);
        dateOfBirth = (EditText) findViewById(R.id.dateOfBirth);
        phoneNum = (EditText) findViewById(R.id.phoneNum);
        btnCheck = (Button) findViewById(R.id.btnCheck);
        btnNext = (Button) findViewById(R.id.btnNext);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        newUserData = (TextView) findViewById(R.id.newUserData);

        handler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this); // 다이얼로그 생성
                builder.setTitle("중복체크"); // 제목 지정
                builder.setPositiveButton("확인", null);
                builder.setCancelable(false);
                if (hdmsg.what == 1111) { // 중복이 아닐 때
                    dialogMsg = "사용 가능한 아이디입니다.";
                    checkFlag = 1;
                    builder.setMessage(dialogMsg);
                    builder.show(); // 보여줌
                } else if (hdmsg.what == 2222) { // 중복일 떄
                    dialogMsg = "이미 사용 중인 아이디입니다.";
                    checkFlag = 0;
                    builder.setMessage(dialogMsg);
                    builder.show(); // 보여줌
                } else if (hdmsg.what == 3333) { // 회원가입 성공
                    Toast.makeText(SignUpActivity.this, "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                } else if (hdmsg.what == 4444) { // 회원가입 실패
                    Toast.makeText(SignUpActivity.this, "회원가입 실패...", Toast.LENGTH_SHORT).show();
                }
            }
        };

        //입력한 아이디 값의 변화가 있다면 다시 중복체크를 하도록 플래그를 초기화함
        new_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkFlag = 0; // 아이디 입력에 변화가 있을 때 중복체크 플래그를 초기화
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //중복체크를 눌렀을 때
        btnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (new_id.getText().toString().equals("")) {
                    Toast.makeText(SignUpActivity.this, "값을 입력하세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
                client = new SocketClient(ServerInfo.ip, ServerInfo.port, "DUPLICATION_CHECK/" + new_id.getText());
                client.start();
            }
        });

        //달력 띄우기 - 포커스가 집중되었을 때
        dateOfBirth.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                            Toast.makeText(SignUpActivity.this, year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일", Toast.LENGTH_SHORT).show();
                            dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            bYear = year;
                            bMonth = monthOfYear;
                            bDay = dayOfMonth;
                        }
                    }, bYear, bMonth, bDay);
                    dialog.show();
                }
            }
        });
        //달력 띄우기 - 다시 클릭했을 때
        dateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(SignUpActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Toast.makeText(SignUpActivity.this, year + "년" + (monthOfYear + 1) + "월" + dayOfMonth + "일", Toast.LENGTH_SHORT).show();
                        dateOfBirth.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                        bYear = year;
                        bMonth = monthOfYear;
                        bDay = dayOfMonth;
                    }
                }, bYear, bMonth, bDay);
                dialog.show();
            }
        });

        //다음 버튼을 눌렀을 때
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkFlag == 0) {
                    Toast.makeText(SignUpActivity.this, "중복체크를 해 주세요.", Toast.LENGTH_SHORT).show();
                    return; // 중복체크를 하지 않았다면 다음화면으로 넘어갈 수 없다.
                }

                String inputnewid = new_id.getText().toString();
                String inputnewpassword = new_pass.getText().toString();
                String inputname = name.getText().toString();
                String inputdateofbirth = dateOfBirth.getText().toString();
                String inputphonenum = phoneNum.getText().toString();

                String newuserdata = "CREATE_USER" + "/" + inputnewid + "/" + inputnewpassword + "/" + inputname + "/" + inputdateofbirth + "/" + inputphonenum;

                if (inputnewid.equals("")) {
                    Toast.makeText(SignUpActivity.this, "아이디를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputnewpassword.equals("")) {
                    Toast.makeText(SignUpActivity.this, "비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputname.equals("")) {
                    Toast.makeText(SignUpActivity.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputdateofbirth.equals("")) {
                    Toast.makeText(SignUpActivity.this, "생년월일을 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                } else if (inputphonenum.equals("")) {
                    Toast.makeText(SignUpActivity.this, "전화번호를 입력하세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                client = new SocketClient(ServerInfo.ip, ServerInfo.port, newuserdata);
                client.start();
            }
        });

        // 취소 버튼
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
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
                    if (msg.equals("NOT_DUPLICATION")) { //중복이 아닐 때
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = 1111;
                        handler.sendMessage(hdmsg);
                        break;
                    } else if (msg.equals("DUPLICATION")) {  // 중복일 때
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = 2222;
                        handler.sendMessage(hdmsg);
                        break;
                    } else {  // DB에 사용자 추가 성공했을 때
                        hdmsg = handler.obtainMessage();
                        hdmsg.what = 3333;
                        handler.sendMessage(hdmsg);
                        break;
                    }
//                    else if (msg.equals("INSERT_FAILED")) {  // DB에 사용자 추가 실패했을 때
//                        hdmsg = handler.obtainMessage();
//                        hdmsg.what = 4444;
//                        handler.sendMessage(hdmsg);
//                        break;
//                    }
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

