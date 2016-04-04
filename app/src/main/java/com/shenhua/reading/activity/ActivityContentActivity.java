package com.shenhua.reading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;

import com.shenhua.reading.R;

public class ActivityContentActivity extends AppCompatActivity {

    private String url = "";
    private ProgressBar content_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        content_pro = (ProgressBar) findViewById(R.id.content_pro);
        Snackbar.make(content_pro, url, Snackbar.LENGTH_SHORT).show();
    }

}
