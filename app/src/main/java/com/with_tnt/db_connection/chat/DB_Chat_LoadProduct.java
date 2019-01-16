package com.with_tnt.db_connection.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.chat.ChattingActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.with_tnt.myapplication3.chat.ChattingActivity.sender;

/**
 * Created by KIM on 2017-05-05.
 */

public class DB_Chat_LoadProduct extends AsyncTask<String, String, String> {
    Context exCon;
    String productName = null;
    String productPrice = null;
    String uploadDate = null;
    String uploaderName = null;
    int productNumber;

    public DB_Chat_LoadProduct(Context exCon, int productNumber) {
        // TODO Auto-generated constructor stub
        this.exCon = exCon;
        this.productNumber = productNumber;
    }

    @Override
    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        Connection conn = null; // Database ������ ���� ���ؼ� ��ü ����
        ResultSet rSet = null; // Query ����� �����ϴ� ��ü ����
        Statement stmt = null; // stmp �Ǵ� pstmp�� �����Ͽ� ����� �� �ִ�.
        try {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            stmt = conn.createStatement();
            String selectQuery = "SELECT Product_Name, Selling_Price, CONVERT(CHAR(8), Upload_Date, 2), Upload_User FROM dbo.Product_Info WHERE Product_Info.Product_Number = " + productNumber + ";";
            rSet = stmt.executeQuery(selectQuery);
            while (rSet.next()) {
                productName = rSet.getString(1);
                productPrice = rSet.getString(2);
                uploadDate = rSet.getString(3);
                uploaderName = rSet.getString(4);
            }
        } catch (Exception e) {
            Log.i("TEXT", e.toString());
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
        super.onPostExecute(result);
        // Html.fromHtml(<font color="red">"+productPrice+"</font>) f14e5c
        //<font color="red">This is some text!</font>
        //uploderName
        ((ChattingActivity) exCon).uploderName = uploaderName;
        ((ChattingActivity) exCon).producNametTxt.setText(Html.fromHtml("<B>" + productName + "</B>"));
        ((ChattingActivity) exCon).productPriceTxt.setText(Html.fromHtml("<font color='#f14e5c'>" + productPrice + "원</font>") );
        ((ChattingActivity) exCon).uploadDateTxt.setText(Html.fromHtml("<font color='#bfbfbf'>" + uploadDate + "</font>") );
        ((ChattingActivity) exCon).uploaderTxt.setText(uploaderName);
        if(sender == null){
            sender = uploaderName;
        }
        ((ChattingActivity) exCon).loadChatting();
    }
}