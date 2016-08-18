package com.shenhua.reading.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shenhua.reading.R;
import com.shenhua.reading.activity.ContentActivity;
import com.shenhua.reading.bean.MyDatasBean;
import com.shenhua.reading.utils.MyStringUtils;

import java.util.List;

/**
 * Created by Shenhua on 4/2/2016.
 */
public class HongheiAdapter extends RecyclerView.Adapter<HongheiAdapter.HongheiViewHolder> {

    private Context context;
    private List<MyDatasBean> datas;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public HongheiAdapter(Context context, List<MyDatasBean> datas, ImageLoader imageLoader, DisplayImageOptions options) {
        this.context = context;
        this.datas = datas;
        this.imageLoader = imageLoader;
        this.options = options;
    }

    @Override
    public HongheiViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_honghei_reclist, parent, false);
        return new HongheiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HongheiViewHolder holder, int position) {
        MyDatasBean bean = datas.get(position);
        holder.bindDatas(bean);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class HongheiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View layout;
        TextView title, describe;
//        TextView time;
        ImageView iv;

        public HongheiViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.hh_item_layout);
            title = (TextView) itemView.findViewById(R.id.hh_item_title);
            describe = (TextView) itemView.findViewById(R.id.hh_item_describe);
//            time = (TextView) itemView.findViewById(R.id.hh_item_time);
            iv = (ImageView) itemView.findViewById(R.id.hh_item_iv);
            layout.setOnClickListener(this);
            iv.setOnClickListener(this);
        }

        public void bindDatas(MyDatasBean bean) {
            title.setText(bean.getTitle());
            describe.setText(bean.getDescribe());
//            time.setText(bean.getTime());
            imageLoader.displayImage(bean.getImgUrl(), iv, options);
            itemView.setTag(bean.getUrl());//将数据保存在Tag中，以便点击时进行获取
            iv.setTag(bean.getImgUrl());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.hh_item_layout:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(new Intent(context, ContentActivity.class).putExtra("url", itemView.getTag().toString()).putExtra("type", MyStringUtils.TYPE_HONGHEI));
                        }
                    }, 1000);
                    break;
                case R.id.hh_item_iv:
                    Toast.makeText(context, iv.getTag().toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}


