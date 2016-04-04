package com.shenhua.reading.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
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
import com.shenhua.reading.fragment.FragmentJcode;
import com.shenhua.reading.fragment.FragmentKaifazhe;
import com.shenhua.reading.fragment.FragmentKaiyuan;
import com.shenhua.reading.fragment.FragmentKanyuanDaima;
import com.shenhua.reading.fragment.FragmentKanyuanZujian;
import com.shenhua.reading.fragment.FragmentSegf;
import com.shenhua.reading.fragment.FragmentTuiku;
import com.shenhua.reading.utils.MyStringUtils;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments = {FragmentHome.newInstance(), FragmentCSDN.newInstance(), FragmentSegf.newInstance(),
            FragmentJcode.newInstance(), FragmentTuiku.newInstance(), FragmentHonghei.newInstance(), FragmentKaiyuan.newInstance(),
            FragmentKaifazhe.newInstance(), FragmentKanyuanDaima.newInstance(), FragmentKanyuanZujian.newInstance()};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(getResources().getDrawable(R.mipmap.ic_launcher));

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        TabLayout mTabLayout = (TabLayout) findViewById(R.id.tabLayout_top);
        mTabLayout.setSelectedTabIndicatorHeight(4);
        MyViewPagerAdapter viewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        for (int i = 0; i < fragments.length; i++) {
            viewPagerAdapter.addFragment(fragments[i], MyStringUtils.tab_name_array[i]);
            mTabLayout.addTab(mTabLayout.newTab().setText(MyStringUtils.tab_name_array[i]));
        }
        mViewPager.setAdapter(viewPagerAdapter);//设置适配器
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
