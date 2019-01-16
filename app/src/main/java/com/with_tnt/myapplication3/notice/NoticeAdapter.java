package com.with_tnt.myapplication3.notice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.with_tnt.myapplication3.R;

import java.util.ArrayList;

/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class NoticeAdapter extends ArrayAdapter<NoticeControl>
{
	private LayoutInflater mInflater;
	public ArrayList<NoticeControl> objectOfArray;

	public NoticeAdapter(Context context, ArrayList<NoticeControl> objectOfArray)
	{
		super(context, 0, objectOfArray);
		this.objectOfArray = objectOfArray;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View v, ViewGroup parent)
	{
		View view = null;
		if (v == null)
		{
			view = mInflater.inflate(R.layout.noti_list, null);
		}
		else
		{
			view = v;
		}
		final NoticeControl data = this.getItem(position);
		if (data != null)
		{
			TextView nt_tit = (TextView) view.findViewById(R.id.nt_tit);
			nt_tit.setText(data.getNt_tit());
			TextView nt_date = (TextView) view.findViewById(R.id.nt_date);
			nt_date.setText(data.getNt_date());
		}
		return view;
	}
}
