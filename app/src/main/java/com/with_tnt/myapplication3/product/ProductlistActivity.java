package com.with_tnt.myapplication3.product;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.with_tnt.db_connection.product.DB_LoadProduct;
import com.with_tnt.myapplication3.R;

import java.util.ArrayList;

/**
 * Created by Ilwoo on 2017-04-08.
 */

public class ProductlistActivity extends AppCompatActivity {
	ListView userHistoryList;
	ArrayList<Product_Control> aList;
	public Product_Adapter adapterOfProduct;
	Context showCon;
	Intent intent;
	int code = 0;
	public TextView resultGuide;
	String word = null;
	Button jdate;
	Button jprice;
	int jFlag = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_product);
		intent =  getIntent();
		code = intent.getIntExtra("code", 0);
		word = intent.getStringExtra("word");
		userHistoryList = (ListView) findViewById(R.id.listviewProduct);
		showCon = this;
		aList = new ArrayList<Product_Control>();
		adapterOfProduct = new Product_Adapter(this, aList);
		resultGuide = (TextView) findViewById(R.id.tv_resultGuide);
		jdate = (Button) findViewById(R.id.button_Jdate);
		jprice = (Button) findViewById(R.id.button_JPrice);

		Resources res = getResources();
		String[] titles= res.getStringArray(R.array.data_spinner_catrgory);

		if(code!=0){
			resultGuide.setText(titles[code]+"카테고리의 상품리스트");
			new DB_LoadProduct(showCon, adapterOfProduct, null, code, jFlag).execute(); // DB_LoadProduct
		}
		else {
			resultGuide.setText("'"+word+"'의 검색 결과");
			new DB_LoadProduct(showCon, adapterOfProduct, word, 0, jFlag).execute(); // DB_LoadProduct
		}

		jdate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// product_Name, product_Information, product_Cost,  product_Cost_Origin,  product_Condition,  ID,  sendBack,  upCon
				jFlag = 0;
				userHistoryList.setAdapter(null);
				adapterOfProduct.clear();
				if(code!=0){
					//resultGuide.setText(titles[code]+"카테고리의 상품리스트");
					new DB_LoadProduct(showCon, adapterOfProduct, null, code, jFlag).execute(); // DB_LoadProduct
				}
				else {
					//resultGuide.setText("'"+word+"'의 검색 결과");
					new DB_LoadProduct(showCon, adapterOfProduct, word, 0, jFlag).execute(); // DB_LoadProduct
				}
			}
		});

		jprice.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// product_Name, product_Information, product_Cost,  product_Cost_Origin,  product_Condition,  ID,  sendBack,  upCon
				jFlag = 1;
				userHistoryList.setAdapter(null);
				adapterOfProduct.clear();
				if(code!=0){
					//resultGuide.setText(titles[code]+"카테고리의 상품리스트");
					new DB_LoadProduct(showCon, adapterOfProduct, null, code, jFlag).execute(); // DB_LoadProduct
				}
				else {
					//resultGuide.setText("'"+word+"'의 검색 결과");
					new DB_LoadProduct(showCon, adapterOfProduct, word, 0, jFlag).execute(); // DB_LoadProduct
				}
			}
		});

	}

	public void updateList()
	{
		Log.i("wdwdwdw", "qqqqqqqqqqq");
		userHistoryList.setAdapter(adapterOfProduct);
		userHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
			{
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(getApplicationContext(), ViewProductActivity.class).putExtra("TEXT", adapterOfProduct.getItem(position).getProductNumber()).putExtra("category", code), 1);
				//Toast.makeText(ProductlistActivity.this, String.valueOf(adapterOfProduct.getItem(position).getProductNumber()), 100).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		adapterOfProduct.clear();
		userHistoryList.setAdapter(null);
		if (resultCode == 1)
		{
			aList = new ArrayList<Product_Control>();
			adapterOfProduct = new Product_Adapter(this, aList);
			new DB_LoadProduct(showCon, adapterOfProduct, word, 0, jFlag).execute();
		}
		else if (resultCode == 2)
		{
			aList = new ArrayList<Product_Control>();
			adapterOfProduct = new Product_Adapter(this, aList);
			new DB_LoadProduct(showCon, adapterOfProduct, null, code, jFlag).execute();
		}
	}

}
