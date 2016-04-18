package com.shenhua.reading.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shenhua.reading.R;
import com.shenhua.reading.activity.ActivityContentActivity;
import com.shenhua.reading.adapter.KaiyuanAdapter;
import com.shenhua.reading.adapter.KaiyuanAdapter;
import com.shenhua.reading.bean.MyDatasBean;
import com.shenhua.reading.utils.MyStringUtils;
import com.shenhua.reading.utils.SpaceItemDecoration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FragmentOpen extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FragmentOpen instance = null;
    private View view;
    private List<MyDatasBean> datas = null;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private KaiyuanAdapter adapter;

    public static FragmentOpen newInstance() {
        if (instance == null) {
            instance = new FragmentOpen();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_content, container, false);
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshlayout);
            recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
            refreshLayout.setColorSchemeResources(R.color.colorSwipeRefresh01, R.color.colorSwipeRefresh02, R.color.colorSwipeRefresh03, R.color.colorSwipeRefresh04);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
            onRefresh();
            recyclerView.addItemDecoration(new SpaceItemDecoration(getResources().getDimensionPixelSize(R.dimen.rec_item_space)));
            LinearLayoutManager llm = new LinearLayoutManager((getContext()));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;
    }

    @Override
    public void onRefresh() {
        initDatas();
    }

    private void initDatas() {
        final AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                datas = new ArrayList<MyDatasBean>();
                datas.clear();
                Document doc = null;
                try {
                    doc = Jsoup.connect(MyStringUtils.URL_OPEN)
                            .header("User-Agent", MyStringUtils.USER_AGENT)
                            .timeout(5000)
                            .get();
                } catch (IOException e) {
                    System.out.println("数据获取失败");
                    e.printStackTrace();
                    return "数据获取失败";
                }
                if (doc != null) {
                    try {
                        Elements main = doc.body().getElementsByTag("section").first().getElementsByClass("item");
                        for (Element content : main) {
                            MyDatasBean data = new MyDatasBean();
                            data.setTitle(content.getElementsByClass("title").text().trim());
                            data.setUrl(content.getElementsByTag("a").attr("href").trim());
                            data.setDescribe("\u3000\u3000" + content.getElementsByClass("description").text().trim());
                            data.setTime(content.getElementsByClass("meta").first().getElementsByTag("span").text().trim());
                            datas.add(data);
                        }
                    } catch (Exception e) {
                        System.out.println("数据解析失败");
                        e.printStackTrace();
                        return "数据解析失败";
                    }
                } else {
                    return "数据为空";
                }
                return "OK";
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (o.equals("OK")) {
                    if (datas.size() != 0 && datas != null) {
                        adapter = new KaiyuanAdapter(getContext(), datas);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                        refreshLayout.setRefreshing(false);
                        adapter.setOnRecItemClickLisenner(new KaiyuanAdapter.OnRecItemClickLisenner() {
                            @Override
                            public void onItemClick(View view, final String data) {
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        startActivity(new Intent(getContext(), ActivityContentActivity.class).putExtra("url", data).putExtra("type",MyStringUtils.TYPE_OPEN));
                                    }
                                }, 1000);
                            }
                        });
                    }
                } else {
                    showToast((String) o);
                    refreshLayout.setRefreshing(false);
                }
            }
        };
        task.execute();
    }

    private void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

}
