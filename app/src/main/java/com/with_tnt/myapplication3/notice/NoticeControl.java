package com.with_tnt.myapplication3.notice;

/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class NoticeControl
{
	int nt_number;
	String nt_tit;
	String nt_date;


	public NoticeControl(String nt_tit, String nt_date, int nt_number)
	{
		// TODO Auto-generated constructor stub
		this.nt_tit = nt_tit;
		this.nt_date = nt_date;
		this.nt_number = nt_number;
	}
	public int getNt_number(){
		return nt_number;
	}

	public String getNt_tit()
	{
		return nt_tit;
	}

	public String getNt_date()
	{
		return nt_date;
	}
}