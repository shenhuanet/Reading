package com.shenhua.reading.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.DimType;
import com.shenhua.reading.R;

/**
 * Created by shenhua on 4/5/2016.
 */
public class ActivityTest extends AppCompatActivity implements
        BoomMenuButton.OnSubButtonClickListener {

    private BoomMenuButton boomMenuButton;
    private boolean isInit = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        boomMenuButton = (BoomMenuButton) findViewById(R.id.boombtn);
        boomMenuButton.setOnSubButtonClickListener(this);
        boomMenuButton.setDimType(DimType.DIM_0);
    }

    @Override
    public void onBackPressed() {
        if (boomMenuButton.isClosed())
            super.onBackPressed();
        else
            boomMenuButton.dismiss();
    }

    @Override
    public void onClick(int buttonIndex) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        if (!isInit) {
//            initBoom();
//            initInfoBoom();
        }
        isInit = true;
    }
}
