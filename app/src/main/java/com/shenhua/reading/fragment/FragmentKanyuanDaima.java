package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentKanyuanDaima extends BaseFragment {

    private static FragmentKanyuanDaima instance = null;

    public static FragmentKanyuanDaima newInstance() {
        if (instance == null) {
            instance = new FragmentKanyuanDaima();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_KAN_DAIMA;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_KANYUAN_DAIMA,"www.see-source.com"};
    }
}
