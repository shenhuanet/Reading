package com.shenhua.reading.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.shenhua.reading.R;
import com.shenhua.reading.adapter.MyListViewAdapter;
import com.shenhua.reading.bean.HistoryData;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private static FragmentHome instance = null;
    private View view;
    private TextInputLayout inputLayout;
    private AppCompatEditText editText;
    private ListView list;
    private List<HistoryData> datas = new ArrayList<HistoryData>();
    private HistoryData data = null;
    private MyListViewAdapter adapter;

    public static FragmentHome newInstance() {
        if (instance == null) {
            instance = new FragmentHome();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_home, container, false);
            inputLayout = (TextInputLayout) view.findViewById(R.id.home_textinput);
            editText = (AppCompatEditText) view.findViewById(R.id.home_et);
            list = (ListView) view.findViewById(R.id.home_list);
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        initDatas();
        return view;
    }

    private void initDatas() {
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                for (int i = 0; i < 10; i++) {
                    data = new HistoryData();
                    data.setTitle("这是标题...");
                    data.setDescribe("\u3000\u3000" + "描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述");
                    data.setTime("2016-04-06");
                    if (i % 2 == 0)
                        data.setType(2);//yellow
                    else
                        data.setType(1);//blue
                    datas.add(data);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                adapter = new MyListViewAdapter(getContext(), datas);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
            }
        };
        task.execute();
    }
}
