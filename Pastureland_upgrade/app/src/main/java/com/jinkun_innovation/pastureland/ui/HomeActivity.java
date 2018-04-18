package com.jinkun_innovation.pastureland.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;

import com.baidu.mapapi.SDKInitializer;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.ui.fragment.ManagerFragment;
import com.jinkun_innovation.pastureland.ui.fragment.MuqunFragment;
import com.jinkun_innovation.pastureland.ui.fragment.RenlingFragment1;
import com.jinkun_innovation.pastureland.ui.fragment.WodeFragment;
import com.jinkun_innovation.pastureland.utilcode.AppManager;

/**
 * Created by Guan on 2018/3/14.
 */

public class HomeActivity extends AppCompatActivity {

    public ViewPager viewPager;
    public TabLayout mTabLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_home);

        AppManager.getAppManager().addActivity(this);


        //Fragment+ViewPager+FragmentViewPager组合的使用
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
                this);
        viewPager.setAdapter(adapter);

        //TabLayout
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mTabLayout.setupWithViewPager(viewPager);
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


        mTabLayout.getTabAt(0).setIcon(R.drawable.manage_selector);
        mTabLayout.getTabAt(1).setIcon(R.drawable.muqun_selector);
        mTabLayout.getTabAt(2).setIcon(R.drawable.claim_selector);
        mTabLayout.getTabAt(3).setIcon(R.drawable.my_selector);

//        tabLayout.getTabAt(0).getCustomView().setSelected(true);


    }

    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public final int COUNT = 4;
        private String[] titles = new String[]{"管理", "牧群", "认领", "我的"};
        private SparseArray<Fragment> fragmentMap;

        private Context context;

        public MyFragmentPagerAdapter(FragmentManager fm, Context context) {
            super(fm);
            this.context = context;


            if (fragmentMap == null) {
                fragmentMap = new SparseArray();
                fragmentMap.put(0, new ManagerFragment());
                fragmentMap.put(1, new MuqunFragment());
                fragmentMap.put(2, new RenlingFragment1());
                fragmentMap.put(3, new WodeFragment());

            }

        }

        @Override
        public Fragment getItem(int position) {
            return fragmentMap.get(position);
        }

        @Override
        public int getCount() {
            return COUNT;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }


    }


}
