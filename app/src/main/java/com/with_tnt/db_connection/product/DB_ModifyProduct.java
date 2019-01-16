package com.with_tnt.db_connection.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.ModifyProductActivity;

import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;


/**
 * Created by Ilwoo on 2017-05-12.
 */

public class DB_ModifyProduct extends AsyncTask<String, String, String> {

    int category_Code;
    String product_Name;
    int list_Price;
    int selling_Price;
    int product_State;
    String product_Content;
    Context exCon;
    //String sendBack;
    String hashTag;
    String rTmp;
    String[] hTArray;
    int productNumber;
    int flag = 0;


    public DB_ModifyProduct(int category_Code, String product_Name, int list_Price, int selling_Price, int product_State, String product_Content, Context exCon, int productNumber, String hashTag) {
        this.category_Code = category_Code;
        this.product_Name = product_Name;
        this.list_Price = list_Price;
        this.selling_Price = selling_Price;
        this.product_State = product_State;
        this.product_Content = product_Content;
        this.exCon = exCon;
        this.hashTag = hashTag;
        this.productNumber = productNumber;
    }


    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        PreparedStatement pre = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.
        //Statement stmt = null;
        try {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            String Query = "UPDATE Product_Info SET Category_Code="+category_Code+", Product_Name='"+product_Name+"', List_Price="+list_Price+", Selling_Price="+selling_Price+", Product_State="+product_State+", Product_Content='"+product_Content+"' WHERE Product_Number = "+productNumber+";";
            pre = conn.prepareStatement(Query);
            pre.executeUpdate();


            rTmp = hashTag.replace(" ", "");
            hTArray = rTmp.split("#");

            for (int i = 1; i < hTArray.length; i++) {
                Query = "INSERT INTO Hashtag VALUES (" + productNumber + ", '" + hTArray[i] + "');";
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
            Log.e("text", e.toString());
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
        if (flag == 1) {
            ((ModifyProductActivity) exCon).toasting2("문제가 발생했어요 ㅠㅠ");
        } else {
            ((ModifyProductActivity) exCon).toasting2("업로드 완료!");
            ((ModifyProductActivity) exCon).finish();
        }
    }





}
