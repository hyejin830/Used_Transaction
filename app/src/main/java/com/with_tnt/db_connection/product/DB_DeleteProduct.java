package com.with_tnt.db_connection.product;

/**
 * Created by ANGELZIN on 2017-05-08.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.ViewProductActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by Ilwoo on 2017-04-30.
 */

public class DB_DeleteProduct extends AsyncTask<String, String, String> {

    int pnumber;
    Context exCon;

    public DB_DeleteProduct(Context exCon, int temper)
    {
        pnumber = temper;
        this.exCon = exCon;
    }


    @Override
    protected String doInBackground(String... arg0)
    {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        PreparedStatement pre = null;

        try
        {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            String Query = "Delete from Product_Info Where Product_Number = "+pnumber+";";
            pre = conn.prepareStatement(Query);
            pre.executeUpdate();
            Log.i("tet", Query);


        }
        catch (Exception e)
        {

        }
        finally
        {
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
    protected void onPostExecute(String result)
    {
        // TODO Auto-generated method stub
        ((ViewProductActivity) exCon).deleteData();
    }


}