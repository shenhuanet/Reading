package com.shenhua.reading.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.shenhua.reading.R;
import com.shenhua.reading.bean.MyDatasBean;

import java.util.List;

/**
 * Created by Shenhua on 4/2/2016.
 */
public class TuikuAdapter extends RecyclerView.Adapter<TuikuAdapter.TuikuViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MyDatasBean> datas;
    private OnRecItemClickLisenner lisenner;
    private ImageLoader imageLoader = null;
    private DisplayImageOptions options = null;

    public TuikuAdapter(Context context, List<MyDatasBean> datas, ImageLoader imageLoader, DisplayImageOptions options) {
        this.context = context;
        this.datas = datas;
        this.imageLoader = imageLoader;
        this.options = options;
    }

    public TuikuAdapter(Context context, List<MyDatasBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public TuikuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_tuiku_reclist, parent, false);
        TuikuViewHolder holder = new TuikuViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(TuikuViewHolder holder, int position) {
        MyDatasBean bean = datas.get(position);
        holder.title.setText(bean.getTitle());
        holder.describe.setText(bean.getDescribe());
        holder.time.setText(bean.getTime());
        holder.nick.setText(bean.getNick());
        holder.itemView.setTag(bean.getUrl());//将数据保存在Tag中，以便点击时进行获取
        if (imageLoader != null && options != null)
            imageLoader.displayImage(bean.getImgUrl(), holder.iv, options);
    }

    public class TuikuViewHolder extends RecyclerView.ViewHolder {

        TextView title, describe, time, nick;
        ImageView iv;

        public TuikuViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tk_item_title);
            describe = (TextView) itemView.findViewById(R.id.tk_item_describe);
            time = (TextView) itemView.findViewById(R.id.tk_item_time);
            nick = (TextView) itemView.findViewById(R.id.tk_item_nick);
            iv = (ImageView) itemView.findViewById(R.id.tk_item_iv);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnRecItemClickLisenner {
        void onItemClick(View view, String data);
    }

    @Override
    public void onClick(View v) {
        if (lisenner != null) {
            lisenner.onItemClick(v, (String) v.getTag());
        }
    }

    public void setOnRecItemClickLisenner(OnRecItemClickLisenner lisenner) {
        this.lisenner = lisenner;
    }
}