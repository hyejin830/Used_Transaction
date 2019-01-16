package com.with_tnt.myapplication3.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.iid.FirebaseInstanceId;
import com.with_tnt.db_connection.push.DB_SetToken;
import com.with_tnt.myapplication3.R;
import com.with_tnt.myapplication3.product.ProductlistActivity;
// 하위 탭 , 액션 바

public class MainActivity extends AppCompatActivity {
    private TabLayout mTabLayout;

    private int[] mTabsIcons =
            {R.drawable.home, R.drawable.category, R.drawable.noticebell, R.drawable.mypage, R.drawable.layer3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // status bar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        if (mTabLayout != null) {
            mTabLayout.setupWithViewPager(viewPager);

            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }
            mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000")); // 탭
            // 선택
            // 색
            // 설정
            mTabLayout.getTabAt(0).getCustomView().setSelected(true);
            // FirebaseInstanceId.getInstance().getToken().toString()
            new DB_SetToken(getSharedPreferences("UserInfo", 0).getString("UserID", ""), FirebaseInstanceId.getInstance().getToken().toString()).execute();
        }
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public final int PAGE_COUNT = 5;

        private final String[] mTabsTitle =
                {"홈", "카테고리", "알림", "마이 페이지", "더보기"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public View getTabView(int position) {
            // Given you have a custom layout in `res/layout/custom_tab.xml`
            // with a TextView and ImageView
            View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.custom_tab, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            ImageView icon = (ImageView) view.findViewById(R.id.icon);
            icon.setImageResource(mTabsIcons[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos) {
            switch (pos) {

                case 0:
                    return MainFragment.newInstance(1); // 메인 페이지 MainFragment.java
                case 1:
                    return CategoryFragment.newInstance(2);
                case 2:
                    return PageFragment.newInstance(3);
                case 3:
                    return MyPageFragment.newInstance(4);
                case 4:
                    return MoreFragment.newInstance(5);

            }
            return null;
        }

        @Override
        public int getCount() {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabsTitle[position];
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) { // 액션 바
        ActionBar actionBar = getSupportActionBar();

        // Custom Actionbar를 사용하기 위해 CustomEnabled을 true 시키고 필요 없는 것은 false 시킨다
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false); // 액션바 아이콘을 업 네비게이션 형태로
        // 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false); // 액션바에 표시되는 제목의 표시유무를
        // 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false); // 홈 아이콘을 숨김처리합니다.

        // layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);
        final EditText temp2 = (EditText) findViewById(R.id.actionbar_text);
        Button temp = (Button) findViewById(R.id.actionbar_clear);
        temp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                temp2.setText("");
            }
        });

        // 액션바 양쪽 공백 없애기
        Toolbar parent = (Toolbar) actionbar.getParent();
        parent.setContentInsetsAbsolute(0, 0);


        Button searchBtn = (Button) findViewById(R.id.button_search);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ProductlistActivity.class);
                intent.putExtra("word", temp2.getText().toString());
                startActivity(intent);

            }
        });

        return true;
    }

}
