package com.example.tirico.today;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by Tirico on 2018-06-06.
 */

public class Account_Impl implements Account_Interface {
    @Override
    public String sign_in(String id, String pwd) {
        ServerConnection sc = new ServerConnection();
        JSONObject obj = new JSONObject();
        String result = "OK";
        try {
            obj.put("id", id);
            obj.put("pwd", pwd);
        } catch (org.json.JSONException json) {
            json.printStackTrace();
        }
        try {
            JSONObject response = sc.uploadToServer(obj);
            result = response.getString("uid");
        } catch(IOException io) {
            io.printStackTrace();
            result = "error";

        } catch (JSONException json) {
            json.printStackTrace();
            result = "error";
        }
        return result;
    }

    @Override
    public String sign_out(String uid) {
        return null;
    }

    @Override
    public String update_account(Account account) {
        return null;
    }

    @Override
    public String delete_account(String id, String pwd) {
        return null;
    }

    @Override
    public String sign_up(Account account) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", account.getId());
            obj.put("pwd", account.getPwd());
            obj.put("nickname", account.getNickname());
            obj.put("email", account.getEmail());
            obj.put("address", account.getAddress());
            obj.put("birth", account.getBirth());
        } catch (org.json.JSONException json) {
            json.printStackTrace();
        }
        String result = "OK";
        ServerConnection sc = new ServerConnection();
        try {
            JSONObject response = sc.uploadToServer(obj);
            result = response.getString("uid");
        } catch(IOException io) {
            io.printStackTrace();
            result = "error";

        } catch (JSONException json) {
            json.printStackTrace();
            result = "error";
        }
        return result;
    }
}
