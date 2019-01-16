package com.with_tnt.myapplication3.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.viewpagerindicator.CirclePageIndicator;
import com.with_tnt.myapplication3.product.ProductlistActivity;
import com.with_tnt.myapplication3.R;

// promotion / category / banner
/**
 * Created by ANGELZIN on 2017-03-26.
 */

public class MainFragment extends Fragment
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

	public static final String ARG_PAGE = "ARG_PAGE";
	private int mPageNo;

	// private CirclePageIndicator

	ViewPager viewPager;

	int images[] =
	{ R.drawable.promotion, R.drawable.promotion2, R.drawable.promotion3 };

	MypromoPagerAdapter mypromoPagerAdapter;

	public static MainFragment newInstance(int pageNo)
	{

		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNo);
		MainFragment fragment = new MainFragment();
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
		View view = inflater.inflate(R.layout.home_page, container, false);

		SharedPreferences localEditor = this.getActivity().getSharedPreferences("UserInfo", 0); // UserInfo

		try
		{
			SharedPreferences mPref = getActivity().getSharedPreferences("isFirst", Activity.MODE_PRIVATE);

			Boolean bFirst = mPref.getBoolean("isFirst", false);
			if (bFirst == false)
			{
				Log.d("version", "first");
				SharedPreferences.Editor editor = mPref.edit();
				editor.putBoolean("isFirst", true);
				editor.commit();

				// 최초 실행시 필요한 코드
				toasting(localEditor.getString("UserName", "") + "님 로그인 되셨습니다.");
			}
			if (bFirst == true)
			{
				Log.d("version", "not first");
			}

		}
		catch (Exception e)
		{

		}

		// 상단 프로모션 이미지 뷰 페이저
		viewPager = (ViewPager) view.findViewById(R.id.view_pager);
		Context context = this.getContext();
		mypromoPagerAdapter = new MypromoPagerAdapter(context, images);
		viewPager.setAdapter(mypromoPagerAdapter);

		CirclePageIndicator circlePageIndicator = (CirclePageIndicator) view.findViewById(R.id.titles);
		circlePageIndicator.setViewPager(viewPager);

		// circlePageIndicator.setOnPageChangeListener(mPageChangeListener);

		bookBtn = (CustomCategoryButton) view.findViewById(R.id.btn_book);
		wearBtn = (CustomCategoryButton) view.findViewById(R.id.btn_wear);
		dailySuppliesBtn = (CustomCategoryButton) view.findViewById(R.id.btn_dailySupplies);
		electronicsBtn = (CustomCategoryButton) view.findViewById(R.id.btn_electronics);
		beautyBtn = (CustomCategoryButton) view.findViewById(R.id.btn_beauty);
		sportsBtn = (CustomCategoryButton) view.findViewById(R.id.btn_sports);
		tiketBtn = (CustomCategoryButton) view.findViewById(R.id.btn_tiket);
		stationeryBtn = (CustomCategoryButton) view.findViewById(R.id.btn_stationery);
		etcBtn = (CustomCategoryButton) view.findViewById(R.id.btn_etc);

		bookBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 1);
				startActivity(intent);
			}
		});

		wearBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 2);
				startActivity(intent);
			}
		});

		dailySuppliesBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 3);
				startActivity(intent);
			}
		});

		electronicsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 4);
				startActivity(intent);
			}
		});

		beautyBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 5);
				startActivity(intent);
			}
		});

		sportsBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 6);
				startActivity(intent);
			}
		});

		tiketBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 7);
				startActivity(intent);
			}
		});

		stationeryBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 8);
				startActivity(intent);
			}
		});

		etcBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				Intent intent = new Intent(MainFragment.this.getActivity(), ProductlistActivity.class);
				intent.putExtra("code", 9);
				startActivity(intent);
			}
		});

		return view;
	}

	public void toasting(String temp)
	{
		Toast.makeText(getActivity().getApplicationContext(), temp, Toast.LENGTH_LONG).show();
	}
}
