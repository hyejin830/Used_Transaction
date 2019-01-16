package com.with_tnt.myapplication3.product;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.cart.DB_UploadCart;
import com.with_tnt.db_connection.product.DB_DeleteProduct;
import com.with_tnt.db_connection.product.DB_LoadData;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.chat.ChattingActivity;


public class ViewProductActivity extends AppCompatActivity {
	TextView category;
	TextView productName;
	TextView dealState;
	TextView date;
	TextView listPrice;
	TextView sellingPrice;
	TextView dicountRate;
	TextView contenttv;
	TextView hashTag;
	TextView sellerName;
	Context viewCon;
	Button deleteBtn;
	Button sendBtn;
	Button fixBtn;
	Button cartBtn;
	int temper;
	int code;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_product);

		viewCon = this;
		Intent get_meth = getIntent(); //값을 받아
		temper = get_meth.getExtras().getInt("TEXT"); //TEXT라는 이름을 가진애를 불러옴
		code = get_meth.getIntExtra("category", 0);
		new DB_LoadData(viewCon, temper).execute(); //DB_LoadData
		// Init
		category = (TextView) findViewById(R.id.tV_category);
		productName = (TextView) findViewById(R.id.tv_productName);
		dealState = (TextView) findViewById(R.id.tV_deallinsState);
		date = (TextView) findViewById(R.id.tV_date);
		listPrice = (TextView) findViewById(R.id.tV_listPrice);
		sellingPrice = (TextView) findViewById(R.id.tV_sellingPrice);
		dicountRate = (TextView) findViewById(R.id.tV_discountRate);
		sellerName = (TextView) findViewById(R.id.tV_sellerName);
		contenttv = (TextView) findViewById(R.id.tV_content);
		hashTag = (TextView) findViewById(R.id.tV_hashtag);
		deleteBtn = (Button) findViewById(R.id.button_del);
		cartBtn = (Button) findViewById(R.id.btn_zzim);
		cartBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AlertDialog.Builder gsDialog = new AlertDialog.Builder(viewCon);
				gsDialog.setMessage("장바구니에 추가 하시겠어요?");
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
						DB_UploadCart chartuploadTask = new DB_UploadCart(getSharedPreferences("UserInfo", 0).getString("UserID", ""), temper, viewCon);
						chartuploadTask.execute();
						Toast.makeText(viewCon, "장바구니에 추가되었습니다", Toast.LENGTH_SHORT).show();
					}
				}).create().show();
			}
		});
		fixBtn = (Button) findViewById(R.id.button_fix);
		sendBtn = (Button) findViewById(R.id.btn_sendMessege);
		sendBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(getApplicationContext(), ChattingActivity.class).putExtra("NUMBER", temper));
			}
		});
		fixBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {  // ! 이부분 추가됐어 !
				//Toast.makeText(getApplicationContext(), "수정 추가해주세요~", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(ViewProductActivity.this, ModifyProductActivity.class);
				intent.putExtra("pnum", temper);
				startActivity(intent);
			}
		});
		deleteBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// product_Name, product_Information, product_Cost,  product_Cost_Origin,  product_Condition,  ID,  sendBack,  upCon
				AlertDialog.Builder gsDialog = new AlertDialog.Builder(viewCon);
				gsDialog.setMessage("삭제 하시겠습니까?");
				gsDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener()
				{

					@Override
					public void onClick(DialogInterface arg0, int arg1)
					{
						//아무일도없었다.
					}
				});
				gsDialog.setPositiveButton("네", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						DB_DeleteProduct deleteTask = new DB_DeleteProduct(viewCon, temper);
						deleteTask.execute();
					}
				}).create().show();
			}
		});



	}
	public  void updateList(int categoty, int productNumber,String productName,int dealState,String uploadDate,int listPrice,int sellingPrice,int product_state, String seller, String contents, String hashTag)
	{
		float lp,sp,r;
		int ds;

		Resources res = getResources();
		String[] titles= res.getStringArray(R.array.data_spinner_catrgory);
		this.category.setText(titles[categoty]);
		this.productName.setText(productName);
		if(dealState==0)
			this.dealState.setText("판매중");
		else if(dealState==1)
			this.dealState.setText("거래중");
		else
			this.dealState.setText("거래 완료");
		this.date.setText(uploadDate);
		this.listPrice.setText(String.valueOf(listPrice));
		this.sellingPrice.setText(String.valueOf(sellingPrice));
		lp = listPrice;
		sp = sellingPrice;
		r = sp / lp;
		r = r * 100;
		ds = (int)r;
		ds = 100 - ds;
		this.dicountRate.setText("("+ds+"%)");

		this.contenttv.setText(contents);
		this.hashTag.setText(hashTag);

		//상품상태 처리 안함
		this.sellerName.setText(seller);
		if(sellerName.getText().equals(getSharedPreferences("UserInfo", 0).getString("UserID", "")))
		{
			deleteBtn.setVisibility(View.VISIBLE);
			fixBtn.setVisibility(View.VISIBLE);
			sendBtn.setVisibility(View.GONE);
		}
	}

	public void deleteData()
	{
		Toast.makeText(getApplicationContext(), "삭제 되었습니다!", Toast.LENGTH_LONG).show();
		if(code==0)
			setResult(1);
		else
			setResult(2);
		finish();
	}



	@Override
	public void onBackPressed() {
		if(code==0)
			setResult(1);
		else
			setResult(2);
		finish();
	}



}