package com.shenhua.reading.activity;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Random;

/**
 * Created by shenhua on 4/5/2016.
 */
public class ActivityTest extends AppCompatActivity implements
        BoomMenuButton.OnSubButtonClickListener {

    private String[] Colors = {"#F44336", "#E91E63", "#9C27B0", "#2196F3", "#03A9F4", "#00BCD4", "#009688", "#4CAF50", "#8BC34A", "#CDDC39", "#FFEB3B", "#FFC107", "#FF9800", "#FF5722", "#795548", "#9E9E9E", "#607D8B"};
    private BoomMenuButton boomMenuButton;
    private boolean isInit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
//        initViews();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInit) {
            initBoom();
        }
        isInit = true;
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

    private void initViews() {
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
        int p = random.nextInt(Colors.length);
        return Color.parseColor(Colors[p]);
    }

    @Override
    public void onBackPressed() {
        if (boomMenuButton.isClosed()) {
            super.onBackPressed();
        } else {
            boomMenuButton.dismiss();
        }
    }
}
