package com.with_tnt.db_connection.push;

import android.os.AsyncTask;

import com.with_tnt.db_connection.DB_Setting_Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by KIM on 2017-05-03.
 */

public class DB_SetToken extends AsyncTask<String, String, String> {
    String userID;
    String token;

    public DB_SetToken(String userID, String token) {
        this.userID = userID;
        this.token = token;
    }

    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        PreparedStatement pre = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.
        try {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            String Query = "UPDATE User_Info SET Token = '" + token + "' WHERE ID = '" + userID + "';";
            pre = conn.prepareStatement(Query);
            pre.executeUpdate();
        } catch (Exception e) {

        } finally {
            try {
                if (pre != null)
                    pre.close();
            } catch (Exception e) {
                ;
            }
            ;
            try {
                if (conn != null)
                    conn.close();
            } catch (Exception e) {
                ;
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub

    }

}
