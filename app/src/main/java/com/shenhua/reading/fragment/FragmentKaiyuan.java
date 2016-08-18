package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentKaiyuan extends BaseFragment {

    private static FragmentKaiyuan instance = null;

    public static FragmentKaiyuan newInstance() {
        if (instance == null) {
            instance = new FragmentKaiyuan();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_KAIYUAN;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_KAIYUAN, "www.oschina.net"};
    }

}
