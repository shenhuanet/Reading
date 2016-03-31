package com.shenhua.reading.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.shenhua.reading.R;
import com.shenhua.reading.adapter.KanyuanAdapter;
import com.shenhua.reading.bean.KanyuanBean;
import com.shenhua.reading.utils.MyStringUtils;
import com.shenhua.reading.utils.SpaceItemDecoration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FragmentKanyuan extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FragmentKanyuan instance = null;
    private View view;
    private KanyuanAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<KanyuanBean> datas = null;

    public static FragmentKanyuan newInstance() {
        if (instance == null) {
            instance = new FragmentKanyuan();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_kanyuan, container, false);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rec_item_space);
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.kanyuan_refreshlayout);
            refreshLayout.setColorSchemeResources(R.color.colorSwipeRefresh01, R.color.colorSwipeRefresh02, R.color.colorSwipeRefresh03, R.color.colorSwipeRefresh04);
            refreshLayout.setOnRefreshListener(this);
            recyclerView = (RecyclerView) view.findViewById(R.id.kanyuan_rec_list);
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
        initDatas();
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
                datas = new ArrayList<KanyuanBean>();
                datas.clear();
                Document doc = null;
                try {
                    doc = Jsoup.connect(MyStringUtils.URL_KANYUAN_DAIMA).get();
                    if (doc != null) {
                        Elements main = doc.body().getElementsByClass("blog-wrap");
                        for (Element element : main) {
                            Elements elements = element.getElementsByTag("li");
                            for (Element content : elements) {
                                KanyuanBean data = new KanyuanBean();
                                data.setTitle(content.getElementsByClass("title").text());
                                data.setUrl(content.getElementsByTag("a").attr("href").toString());
                                data.setDescribe(content.getElementsByClass("shortContent").text());
                                data.setNick(content.getElementsByClass("userNick").text());
                                data.setTime(content.getElementsByClass("put-time").text());
                                data.setComment(content.getElementsByClass("comment-num").text());
                                String viewnum = content.getElementsByClass("view-num").text();
                                String[] num = viewnum.split(" ");
                                data.setRead(num[0]);
                                data.setLike(num[1]);
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
                adapter = new KanyuanAdapter(getContext(), datas);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new KanyuanAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data) {
                        Toast.makeText(getContext(), data, Toast.LENGTH_SHORT).show();//这里处理跳转事件
                    }
                });
            }
        };
        task.execute();
    }

    @Override
    public void onRefresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initDatas();
                refreshLayout.setRefreshing(false);
            }
        }).start();
    }
}
