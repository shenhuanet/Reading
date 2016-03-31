package com.shenhua.reading.fragment;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenhua.reading.R;
import com.shenhua.reading.adapter.KanyuanDaimaAdapter;
import com.shenhua.reading.adapter.KanyuanZujianAdapter;
import com.shenhua.reading.bean.KanyuanDaimaBean;
import com.shenhua.reading.bean.KanyuanZujianBean;
import com.shenhua.reading.utils.MyStringUtils;
import com.shenhua.reading.utils.SpaceItemDecoration;

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
    private List<KanyuanZujianBean> datas = null;
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
            view = inflater.inflate(R.layout.fragment_kanyuan_zujian, container, false);
            refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.kanyuanzujian_refreshlayout);
            refreshLayout.setColorSchemeResources(R.color.colorSwipeRefresh01, R.color.colorSwipeRefresh02, R.color.colorSwipeRefresh03, R.color.colorSwipeRefresh04);
            refreshLayout.setOnRefreshListener(this);
            recyclerView = (RecyclerView) view.findViewById(R.id.kanyuanzujian_rec_list);
            mLayoutManager = new GridLayoutManager(getContext(), 1, GridLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            mImageLoader = ImageLoader.getInstance();
            options = new DisplayImageOptions.Builder()
                    .showStubImage(R.mipmap.gif)
                    .showImageForEmptyUri(R.mipmap.gif)
                    .showImageOnFail(R.mipmap.gif).cacheInMemory(true)
                    .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.EXACTLY).build();
            initDatas();
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
                datas = new ArrayList<KanyuanZujianBean>();
                datas.clear();
                Document doc = null;
                try {
                    doc = Jsoup.connect(MyStringUtils.URL_KANYUAN_ZUJIAN).get();
                    if (doc != null) {
                        Elements main = doc.body().getElementsByClass("left-wrap");
                        for (Element element : main) {
                            Elements elements = element.getElementsByTag("li");
                            for (Element content : elements) {
                                KanyuanZujianBean data = new KanyuanZujianBean();
                                data.setTitle(content.getElementsByClass("title").text());
                                data.setUrlDetail(content.getElementsByTag("a").attr("href").toString());
                                data.setUrlImg(content.getElementsByTag("img").attr("src"));
                                data.setTitle(content.getElementsByClass("name").text());
                                data.setFrom(content.getElementsByClass("author").text());
                                data.setDescribe("\u3000\u3000" + content.getElementsByClass("description").text().trim());
                                data.setOtherInfo(content.getElementsByClass("otherinfo").text());
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
                adapter = new KanyuanZujianAdapter(getContext(), datas);
                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(new KanyuanZujianAdapter.OnRecyclerViewItemClickListener() {
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
