package com.shenhua.reading.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
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

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenhua.reading.R;
import com.shenhua.reading.activity.ActivityContentActivity;
import com.shenhua.reading.adapter.KaifazheAdapter;
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

public class FragmentKaifazhe extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static FragmentKaifazhe instance = null;
    private View view;
    private KaifazheAdapter adapter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<MyDatasBean> datas = null;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

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
            mImageLoader = ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
                    .imageScaleType(ImageScaleType.EXACTLY).build();
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
                    doc = Jsoup.connect(MyStringUtils.URL_KAIFAZHE)
                            .timeout(5000)
                            .get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (doc != null) {
                    try {
                        Element main = doc.body().getElementById("daily");
                        Elements content = main.getElementsByClass("post");
                        for (Element element : content) {
                            MyDatasBean data = new MyDatasBean();
                            data.setTitle(element.getElementsByClass("title").text().trim());
                            String der = element.getElementsByClass("meta").text().trim();
                            String[] drecit = der.split("  ");
                            data.setNick(drecit[0]);
                            data.setComment(drecit[1]);
                            data.setFrom(element.getElementsByClass("subject-name").text().trim().replace("来自", "").trim());
                            Element img = element.getElementsByClass("user-avatar").get(0);
                            data.setImgUrl(img.getElementsByTag("img").attr("src"));
                            Element href = element.getElementsByClass("title").get(0);
                            data.setUrl(href.getElementsByTag("a").attr("href"));
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
                if (datas.size() != 0 && datas != null) {
                    adapter = new KaifazheAdapter(getContext(), datas, mImageLoader, options);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                    adapter.setOnItemClickListener(new KaifazheAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, final String data) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(getContext(), ActivityContentActivity.class).putExtra("url", data).putExtra("type",MyStringUtils.TYPE_KAIFAZHE));
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
