package com.with_tnt.myapplication3.main;

import android.graphics.drawable.Drawable;

/**
 * Created by ANGELZIN on 2017-04-08. 마이페이지 리스트 아이템
 */

public class MyPageListViewItem
{
	private Drawable iconDrawable;
	private String titleStr;
	private Drawable moveBt;

	public void setIcon(Drawable icon)
	{
		iconDrawable = icon;
	}

	public void setTitle(String title)
	{
		titleStr = title;
	}

	public void setMove(Drawable move)
	{
		moveBt = move;
	}

	public Drawable getIcon()
	{
		return this.iconDrawable;
	}

	public String getTitle()
	{
		return this.titleStr;
	}

	public Drawable getMove()
	{
		return this.moveBt;
	}
}
