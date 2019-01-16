package com.with_tnt.db_connection.login_signup;

import android.content.Context;
import android.os.AsyncTask;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.login_signup.JoinActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class DB_Signup extends AsyncTask<String, String, String>
{
	Context exCon;
	String ID;
	String PW;
	String name;
	String email;
	int flag = 0;

	public DB_Signup(String ID, String PW, String name, String email, Context exCon)
	{
		this.ID = ID;
		this.PW = PW;
		this.name = name;
		this.email = email;
		this.exCon = exCon;
	}

	@Override
	protected String doInBackground(String... arg0)
	{
		// TODO Auto-generated method stub
		Connection conn = null; // Database 연결을 위한 컨넥션 객체 선언
		PreparedStatement pre = null; // stmp 또는 pstmp를 선언하여 사용할 수 있다.
		try
		{
			Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
			conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
			String Query = " INSERT INTO User_Info VALUES ('" + ID + "', '" + PW + "', '" + name + "', '" + email + "', NULL)";
			pre = conn.prepareStatement(Query);
			pre.executeUpdate();
		}
		catch (Exception e)
		{
			flag = 1;
			// Log.e("aaaaaaaaaaaaaaa", e.toString());
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
		if (flag == 1)
		{
			((JoinActivity) exCon).toasting("이미 존재하는 아이디입니다.");
		}
		else
		{
			((JoinActivity) exCon).toasting("성공적으로 가입되었습니다.");
			((JoinActivity) exCon).successSignUp(name);
			((JoinActivity) exCon).finish();
		}
	}
}
