package com.with_tnt.myapplication3.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.cart.DB_UploadCart;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.login_signup.LoginActivity;
import com.with_tnt.myapplication3.main.MyPageFragment;

import java.util.ArrayList;

/**
 * Created by Ilwoo on 2017-04-08.
 */

public class Product_Adapter extends ArrayAdapter<Product_Control>
{
	private LayoutInflater mInflater;
	Context con;
	public ArrayList<Product_Control> objectOfArray;

	public Product_Adapter(Context context, ArrayList<Product_Control> objectOfArray)
	{
		super(context, 0, objectOfArray);
		this.con = context;
		this.objectOfArray = objectOfArray;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent)
	{
		View view = null;
		int state;
		if (v == null)
		{
			view = mInflater.inflate(R.layout.custom_listview, null);
		}
		else
		{
			view = v;
		}
		final Product_Control data = this.getItem(position);
		if (data != null)
		{
			TextView patPNameText = (TextView) view.findViewById(R.id.clv_productName);
			patPNameText.setText(data.getProductName());
			TextView patPPriceText = (TextView) view.findViewById(R.id.clv_productPrice);
			patPPriceText.setText(data.getPrice() + "원");
			TextView patSellerText = (TextView) view.findViewById(R.id.clv_seller);
			patSellerText.setText(data.getSeller());
			TextView patDateText = (TextView) view.findViewById(R.id.clv_date);
			patDateText.setText(data.getDate());
			TextView patStateText = (TextView) view.findViewById(R.id.clv_state);
			state = Integer.valueOf(data.getState());
			if (state == 0)
				patStateText.setText("판매중");

			else if (state == 1)
				patStateText.setText("거래중");

			else
				patStateText.setText("거래 완료");
			Button btnstate = (Button) view.findViewById(R.id.clv_zzim_btn);

			btnstate.setOnClickListener(new View.OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					AlertDialog.Builder gsDialog = new AlertDialog.Builder(getContext());
					gsDialog.setMessage("장바구니에 추가 하시겠어요?");
					gsDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							// TODO Auto-generated method stub
						}
					});
					gsDialog.setPositiveButton("네", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							DB_UploadCart chartuploadTask = new DB_UploadCart(con.getSharedPreferences("UserInfo", 0).getString("UserID", ""),data.getProductNumber(),con);
							chartuploadTask.execute();
							Toast.makeText(con, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show();
						}
					}).create().show();
				}
			});

		}
		return view;
	}
}
