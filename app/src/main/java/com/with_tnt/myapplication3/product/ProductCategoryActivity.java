package com.with_tnt.myapplication3.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.main.CustomCategoryButton;

/**
 * Created by Ilwoo on 2017-04-14.
 */

public class ProductCategoryActivity extends AppCompatActivity
{
	CustomCategoryButton bookBtn;
	CustomCategoryButton wearBtn;
	CustomCategoryButton dailySuppliesBtn;
	CustomCategoryButton electronicsBtn;
	CustomCategoryButton beautyBtn;
	CustomCategoryButton sportsBtn;
	CustomCategoryButton tiketBtn;
	CustomCategoryButton stationeryBtn;
	CustomCategoryButton etcBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_category);

		bookBtn = (CustomCategoryButton) findViewById(R.id.btn_book);
		wearBtn = (CustomCategoryButton) findViewById(R.id.btn_wear);
		dailySuppliesBtn = (CustomCategoryButton) findViewById(R.id.btn_dailySupplies);
		electronicsBtn = (CustomCategoryButton) findViewById(R.id.btn_electronics);
		beautyBtn = (CustomCategoryButton) findViewById(R.id.btn_beauty);
		sportsBtn = (CustomCategoryButton) findViewById(R.id.btn_sports);
		tiketBtn = (CustomCategoryButton) findViewById(R.id.btn_tiket);
		stationeryBtn = (CustomCategoryButton) findViewById(R.id.btn_stationery);
		etcBtn = (CustomCategoryButton) findViewById(R.id.btn_etc);

		bookBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 1);
				startActivity(intent);
			}
		});

		wearBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 2);
				startActivity(intent);
			}
		});

		dailySuppliesBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 3);
				startActivity(intent);
			}
		});

		electronicsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 4);
				startActivity(intent);
			}
		});

		beautyBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 5);
				startActivity(intent);
			}
		});

		sportsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 6);
				startActivity(intent);
			}
		});

		tiketBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 7);
				startActivity(intent);
			}
		});

		stationeryBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 8);
				startActivity(intent);
			}
		});

		etcBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(ProductCategoryActivity.this, ProductlistActivity.class);
				intent.putExtra("code", 9);
				startActivity(intent);
			}
		});

	}

}
