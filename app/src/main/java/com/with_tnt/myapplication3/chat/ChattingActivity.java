package com.with_tnt.myapplication3.chat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.db_connection.chat.DB_Chat_LoadProduct;
import com.with_tnt.db_connection.chat.DB_LoadChat;
import com.with_tnt.db_connection.product.DB_DoneSelling;
import com.with_tnt.db_connection.push.DB_SendMessage;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.login_signup.LoginActivity;
import com.with_tnt.myapplication3.main.MyPageFragment;
import com.with_tnt.myapplication3.product.ViewProductActivity;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class ChattingActivity extends AppCompatActivity {
    public static int productNumber;
    public static String sender;
    public String uploderName;
    public TextView producNametTxt;
    public TextView productPriceTxt;
    public TextView uploadDateTxt;
    public TextView uploaderTxt;
    public ImageButton productImageBtn;
    Button sendBtn;
    EditText messageTxt;
    ImageButton moreBtn;
    ImageButton sendImageBtn;
    ImageButton okBtn;
    ImageButton cancelBtn;
    LinearLayout option_panel;
    public static Context chatCon;
    public ListView messageList;
    ArrayList<Chatting_Control> aList;
    ChattingAdapater adapterOfList;
    boolean shown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        chatCon = this;
        Intent intent = getIntent();
        aList = new ArrayList<Chatting_Control>();
        adapterOfList = new ChattingAdapater(this, aList);
        messageList = (ListView) findViewById(R.id.chatting_middle_list);
        messageTxt = (EditText) findViewById(R.id.send_text);
        sendBtn = (Button) findViewById(R.id.send_button);
        option_panel = (LinearLayout) findViewById(R.id.chatting_option);
        productNumber = intent.getIntExtra("NUMBER", 0);
        sender = intent.getStringExtra("SENDER");
        Log.e("text", "Sender is " + sender);
        producNametTxt = (TextView) findViewById(R.id.product_tittle_txt);
        productPriceTxt = (TextView) findViewById(R.id.product_price_txt);
        uploadDateTxt = (TextView) findViewById(R.id.product_uploaddate_txt);
        uploaderTxt = (TextView) findViewById(R.id.product_uploader_txt);
        moreBtn = (ImageButton) findViewById(R.id.more_button);
        productImageBtn = (ImageButton) findViewById(R.id.chatting_product_button);
        cancelBtn = (ImageButton) findViewById(R.id.cancel_button);
        sendImageBtn = (ImageButton) findViewById(R.id.send_image_button);
        okBtn = (ImageButton) findViewById(R.id.ok_button);
        productImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ViewProductActivity.class).putExtra("TEXT", productNumber));
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Layout();
            }
        });
        DB_Chat_LoadProduct loadData = new DB_Chat_LoadProduct(chatCon, productNumber);
        loadData.execute();
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show_Layout();
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() { // 거래 완료 버튼
            @Override
            public void onClick(View v) {
                AlertDialog.Builder gsDialog = new AlertDialog.Builder(chatCon);
                gsDialog.setMessage("거래를 완료하겠습니까?");
                gsDialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
                });
                gsDialog.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        new DB_DoneSelling(chatCon, productNumber, getSharedPreferences("UserInfo", 0).getString("UserID", "")).execute();
                    }
                }).create().show();
            }
        });
        messageList.setAdapter(adapterOfList);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!uploaderTxt.getText().toString().equals(getSharedPreferences("UserInfo", 0).getString("UserID", "")))
                {
                    Log.e("text", "여긴가?");
                    new DB_SendMessage(String.valueOf(productNumber), getSharedPreferences("UserInfo", 0).getString("UserID", ""), uploaderTxt.getText().toString(), messageTxt.getText().toString(), 0).execute();
                }
                else{
                    Log.e("text", "여기로 떨어지나?");
                    Log.e("text", "여기 : "+ sender);
                    new DB_SendMessage(String.valueOf(productNumber), sender, getSharedPreferences("UserInfo", 0).getString("UserID", ""), messageTxt.getText().toString(), 1).execute();
                }
                sendingMessage(messageTxt.getText().toString());

                messageTxt.setText("");
            }
        });
    }

    public void show_Layout() {
        if (shown) {
            shown = false;
            Animation animation = new AlphaAnimation(1, 0);
            animation.setDuration(500);
            option_panel.setVisibility(View.INVISIBLE);
            option_panel.setAnimation(animation);
        } else {
            shown = true;
            Animation animation = new AlphaAnimation(0, 1);
            animation.setDuration(500);
            option_panel.setVisibility(View.VISIBLE);
            option_panel.setAnimation(animation);
        }
    }

    public void doneSelling() {
        Toast.makeText(getApplicationContext(), "거래가 성사되었습니다!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void receiveMessage(String receiveMessage) {
        Log.e("text", "receive : " + receiveMessage);
        adapterOfList.add(new Chatting_Control(receiveMessage, 1));
    }

    public void sendingMessage(String sendingMessage) {
        Log.e("text", "send : " + sendingMessage);
        adapterOfList.add(new Chatting_Control(sendingMessage,  0));
    }

    public void loadChatting()
    {
        if(!uploaderTxt.getText().toString().equals(getSharedPreferences("UserInfo", 0).getString("UserID", "")))
        {
            Log.e("text", "여긴가?");
            new DB_LoadChat(chatCon, productNumber, uploaderTxt.getText().toString(), adapterOfList).execute();
        }
        else{
            Log.e("text", "여긴ㄴㄴㄴㄴㄴㄴㄴ가?");
            new DB_LoadChat(chatCon, productNumber, sender, adapterOfList).execute();
        }
    }
}
//adapterOfInquiry.add(new Inquiry_Control(temp, 1));
//   messageList.setAdapter(adapterOfInquiry);
//    messageList.setOnItemClickListener(new AdapterView.OnItemClickListener()
//     {

//@Override
//public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//    {
// TODO Auto-generated method stub

//  }
//});