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

public class FragmentKanyuanDaima extends Fragment implements SwipeRefreshLayout.OnRefreshListener{

    private static FragmentKanyuanDaima instance = null;
    private View view;
    private KanyuanDaimaAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<MyDatasBean> datas = null;

    public static FragmentKanyuanDaima newInstance() {
        if (instance == null) {
            instance = new FragmentKanyuanDaima();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_content, container, false);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rec_item_space);
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
            recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
            LinearLayoutManager llm = new LinearLayoutManager((getContext()));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);
        return view;
    }

    private void initDatas() {
        AsyncTask<String, Integer, Void> task = new AsyncTask<String, Integer, Void>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                System.out.println("start");
            }

            @Override
            protected Void doInBackground(String... params) {
                datas = new ArrayList<MyDatasBean>();
                datas.clear();
                Document doc = null;
                try {
                    doc = Jsoup.connect(MyStringUtils.URL_KANYUAN_DAIMA)
                            .header("User-Agent", MyStringUtils.USER_AGENT)
                            .header("Host", "www.see-source.com")
                            .cookie("auth", "token")
                            .timeout(5000)
                            .get();
                    if (doc != null) {
                        Elements main = doc.body().getElementsByClass("blog-wrap");
                        for (Element element : main) {
                            Elements elements = element.getElementsByTag("li");
                            for (Element content : elements) {
                                MyDatasBean data = new MyDatasBean();
                                data.setTitle(content.getElementsByClass("title").text().trim());
                                data.setUrl(content.getElementsByTag("a").attr("href").toString().trim());
                                data.setDescribe("\u3000\u3000" + content.getElementsByClass("shortContent").text().trim());
                                data.setNick(content.getElementsByClass("userNick").text().trim());
                                data.setTime(content.getElementsByClass("put-time").text().trim());
                                data.setComment(content.getElementsByClass("comment-num").text().trim().replace("•", "").trim());
                                String viewnum = content.getElementsByClass("view-num").text();
                                String[] num = viewnum.split(" ");
                                data.setRead(num[0].trim());
                                data.setLike(num[1].trim());
                                datas.add(data);
                            }
                        }
                    } else {
                        System.out.println("数据为空 ");
                    }
                } catch (IOException e) {
                    System.out.println("请求错误");
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void o) {
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
                                    startActivity(new Intent(getContext(), ActivityContentActivity.class).putExtra("url", data));
                                }
                            }, 1000);
                        }
                    });
                }
            }
        };
        task.execute();
    }

    @Override
    public void onRefresh() {
        initDatas();
    }
}
