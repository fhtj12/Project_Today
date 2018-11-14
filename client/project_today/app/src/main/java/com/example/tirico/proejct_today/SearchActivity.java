package com.example.tirico.proejct_today;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    String search_result;
    Button btnBack;
    ListView lvSearchResult;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Intent intent = getIntent();
        //search_result = intent.getStringExtra("result");

        lvSearchResult = (ListView) findViewById(R.id._taglistview);
        btnBack = (Button) findViewById(R.id.btnBack);

        ArrayList<String> list = new ArrayList<String>();
        list.add("점포명 : 소천지\n" + "주소 : 서울특별시 구로구 경인로43길 21 소천지(한식당)\n" + "전화번호 : 02-2688-5500");
        list.add("점포명 : 뒤뜰\n" + "주소 : 서울특별시 구로구 안양천로539길 6\n" + "전화번호 : 02-2613-1973");
        list.add("점포명 : 데일리스위츠\n" + "주소 : 서울특별시 구로구 중앙로 9 설송빌딩\n" + "전화번호 : 02-877-6126");
        list.add("점포명 : 돈까스클럽 고척점\n" + "주소 : 서울특별시 구로구 경인로43길 21\n" + "전화번호 : 02-2615-7100");
        list.add("점포명 : 개봉육고기\n" + "주소 : 서울특별시 구로구 경인로35길 131-25 \n" + "전화번호 : 02-2613-7115");
        list.add("점포명 : 실크로드\n" + "주소 : 서울특별시 구로구 경인로 398 \n" + "전화번호 : 02-2066-6100");
        list.add("점포명 : 송림가\n" + "주소 : 서울특별시 구로구 경인로 398 송림가\n" + "전화번호 : 02-2066-6000");

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
        lvSearchResult.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppActivity.class);
                startActivity(intent);
            }
        });
    }
}
