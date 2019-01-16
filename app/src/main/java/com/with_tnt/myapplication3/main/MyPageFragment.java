package com.with_tnt.myapplication3.main;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.main.DB_ReplaceUserName;
import com.with_tnt.myapplication3.cart.CartActivity;
import com.with_tnt.myapplication3.login_signup.LoginActivity;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.product.UploadProductActivity;

/**
 * Created by ANGELZIN on 2017-04-08. 마이페이지 화면
 */

public class MyPageFragment extends Fragment
{
	public static final String ARG_PAGE = "ARG_PAGE";
	SharedPreferences localEditor;
	int UPLOAD_PRODUCT = 2;
	int CART = 4;
	int LOGOUT = 5;
	private int mPageNo;

	public static MyPageFragment newInstance(int pageNo)
	{

		Bundle args = new Bundle();
		args.putInt(ARG_PAGE, pageNo);

		MyPageFragment fragment = new MyPageFragment();
		fragment.setArguments(args);

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mPageNo = getArguments().getInt(ARG_PAGE);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{

		View view = inflater.inflate(R.layout.mypage_page, container, false);

		localEditor = this.getActivity().getSharedPreferences("UserInfo", 0); // UserInfo

		// toasting(localEditor.getString("UserName","")+"님 로그인 되셨습니다.");

		ListView listview;
		MyPageListViewAdapter adapter;
		final TextView mypage_name;
		ImageButton fixBtn;
		fixBtn = (ImageButton) view.findViewById(R.id.fixNameButton);

		adapter = new MyPageListViewAdapter();
		final Context context = this.getContext();

		mypage_name = (TextView) view.findViewById(R.id.mypage_user_name);

		mypage_name.setText(localEditor.getString("UserName", ""));

		listview = (ListView) view.findViewById(R.id.mypage_list);
		listview.setAdapter(adapter);

		adapter.addItem(ContextCompat.getDrawable(context, R.drawable.mypage_deal), "거래내역", ContextCompat.getDrawable(context, R.drawable.mypage_bt));

		adapter.addItem(ContextCompat.getDrawable(context, R.drawable.mypage_deal), "대화내역", ContextCompat.getDrawable(context, R.drawable.mypage_bt));

		adapter.addItem(ContextCompat.getDrawable(context, R.drawable.profile_edit), "상품등록", ContextCompat.getDrawable(context, R.drawable.mypage_bt));

		adapter.addItem(ContextCompat.getDrawable(context, R.drawable.mypage_deal), "내가 올린 상품", ContextCompat.getDrawable(context, R.drawable.mypage_bt));

		adapter.addItem(ContextCompat.getDrawable(context, R.drawable.mypage_cart), "장바구니", ContextCompat.getDrawable(context, R.drawable.mypage_bt));

		adapter.addItem(ContextCompat.getDrawable(context, R.drawable.mypage_logout), "로그아웃", null);

		listview.setClickable(true);
		listview.setFocusable(true);
		listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listview.setFocusableInTouchMode(true);

		listview.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long l)
			{
				if (position == UPLOAD_PRODUCT)
				{
					Intent i = new Intent(MyPageFragment.this.getActivity(), UploadProductActivity.class);
					startActivity(i);
				}
				if(position == CART)
				{
					startActivity(new Intent(MyPageFragment.this.getActivity(), CartActivity.class));
				}
				if (position == LOGOUT)
				{
					AlertDialog.Builder gsDialog = new AlertDialog.Builder(getContext());
					gsDialog.setMessage("로그아웃 하시겠습니까?");
					gsDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener()
					{
						@Override
						public void onClick(DialogInterface arg0, int arg1)
						{
							// TODO Auto-generated method stub
						}
					});
					gsDialog.setPositiveButton("네", new DialogInterface.OnClickListener()
					{
						public void onClick(DialogInterface dialog, int which)
						{
							SharedPreferences.Editor localEditor = getActivity().getSharedPreferences("UserInfo", 0).edit(); // UserInfo
							localEditor.clear();
							localEditor.commit();
							Intent i = new Intent(MyPageFragment.this.getActivity(), LoginActivity.class);
							i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
							startActivity(i);
						}
					}).create().show();
				}
			}
		});
		fixBtn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				LayoutInflater layoutInflaterAndroid = LayoutInflater.from(context);
				View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
				AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(context);
				alertDialogBuilderUserInput.setView(mView);

				final EditText userInputDialogEditText = (EditText) mView.findViewById(R.id.userInputDialog);
				userInputDialogEditText.setText(mypage_name.getText().toString());
				alertDialogBuilderUserInput.setCancelable(false).setPositiveButton("적용", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialogBox, int id)
					{
						// ToDo get user input here
						new DB_ReplaceUserName(localEditor.getString("UserID", ""), userInputDialogEditText.getText().toString()).execute();
						SharedPreferences.Editor temp = localEditor.edit();
						temp.putString("UserName", userInputDialogEditText.getText().toString());
						temp.commit();
						mypage_name.setText(userInputDialogEditText.getText().toString());
					}
				})

						.setNegativeButton("취소", new DialogInterface.OnClickListener()
						{
							public void onClick(DialogInterface dialogBox, int id)
							{
								dialogBox.cancel();
							}
						});

				AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
				alertDialogAndroid.show();
			}
		});
		return view;
	}

	public void toasting(String temp)
	{
		Toast.makeText(getActivity().getApplicationContext(), temp, Toast.LENGTH_LONG).show();
	}
}