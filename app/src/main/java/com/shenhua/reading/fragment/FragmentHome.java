package com.shenhua.reading.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.reading.R;
import com.shenhua.reading.adapter.MyHomeListAdapter;
import com.shenhua.reading.bean.HistoryData;
import com.shenhua.reading.bean.MyHistoryDBdao;
import com.shenhua.reading.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class FragmentHome extends Fragment {

    private static FragmentHome instance = null;
    private View view;
    private TextInputLayout inputLayout;
    private AppCompatEditText editText;
    private RecyclerView list;
    private List<HistoryData> datas = new ArrayList<HistoryData>();
//    private HistoryData data = null;
    private MyHomeListAdapter adapter;
    private MyHistoryDBdao dBdao;

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
            LinearLayoutManager llm = new LinearLayoutManager((getContext()));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            list = (RecyclerView) view.findViewById(R.id.home_list);
            list.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.list_item_space)));
            list.setLayoutManager(llm);
            list.setHasFixedSize(true);
            list.setItemAnimator(new DefaultItemAnimator());
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
//                    data = new HistoryData();
                    dBdao = new MyHistoryDBdao(getContext());
                    dBdao.open();
//                    data.setTitle("这是标题..." + Integer.toString(i));
//                    data.setDescribe("\u3000\u3000" + "描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述描述");
//                    data.setTime("2016-04-06");
//                    data.setUrl("http:" + Integer.toString(i));
//                    if (i % 2 == 0)
//                        data.setType(2);//yellow
//                    else
//                        data.setType(1);//blue
//                    datas.add(data);
                    datas = dBdao.getAllDatas();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                adapter = new MyHomeListAdapter(getContext(), datas);
                adapter.notifyDataSetChanged();
                list.setAdapter(adapter);
                dBdao.close();
                adapter.setOnItemClickListener(new MyHomeListAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data) {

                    }
                });

            }
        };
        task.execute();
    }
}
