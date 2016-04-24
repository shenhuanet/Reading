package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentKanyuanZujian extends BaseFragment {

    private static FragmentKanyuanZujian instance = null;

    public static FragmentKanyuanZujian newInstance() {
        if (instance == null) {
            instance = new FragmentKanyuanZujian();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_KAN_ZUJIAN;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_KANYUAN_ZUJIAN, "www.see-source.com"};
    }
}
