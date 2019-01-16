package com.with_tnt.myapplication3.login_signup.find_info;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.with_tnt.myapplication3.R;


public class Find_Info_Activity extends AppCompatActivity {
    TabLayout mTabLayout;
    private int[] mTabsIcons =
            { R.drawable.home, R.drawable.category, R.drawable.noticebell, R.drawable.mypage, R.drawable.layer3 };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find__info_);

        // status bar 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ViewPager viewPager = (ViewPager) findViewById(R.id.info_view_pager);
        Find_Info_Activity.MyPagerAdapter2 pagerAdapter = new Find_Info_Activity.MyPagerAdapter2(getSupportFragmentManager());
        if (viewPager != null)
            viewPager.setAdapter(pagerAdapter);
        mTabLayout = (TabLayout) findViewById(R.id.info_tab_layout);
        if (mTabLayout != null)
        {
            mTabLayout.setupWithViewPager(viewPager);
            Log.e("text", String.valueOf(mTabLayout.getTabCount()));
            for (int i = 0; i < mTabLayout.getTabCount(); i++) {
                TabLayout.Tab tab = mTabLayout.getTabAt(i);
                if (tab != null)
                    tab.setCustomView(pagerAdapter.getTabView(i));
            }
            mTabLayout.setSelectedTabIndicatorColor(Color.parseColor("#000000")); // 탭
            //mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        }
    }

    public void findSuccess(String foundID)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", foundID);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "클립보드에 아이디가 복사되었습니다!", Toast.LENGTH_SHORT).show();
    }

    public void errorToFind()
    {
        Toast.makeText(getApplicationContext(), "이름과 이메일을 확인해주세요!", Toast.LENGTH_SHORT).show();
    }

    public void findPWSuccess(String foundID)
    {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied", foundID);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(), "클립보드에 비밀번호가 복사되었습니다!", Toast.LENGTH_SHORT).show();
    }

    public void errorPWFind()
    {
        Toast.makeText(getApplicationContext(), "이름, 이메일, 아이디를 확인해주세요!", Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter2 extends FragmentPagerAdapter
    {

        public final int PAGE_COUNT = 2;

        private final String[] mTabsTitle =
                { "아이디 찾기", "비밀번호 찾기"};

        public MyPagerAdapter2(FragmentManager fm)
        {
            super(fm);
        }

        public View getTabView(int position)
        {
            // Given you have a custom layout in `res/layout/custom_tab.xml`
            // with a TextView and ImageView

            View view = LayoutInflater.from(Find_Info_Activity.this).inflate(R.layout.custom_tab_info, null);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(mTabsTitle[position]);
            return view;
        }

        @Override
        public Fragment getItem(int pos)
        {
            switch (pos)
            {
                case 0:
                    return Find_ID_Fragment.newInstance(1); // 메인 페이지 MainFragment.java
                case 1:
                    return Find_PW_Fragment.newInstance(2);
            }
            return null;
        }

        @Override
        public int getCount()
        {
            return PAGE_COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position)
        {
            return mTabsTitle[position];
        }
    }

}


