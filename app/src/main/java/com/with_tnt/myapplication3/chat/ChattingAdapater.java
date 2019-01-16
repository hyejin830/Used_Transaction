package com.with_tnt.myapplication3.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.with_tnt.myapplication3.R;

import java.util.ArrayList;

public class ChattingAdapater extends ArrayAdapter<Chatting_Control> {
    private LayoutInflater mInflater;
    ArrayList<Chatting_Control> objectOfArray;

    public ChattingAdapater(Context context, ArrayList<Chatting_Control> objectOfArray) {
        super(context, 0, objectOfArray);
        this.objectOfArray = objectOfArray;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent) {
        View view = null;
        final Chatting_Control data = this.getItem(position);

        if (data != null) {
            if (data.getAlign() == 0) {
                view = mInflater.inflate(R.layout.custom_chat_listview_right, null);
            } else {
                view = mInflater.inflate(R.layout.custom_chat_listview_left, null);
            }
        }

        if (data != null) {
            if (data.getAlign() == 0) {
                TextView numberT = (TextView) view.findViewById(R.id.send_message_text);
                numberT.setText(data.getNumberOfInquiry());
                numberT.setBackground(this.getContext().getResources().getDrawable(R.drawable.bg_talk));
            } else {
                TextView numberText = (TextView) view.findViewById(R.id.receive_message_text);
                numberText.setText(data.getNumberOfInquiry());
                numberText.setBackground(this.getContext().getResources().getDrawable(R.drawable.bg_talk));
            }
        }
        return view;
    }
}
