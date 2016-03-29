package com.shenhua.reading;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class FragmentKaifazhe extends Fragment {
    private static FragmentKaifazhe instance = null;

    public static FragmentKaifazhe newInstance() {
        if (instance == null) {
            instance = new FragmentKaifazhe();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaifazhe, container, false);

        return view;
    }


}
