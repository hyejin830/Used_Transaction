package com.with_tnt.db_connection.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.UploadProductActivity;

import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by Ilwoo on 2017-04-01.
 */

public class DB_ReplaceUserName extends AsyncTask<String, String, String>
{
	String userID;
	String userName;

	public DB_ReplaceUserName(String userID, String userName)
	{
		this.userID = userID;
		this.userName = userName;
	}

	protected String doInBackground(String... arg0)
	{
		// TODO Auto-generated method stub
		Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
		PreparedStatement pre = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.
		ResultSet rSet = null;
		Statement stmt = null;
		try
		{
			Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
			conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
			String Query = "UPDATE User_Info SET Name = '" + userName + "' WHERE ID = '" + userID + "';";
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
					conn.close();
			}
			catch (Exception e)
			{
				;
			}
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub

	}

}
