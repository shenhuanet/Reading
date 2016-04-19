package com.shenhua.reading.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenhua.reading.R;
import com.shenhua.reading.activity.ActivityContentActivity;
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
    private MyHomeListAdapter adapter;
    private MyHistoryDBdao dBdao;
    private static final String[] MENU_ITEMS = {"修 改", "打 开", "删 除"};

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
                    dBdao = new MyHistoryDBdao(getContext());
                    dBdao.open();
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
                    public void onItemClick(View view, final String data) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getContext(), ActivityContentActivity.class).putExtra("url", data).putExtra("type", -1));
                            }
                        }, 1000);
                    }
                });
                adapter.setOnItemLongClickListener(new MyHomeListAdapter.OnItemLongClickListener() {
                    @Override
                    public void onItemLongClick(View view) {
                        TextView tv = (TextView) view.findViewById(R.id.home_item_describe);
                        final String url = tv.getText().toString();
                        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                        builder.setItems(MENU_ITEMS, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        break;
                                    case 1:
                                        startActivity(new Intent(getContext(), ActivityContentActivity.class).putExtra("url", url).putExtra("type", -1));
                                        break;
                                    case 2:
                                        break;
                                }
                            }
                        });
                        builder.show();
                    }
                });
            }
        };
        task.execute();
    }


}
