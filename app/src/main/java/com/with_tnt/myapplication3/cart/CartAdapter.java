package com.with_tnt.myapplication3.cart;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.with_tnt.db_connection.cart.DB_DeleteCart;
import com.with_tnt.myapplication3.R;

import java.util.ArrayList;

/**
 * Created by ANGELZIN on 2017-05-09.
 */

public class CartAdapter extends ArrayAdapter<CartControl>
{
    private LayoutInflater mInflater;
    public ArrayList<CartControl> objectOfArray;
    private final  Context mContext;


    public CartAdapter(Context context, ArrayList<CartControl> objectOfArray)
    {
        super(context, 0, objectOfArray);
        mContext=context;
        this.objectOfArray = objectOfArray;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(final int position, View v, ViewGroup parent)
    {
        View view = null;
        if (v == null)
        {
            view = mInflater.inflate(R.layout.custom_cart_listview, null);
        }
        else
        {
            view = v;
        }
        final CartControl data = this.getItem(position);

        Resources res = mContext.getResources();
        String[] titles= res.getStringArray(R.array.data_spinner_catrgory);
        String[] states =res.getStringArray(R.array.data_spinner_state);

        if (data != null)
        {

            CheckBox titleCheckBox = (CheckBox) view.findViewById(R.id.subject);
            titleCheckBox.setText(data.getTitle());
            TextView categoryTextView = (TextView) view.findViewById(R.id.category);
            categoryTextView.setText(titles[data.getCategoryStr()]+"");
            TextView stateTextView = (TextView)view.findViewById(R.id.state);
            stateTextView.setText(states[data.getStateStr()]+"");
            TextView costTextView = (TextView)view.findViewById(R.id.cost);
            costTextView.setText(data.getCostStr()+"");

            Button deleteButton = (Button)view.findViewById(R.id.cart_delete);


            deleteButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    //Toast.makeText(con, String.valueOf(data.getProductNumber()) + "찜", Toast.LENGTH_SHORT).show();

                    AlertDialog.Builder gsDialog = new AlertDialog.Builder(mContext);
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
                            DB_DeleteCart deleteCartTask = new DB_DeleteCart(mContext,data.getProductNum(),mContext.getSharedPreferences("UserInfo", 0).getString("UserID", ""));
                            deleteCartTask.execute();
                        }
                    }).create().show();
                }
            });

        }
        return view;
    }
}
