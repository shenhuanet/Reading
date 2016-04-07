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
import com.shenhua.reading.adapter.HongheiAdapter;
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


public class FragmentHonghei extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FragmentHonghei instance = null;
    private View view;
    private HongheiAdapter adapter;
    private List<MyDatasBean> datas = null;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    public static FragmentHonghei newInstance() {
        if (instance == null) {
            instance = new FragmentHonghei();
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
            mImageLoader = ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .cacheInMemory(true)
                    .cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
                    .imageScaleType(ImageScaleType.NONE).build();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null)
            parent.removeView(view);
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
                    doc = Jsoup.connect(MyStringUtils.URL_HONGHEI)
                            .header("User-Agent", MyStringUtils.USER_AGENT)
                            .header("Host", "www.2cto.com")
                            .cookie("auth", "token")
                            .timeout(5000)
                            .get();
                } catch (IOException e) {
                    System.out.println("获取失败");
                    e.printStackTrace();
                }
                if (doc != null) {
                    try {
                        Elements main = doc.body().getElementById("fontzoom").getElementsByClass("AtrListBox");
                        for (Element element : main) {
                            MyDatasBean data = new MyDatasBean();
                            data.setTitle(element.getElementsByTag("h3").text().trim());
                            data.setUrl(element.getElementsByTag("h3").get(0).getElementsByTag("a").attr("href").trim());
                            String[] ss = element.getElementsByClass("ADesc").text().trim().split("    ");
                            Elements s = element.getElementsByClass("ArtPic");
                            data.setDescribe("\u3000\u3000" + ss[0]);
                            data.setTime(ss[1]);
                            if (!s.isEmpty()) {
                                data.setImgUrl(s.get(0).getElementsByTag("img").attr("src").trim());
                            } else {
                                data.setImgUrl("");
                            }
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
                    adapter = new HongheiAdapter(getContext(), datas, mImageLoader, options);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                }
            }
        };

        task.execute();
    }

}
