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
import com.shenhua.reading.activity.ActivityContentActivity;
import com.shenhua.reading.adapter.CSDNAdapter;
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

public class FragmentCSDN extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FragmentCSDN instance = null;
    private View view;
    private List<MyDatasBean> datas = null;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private CSDNAdapter adapter;

    public static FragmentCSDN newInstance() {
        if (instance == null) {
            instance = new FragmentCSDN();
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
                    doc = Jsoup.connect(MyStringUtils.URL_CSDN_YD)
                            .header("User-Agent", MyStringUtils.USER_AGENT)
                            .header("Host", "blog.csdn.net")
                            .cookie("auth", "token")
                            .timeout(5000)
                            .get();
                } catch (IOException e) {
                    System.out.println("获取失败");
                    e.printStackTrace();
                }
                if (doc != null) {
                    try {
                        Elements main = doc.body().getElementsByClass("main_center").get(0).getElementsByClass("blog_list");
                        for (Element content : main) {
                            MyDatasBean data = new MyDatasBean();
                            data.setTitle(content.getElementsByTag("h1").text().trim());
                            String url = content.getElementsByTag("h1").get(0).getElementsByTag("a").attr("href").trim();
                            data.setUrl(getMobileUrl(url, url + "f"));
                            data.setDescribe("\u3000\u3000" + content.getElementsByTag("dd").text().trim());
                            data.setNick(content.getElementsByClass("fl").get(0).getElementsByTag("a").get(0).text().trim());
                            data.setTime(content.getElementsByClass("time").text().trim());
                            data.setRead(content.getElementsByClass("fl").get(0).getElementsByTag("a").get(1).text().trim());
                            data.setComment(content.getElementsByClass("fl").get(0).getElementsByTag("a").get(2).text().trim());
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
                    adapter = new CSDNAdapter(getContext(), datas);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                    adapter.setOnItemClickListener(new CSDNAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, final String data) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getContext(), ActivityContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_CSDN));
                                }
                            }, 1000);
                        }
                    });
                }
            }
        };

        task.execute();

    }

    private String getMobileUrl(String url, String str) {
        Pattern p = Pattern.compile(".*?details/(.*?)f");
        Matcher m = p.matcher(str);
        while (m.find()) {
            MatchResult result = m.toMatchResult();
//            System.out.println(MyStringUtils.URL_CSDN_YD_M + result.group(1));
            return MyStringUtils.URL_CSDN_YD_M + result.group(1);
        }
        return url;
    }
}
