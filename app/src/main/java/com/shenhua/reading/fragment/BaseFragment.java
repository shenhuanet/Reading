package com.shenhua.reading.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.shenhua.reading.R;
import com.shenhua.reading.activity.ContentActivity;
import com.shenhua.reading.adapter.CSDNAdapter;
import com.shenhua.reading.adapter.HongheiAdapter;
import com.shenhua.reading.adapter.KaifazheAdapter;
import com.shenhua.reading.adapter.KaiyuanAdapter;
import com.shenhua.reading.adapter.KanyuanDaimaAdapter;
import com.shenhua.reading.adapter.KanyuanZujianAdapter;
import com.shenhua.reading.adapter.TuikuAdapter;
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

/**
 * Created by Shenhua on 4/20/2016.
 */
public abstract class BaseFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private List<MyDatasBean> datas = null;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private ImageLoader mImageLoader;
    private DisplayImageOptions options;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
            if (getType() == MyStringUtils.TYPE_TUIKU || getType() == MyStringUtils.TYPE_HONGHEI || getType() == MyStringUtils.TYPE_KAIFAZHE || getType() == MyStringUtils.TYPE_KAN_ZUJIAN) {
                mImageLoader = ImageLoader.getInstance();
//                options = new DisplayImageOptions.Builder().cacheInMemory(true)
//                        .cacheOnDisc(true).bitmapConfig(Bitmap.Config.ARGB_8888)
//                        .imageScaleType(ImageScaleType.NONE).build();
                options = new DisplayImageOptions.Builder().cacheInMemory(true)
                        .cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
                        .imageScaleType(ImageScaleType.EXACTLY).build();
            }
        }
        ViewGroup group = (ViewGroup) view.getParent();
        if (group != null)
            group.removeView(view);
        return view;

    }

    @Override
    public void onRefresh() {
        System.out.println(getType() + "开始");
        MyAsyncTask task = new MyAsyncTask();
        task.execute(getType());
    }

    protected abstract int getType();

    protected abstract String[] getTypeUrl();

    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Integer... params) {
            datas = new ArrayList<>();
            datas.clear();
            Document doc;
            try {
                if (getType() == MyStringUtils.TYPE_KAIFAZHE)
                    doc = Jsoup.connect(getTypeUrl()[0]).timeout(5000).get();
                else
                    doc = Jsoup.connect(getTypeUrl()[0]).header("User-Agent", MyStringUtils.USER_AGENT).header("Host", getTypeUrl()[1])
                            .cookie("auth", "token").timeout(5000).get();
            } catch (IOException e) {
                System.out.println(Integer.toString(getType()) + ":数据获取失败");
                e.printStackTrace();
                return "数据获取失败";
            }
            if (doc == null) {
                return "数据为空";
            } else {
                try {
                    System.out.println(getType() + "开始解析");
                    doGetDatas(doc);
                } catch (Exception e) {
                    System.out.println(getType() + "解析失败");
                    e.printStackTrace();
                    return "数据解析失败";
                }
            }
            return "OK";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("OK")) {
                System.out.println("解析成功");
                if (datas.size() != 0 && datas != null) {
                    switch (getType()) {
                        case MyStringUtils.TYPE_CSDN:
                            CSDNAdapter adapter = new CSDNAdapter(getContext(), datas);
                            adapter.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(new CSDNAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_CSDN));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_SENG:
                            TuikuAdapter adapter2 = new TuikuAdapter(getContext(), datas);
                            adapter2.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter2);
                            adapter2.setOnRecItemClickLisenner(new TuikuAdapter.OnRecItemClickLisenner() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_SENG));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_JCODE:
                            KanyuanDaimaAdapter adapter3 = new KanyuanDaimaAdapter(getContext(), datas);
                            adapter3.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter3);
                            adapter3.setOnItemClickListener(new KanyuanDaimaAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_JCODE));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_TUIKU:
                            TuikuAdapter adapter4 = new TuikuAdapter(getContext(), datas, mImageLoader, options);
                            adapter4.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter4);
                            adapter4.setOnRecItemClickLisenner(new TuikuAdapter.OnRecItemClickLisenner() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_TUIKU));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_HONGHEI:
                            HongheiAdapter adapter5 = new HongheiAdapter(getContext(), datas, mImageLoader, options);
                            adapter5.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter5);
                            break;
                        case MyStringUtils.TYPE_KAIYUAN:
                            KaiyuanAdapter adapter6 = new KaiyuanAdapter(getContext(), datas);
                            adapter6.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter6);
                            adapter6.setOnRecItemClickLisenner(new KaiyuanAdapter.OnRecItemClickLisenner() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_KAIYUAN));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_KAIFAZHE:
                            KaifazheAdapter adapter7 = new KaifazheAdapter(getContext(), datas, mImageLoader, options);
                            adapter7.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter7);
                            adapter7.setOnItemClickListener(new KaifazheAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_KAIFAZHE));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_KAN_DAIMA:
                            KanyuanDaimaAdapter adapter8 = new KanyuanDaimaAdapter(getContext(), datas);
                            adapter8.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter8);
                            adapter8.setOnItemClickListener(new KanyuanDaimaAdapter.OnRecyclerViewItemClickListener() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_KAN_DAIMA));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                        case MyStringUtils.TYPE_KAN_ZUJIAN:
                            KanyuanZujianAdapter adapter9 = new KanyuanZujianAdapter(getContext(), datas, mImageLoader, options);
                            adapter9.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter9);
                            break;
                        case MyStringUtils.TYPE_OPEN:
                            KaiyuanAdapter adapter10 = new KaiyuanAdapter(getContext(), datas);
                            adapter10.notifyDataSetChanged();
                            recyclerView.setAdapter(adapter10);
                            adapter10.setOnRecItemClickLisenner(new KaiyuanAdapter.OnRecItemClickLisenner() {
                                @Override
                                public void onItemClick(View view, final String data) {
                                    new Handler().postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            startActivity(new Intent(getContext(), ContentActivity.class).putExtra("url", data).putExtra("type", MyStringUtils.TYPE_OPEN));
                                        }
                                    }, 1000);
                                }
                            });
                            break;
                    }
                    refreshLayout.setRefreshing(false);
                }
            } else {
                showToast(s);
                refreshLayout.setRefreshing(false);
            }
        }
    }

    private void doGetDatas(Document doc) {
        if (getType() == MyStringUtils.TYPE_CSDN) {
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
        } else if (getType() == MyStringUtils.TYPE_SENG) {
            Elements main = doc.body().getElementsByClass("summary");
            for (Element content : main) {
                MyDatasBean data = new MyDatasBean();
                data.setTitle(content.getElementsByClass("title").text().trim());
                data.setUrl("https://segmentfault.com" + content.getElementsByClass("title").get(0).getElementsByTag("a").attr("href").trim());
                data.setDescribe("\u3000\u3000" + content.getElementsByAttributeValue("class", "excerpt wordbreak hidden-xs").text().trim());
                String ss = content.getElementsByAttributeValue("class", "author list-inline").text().trim();
                String[] t = ss.split("发布于");
                data.setNick(t[0].trim());
                data.setTime(t[1].trim());
                datas.add(data);
            }
        } else if (getType() == MyStringUtils.TYPE_JCODE) {
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
        } else if (getType() == MyStringUtils.TYPE_TUIKU) {
            Elements main = doc.body().getElementById("list_article")
                    .getElementsByClass("single_fake");
            for (Element content : main) {
                MyDatasBean data = new MyDatasBean();
                data.setTitle(content.getElementsByClass("single").get(0).getElementsByTag("a").text());
                data.setUrl("http://www.tuicool.com" + content.getElementsByClass("single").get(0).getElementsByTag("a").attr("href").trim());
                data.setDescribe("\u3000\u3000" + content.getElementsByClass("article_cut").text().trim());
                data.setNick(content.getElementsByAttributeValue("class", "cut cut28").text().trim());
                data.setTime(content.getElementsByTag("span").get(1).text().trim());
                Elements img = content.getElementsByClass("article_thumb");
                if (!img.isEmpty()) {
                    data.setImgUrl(img.get(0).getElementsByTag("img").attr("src").trim());
                } else {
                    data.setImgUrl("");
                }
                datas.add(data);
            }
        } else if (getType() == MyStringUtils.TYPE_HONGHEI) {
            Elements main = doc.body().getElementById("fontzoom").getElementsByClass("AtrListBox");
            for (Element element : main) {
                MyDatasBean data = new MyDatasBean();
                String title = element.getElementsByClass("LTitle").text().trim();
                if (title.equals("")) {
                    title = element.getElementsByClass("ATitle").text().trim();
                }
                data.setTitle(title);
                Elements href = element.getElementsByClass("LTitle");
                if (href.isEmpty()) {
                    href = element.getElementsByClass("ATitle");
                }
                String h = "http://www.2cto.com" + href.get(0).getElementsByTag("a").attr("href").trim();
                data.setUrl(h);
                String ss = element.getElementsByClass("ADesc").text().trim();
                Elements s = element.getElementsByClass("ArtPic");
                data.setDescribe("\u3000\u3000" + ss);
                if (!s.isEmpty()) {
                    data.setImgUrl("http://www.2cto.com" + s.get(0).getElementsByTag("img").attr("src").trim());
                } else {
                    data.setImgUrl("");
                }
                datas.add(data);
            }
        } else if (getType() == MyStringUtils.TYPE_KAIYUAN) {
            Elements blog = doc.body().getElementById("RecentBlogs").getElementsByClass("b");
            for (Element element : blog) {
                MyDatasBean data = new MyDatasBean();
                data.setTitle(element.getElementsByTag("a").attr("title").trim());
                data.setDescribe("\u3000\u3000" + element.getElementsByTag("p").text().trim());
                data.setTime(element.getElementsByClass("date").text().trim());
                data.setUrl(element.getElementsByTag("a").attr("href").trim());
                datas.add(data);
            }
        } else if (getType() == MyStringUtils.TYPE_KAIFAZHE) {
            Elements content = doc.body().getElementById("daily").getElementsByClass("post");
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
        } else if (getType() == MyStringUtils.TYPE_KAN_DAIMA) {
            Elements main = doc.body().getElementsByClass("blog-wrap");
            for (Element element : main) {
                Elements elements = element.getElementsByTag("li");
                for (Element content : elements) {
                    MyDatasBean data = new MyDatasBean();
                    data.setTitle(content.getElementsByClass("title").text().trim());
                    data.setUrl(content.getElementsByTag("a").attr("href").trim());
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
        } else if (getType() == MyStringUtils.TYPE_KAN_ZUJIAN) {
            Elements main = doc.body().getElementsByClass("left-wrap");
            for (Element element : main) {
                Elements elements = element.getElementsByTag("li");
                for (Element content : elements) {
                    MyDatasBean data = new MyDatasBean();
                    data.setTitle(content.getElementsByClass("title").text().trim());
                    data.setUrl(content.getElementsByTag("a").attr("href").trim());
                    data.setImgUrl(content.getElementsByTag("img").attr("src").trim());
                    data.setTitle(content.getElementsByClass("name").text().trim());
                    data.setFrom(content.getElementsByClass("author").text().trim());
                    data.setDescribe("\u3000\u3000" + content.getElementsByClass("description").text().trim());
                    data.setOtherInfo(content.getElementsByClass("otherinfo").text().trim());
                    datas.add(data);
                }
            }
        } else if (getType() == MyStringUtils.TYPE_OPEN) {
            Elements main = doc.body().getElementsByTag("section").first().getElementsByClass("item");
            for (Element content : main) {
                MyDatasBean data = new MyDatasBean();
                data.setTitle(content.getElementsByClass("title").text().trim());
                data.setUrl(content.getElementsByTag("a").attr("href").trim());
                data.setDescribe("\u3000\u3000" + content.getElementsByClass("description").text().trim());
                data.setTime(content.getElementsByClass("meta").first().getElementsByTag("span").text().trim());
                datas.add(data);
            }
        }

    }

    private void showToast(String str) {
        Toast.makeText(getContext(), str, Toast.LENGTH_SHORT).show();
    }

    private String getMobileUrl(String url, String str) {
        Pattern p = Pattern.compile(".*?details/(.*?)f");
        Matcher m = p.matcher(str);
        if (m.find()) {
            MatchResult result = m.toMatchResult();
            return MyStringUtils.URL_CSDN_YD_M + result.group(1);
        }
        return url;
    }

}
