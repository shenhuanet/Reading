package com.shenhua.reading;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentTuiku extends Fragment {

    private static FragmentTuiku instance=null;
    public static FragmentTuiku newInstance() {
        if(instance==null){
            instance= new FragmentTuiku();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tuiku, container, false);
        return view;
    }


}
