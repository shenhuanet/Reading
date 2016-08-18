package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentHonghei extends BaseFragment {

    private static FragmentHonghei instance = null;

    public static FragmentHonghei newInstance() {
        if (instance == null) {
            instance = new FragmentHonghei();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_HONGHEI;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_HONGHEI, "www.2cto.com"};
    }

}
