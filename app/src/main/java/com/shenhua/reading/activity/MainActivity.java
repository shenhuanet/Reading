package com.shenhua.reading.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.shenhua.reading.R;
import com.shenhua.reading.adapter.MyViewPagerAdapter;
import com.shenhua.reading.fragment.FragmentCSDN;
import com.shenhua.reading.fragment.FragmentHome;
import com.shenhua.reading.fragment.FragmentHonghei;
import com.shenhua.reading.fragment.FragmentKaifazhe;
import com.shenhua.reading.fragment.FragmentKaiyuan;
import com.shenhua.reading.fragment.FragmentKanyuanDaima;
import com.shenhua.reading.fragment.FragmentKanyuanZujian;
import com.shenhua.reading.fragment.FragmentSegf;
import com.shenhua.reading.fragment.FragmentTuiku;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(FragmentHome.newInstance(), getString(R.string.tab_home));//添加Fragment
        viewPagerAdapter.addFragment(FragmentCSDN.newInstance(), getString(R.string.tab_csdn));
        viewPagerAdapter.addFragment(FragmentSegf.newInstance(), getString(R.string.tab_segf));
        viewPagerAdapter.addFragment(FragmentTuiku.newInstance(), getString(R.string.tab_tuiku));
        viewPagerAdapter.addFragment(FragmentHonghei.newInstance(), getString(R.string.tab_honghei));
        viewPagerAdapter.addFragment(FragmentKaiyuan.newInstance(), getString(R.string.tab_kaiyuan));
        viewPagerAdapter.addFragment(FragmentKaifazhe.newInstance(), getString(R.string.tab_kaifazhe));
        viewPagerAdapter.addFragment(FragmentKanyuanDaima.newInstance(), getString(R.string.tab_kanyuan_daima));
        viewPagerAdapter.addFragment(FragmentKanyuanZujian.newInstance(), getString(R.string.tab_kanyuan_zujian));
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器

        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout_top);
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_home)));//给TabLayout添加Tab
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_csdn)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_segf)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_tuiku)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_honghei)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_kaiyuan)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_kaifazhe)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_kanyuan_daima)));
        mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.tab_kanyuan_zujian)));
        mTabLayout.setSelectedTabIndicatorHeight(4);
        mTabLayout.setupWithViewPager(mViewPager);//给TabLayout设置关联ViewPager，如果设置了ViewPager，那么ViewPagerAdapter中的getPageTitle()方法返回的就是Tab上的标题
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
