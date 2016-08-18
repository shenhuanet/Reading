package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentJcode extends BaseFragment {

    private static FragmentJcode instance = null;

    public static FragmentJcode newInstance() {
        if (instance == null) {
            instance = new FragmentJcode();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_JCODE;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_JCODE, "www.jcodecraeer.com"};
    }


}
