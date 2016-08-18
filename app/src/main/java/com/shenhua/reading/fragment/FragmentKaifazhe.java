package com.shenhua.reading.fragment;

import com.shenhua.reading.utils.MyStringUtils;

public class FragmentKaifazhe extends BaseFragment {
    private static FragmentKaifazhe instance = null;

    public static FragmentKaifazhe newInstance() {
        if (instance == null) {
            instance = new FragmentKaifazhe();
        }
        return instance;
    }

    @Override
    protected int getType() {
        return MyStringUtils.TYPE_KAIFAZHE;
    }

    @Override
    protected String[] getTypeUrl() {
        return new String[]{MyStringUtils.URL_KAIFAZHE, ""};
    }
}
