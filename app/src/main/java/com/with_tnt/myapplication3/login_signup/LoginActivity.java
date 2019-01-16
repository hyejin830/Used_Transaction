package com.with_tnt.myapplication3.login_signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.login_signup.DB_Login;
import com.with_tnt.myapplication3.login_signup.find_info.Find_Info_Activity;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.main.MainActivity;

/**
 * Created by ANGELZIN on 2017-04-12.
 */

public class LoginActivity extends AppCompatActivity
{
	Button loginBtn;
	EditText ID;
	public EditText PW;
	Context conLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{

		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.login);
		if (!getSharedPreferences("UserInfo", 0).getString("UserID", "").equals(""))
		{
			startActivity(new Intent(getApplicationContext(), MainActivity.class));
			finish();
		}
		// status bar 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		// Set up the login form.
		conLogin = this;
		loginBtn = (Button) findViewById(R.id.btn_login);
		ID = (EditText) findViewById(R.id.user_id_lg);
		PW = (EditText) findViewById(R.id.user_pw_lg);
		loginBtn.setOnClickListener(new View.OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				// TODO Auto-generated method stub
				// DB 연결
				DB_Login loginTask = new DB_Login(ID.getText().toString(), PW.getText().toString(), conLogin);
				loginTask.execute();
				// 키보드 내리기
				InputMethodManager mInputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mInputMethodManager.hideSoftInputFromWindow(ID.getWindowToken(), 0);
				// startActivity(new Intent(getApplicationContext(),
				// MainActivity.class));

				/*
				 * Intent i = new Intent(LoginActivity.this,
				 * MainActivity.class); startActivity(i);
				 */
			}
		});

		// 밑줄긋기
		String str_find = "아이디/비밀번호 찾기";
		TextView link_find = (TextView) findViewById(R.id.link_find);
		link_find.setText(Html.fromHtml("<u>" + str_find + "</u>"));

		link_find.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(LoginActivity.this, Find_Info_Activity.class));
			}
		});

		String str_join = "회원가입";
		TextView link_join = (TextView) findViewById(R.id.link_join);
		link_join.setText(Html.fromHtml("<u>" + str_join + "</u>"));

		link_join.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 다음 넘어가는거
				startActivity(new Intent(LoginActivity.this, JoinActivity.class));
			}
		});
	}

	public void successLogin(String nameOfUser)
	{
		SharedPreferences.Editor localEditor = getSharedPreferences("UserInfo", 0).edit(); // UserInfo
		localEditor.putString("UserID", ID.getText().toString()); // UserID 컬럼
		localEditor.putString("UserName", nameOfUser); // UserID 컬럼
		localEditor.commit();
		startActivity(new Intent(getApplicationContext(), MainActivity.class));
		finish();
	}

	public void toasting(String temp)
	{
		Toast.makeText(getApplicationContext(), temp, Toast.LENGTH_LONG).show();
	}
}