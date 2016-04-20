package com.shenhua.reading.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.shenhua.reading.R;
import com.shenhua.reading.view.ZoomImageView;
import com.squareup.picasso.Picasso;

/**
 * Created by Shenhua on 4/20/2016.
 */
public class ViewImgActivity extends AppCompatActivity {

    private RelativeLayout view_img_layout;
    private ZoomImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewimg);
        view_img_layout = (RelativeLayout) findViewById(R.id.view_img_layout);
        imageView = (ZoomImageView) findViewById(R.id.view_img);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        if (url != null || url.equals("")) viewImg(url);
        else Toast.makeText(getApplicationContext(), "查看图片失败！", Toast.LENGTH_LONG).show();
    }

    private void viewImg(String imgUrl) {
        Picasso.with(this).load(imgUrl).into(imageView);
    }
}
