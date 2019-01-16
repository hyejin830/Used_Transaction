package com.with_tnt.db_connection.login_signup;

import android.content.Context;
import android.os.AsyncTask;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.login_signup.LoginActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class DB_Login extends AsyncTask<String, String, String>
{
	boolean ok = false;
	Context exCon = null;
	String ID = null;
	String PW = null;
	String classOfUser = null;

	public DB_Login(String ID, String PW, Context exCon)
	{
		this.exCon = exCon;
		this.ID = ID;
		this.PW = PW;
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
			String Query = "SELECT Name FROM User_Info  WHERE ID = '" + ID + "' AND Password = '" + PW + "';";
			rSet = stmt.executeQuery(Query);
			while (rSet.next())
			{
				if (rSet.getString(1) == null)
				{

				}
				else
				{
					ok = true;
					classOfUser = rSet.getString(1);
				}
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
			if (ok)
			{
				// 로그인 성공
				((LoginActivity) exCon).successLogin(classOfUser);
			}
			else
			{
				((LoginActivity) exCon).toasting("아이디 또는 비밀번호를 다시 확인해주세요.");
				((LoginActivity) exCon).PW.setText("");
			}
		}
		catch (Exception e)
		{

		}
	}

}
