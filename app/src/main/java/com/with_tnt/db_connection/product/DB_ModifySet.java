package com.with_tnt.db_connection.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.ModifyProductActivity;
import com.with_tnt.myapplication3.product.ViewProductActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Ilwoo on 2017-05-12.
 */

public class DB_ModifySet extends AsyncTask<String, String, String> {

    Context exCon;
    int temper;

    int category;
    //String imageUrl;
    String productName;
    int listPrice;
    int sellingPrice;
    int product_state;
    String contents;
    String hashTag = "";
    String tmp;



    public DB_ModifySet(Context exCon, int temper)
    {
        this.temper = temper;
        this.exCon = exCon;
    }


    @Override
    protected String doInBackground(String... arg0)
    {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        ResultSet rSet = null; // Query 결과를 저장하는 객체 선언
        Statement stmt = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.
        PreparedStatement pre = null;

        try
        {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            stmt = conn.createStatement();
            String Query = "SELECT Category_Code, Product_Name, List_Price, Selling_Price, Product_State, Product_Content FROM Product_Info WHERE Product_Number = " + temper + ";";
            rSet = stmt.executeQuery(Query);
            Log.i("tet", Query);
            while (rSet.next())
            {
                category = rSet.getInt(1);
                productName = rSet.getString(2);
                listPrice = rSet.getInt(3);
                sellingPrice = rSet.getInt(4);
                product_state = rSet.getInt(5);
                contents = rSet.getString(6);
            }

            Query = "SELECT Hashtag FROM Hashtag WHERE Product_Number = "+temper+";";
            rSet = stmt.executeQuery(Query);
            while (rSet.next())
            {
                tmp = rSet.getString(1);
                hashTag = hashTag + "#" + tmp + " ";
            }

            Query = "Delete from Hashtag Where Product_Number = "+temper+";";
            pre = conn.prepareStatement(Query);
            pre.executeUpdate();

        }
        catch (Exception e)
        {

        }
        finally
        {
            try
            {
                if (rSet != null)
                {
                    rSet.close();
                }
            }
            catch (Exception e)
            {
                ;
            }
            ;
            try
            {
                if (stmt != null)
                {
                    stmt.close();
                }
            }
            catch (Exception e)
            {
                ;
            }
            ;
            try
            {
                if (pre != null)
                    pre.close();
            }
            catch (Exception e)
            {
                ;
            }
            ;
            try
            {
                if (conn != null)
                {
                    conn.close();
                }
            }
            catch (Exception e)
            {
                ;
            }
            ;
        }
        return null;
    }



    @Override
    protected void onPostExecute(String result) // UI처리 구간
    {
        // TODO Auto-generated method stub
        try
        {
            ((ModifyProductActivity) exCon).updateList(category, productName, listPrice, sellingPrice, product_state, contents, hashTag);
        }
        catch (Exception e)
        {

        }
    }




}
