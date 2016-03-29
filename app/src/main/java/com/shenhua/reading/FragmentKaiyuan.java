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

public class FragmentKaiyuan extends Fragment {
    private static FragmentKaiyuan instance = null;

    public static FragmentKaiyuan newInstance() {
        if (instance == null) {
            instance = new FragmentKaiyuan();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_kaiyuan, container, false);

        return view;
    }


}
