package com.with_tnt.db_connection.cart;

import android.content.Context;
import android.os.AsyncTask;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.cart.CartActivity;
import com.with_tnt.myapplication3.cart.CartAdapter;
import com.with_tnt.myapplication3.cart.CartControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ANGELZIN on 2017-05-13.
 */

public class DB_LoadCart extends AsyncTask<String, String, String>
{
    Context exCon = null;
    CartAdapter adapterOfHistory;
    String id;

    public DB_LoadCart(Context exCon, CartAdapter adapterOfHistory, String id)
    {
        this.id = id;
        this.adapterOfHistory = adapterOfHistory;
        this.exCon = exCon;
    }

    @Override
    protected String doInBackground(String... arg0)
    {
        // TODO Auto-generated method stub
        Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
        ResultSet rSet = null; // Query 결과를 저장하는 객체 선언
        Statement stmt = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.

        try
        {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            stmt = conn.createStatement();
            String Query;
           Query= "SELECT Product_Name,Category_Code,Product_State,Selling_Price, ct.Product_Number FROM Cart AS ct INNER JOIN Product_Info AS pinfo ON ct.Product_Number = pinfo.Product_Number WHERE ct.ID = "+"'"+id+"'" ;
            rSet = stmt.executeQuery(Query);
            while (rSet.next())
            {
                adapterOfHistory.add(new CartControl(rSet.getString(1), rSet.getInt(2), rSet.getInt(3),rSet.getInt(4),rSet.getInt(5)));
            }
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
        ((CartActivity) exCon).updateList();

    }
}
