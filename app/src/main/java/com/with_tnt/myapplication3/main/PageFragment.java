package com.with_tnt.myapplication3.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.myapplication3.R;

/**
 * Created by ANGELZIN on 2017-03-22. 프래그먼트 샘플
 */

public class PageFragment extends Fragment
{
	public static final String ARG_PAGE = "ARG_PAGE";
	private int mPageNo;

	public static PageFragment newInstance(int pageNo)
	{

		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNo);
		PageFragment fragment = new PageFragment();
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
		View view = inflater.inflate(R.layout.fragment_page, container, false);

		TextView textView = (TextView) view;
		textView.setText("Fragment #" + mPageNo);
		return view;
	}

	public void toasting(String temp)
	{
		Toast.makeText(getActivity().getApplicationContext(), temp, Toast.LENGTH_LONG).show();
	}
}