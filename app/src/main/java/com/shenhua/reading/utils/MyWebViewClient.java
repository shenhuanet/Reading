package com.shenhua.reading.utils;

import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

/**
 * Created by shenhua on 4/5/2016.
 */
public class MyWebViewClient extends WebViewClient {

    private ProgressBar progressBar;
    private TextView textView;

    public  MyWebViewClient(ProgressBar progressBar,TextView textView){
        this.progressBar = progressBar;
        this.textView =textView;
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//            if (progressBar!=null&&progressBar.getVisibility()== View.VISIBLE){
//
//            }
        progressBar.setVisibility(View.VISIBLE);
        textView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }

}
