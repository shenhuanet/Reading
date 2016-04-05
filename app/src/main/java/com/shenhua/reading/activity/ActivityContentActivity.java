package com.shenhua.reading.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.shenhua.reading.R;
import com.shenhua.reading.utils.MyWebViewClient;

public class ActivityContentActivity extends AppCompatActivity {

    private String url = "";
    private ProgressBar content_pro, content_progressbar;
    private WebView webView;
    private TextView textView;

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
        content_pro = (ProgressBar) findViewById(R.id.content_pro);
        content_progressbar = (ProgressBar) findViewById(R.id.content_progressbar);
        webView = (WebView) findViewById(R.id.content_web);
        textView = (TextView) findViewById(R.id.content_info);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        webView.setDrawingCacheEnabled(true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setWebViewClient(new MyWebViewClient(content_pro, textView));
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100)
                    content_progressbar.setVisibility(View.GONE);
                else {
                    if (View.GONE == content_progressbar.getVisibility())
                        content_progressbar.setVisibility(View.VISIBLE);
                    content_progressbar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
