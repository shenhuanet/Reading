package com.shenhua.reading.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.reading.R;

public class FragmentHome extends Fragment {
    private static FragmentHome instance = null;

    public static FragmentHome newInstance() {
        if (instance == null) {
            instance = new FragmentHome();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        return view;
    }


}
