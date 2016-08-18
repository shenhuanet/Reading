package com.shenhua.reading.fragment;


import com.shenhua.reading.utils.MyStringUtils;

public class FragmentSegf extends BaseFragment {

    private static FragmentSegf instance = null;

    public static FragmentSegf newInstance() {
        if (instance == null) {
            instance = new FragmentSegf();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_SENG;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_SEGF, "segmentfault.com"};
    }

}
