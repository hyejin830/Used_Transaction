package com.with_tnt.db_connection.push;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.with_tnt.myapplication3.chat.ChattingActivity;

import static com.with_tnt.myapplication3.chat.ChattingActivity.chatCon;

/**
 * Created by KIM on 2017-05-12.
 */

public class ReceiveMessage extends AsyncTask<String, String, String> {
    Context chatCon;
    String message;

    public ReceiveMessage(Context chatCon, String message) {
        this.chatCon = chatCon;
        this.message = message;
    }
    protected String doInBackground(String... arg0) {
        // TODO Auto-generated method stub

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        Log.e("text", "received : " + message);
        ((ChattingActivity) chatCon).receiveMessage(message);
    }
}
