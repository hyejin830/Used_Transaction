package com.with_tnt.db_connection.notice;

import android.content.Context;
import android.os.AsyncTask;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.notice.NoticeDetailActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ANGELZIN on 2017-05-08.
 */

public class DB_LoadNtData extends AsyncTask<String, String, String>
{
    Context showCon;
    int nt_num;
    String nt_tit;
    String nt_date;
    String nt_cont;
    String nt_tit_prev;
    String nt_date_prev;
    String nt_cont_prev;
    String nt_tit_next;
    String nt_date_next;
    String nt_cont_next;

    public DB_LoadNtData(Context showCon, int nt_num)
    {
        this.showCon = showCon;
        this.nt_num = nt_num;
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
            String Query = "SELECT nt_tit, CONVERT(VARCHAR, nt_date, 10), nt_cont FROM Notice WHERE nt_number = " + nt_num + ";";
            rSet = stmt.executeQuery(Query);
            while (rSet.next())
            {
                nt_tit = rSet.getString(1);
                nt_date = rSet.getString(2);
                nt_cont = rSet.getString(3);
            }
            Query = "SELECT nt_tit FROM Notice WHERE nt_number = " + (nt_num-1) + ";";
            rSet = stmt.executeQuery(Query);
            while (rSet.next())
            {
                nt_tit_prev = rSet.getString(1);
            }
            Query = "SELECT nt_tit FROM Notice WHERE nt_number = " + (nt_num+1) + ";";
            rSet = stmt.executeQuery(Query);
            while (rSet.next())
            {
                nt_tit_next = rSet.getString(1);
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
        try
        {
            ((NoticeDetailActivity) showCon).updateList(nt_tit, nt_date, nt_cont, nt_tit_prev, nt_tit_next);
        }
        catch (Exception e)
        {

        }
    }
}