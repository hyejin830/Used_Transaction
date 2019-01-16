package com.with_tnt.db_connection.cart;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ANGELZIN on 2017-05-14.
 */

public class DB_UploadCart extends AsyncTask<String, String, String> {

    String userID;
    int product_Number;
    Context exCon;
    int flag = 0;

    public DB_UploadCart(String userID, int product_Number, Context exCon) {

        this.userID = userID;
        this.product_Number = product_Number;
        this.exCon = exCon;
    }

    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        PreparedStatement pre = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.
        ResultSet rSet = null;
        ;
        Statement stmt = null;
        try {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            String Query = "INSERT INTO Cart VALUES ('" + userID + "'," + product_Number+")";
            pre = conn.prepareStatement(Query);
            pre.executeUpdate();

        } catch (Exception e) {
            flag = 1;
            e.toString();
        } finally {
            try {
                if (rSet != null) {
                    rSet.close();
                }
            } catch (Exception e) {
                ;
            }
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                ;
            }
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
            Log.i("혜진","업로드 완료");

    }

}
