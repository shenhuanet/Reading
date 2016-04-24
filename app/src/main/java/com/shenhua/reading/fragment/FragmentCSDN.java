package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentCSDN extends BaseFragment {

    private static FragmentCSDN instance = null;

    public static FragmentCSDN newInstance() {
        if (instance == null) {
            instance = new FragmentCSDN();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_CSDN;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_CSDN_YD, "blog.csdn.net"};
    }
}
