package com.shenhua.reading.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.felipecsl.gifimageview.library.GifImageView;
import com.shenhua.reading.R;
import com.shenhua.reading.utils.GifDataDownloader;


public class FragmentHonghei extends Fragment {

    private static FragmentHonghei instance = null;
    private View view;
    private GifImageView gifImageView;

    public static FragmentHonghei newInstance() {
        if (instance == null) {
            instance = new FragmentHonghei();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_honghei, container, false);
            gifImageView = (GifImageView) view.findViewById(R.id.gifImageView);
            new GifDataDownloader() {
                @Override protected void onPostExecute(final byte[] bytes) {
                    gifImageView.setBytes(bytes);
                    gifImageView.startAnimation();
                    Log.d("", "GIF width is " + gifImageView.getGifWidth());
                    Log.d("", "GIF height is " + gifImageView.getGifHeight());
                }
            }.execute("http://katemobile.ru/tmp/sample3.gif");


        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }


}
