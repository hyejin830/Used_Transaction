package com.with_tnt.db_connection.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.ViewProductActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Ilwoo on 2017-04-08.
 */

public class DB_LoadData extends AsyncTask<String, String, String>
{
	Context exCon;
	int temper;
	int category;
	int productNumber;
	String productName;
	int dealState;
	String uploadDate;
	int listPrice;
	int sellingPrice;
	int product_state;
	String seller;
	//@혜진 내가일단 처리
	String contents;String hashTag = "";
	String tmp;


	public DB_LoadData(Context exCon, int temper)
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

		try
		{
			Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
			conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
			stmt = conn.createStatement();
			String Query = "SELECT Category_Code, Product_Number, Product_Name, Deal_State, CONVERT(VARCHAR, Upload_Date,  20), List_Price, Selling_Price, Product_State, Upload_User, Product_Content FROM Product_Info WHERE Product_Number = "+temper+";";
			rSet = stmt.executeQuery(Query);
			Log.i("tet", Query);
			while (rSet.next())
			{
				category = rSet.getInt(1);
				productNumber = rSet.getInt(2);
				productName = rSet.getString(3);
				dealState = rSet.getInt(4);
				uploadDate = rSet.getString(5);
				listPrice = rSet.getInt(6);
				sellingPrice = rSet.getInt(7);
				product_state = rSet.getInt(8);
				seller = rSet.getString(9);
				//@혜진 내가일단 처리
				contents = rSet.getString(10);
			}

			Query = "SELECT Hashtag FROM Hashtag WHERE Product_Number = "+productNumber+";";
			rSet = stmt.executeQuery(Query);
			while (rSet.next())
			{
				tmp = rSet.getString(1);
				hashTag = hashTag + "#" + tmp + " ";
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
			//@혜진 내가일단 처리
			((ViewProductActivity) exCon).updateList(category, productNumber, productName, dealState, uploadDate, listPrice, sellingPrice, product_state, seller,contents,hashTag);
		}
		catch (Exception e)
		{

		}
	}
}
