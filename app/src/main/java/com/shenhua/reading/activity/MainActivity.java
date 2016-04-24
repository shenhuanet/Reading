package com.shenhua.reading.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.ClickEffectType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
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
import com.shenhua.reading.fragment.FragmentOpen;
import com.shenhua.reading.fragment.FragmentSegf;
import com.shenhua.reading.fragment.FragmentTuiku;
import com.shenhua.reading.utils.MyStringUtils;

public class MainActivity extends AppCompatActivity {

    private Fragment[] fragments = {FragmentHome.newInstance(), FragmentCSDN.newInstance(), FragmentSegf.newInstance(),
            FragmentJcode.newInstance(), FragmentTuiku.newInstance(), FragmentHonghei.newInstance(), FragmentKaiyuan.newInstance(),
            FragmentKaifazhe.newInstance(), FragmentKanyuanDaima.newInstance(), FragmentKanyuanZujian.newInstance(), FragmentOpen.newInstance()};
    private BoomMenuButton boomInfo;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initViewPager();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setLogo(ContextCompat.getDrawable(this, R.mipmap.ic_launcher));
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.main_toolbar, null);
        TextView mTitleTextView = (TextView) mCustomView.findViewById(R.id.title_text);
        mTitleTextView.setText(R.string.app_name);
        boomInfo = (BoomMenuButton) mCustomView.findViewById(R.id.info);
        boomInfo.setOnSubButtonClickListener(new BoomMenuButton.OnSubButtonClickListener() {
            @Override
            public void onClick(int buttonIndex) {
                if (buttonIndex == 0) {
                    Toast.makeText(MainActivity.this, "关于", Toast.LENGTH_SHORT).show();
                } else if (buttonIndex == 1) {
                    Toast.makeText(MainActivity.this, "设置", Toast.LENGTH_SHORT).show();
                } else if (buttonIndex == 2) {
                    Toast.makeText(MainActivity.this, "分享", Toast.LENGTH_SHORT).show();
                }
            }
        });
        boomInfo.setClickEffectType(ClickEffectType.RIPPLE);
        Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.MATCH_PARENT, Toolbar.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
        mCustomView.setLayoutParams(params);
        toolbar.addView(mCustomView);
    }

    private void initViewPager() {
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
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInit) {
            initInfoBoom();
        }
        isInit = true;
    }

    private void initInfoBoom() {
        Drawable[] drawables = new Drawable[3];
        int[] drawablesResource = new int[]{R.mipmap.ic_boom_about, R.mipmap.ic_boom_setting, R.mipmap.ic_boom_share};
        for (int i = 0; i < 3; i++)
            drawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);
        int[][] colors = new int[3][2];
        for (int i = 0; i < 3; i++) {
            colors[i][1] = ContextCompat.getColor(this, R.color.colorMaterialWhite);
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }
        boomInfo.init(drawables, new String[]{"关于", "设置", "分享"}, colors, ButtonType.HAM, BoomType.PARABOLA, PlaceType.HAM_3_1, null, null, null, null, null, null, null);
        boomInfo.setSubButtonShadowOffset(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2));
        boomInfo.setTextViewColor(Color.BLACK);
        boomInfo.setBoomType(BoomType.PARABOLA_2);
    }

    @Override
    public void onBackPressed() {
        if (boomInfo.isClosed()) {
            super.onBackPressed();
        } else {
            boomInfo.dismiss();
        }
    }

}
