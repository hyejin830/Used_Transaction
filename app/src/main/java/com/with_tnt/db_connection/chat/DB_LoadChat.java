package com.with_tnt.db_connection.chat;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;

import com.with_tnt.db_connection.DB_Setting_Value;
import com.with_tnt.myapplication3.chat.ChattingActivity;
import com.with_tnt.myapplication3.chat.ChattingAdapater;
import com.with_tnt.myapplication3.chat.Chatting_Control;
import com.with_tnt.myapplication3.product.Product_Control;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import static com.with_tnt.myapplication3.chat.ChattingActivity.sender;

/**
 * Created by KIM on 2017-05-14.
 */

public class DB_LoadChat extends AsyncTask<String, String, String> {
    Context exCon;
    int productNumber;
    String messageSender;
    ChattingAdapater chatAdapter;
    ArrayList<String> messageList;
    ArrayList<Integer> whoList;

    public DB_LoadChat(Context exCon, int productNumber, String messageSender, ChattingAdapater chatAdapter) {
        // TODO Auto-generated constructor stub
        this.exCon = exCon;
        this.productNumber = productNumber;
        this.messageSender = messageSender;
        this.chatAdapter = chatAdapter;
        messageList = new ArrayList<String>();
        whoList= new ArrayList<Integer>();
    }

    @Override
    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub
        Connection conn = null; // Database ������ ���� ���ؼ� ��ü ����
        ResultSet rSet = null; // Query ����� �����ϴ� ��ü ����
        Statement stmt = null; // stmp �Ǵ� pstmp�� �����Ͽ� ����� �� �ִ�.
        try
        {
            Class.forName(DB_Setting_Value.Setting_Value.DBCLASS).newInstance();
            conn = DriverManager.getConnection(DB_Setting_Value.Setting_Value.SERVER_DB_IP, DB_Setting_Value.Setting_Value.DBID, DB_Setting_Value.Setting_Value.DBPW);
            stmt = conn.createStatement();
            String selectQuery = "SELECT Message, WhoSend FROM Chatting WHERE Product_Number = "+productNumber+" AND Sender = '"+messageSender+"'  ORDER BY Send_Date ASC;";
            Log.e("text", selectQuery);
            rSet = stmt.executeQuery(selectQuery);
            while (rSet.next())
            {
                int chatFlag = 0;
                if(Integer.parseInt(rSet.getString(2)) == 0)
                {
                    chatFlag = 1;
                }
                messageList.add(rSet.getString(1));
                whoList.add(chatFlag);

            }
        } catch (Exception e) {
            Log.i("TEXT", e.toString());
        } finally {
            try {
                if (rSet != null) {
                    rSet.close();
                }
            } catch (Exception e) {
                ;
            }
            ;
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (Exception e) {
                ;
            }
            ;
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                ;
            }
            ;
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        for(int i = 0; i < messageList.size(); i++)
        {
            chatAdapter.add(new Chatting_Control(messageList.get(i), whoList.get(i)));
        }
    }
}