package com.with_tnt.myapplication3.main;

import android.graphics.drawable.Drawable;

/**
 * Created by ANGELZIN on 2017-04-29.
 */

public class MorePageListViewItem
{
	private String titleStr;
	private Drawable moveBt;

	public void setTitle(String title)
	{
		titleStr = title;
	}

	public void setMove(Drawable move)
	{
		moveBt = move;
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
