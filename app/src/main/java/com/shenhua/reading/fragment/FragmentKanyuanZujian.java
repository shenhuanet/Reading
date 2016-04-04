package com.shenhua.reading.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenhua.reading.R;
import com.shenhua.reading.activity.ActivityContentActivity;
import com.shenhua.reading.adapter.KanyuanZujianAdapter;
import com.shenhua.reading.bean.MyDatasBean;
import com.shenhua.reading.utils.MyStringUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FragmentKanyuanZujian extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static FragmentKanyuanZujian instance = null;
    private View view;
    private KanyuanZujianAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<MyDatasBean> datas = null;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    public static FragmentKanyuanZujian newInstance() {
        if (instance == null) {
            instance = new FragmentKanyuanZujian();
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
            mLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mImageLoader = ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .showImageOnFail(R.mipmap.ic_imgload_error).cacheInMemory(true)
                    .cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
                    .imageScaleType(ImageScaleType.EXACTLY).build();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
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
                    doc = Jsoup.connect(MyStringUtils.URL_KANYUAN_ZUJIAN)
                            .header("User-Agent", MyStringUtils.USER_AGENT)
                            .header("Host", "www.see-source.com")
                            .cookie("auth", "token")
                            .timeout(5000)
                            .get();
                    if (doc != null) {
                        Elements main = doc.body().getElementsByClass("left-wrap");
                        for (Element element : main) {
                            Elements elements = element.getElementsByTag("li");
                            for (Element content : elements) {
                                MyDatasBean data = new MyDatasBean();
                                data.setTitle(content.getElementsByClass("title").text().trim());
                                data.setUrl(content.getElementsByTag("a").attr("href").toString().trim());
                                data.setImgUrl(content.getElementsByTag("img").attr("src").trim());
                                data.setTitle(content.getElementsByClass("name").text().trim());
                                data.setFrom(content.getElementsByClass("author").text().trim());
                                data.setDescribe("\u3000\u3000" + content.getElementsByClass("description").text().trim());
                                data.setOtherInfo(content.getElementsByClass("otherinfo").text().trim());
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
                    adapter = new KanyuanZujianAdapter(getContext(), datas, mImageLoader, options);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                    refreshLayout.setRefreshing(false);
                    adapter.setOnItemClickListener(new KanyuanZujianAdapter.OnRecyclerViewItemClickListener() {
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
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                initDatas();
            }
        }).start();
    }
}
