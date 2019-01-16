package com.with_tnt.db_connection.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.UploadProductActivity;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Ilwoo on 2017-04-01.
 */

public class DB_UploadProduct extends AsyncTask<String, String, String> {
    int category_Code;
    String image_Url;
    String product_Name;
    String userID;
    int list_Price;
    int selling_Price;
    int product_State;
    String product_Content;
    Context exCon;
    int flag = 0;
    String sendBack;
    String hashTag;
    String rTmp;
    String[] hTArray;
    int pn;

    public DB_UploadProduct(int category_Code, String image_Url, String product_Name, int list_Price, int selling_Price, int product_State, String hashTag, String product_Content, Context exCon, String sendBack, String userID) {
        this.category_Code = category_Code;
        this.image_Url = image_Url;
        this.product_Name = product_Name;
        this.list_Price = list_Price;
        this.selling_Price = selling_Price;
        this.product_State = product_State;
        this.product_Content = product_Content;
        this.exCon = exCon;
        this.sendBack = sendBack;
        this.hashTag = hashTag;
        this.userID = userID;
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
            String Query = "INSERT INTO Product_Info VALUES (" + category_Code + ",'" + sendBack + "','" + product_Name + "'," + list_Price + "," + selling_Price + "," + product_State + ",'" + product_Content + "',GETDATE(),'" + userID + "',0, NULL)";
            pre = conn.prepareStatement(Query);
            pre.executeUpdate();
            Query = "select Product_Number from Product_Info where ImageURL = '" + sendBack + "';";
            // pre = conn.prepareStatement(Query);
            stmt = conn.createStatement();
            rSet = stmt.executeQuery(Query);
            while (rSet.next()) {
                pn = rSet.getInt(1);
            }

            rTmp = hashTag.replace(" ", "");
            hTArray = rTmp.split("#");

            for (int i = 1; i < hTArray.length; i++) {
                Query = "INSERT INTO Hashtag VALUES (" + pn + ", '" + hTArray[i] + "');";
                pre = conn.prepareStatement(Query);
                pre.executeUpdate();
            }
            Log.e("text", String.valueOf(hTArray.length));
            if (!(hTArray.length == 1)) {
                Socket socket = null;
                DataOutputStream dos;
                try {
                    // 서버 연결
                    socket = new Socket("192.168.0.68", 7120);
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF(hashTag);
                    dos.flush();
                    dos.close();
                    socket.close();

                } catch (Exception e) {
                    Log.e("text", e.toString());
                }
            }

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
        if (flag == 1) {
            ((UploadProductActivity) exCon).toasting2("문제가 발생했어요 ㅠㅠ");
        } else {
            ((UploadProductActivity) exCon).toasting2("업로드 완료!");
            ((UploadProductActivity) exCon).finish();
        }
    }

}
