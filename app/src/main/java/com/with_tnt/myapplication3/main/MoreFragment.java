package com.with_tnt.myapplication3.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.with_tnt.myapplication3.Setting.SettingsActivity;
import com.with_tnt.myapplication3.notice.NoticeActivity;
import com.with_tnt.myapplication3.R;

/**
 * Created by ANGELZIN on 2017-04-29.
 */

public class MoreFragment extends Fragment
{
	public static final String ARG_PAGE = "ARG_PAGE";
	private int mPageNo;

	public static int NOTICE = 0;

	public static MoreFragment newInstance(int pageNo)
	{

		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNo);
		MoreFragment fragment = new MoreFragment();
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mPageNo = getArguments().getInt(ARG_PAGE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View view = inflater.inflate(R.layout.fragment_more_page, container, false);

		ListView listview;
		MoreListViewAdapter adapter;

		adapter = new MoreListViewAdapter();
		Context context = this.getContext();

		listview = (ListView) view.findViewById(R.id.more_list);
		listview.setAdapter(adapter);

		adapter.addItem("공지사항", ContextCompat.getDrawable(context, R.drawable.mypage_bt));
		adapter.addItem("1:1문의", ContextCompat.getDrawable(context, R.drawable.mypage_bt));
		adapter.addItem("설정", ContextCompat.getDrawable(context, R.drawable.mypage_bt));

		listview.setClickable(true);
		listview.setFocusable(true);
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listview.setFocusableInTouchMode(true);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
			{
				// String toastMessage = "positione is " +
				// String.valueOf(position);
				// Toast.makeText(getActivity().getApplicationContext(),
				// toastMessage,Toast.LENGTH_SHORT).show();
				if (position == NOTICE)
				{
					Intent i = new Intent(MoreFragment.this.getActivity(), NoticeActivity.class);
					startActivity(i);
				}
				if(position == 2)
				{
					startActivity(new Intent(MoreFragment.this.getActivity(), SettingsActivity.class));
				}
			}
		});

		return view;
	}

	public void toasting(String temp)
	{
		Toast.makeText(getActivity().getApplicationContext(), temp, Toast.LENGTH_LONG).show();
	}
}
