package com.shenhua.reading.fragment;


import com.shenhua.reading.utils.MyStringUtils;

public class FragmentTuiku extends BaseFragment {

    private static FragmentTuiku instance = null;

    public static FragmentTuiku newInstance() {
        if (instance == null) {
            instance = new FragmentTuiku();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_TUIKU;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_TUIKU, "www.tuicool.com"};
    }

}
