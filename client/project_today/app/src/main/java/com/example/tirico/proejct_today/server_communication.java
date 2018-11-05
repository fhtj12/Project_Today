package com.example.tirico.proejct_today;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class server_communication extends Thread {
    final String basic_url = "http://fhtj12.iptime.org:9503/";
    private String param;
    Message_Code code = new Message_Code();
    String res_str;
    String uid;
    String last_login;
    String result_code;
    String id;
    MainActivity ma;
    Account_Create_Activity create;
    Find_Id_Activity find_id_activity;
    Find_Pwd_Activity find_pwd_activity;
    Update_Pwd_Activity update_pwd_activity;

    public server_communication(String arg1, String arg2, int env) {
        if(env == 1) { // login
            this.param = "login?id=" + arg1 + "&pwd='" + arg2 + "'";
        } else if(env == 2) { // find_pwd
            this.param = "find_pwd?id=" + arg1 + "&email='" + arg2 + "'";
        } else if(env == 3) { // update_pwd
            this.param = "update_pwd?id=" + arg1 + "&pwd=" + arg2;
        }
    }
    public server_communication(String id) {
        this.param = "duplicate_id?id=" + id;
    }
    public server_communication(String id, String pwd, String address, String email, String birth) {
        this.param = "create_account?id=" + id + "&pwd=" + pwd + "&address=" + address + "&email=" + email + "&birth=" + birth;
    }
    public server_communication(String email, String birth) {
        if(birth.contains("-") && birth.length() > 0) {
            this.param = "find_id?email=" + email;
        } else {
            Log.e("connect_error", result_code);
            find_id_activity.hdmsg = find_id_activity.handler.obtainMessage();
            find_id_activity.hdmsg.what = code.Format_Error;
            find_id_activity.handler.sendMessage(find_id_activity.hdmsg);
        }
    }

    // 스레드 구동
    @Override
    public void run() {
        if(param == null) {
            Log.e("connect_error", "parameter_error");
            return;
        }
        URL url = generate_url(basic_url + param);

        String result = http_request(url);
        if(param.contains("login")) { // login 메세지 처리
            if(result == null) {
                ma.hdmsg = ma.handler.obtainMessage();
                ma.hdmsg.what = code.Server_Connection_Error;
                ma.handler.sendMessage(ma.hdmsg);
            } else {
                this.res_str = result;
                json_manager jm = new json_manager();
                JSONObject json = jm.parseJson(res_str);
                String ret_code = jm.getDataFromJson(json, "ret");
                if(ret_code.contains("ok")) {
                    this.uid = jm.getDataFromJson(json, "uid");
                    this.last_login = jm.getDataFromJson(json, "last_login");
                    ma.hdmsg = ma.handler.obtainMessage();
                    ma.hdmsg.what = code.Login_Ok;
                    ma.handler.sendMessage(ma.hdmsg);
                    Log.v("complete", this.res_str);
                } else {
                    result_code = jm.getDataFromJson(json, "ret");
                    ma.hdmsg = ma.handler.obtainMessage();
                    ma.hdmsg.what = code.Login_Error;
                    ma.handler.sendMessage(ma.hdmsg);
                    Log.e("connect_error", result_code);
                }
            }
        } else if(param.contains("duplicate_id")) { // 아이디 중복 메세지 처리
            if(result == null) {
                result_code = "server error";
                Log.e("connect_error", result_code);
                create.hdmsg = create.handler.obtainMessage();
                create.hdmsg.what = code.Server_Connection_Error;
                create.handler.sendMessage(create.hdmsg);
                return;
            } else {
                this.res_str = result;
                json_manager jm = new json_manager();
                JSONObject json = jm.parseJson(res_str);
                result_code = jm.getDataFromJson(json, "ret");
                if(result_code.contains("ok")) {
                    Log.v("complete", result_code);
                    create.hdmsg = create.handler.obtainMessage();
                    create.hdmsg.what = code.find_id_Ok;
                    create.handler.sendMessage(create.hdmsg);
                } else {
                    Log.e("duplicate", result_code);
                    create.hdmsg = create.handler.obtainMessage();
                    create.hdmsg.what = code.Create_Duplicate;
                    create.handler.sendMessage(create.hdmsg);
                }
                return;
            }
        } else if(param.contains("create")) { // 회원가입 메세지 처리
            if(result == null) {
                result_code = "server error";
                Log.e("connect_error", result_code);
                create.hdmsg = create.handler.obtainMessage();
                create.hdmsg.what = code.Server_Connection_Error;
                create.handler.sendMessage(create.hdmsg);
                return;
            } else {
                this.res_str = result;
                json_manager jm = new json_manager();
                JSONObject json = jm.parseJson(res_str);
                result_code = jm.getDataFromJson(json, "ret");
                if(result_code.contains("ok")) {
                    Log.v("complete", result_code);
                    create.hdmsg = create.handler.obtainMessage();
                    create.hdmsg.what = code.Create_Ok;
                    create.handler.sendMessage(create.hdmsg);
                } else {
                    Log.e("error", result_code);
                    create.hdmsg = create.handler.obtainMessage();
                    create.hdmsg.what = code.Create_Error;
                    create.handler.sendMessage(create.hdmsg);
                }
                return;
            }
        } else if(param.contains("find_id")) { // 아이디 찾기 메세지 처리
            if (result == null) {
                result_code = "server error";
                Log.e("connect_error", result_code);
                find_id_activity.hdmsg = find_id_activity.handler.obtainMessage();
                find_id_activity.hdmsg.what = code.Server_Connection_Error;
                find_id_activity.handler.sendMessage(find_id_activity.hdmsg);
                return;
            } else {
                this.res_str = result;
                json_manager jm = new json_manager();
                JSONObject json = jm.parseJson(res_str);
                result_code = jm.getDataFromJson(json, "ret");
                id = jm.getDataFromJson(json, "id");
                if (result_code.contains("ok")) {
                    Log.v("complete", result_code);
                    find_id_activity.hdmsg = find_id_activity.handler.obtainMessage();
                    find_id_activity.hdmsg.what = code.find_id_Ok;
                    find_id_activity.handler.sendMessage(find_id_activity.hdmsg);
                } else {
                    Log.e("connect_error", result_code);
                    find_id_activity.hdmsg = find_id_activity.handler.obtainMessage();
                    find_id_activity.hdmsg.what = code.Server_Connection_Error;
                    find_id_activity.handler.sendMessage(find_id_activity.hdmsg);
                }
                return;
            }
        } else if(param.contains("find_pwd")) {
            if(result == null) {
                find_pwd_activity.hdmsg = find_pwd_activity.handler.obtainMessage();
                find_pwd_activity.hdmsg.what = code.Server_Connection_Error;
                find_pwd_activity.handler.sendMessage(find_pwd_activity.hdmsg);
            } else {
                this.res_str = result;
                json_manager jm = new json_manager();
                JSONObject json = jm.parseJson(res_str);
                result_code = jm.getDataFromJson(json, "ret");
                if(result_code.contains("ok")) {
                    find_pwd_activity.hdmsg = find_pwd_activity.handler.obtainMessage();
                    find_pwd_activity.hdmsg.what = code.find_pwd_Ok;
                    find_pwd_activity.handler.sendMessage(find_pwd_activity.hdmsg);
                    Log.v("complete", this.res_str);
                } else {
                    find_pwd_activity.hdmsg = find_pwd_activity.handler.obtainMessage();
                    find_pwd_activity.hdmsg.what = code.find_pwd_Error;
                    find_pwd_activity.handler.sendMessage(find_pwd_activity.hdmsg);
                    Log.e("connect_error", result_code);
                }
            }
        } else if(param.contains("update_pwd")) {
            if(result == null) {
                update_pwd_activity.hdmsg = update_pwd_activity.handler.obtainMessage();
                update_pwd_activity.hdmsg.what = code.Server_Connection_Error;
                update_pwd_activity.handler.sendMessage(update_pwd_activity.hdmsg);
            } else {
                this.res_str = result;
                json_manager jm = new json_manager();
                JSONObject json = jm.parseJson(res_str);
                result_code = jm.getDataFromJson(json, "ret");
                if(result_code.contains("ok")) {
                    update_pwd_activity.hdmsg = update_pwd_activity.handler.obtainMessage();
                    update_pwd_activity.hdmsg.what = code.update_pwd_Ok;
                    update_pwd_activity.handler.sendMessage(update_pwd_activity.hdmsg);
                    Log.v("complete", this.res_str);
                } else {
                    update_pwd_activity.hdmsg = update_pwd_activity.handler.obtainMessage();
                    update_pwd_activity.hdmsg.what = code.update_pwd_Error;
                    update_pwd_activity.handler.sendMessage(update_pwd_activity.hdmsg);
                    Log.e("connect_error", result_code);
                }
            }
        }
    }

    // url 생성 메소드
    private URL generate_url(String str_url) {
        URL url = null;
        try {
            Log.i("url", str_url);
            url = new URL(str_url);
        } catch (MalformedURLException e) {
            // URL 작성 오류
            ma.hdmsg = ma.handler.obtainMessage();
            ma.hdmsg.what = code.Create_URL_Error;
            ma.handler.sendMessage(ma.hdmsg);
            Log.e("connect_error", "url_error " + url);
            return url;
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
                ma.hdmsg = ma.handler.obtainMessage();
                ma.hdmsg.what = code.Server_Connection_Error;
                ma.handler.sendMessage(ma.hdmsg);
                Log.e("connect_error", "connecting_error " + conn.getResponseCode() + " : " + conn.getResponseMessage());
                return null;
            } else {
                // 응답받은 json 파싱
                Log.v("response", conn.getResponseMessage());
                json_manager jm = new json_manager(conn.getInputStream());
                ret = jm.getAllString();
            }
        } catch (IOException e) {
            Log.e("connect_error", "io_error");
            Log.e("connect_error", e.toString());
            return null;
        } finally {
            if(conn != null) {
                conn.disconnect();
            }
        }
        return ret;
    }
    public String getUID() {
        return this.uid;
    }
    public String getLast_login() {
        return this.last_login;
    }
    public String getResultCode() {
        return this.result_code;
    }
    public String getID() {
        return this.id;
    }
}

// json 파싱
class json_manager {
    private InputStream res;
    public json_manager(InputStream res) {
        Log.v("res", res.toString());
        this.res = res;
    }
    public json_manager() {}
    public String getAllString() {
        String ret = null;

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(res, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            ret = sb.toString().trim();
            Log.v("json", ret);
        } catch (IOException e) {
            Log.e("parsing_error", "json_error");
            return null;
        }

        return ret;
    }
    public JSONObject parseJson(String str) {
        JSONObject ret = null;
        try {
            ret = new JSONObject(str);
        } catch (JSONException e) {
            Log.e("parsing_error", "json_error");
        }
        return ret;
    }
    public String getFunc(JSONObject json) {
        String func = null;
        //json.getString("");
        return func;
    }
    public String getDataFromJson(JSONObject json, String name) {
        String ret = null;
        try {
            ret = json.getString(name);
        } catch (JSONException e) {
            Log.e("parsing_error", "json_error");
        }

        return ret;
    }
}