package com.example.tirico.today;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tirico on 2018-06-06.
 */

public class ServerConnection {
    final int port = 80;
    final String ip = "127.0.0.1";

    public ServerConnection() { }

    public JSONObject uploadToServer(JSONObject object) throws IOException, JSONException {
        URL url = new URL(ip);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        OutputStream os = conn.getOutputStream();
        String json = object.toString();
        os.write(json.getBytes("UTF-8"));
        os.close();
        // read the response
        InputStream in = new BufferedInputStream(conn.getInputStream());
        String result = in.toString();
        JSONObject jsonObject = new JSONObject(result);
        in.close();
        conn.disconnect();
        return jsonObject;
    }
}
