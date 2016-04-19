package com.shenhua.reading.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.Types.BoomType;
import com.nightonke.boommenu.Types.ButtonType;
import com.nightonke.boommenu.Types.ClickEffectType;
import com.nightonke.boommenu.Types.DimType;
import com.nightonke.boommenu.Types.OrderType;
import com.nightonke.boommenu.Types.PlaceType;
import com.nightonke.boommenu.Util;
import com.shenhua.reading.R;
import com.shenhua.reading.bean.HistoryData;
import com.shenhua.reading.bean.MyHistoryDBdao;
import com.shenhua.reading.utils.MyStringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ActivityContentActivity extends AppCompatActivity implements BoomMenuButton.OnSubButtonClickListener {

    private String _url = "", _title = "", imgUrl = "";
    private int _type = 0;
    private ProgressBar content_pro;
    private WebView webView;
    private TextView textView;
    private BoomMenuButton boomMenuButton;
    private boolean isInit = false;
    public boolean isLoading = false;
    private String JsTag = "img_view";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        _url = intent.getStringExtra("url");
        _type = intent.getIntExtra("type", 0);
        System.out.println(_url);
        webView.loadUrl(_url);
    }

    @SuppressLint("JavascriptInterface")
    private void initView() {
        final Toolbar toolbar = (Toolbar) findViewById(R.id.content_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityContentActivity.this.finish();
            }
        });
        boomMenuButton = (BoomMenuButton) findViewById(R.id.boombtn);
        boomMenuButton.setOnSubButtonClickListener(this);
        boomMenuButton.setDimType(DimType.DIM_0);
        boomMenuButton.setDuration(500);
        boomMenuButton.setDelay(100);
        boomMenuButton.setRotateDegree(1 * 360);
        boomMenuButton.setAutoDismiss(false);
        boomMenuButton.setShowOrderType(OrderType.DEFAULT);
        boomMenuButton.setHideOrderType(OrderType.DEFAULT);
        boomMenuButton.setClickEffectType(ClickEffectType.RIPPLE);
        content_pro = (ProgressBar) findViewById(R.id.content_pro);
        webView = (WebView) findViewById(R.id.content_web);
        textView = (TextView) findViewById(R.id.content_info);
        this.registerForContextMenu(webView);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setBuiltInZoomControls(false);
        webView.setDrawingCacheEnabled(true);
        webView.addJavascriptInterface(new JavascriptInterface(this), JsTag);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                toolbar.setTitle(title);
            }
        });
        webView.setWebViewClient(new MyWebViewClient());
    }

    public class JavascriptInterface {
        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        public void openImage(String img) {
            System.out.println("clickImgUrl:" + img);
            Toast.makeText(context, "----" + img, Toast.LENGTH_LONG).show();
//            Intent intent = new Intent();
//            intent.putExtra("image", img);
//            intent.setClass(context, ShowWebImageActivity.class);
//            context.startActivity(intent);
//            System.out.println(img);
        }
    }

    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window." + JsTag + ".openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuItem.OnMenuItemClickListener handler = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == 0)
                    Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_SHORT).show();

                return true;
            }
        };
        if (v instanceof WebView) {
            WebView.HitTestResult result = ((WebView) v).getHitTestResult();
            int type = result.getType();
            if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                imgUrl = result.getExtra();
                Toast.makeText(getApplicationContext(), imgUrl, Toast.LENGTH_SHORT).show();
                menu.add(0, 0, 0, "查看图片")
                        .setOnMenuItemClickListener(handler);
                menu.add(0, 1, 0, "保存图片")
                        .setOnMenuItemClickListener(handler);
            }
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(int buttonIndex) {
        MyHistoryDBdao mdao = new MyHistoryDBdao(getApplicationContext());
        mdao.open();
        _title = webView.getTitle();
        _url = webView.getUrl();
        switch (buttonIndex) {
            case 0://copyurl
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip = ClipData.newPlainText("text", _url.trim());
                myClipboard.setPrimaryClip(myClip);
                showToast("已复制网址到剪切板");
                break;
            case 1://shoucang
                HistoryData data = new HistoryData();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date now = new Date();
                data.setTime(dateFormat.format(now));
                data.setUrl(_url);
                data.setDescribe(_url);
                data.setTitle(_title);
                data.setType(_type);
                if (mdao.insertTodb(data) > 0) showToast("收藏成功！");
                else showToast("收藏失败！");
                break;
            case 2://send
                boomMenuButton.dismiss();
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TITLE, "分享Reading文章链接");
                intent.putExtra(Intent.EXTRA_TEXT, "标题：" + _title + "\n链接：" + _url);
                Intent chooserIntent = Intent.createChooser(intent, "请选择一个要发送的应用：");
                if (chooserIntent == null) {
                    return;
                }
                try {
                    startActivity(chooserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    showToast("发送失败，失败原因：未找到该应用。");
                }
                break;
            case 3://scroll to top
                webView.scrollTo(0, 0);
                break;
        }
        if (boomMenuButton.isClosed() == false)
            boomMenuButton.dismiss();
        mdao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_content, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem menuItem = menu.findItem(R.id.action_web);
        if (isLoading) menuItem.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_menu_stop));
        else menuItem.setIcon(ContextCompat.getDrawable(this, R.mipmap.ic_menu_refresh));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_web) {
            if (isLoading) webView.stopLoading();
            else webView.reload();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            isLoading = true;
            invalidateOptionsMenu();
            content_pro.setVisibility(View.VISIBLE);
            textView.setText(url);
            textView.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            isLoading = false;
            invalidateOptionsMenu();
            content_pro.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            addImageClickListner();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public int GetRandomColor() {
        Random random = new Random();
        int p = random.nextInt(MyStringUtils.RandomColors.length);
        return Color.parseColor(MyStringUtils.RandomColors[p]);
    }

    @Override
    public void onBackPressed() {
        if (boomMenuButton.isClosed()) {
            super.onBackPressed();
        } else {
            boomMenuButton.dismiss();
        }
    }

    private void initBoom() {
        int number = 4;
        Drawable[] drawables = new Drawable[number];
        int[] drawablesResource = new int[]{R.mipmap.ic_boom_copy, R.mipmap.ic_boom_like, R.mipmap.ic_boom_send, R.mipmap.ic_boom_top};
        for (int i = 0; i < number; i++)
            drawables[i] = ContextCompat.getDrawable(this, drawablesResource[i]);
        String[] STRINGS = new String[]{"复制网址", "收藏到本地", "发送", "回到顶部"};
        String[] strings = new String[number];
        for (int i = 0; i < number; i++)
            strings[i] = STRINGS[i];
        int[][] colors = new int[number][2];
        for (int i = 0; i < number; i++) {
            colors[i][1] = GetRandomColor();
            colors[i][0] = Util.getInstance().getPressedColor(colors[i][1]);
        }
        boomMenuButton.init(drawables, strings, colors, ButtonType.CIRCLE, BoomType.PARABOLA, PlaceType.CIRCLE_4_1, null, null, null, null, null, null, null);
        boomMenuButton.setSubButtonShadowOffset(Util.getInstance().dp2px(2), Util.getInstance().dp2px(2));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (!isInit) {
            initBoom();
        }
        isInit = true;
    }

    public void showToast(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }
}
