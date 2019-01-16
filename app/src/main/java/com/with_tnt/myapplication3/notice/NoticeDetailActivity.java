package com.with_tnt.myapplication3.notice;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.with_tnt.db_connection.notice.DB_LoadNtData;
import com.with_tnt.myapplication3.R;

/**
 * Created by ANGELZIN on 2017-04-14.
 */


public class NoticeDetailActivity extends AppCompatActivity
{
	Context showCon;
	TextView nt_tit;
	TextView nt_tit_prev;
	TextView nt_tit_next;
	TextView nt_date;
	TextView nt_cont;
	int num;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notice_detail);

		getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setCustomView(R.layout.common_custom_actionbar);

		TextView tt = (TextView)findViewById(R.id.act_text);
		tt.setText("공지사항");

		Button btnList;
		btnList = (Button) findViewById(R.id.btn_list);
		btnList.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});

		Button btnPrev;
		btnPrev = (Button) findViewById(R.id.btn_prev);
		btnPrev.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!nt_tit_prev.getText().equals("이전 글이 없습니다.")){
					num = num-1;
					new DB_LoadNtData(showCon, num).execute();
				}
			}
		});

		Button btnNext;
		btnNext = (Button) findViewById(R.id.btn_next);
		btnNext.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!nt_tit_next.getText().equals("다음 글이 없습니다.")){
					num = num+1;
					new DB_LoadNtData(showCon, num).execute();
				}
			}
		});

		TextView nt_p = (TextView) findViewById(R.id.nt_p);
		nt_p.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!nt_tit_prev.getText().equals("이전 글이 없습니다.")){
					num = num-1;
					new DB_LoadNtData(showCon, num).execute();
				}
			}
		});

		TextView nt_n = (TextView) findViewById(R.id.nt_n);
		nt_n.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!nt_tit_next.getText().equals("다음 글이 없습니다.")){
					num = num+1;
					new DB_LoadNtData(showCon, num).execute();
				}
			}
		});

		TextView nt_tprev = (TextView) findViewById(R.id.nt_tit_prev);
		nt_tprev.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!nt_tit_prev.getText().equals("이전 글이 없습니다.")){
					num = num-1;
					new DB_LoadNtData(showCon, num).execute();
				}
			}
		});

		TextView nt_tnext = (TextView) findViewById(R.id.nt_tit_next);
		nt_tnext.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if(!nt_tit_next.getText().equals("다음 글이 없습니다.")){
					num = num+1;
					new DB_LoadNtData(showCon, num).execute();
				}
			}
		});

		showCon = this;
		Intent get_num = getIntent(); // 값을 받아
		num = get_num.getExtras().getInt("TEXT"); // TEXT라는 이름을 가진애를 불러옴
		new DB_LoadNtData(showCon, num).execute();

		nt_tit = (TextView) findViewById(R.id.nt_title);
		nt_tit_prev = (TextView) findViewById(R.id.nt_tit_prev);
		nt_tit_next = (TextView) findViewById(R.id.nt_tit_next);
		nt_date = (TextView) findViewById(R.id.nt_date);
		nt_cont = (TextView) findViewById(R.id.nt_cont);
	}

	public void updateList(String nt_tit, String nt_date, String nt_cont, String nt_tit_prev, String nt_tit_next)
	{
		this.nt_tit.setText(nt_tit);
		this.nt_date.setText(nt_date);
		this.nt_cont.setText(nt_cont);
		if(nt_tit_prev==null){
			this.nt_tit_prev.setText("이전 글이 없습니다.");
		}else{
			this.nt_tit_prev.setText(nt_tit_prev);
		}
		if(nt_tit_next==null){
			this.nt_tit_next.setText("다음 글이 없습니다.");
		}else{
			this.nt_tit_next.setText(nt_tit_next);
		}
	}
}