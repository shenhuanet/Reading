package com.shenhua.reading.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.ClickEffectType;
import com.nightonke.boommenu.Types.DimType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.shenhua.reading.R;
import com.shenhua.reading.utils.MyStringUtils;
import com.shenhua.reading.utils.MyWebViewClient;

import java.util.Random;

public class ActivityContentActivity extends AppCompatActivity implements BoomMenuButton.OnSubButtonClickListener{

    private String url = "";
    private ProgressBar content_pro;
    private WebView webView;
    private TextView textView;

    private BoomMenuButton boomMenuButton;
    private boolean isInit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        textView.setText(url);
        System.out.println(url);
        webView.loadUrl(url);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.content_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityContentActivity.this.finish();
            }
        });
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boombtn);
        boomMenuButton.setOnSubButtonClickListener(this);
        boomMenuButton.setDimType(DimType.DIM_0);
        boomMenuButton.setDuration(500);
        boomMenuButton.setDelay(100);
        boomMenuButton.setRotateDegree(1 * 360);
        boomMenuButton.setAutoDismiss(false);
        boomMenuButton.setShowOrderType(OrderType.DEFAULT);
        boomMenuButton.setHideOrderType(OrderType.DEFAULT);
        boomMenuButton.setClickEffectType(ClickEffectType.RIPPLE);
        content_pro = (ProgressBar) findViewById(R.id.content_pro);
        webView = (WebView) findViewById(R.id.content_web);
        textView = (TextView) findViewById(R.id.content_info);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        webView.setDrawingCacheEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new MyWebViewClient(content_pro, textView));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(int buttonIndex) {
        Toast.makeText(this, "On click " +
                boomMenuButton.getTextViews()[buttonIndex].getText().toString() +
                " button", Toast.LENGTH_SHORT).show();
        if (boomMenuButton.isClosed() == false)
            boomMenuButton.dismiss();
    }

    public int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(MyStringUtils.RandomColors.length);
        return Color.parseColor(MyStringUtils.RandomColors[p]);
    }

    @Override
    public void onBackPressed() {
        if (boomMenuButton.isClosed()) {
            super.onBackPressed();
        } else {
            boomMenuButton.dismiss();
        }
    }

    private void initBoom() {
        int number = 4;
        Drawable[] drawables = new Drawable[number];
        int[] drawablesResource = new int[]{R.mipmap.ic_boom_copy, R.mipmap.ic_boom_like, R.mipmap.ic_boom_send, R.mipmap.ic_boom_top};
        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);
        String[] STRINGS = new String[]{"复制网址", "收藏到本地", "发送", "置顶"};
        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];
        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = GetRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }
        boomMenuButton.init(drawables, strings, colors, ButtonType.CIRCLE, BoomType.PARABOLA, PlaceType.CIRCLE_4_1, null, null, null, null, null, null, null);
        boomMenuButton.setSubButtonShadowOffset(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInit) {
            initBoom();
        }
        isInit = true;
    }
}
