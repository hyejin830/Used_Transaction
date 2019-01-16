package com.with_tnt.myapplication3.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.with_tnt.db_connection.notice.DB_LoadNotice;
import com.with_tnt.myapplication3.R;

import java.util.ArrayList;

/**
 * Created by ANGELZIN on 2017-04-14.
 */

public class NoticeActivity extends AppCompatActivity
{
	ListView userHistoryList;
	ArrayList<NoticeControl> aList;
	public NoticeAdapter adapterOfHistory;
	Context showCon;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.common_custom_actionbar);

		TextView tt = (TextView)findViewById(R.id.act_text);
		tt.setText("공지사항");

		userHistoryList = (ListView) findViewById(R.id.list_notice);
		showCon = this;
		aList = new ArrayList<NoticeControl>();
		adapterOfHistory = new NoticeAdapter(this, aList);
		new DB_LoadNotice(showCon, adapterOfHistory, null).execute();
	} // end onCreate()

	public void updateList()
	{
		userHistoryList.setAdapter(adapterOfHistory);
		userHistoryList.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, final int position, long id)
			{
				// TODO Auto-generated method stub
				startActivityForResult(new Intent(getApplicationContext(), NoticeDetailActivity.class).putExtra("TEXT", adapterOfHistory.getItem(position).getNt_number()), 1);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == 0)
		{
			aList = new ArrayList<NoticeControl>();
			adapterOfHistory = new NoticeAdapter(this, aList);
			new DB_LoadNotice(showCon, adapterOfHistory, null).execute();
		}
		else
		{

		}
	}
}
