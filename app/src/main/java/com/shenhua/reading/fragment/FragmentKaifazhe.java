package com.shenhua.reading.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shenhua.reading.R;
import com.shenhua.reading.adapter.KaifazheAdapter;
import com.shenhua.reading.bean.KaifazheBean;
import com.shenhua.reading.bean.KanyuanDaimaBean;
import com.shenhua.reading.utils.MyStringUtils;
import com.shenhua.reading.utils.SpaceItemDecoration;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentKaifazhe extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static FragmentKaifazhe instance = null;
    private View view;
    private KaifazheAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<KaifazheBean> datas = null;

    public static FragmentKaifazhe newInstance() {
        if (instance == null) {
            instance = new FragmentKaifazhe();
        }
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_kaifazhe, container, false);
            int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.rec_item_space);
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.kaifazhe_refreshlayout);
            refreshLayout.setColorSchemeResources(R.color.colorSwipeRefresh01, R.color.colorSwipeRefresh02, R.color.colorSwipeRefresh03, R.color.colorSwipeRefresh04);
            refreshLayout.setOnRefreshListener(this);
            refreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    refreshLayout.setRefreshing(true);
                }
            });
            onRefresh();
            recyclerView = (RecyclerView) view.findViewById(R.id.kaifazhe_rec_list);
            recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));
            LinearLayoutManager llm = new LinearLayoutManager((getContext()));
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        initDatas();
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
                datas = new ArrayList<KaifazheBean>();
                datas.clear();
                Document doc = null;
                try {
                    doc = Jsoup.connect(MyStringUtils.URL_KAIFAZHE).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (doc != null) {
                    try {
                        Element main = doc.body().getElementById("daily");
                        Elements content = main.getElementsByClass("post");
                        for (Element element : content) {
                            KaifazheBean data = new KaifazheBean();
                            data.setTitle(element.getElementsByClass("title").text().trim());
                            String der = element.getElementsByClass("meta").text().trim();
                            String[] drecit = der.split("  ");
                            data.setDerecit(drecit[0]);
                            data.setComment(drecit[1]);
                            data.setFrom(element.getElementsByClass("subject-name").text().trim());
                            Element img = element.getElementsByClass("user-avatar").get(0);
                            data.setImg_url(img.getElementsByTag("img").attr("src"));
                            Element href = element.getElementsByClass("title").get(0);
                            data.setHref(href.getElementsByTag("a").attr("href"));
                            datas.add(data);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //数据为空
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                adapter = new KaifazheAdapter(getContext(), datas);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                refreshLayout.setRefreshing(false);
                adapter.setOnItemClickListener(new KaifazheAdapter.OnRecyclerViewItemClickListener() {
                    @Override
                    public void onItemClick(View view, String data) {

                    }
                });
            }
        };
        task.execute();

    }
}
