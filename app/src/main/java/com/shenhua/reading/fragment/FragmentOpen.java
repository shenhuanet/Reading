package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentOpen extends BaseFragment {

    private static FragmentOpen instance = null;

    public static FragmentOpen newInstance() {
        if (instance == null) {
            instance = new FragmentOpen();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_OPEN;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_OPEN, "www.open-open.com"};
    }
}
