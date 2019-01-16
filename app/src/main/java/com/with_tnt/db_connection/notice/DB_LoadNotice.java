package com.with_tnt.db_connection.notice;

import android.content.Context;
import android.os.AsyncTask;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.notice.NoticeActivity;
import com.with_tnt.myapplication3.notice.NoticeAdapter;
import com.with_tnt.myapplication3.notice.NoticeControl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class DB_LoadNotice extends AsyncTask<String, String, String>
{
	Context exCon = null;
	NoticeAdapter adapterOfHistory;
	String search;

	public DB_LoadNotice(Context exCon, NoticeAdapter adapterOfHistory, String search)
	{
		this.search = search;
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
			Query = "SELECT nt_tit, CONVERT(VARCHAR, nt_date, 10), nt_number FROM Notice ORDER BY nt_date DESC;";
			rSet = stmt.executeQuery(Query);
			while (rSet.next())
			{
				adapterOfHistory.add(new NoticeControl(rSet.getString(1), rSet.getString(2), rSet.getInt(3)));
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
		((NoticeActivity) exCon).updateList();

	}
}
