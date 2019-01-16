package com.with_tnt.myapplication3.login_signup.find_info;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.with_tnt.db_connection.login_signup.find_info.DB_FindPW;
import com.with_tnt.myapplication3.R;

/**
 * Created by KIM on 2017-05-02.
 */

public class Find_PW_Fragment extends Fragment
{
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;
    EditText findNameTxt;
    EditText findEmailTxt;
    EditText findIDTxt;
    Button findBtn;
    Context context;

    public static Find_PW_Fragment newInstance(int pageNo)
    {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        Find_PW_Fragment fragment = new Find_PW_Fragment();
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
        View view = inflater.inflate(R.layout.find_pw_page, container, false);
        findNameTxt = (EditText) view.findViewById(R.id.find_pw_name_Text);
        findEmailTxt = (EditText) view.findViewById(R.id.find_pw_email_Text);
        findIDTxt = (EditText) view.findViewById(R.id.find_pw_id_Text);
        findBtn = (Button) view.findViewById(R.id.find_pw_btn);
        context = this.getContext();
        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DB_FindPW(findNameTxt.getText().toString(), findEmailTxt.getText().toString(), findIDTxt.getText().toString(), context).execute();
            }
        });
        return view;
    }
}
