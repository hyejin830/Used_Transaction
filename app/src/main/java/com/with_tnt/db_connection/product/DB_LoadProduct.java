package com.with_tnt.db_connection.product;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.product.Product_Adapter;
import com.with_tnt.myapplication3.product.Product_Control;
import com.with_tnt.myapplication3.product.ProductlistActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by Ilwoo on 2017-04-08.
 */

public class DB_LoadProduct extends AsyncTask<String, String, String> {
	Context exCon = null;
	Product_Adapter adapterOfProduct;
	String search;
	int category;
	int jFlag = 0;
    int a[];

	public DB_LoadProduct(Context exCon, Product_Adapter adapterOfProduct, String search, int category, int jFlag)
	{
		this.search = search;
		this.adapterOfProduct = adapterOfProduct;
		this.exCon = exCon;
		this.category = category;
		this.jFlag = jFlag;
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
			if(jFlag == 1) {
				//
				if(category != 0)
				{
					Query = "SELECT Product_Number, Product_Name, Selling_Price, Upload_User, CONVERT(VARCHAR, Upload_Date,  20), Deal_State FROM Product_Info WHERE Category_Code = "+category+" ORDER BY Selling_Price ASC;";
					//Query = "SELECT Product_Number, Product_Name, Selling_Price, Upload_User, CONVERT(VARCHAR, Upload_Date,  20), Deal_State FROM Product_Info WHERE Product_Name LIKE '%"+search+"%' ORDER BY Upload_Date DESC;";
				}
				else
				{
					// ! 여기 쿼리문 수정됐어 ! 해쉬태그검색
					Query = "SELECT  Product_Number, Product_Name, Selling_Price, Upload_User, CONVERT(VARCHAR, Upload_Date,  20), Deal_State FROM Product_Info WHERE Product_Name LIKE '%"+search+"%' OR Product_Number IN (SELECT Product_Number FROM Hashtag WHERE Hashtag LIKE '%"+search+"%') ORDER BY Selling_Price ASC;";
				}
			}
			else {
				//
				if(category != 0)
				{
					Query = "SELECT Product_Number, Product_Name, Selling_Price, Upload_User, CONVERT(VARCHAR, Upload_Date,  20), Deal_State FROM Product_Info WHERE Category_Code = "+category+" ORDER BY Upload_Date DESC;";
					//Query = "SELECT Product_Number, Product_Name, Selling_Price, Upload_User, CONVERT(VARCHAR, Upload_Date,  20), Deal_State FROM Product_Info WHERE Product_Name LIKE '%"+search+"%' ORDER BY Upload_Date DESC;";
				}
				else
				{
					// ! 여기 쿼리문 수정됐어 ! 해쉬태그검색
					Query = "SELECT  Product_Number, Product_Name, Selling_Price, Upload_User, CONVERT(VARCHAR, Upload_Date,  20), Deal_State FROM Product_Info WHERE Product_Name LIKE '%"+search+"%' OR Product_Number IN (SELECT Product_Number FROM Hashtag WHERE Hashtag LIKE '%"+search+"%') ORDER BY Upload_Date DESC;";
				}
			}

			rSet = stmt.executeQuery(Query);
			while (rSet.next())
			{
				Log.e("text", "여긴 읽어온다");
				adapterOfProduct.add(new Product_Control(rSet.getInt(1), rSet.getString(2), rSet.getInt(3), rSet.getString(4), rSet.getString(5), rSet.getInt(6)));
			}
		}
		catch (Exception e)
		{
			Log.i("에러났네",e.toString());
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
			if(adapterOfProduct.getCount() == 0)
			{
				if(category==0)
					((ProductlistActivity) exCon).resultGuide.setText("검색결과가 없습니다.");
				else
					((ProductlistActivity) exCon).resultGuide.setText("카테고리에 상품이 없습니다.");
			}
			else
			{
				if(search == null)
				{
					((ProductlistActivity) exCon).updateList();
				}
				else
				{
					((ProductlistActivity) exCon).updateList();
				}
			}
		}
		catch (Exception e)
		{

		}
	}

}