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

import com.shenhua.reading.R;
import com.shenhua.reading.activity.ContentActivity;
import com.shenhua.reading.adapter.KanyuanDaimaAdapter;
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

public class FragmentJcode extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FragmentJcode instance = null;
    private View view;
    private List<MyDatasBean> datas = null;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private KanyuanDaimaAdapter adapter;

    public static FragmentJcode newInstance() {
        if (instance == null) {
            instance = new FragmentJcode();
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
        AsyncTask task = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                datas = new ArrayList<MyDatasBean>();
                datas.clear();
                Document doc = null;
                try {
                    doc = Jsoup.connect(MyStringUtils.URL_JCODE)
                            .header("User-Agent", MyStringUtils.USER_AGENT)
                            .header("Host", "www.jcodecraeer.com")
                            .cookie("auth", "token")
                            .timeout(5000)
                            .get();
                } catch (IOException e) {
                    System.out.println("获取失败");
                    e.printStackTrace();
                }
                if (doc != null) {
                    try {
                        Elements main = doc.body().getElementsByClass("archive-list").get(0).getElementsByAttributeValue("class", "archive-item clearfix");
                        for (Element content : main) {
                            MyDatasBean data = new MyDatasBean();
                            data.setTitle(content.getElementsByTag("h3").text().trim());
                            data.setUrl("http://www.jcodecraeer.com" + content.getElementsByTag("h3").get(0).getElementsByTag("a").attr("href").trim());
                            data.setDescribe("\u3000\u3000" + content.getElementsByTag("p").text().trim());
                            data.setNick(content.getElementsByClass("list-user").text().trim());
                            data.setTime(content.getElementsByClass("archive-data").text().trim());
                            String ss = content.getElementsByClass("list-msg").text().trim();
                            String[] t = ss.split(" ");
                            data.setRead(t[0].trim());
                            data.setComment(t[1].trim());
                            data.setLike(t[2].trim());
                            datas.add(data);
                        }
                    } catch (Exception e) {
                        System.out.println("解析失败");
                        e.printStackTrace();
                    }
                } else {

                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                if (datas.size() != 0 && datas != null) {
                    adapter = new KanyuanDaimaAdapter(getContext(), datas);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                    adapter.setOnItemClickListener(new KanyuanDaimaAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, final String data) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type",MyStringUtils.TYPE_JCODE));
                                }
                            }, 1000);
                        }
                    });
                }
            }
        };

        task.execute();

    }
}
