package com.with_tnt.db_connection.push;

import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.with_tnt.db_connection.DB_Setting_Value.Setting_Value.API_URL_FCM;
import static com.with_tnt.db_connection.DB_Setting_Value.Setting_Value.AUTH_KEY_FCM;

/**
 * Created by KIM on 2017-05-12.
 */

public class DB_SendMessage extends AsyncTask<String, String, String> {
    String productNumber;
    String sendUser;
    String receiveUser;
    String message;
    String token;
    int flagMe;

    public DB_SendMessage(String productNumber, String sendUser, String receiveUser, String message, int flagMe) {
        this.productNumber = productNumber;
        this.sendUser = sendUser;
        this.receiveUser = receiveUser;
        this.message = message;
        this.flagMe = flagMe;
    }

    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        ResultSet rSet = null; // Query 결과를 저장하는 객체 선언
        Statement stmt = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.

        try {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            stmt = conn.createStatement();
            String query;
            if (flagMe == 0) // 보낸 사람이 내가 아니면
            {
                query = "SELECT Token FROM User_Info WHERE ID = '" + receiveUser + "';";
            } else {
                query = "SELECT Token FROM User_Info WHERE ID = '" + sendUser + "';";
            }
            Log.e("text", query);
            rSet = stmt.executeQuery(query);
            while (rSet.next()) {
                token = rSet.getString(1);
            }
            query = "INSERT INTO Chatting VALUES (" + productNumber + ", '" + message + "', '" + receiveUser + "', '" + sendUser + "', GETDATE(), '"+flagMe+"');";
            Log.e("text", query);
            stmt.executeUpdate(query);
            // send
            URL url = new URL(API_URL_FCM);
            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoInput(true);
            httpConn.setDoOutput(true);
            httpConn.setRequestMethod("POST");
            httpConn.setRequestProperty("Authorization", "key=" + AUTH_KEY_FCM);
            httpConn.setRequestProperty("Content-Type", "application/json");
            JSONObject json = new JSONObject();
            json.put("to", token.trim());
            JSONObject info = new JSONObject();
            info.put("title", "message"); // Notification title
            if(flagMe == 0){
                Log.e("text", "여ㅑ기냐?"+ receiveUser+ "~ " + sendUser);
                info.put("body", productNumber + "%_%" + sendUser + "%_%" + message); // Notification
            }
            else
            {
                Log.e("text", "여기임??" + receiveUser+ "~ " + sendUser);
                info.put("body", productNumber + "%_%" + receiveUser + "%_%" + message); // Notification
            }
            json.put("data", info);
            Log.e("text", json.toString());
            try {
                OutputStreamWriter wr = new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8");
                wr.write(json.toString());
                wr.flush();
                BufferedReader br = new BufferedReader(new InputStreamReader((httpConn.getInputStream())));
            } catch (Exception e) {
                Log.e("text", e.toString());
            }
            // send End
        } catch (Exception e) {
            Log.e("text", e.toString());
        } finally {
            try {
                if (rSet != null) {
                    rSet.close();
                }
            } catch (Exception e) {
                ;
            }
            ;
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                ;
            }
            ;
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                ;
            }
            ;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub

    }
}
