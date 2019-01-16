package com.with_tnt.myapplication3.cart;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.cart.DB_LoadCart;
import com.with_tnt.myapplication3.R;

import java.util.ArrayList;

/**
 * Created by ANGELZIN on 2017-05-09.
 */

public class CartActivity extends AppCompatActivity {
    ListView userHistoryList;
    ArrayList<CartControl> aList;
    public  CartAdapter adapterOfHistory;
    Context showCon;
    String userData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.common_custom_actionbar);

        TextView tt = (TextView) findViewById(R.id.act_text);
        tt.setText("장바구니");

        userData = getSharedPreferences("UserInfo", 0).getString("UserID", "");
        Log.i("혜진", userData);
        userHistoryList = (ListView) findViewById(R.id.cart_list);
        showCon = this;
        aList = new ArrayList<CartControl>();
        adapterOfHistory = new CartAdapter(this, aList);
        new DB_LoadCart(showCon, adapterOfHistory, userData).execute();
    } // end onCreate()

    public void updateList() {
        userHistoryList.setAdapter(adapterOfHistory);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            aList = new ArrayList<CartControl>();
            adapterOfHistory = new CartAdapter(this, aList);
            new DB_LoadCart(showCon, adapterOfHistory, userData).execute();
            adapterOfHistory.notifyDataSetChanged();
        }
    }

    public void deleteData() {
        Toast.makeText(getApplicationContext(), "삭제 되었습니다!", Toast.LENGTH_LONG).show();

        adapterOfHistory.clear();
        userHistoryList.setAdapter(null);
        aList = new ArrayList<CartControl>();
        adapterOfHistory = new CartAdapter(this, aList);
        new DB_LoadCart(showCon, adapterOfHistory, userData).execute();

    }

    @Override
    public void onBackPressed() {
        setResult(1);
        finish();
    }

}