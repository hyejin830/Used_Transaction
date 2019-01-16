package com.with_tnt.myapplication3.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.with_tnt.myapplication3.product.ProductlistActivity;
import com.with_tnt.myapplication3.R;

/**
 * Created by ANGELZIN on 2017-04-29.
 */

public class CategoryFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";
    private int mPageNo;

    CustomCategoryButton bookBtn;
    CustomCategoryButton wearBtn;
    CustomCategoryButton dailySuppliesBtn;
    CustomCategoryButton electronicsBtn;
    CustomCategoryButton beautyBtn;
    CustomCategoryButton sportsBtn;
    CustomCategoryButton tiketBtn;
    CustomCategoryButton stationeryBtn;
    CustomCategoryButton etcBtn;

    public static CategoryFragment newInstance(int pageNo) {

        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNo);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNo = getArguments().getInt(ARG_PAGE);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_category_fragment, container, false);

        bookBtn = (CustomCategoryButton) view.findViewById(R.id.btn_book);
        wearBtn = (CustomCategoryButton) view.findViewById(R.id.btn_wear);
        dailySuppliesBtn = (CustomCategoryButton) view.findViewById(R.id.btn_dailySupplies);
        electronicsBtn = (CustomCategoryButton) view.findViewById(R.id.btn_electronics);
        beautyBtn = (CustomCategoryButton) view.findViewById(R.id.btn_beauty);
        sportsBtn = (CustomCategoryButton) view.findViewById(R.id.btn_sports);
        tiketBtn = (CustomCategoryButton) view.findViewById(R.id.btn_tiket);
        stationeryBtn = (CustomCategoryButton) view.findViewById(R.id.btn_stationery);
        etcBtn = (CustomCategoryButton) view.findViewById(R.id.btn_etc);


        bookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 1);
                startActivity(intent);
            }
        });

        wearBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 2);
                startActivity(intent);
            }
        });

        dailySuppliesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 3);
                startActivity(intent);
            }
        });

        electronicsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 4);
                startActivity(intent);
            }
        });

        beautyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 5);
                startActivity(intent);
            }
        });

        sportsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 6);
                startActivity(intent);
            }
        });

        tiketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 7);
                startActivity(intent);
            }
        });

        stationeryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 8);
                startActivity(intent);
            }
        });

        etcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryFragment.this.getActivity(),ProductlistActivity.class);
                intent.putExtra("code", 9);
                startActivity(intent);
            }
        });

        return view;
    }

    public void toasting(String temp)
    {
        Toast.makeText(getActivity().getApplicationContext(), temp, Toast.LENGTH_LONG).show();
    }
}