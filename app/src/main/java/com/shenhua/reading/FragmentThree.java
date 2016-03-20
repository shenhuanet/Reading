package com.shenhua.reading;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FragmentThree extends Fragment {

    private static FragmentThree instance=null;
    public static FragmentThree newInstance() {
        if(instance==null){
            instance= new FragmentThree();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_three, container, false);
        TextView mTextView = (TextView) view.findViewById(R.id.fragment_tv);
        mTextView.setText("这是第三个Fragment");
        return view;
    }


}
