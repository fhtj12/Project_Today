package com.example.syj0918.myapplication;

import org.json.JSONArray;
import org.json.JSONObject;

public class json_parsing {

    // 로그인시 JSON 파일 파싱
    public String json_parsing_login (String res) throws Exception {
        JSONArray jsonArray = new JSONArray(res);
        JSONObject jsonObject = null;
        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
        }
        String ret = jsonObject.getString("ret");
        String uid = jsonObject.getString("uid");
        String last_login = jsonObject.getString("last_login");

        return ret + "/" + uid + "/" + last_login;
    }

    // 일반 확인 응답시 JSON 파일 파싱
    public String json_parsing_confirm (String res) throws Exception {
        JSONArray jsonArray = new JSONArray(res);
        JSONObject jsonObject = null;
        for(int i = 0; i < jsonArray.length(); i++) {
            jsonObject = jsonArray.getJSONObject(i);
        }
        String ret = jsonObject.getString("ret");

        return ret;
    }
}
