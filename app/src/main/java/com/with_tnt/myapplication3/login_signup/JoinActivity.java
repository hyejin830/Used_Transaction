package com.with_tnt.myapplication3.login_signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.login_signup.DB_Signup;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.main.MainActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ANGELZIN on 2017-04-12.
 */

public class JoinActivity extends Activity
{
	Context signCon;
	EditText eUser_Id, eUser_Pw, eUser_Pw_Confirm, eUser_Name, eUser_Email;
	Button bSignup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.join);

		// 상태바 없애기
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		signCon = this;
		eUser_Id = (EditText) findViewById(R.id.user_id);
		eUser_Pw = (EditText) findViewById(R.id.user_pw);
		eUser_Pw_Confirm = (EditText) findViewById(R.id.user_pw_chk);
		eUser_Name = (EditText) findViewById(R.id.user_name);
		eUser_Email = (EditText) findViewById(R.id.user_email);

		bSignup = (Button) findViewById(R.id.btn_join);
		bSignup.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (eUser_Id.getText().toString().length() == 0)
				{
					Toast.makeText(signCon, "아이디를 입력해주세요.", 1000).show();
				}
				else if (eUser_Pw.getText().toString().length() == 0)
				{
					Toast.makeText(signCon, "비밀번호를 입력해주세요.", 1000).show();
				}
				else if (!eUser_Pw.getText().toString().equals(eUser_Pw_Confirm.getText().toString()))
				{
					Toast.makeText(signCon, "비밀번호가 일치하지 않습니다.", 1000).show();
				}
				else if (eUser_Name.getText().toString().length() == 0)
				{
					Toast.makeText(signCon, "이름을 입력해주세요.", 1000).show();
				}
				else if (eUser_Email.getText().toString().length() == 0)
				{
					Toast.makeText(signCon, "이메일을 입력해주세요.", 1000).show();
				}
				else if (!checkEmail(eUser_Email.getText().toString()))
				{
					Toast.makeText(signCon, "이메일 형식을 확인해주세요.", 1000).show();
				}
				else
				{
					DB_Signup loginTask = new DB_Signup(eUser_Id.getText().toString(), eUser_Pw.getText().toString(), eUser_Name.getText().toString(), eUser_Email.getText().toString(), signCon);
					loginTask.execute();
				}
			}
		});

		// 로그인 밑줄긋는 소스
		String txt_login = "로그인";
		TextView link_login = (TextView) findViewById(R.id.link_login);
		link_login.setText(Html.fromHtml("<u>" + txt_login + "</u>"));

		link_login.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				startActivity(new Intent(JoinActivity.this, LoginActivity.class));
			}
		});
	}

	public boolean checkEmail(String email)
	{
		String regex = "^[_a-zA-Z0-9-\\.]+@[\\.a-zA-Z0-9-]+\\.[a-zA-Z]+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);
		boolean isNormal = m.matches();
		return isNormal;
	}

	public void successSignUp(String nameOfUser)
	{
		// TODO Auto-generated method stub
		SharedPreferences.Editor localEditor = getSharedPreferences("UserInfo", 0).edit(); // UserInfo
		localEditor.putString("UserID", eUser_Id.getText().toString()); // UserID
																		// 컬럼
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
