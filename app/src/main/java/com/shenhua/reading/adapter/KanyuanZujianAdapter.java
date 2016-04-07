package com.shenhua.reading.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.shenhua.reading.activity.ActivityContentActivity;
import com.shenhua.reading.bean.MyDatasBean;

import java.util.List;

/**
 * Created by shenhua on 3/31/2016.
 */
public class KanyuanZujianAdapter extends RecyclerView.Adapter<KanyuanZujianAdapter.KanyuanZujianViewHolder> {

    private Context context;
    private List<MyDatasBean> datas;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public KanyuanZujianAdapter(Context context, List<MyDatasBean> datas, ImageLoader imageLoader, DisplayImageOptions options) {
        this.context = context;
        this.datas = datas;
        this.imageLoader = imageLoader;
        this.options = options;
    }

    @Override
    public KanyuanZujianViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_kanyuan_zujian_reclist, parent, false);
        return new KanyuanZujianViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final KanyuanZujianViewHolder holder, final int position) {
        MyDatasBean d = datas.get(position);
        holder.bindDatas(d);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class KanyuanZujianViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View layout;
        TextView title, describe, from, other;
        ImageView iv;

        public void bindDatas(MyDatasBean data) {
            title.setText(data.getTitle());
            describe.setText(data.getDescribe());
            from.setText(data.getFrom());
            other.setText(data.getOtherInfo());
            imageLoader.displayImage(data.getImgUrl(), iv, options);
            itemView.setTag(data.getUrl());//设置item的标志
            iv.setTag(data.getImgUrl());//设置imageview的标志
        }


        public KanyuanZujianViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.kyzj_item_layout);
            title = (TextView) itemView.findViewById(R.id.kyzj_item_title);
            describe = (TextView) itemView.findViewById(R.id.kyzj_item_describe);
            from = (TextView) itemView.findViewById(R.id.kyzj_item_from);
            other = (TextView) itemView.findViewById(R.id.kyzj_item_other);
            iv = (ImageView) itemView.findViewById(R.id.kyzj_item_iv);
            layout.setOnClickListener(this);
            iv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.kyzj_item_layout:
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            context.startActivity(new Intent(context, ActivityContentActivity.class).putExtra("url", itemView.getTag().toString()));
                        }
                    }, 1000);
                    break;
                case R.id.kyzj_item_iv:
                    Toast.makeText(context, iv.getTag().toString(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
