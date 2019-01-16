package com.with_tnt.myapplication3.product;

/**
 * Created by Ilwoo on 2017-04-08.
 */

public class Product_Control
{
	int productNumber;
	String productName;
	int price;
	String seller;
	String date;
	int state;

	public Product_Control(int productNumber, String productName, int price, String seller, String date, int state)
	{
		// TODO Auto-generated constructor stub
		this.productNumber = productNumber;
		this.productName = productName;
		this.price = price;
		this.seller = seller;
		this.date = date;
		this.state = state;
	}

	public int getProductNumber()
	{
		return productNumber;
	}

	public String getProductName()
	{
		return productName;
	}

	public int getPrice()
	{
		return price;
	}

	public String getSeller()
	{
		return seller;
	}

	public String getDate()
	{
		return date;
	}

	public int getState()
	{
		return state;
	}

}