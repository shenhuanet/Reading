package com.shenhua.reading.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shenhua.reading.R;

import org.apache.http.Header;

import java.io.IOException;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class FragmentHonghei extends Fragment {

    private static FragmentHonghei instance = null;
    private GifImageView network_gifimageview;
    private View view;
    private AsyncHttpClient asyncHttpClient;

    public static FragmentHonghei newInstance() {
        if (instance == null) {
            instance = new FragmentHonghei();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_honghei, container, false);
        network_gifimageview = (GifImageView) view.findViewById(R.id.network_gifimageview);

        asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient
                .get("http://www.see-source.com/mykanyuan/file/kindeditor/androidwidget/null/boommenu/boommenu.gif",
                        new AsyncHttpResponseHandler() {

                            @Override
                            public void onSuccess(int arg0, Header[] arg1,
                                                  byte[] arg2) {
                                // TODO Auto-generated method stub
                                GifDrawable drawable = null;
                                try {
                                    drawable = new GifDrawable(arg2);
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    System.out.println("错误11111IOException");
                                    e.printStackTrace();
                                }
                                network_gifimageview
                                        .setBackgroundDrawable(drawable);
                            }

                            @Override
                            public void onFailure(int arg0, Header[] arg1,
                                                  byte[] arg2, Throwable arg3) {
                                // TODO Auto-generated method stub
                                System.out.println("错误22222 onFailure 方法");

                            }
                        });

        return view;
    }


}
