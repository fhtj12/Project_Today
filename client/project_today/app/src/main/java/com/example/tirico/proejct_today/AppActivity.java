package com.example.tirico.proejct_today;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AppActivity extends AppCompatActivity {
    TextView tv_user;
    Button btnLogout;
    Button btnSearch;
    static public Message_Code code = new Message_Code();

    //아답터관련 변수들
    private List<String> list;          // 데이터를 넣은 리스트변수
    private ListView listView;          // 검색을 보여줄 리스트변수
    private EditText editSearch;        // 검색어를 입력할 Input 창
    private SearchAdapter adapter;      // 리스트뷰에 연결할 아답터
    private ArrayList<String> arraylist;

    public static Handler handler;
    public static Message hdmsg;

    ProgressDialog progressDialog;

    static String id;
    static String last_login;

    server_communication sc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        last_login = intent.getStringExtra("last_login");

        tv_user = (TextView) findViewById(R.id.tv_user);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnSearch = (Button) findViewById(R.id.btn_search);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search = editSearch.getText().toString();
                sc = new server_communication(search, 2);
                sc.start();
            }
        });

        handler = new Handler() {
            @Override
            public void handleMessage(Message hdmsg) {
                if(hdmsg.what == code.search_Ok) {
                    Intent intent2 = new Intent(getApplicationContext(), SearchActivity.class);
                    String result = sc.getSearchResult();
                    intent2.putExtra("result", result);
                    startActivity(intent2);
                } else if(hdmsg.what == code.search_failed) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AppActivity.this);
                    builder.setMessage("알 수 없는 오류가 발생했습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                } else if(hdmsg.what == code.Server_Connection_Error) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(AppActivity.this);
                    builder.setMessage("서버와 연결이 끊어졌습니다.");
                    builder.setPositiveButton("확인", null);
                    builder.setCancelable(false);
                    builder.show();
                }
            }
        };

//        //검색관련 시작
//        editSearch = (EditText) findViewById(R.id.editSearch);
//        listView = (ListView) findViewById(R.id.listView);
//        listView.setVisibility(View.INVISIBLE); //리스트뷰 숨기기
//
//        tv_user.setText(id);
//
//        // 리스트를 생성한다.
//        list = new ArrayList<String>();
//
//        // 리스트의 모든 데이터를 arraylist에 복사한다.// list 복사본을 만든다.
//        arraylist = new ArrayList<String>();
//        arraylist.addAll(list);
//
//        // 리스트에 연동될 아답터를 생성한다.
//        adapter = new SearchAdapter(list, this);
//
//        // 리스트뷰에 아답터를 연결한다.
//        listView.setAdapter(adapter);
//
//        // input창에 검색어를 입력시 "addTextChangedListener" 이벤트 리스너를 정의한다.
//        editSearch.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                //String text =
//            }
//        });


    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }
}
