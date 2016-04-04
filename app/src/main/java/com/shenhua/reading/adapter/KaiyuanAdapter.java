package com.shenhua.reading.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shenhua.reading.R;
import com.shenhua.reading.bean.MyDatasBean;

import java.util.List;

/**
 * Created by Shenhua on 4/2/2016.
 */
public class KaiyuanAdapter extends RecyclerView.Adapter<KaiyuanViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MyDatasBean> datas;
    private OnRecItemClickLisenner lisenner;

    public KaiyuanAdapter(Context context, List<MyDatasBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public KaiyuanViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_kaiyuan_reclist,parent,false);
        KaiyuanViewHolder holder = new KaiyuanViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(KaiyuanViewHolder holder, int position) {
        MyDatasBean bean = datas.get(position);
        holder.title.setText(bean.getTitle());
        holder.describe.setText(bean.getDescribe());
        holder.time.setText(bean.getTime());
        holder.itemView.setTag(bean.getUrl());//将数据保存在Tag中，以便点击时进行获取
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static interface OnRecItemClickLisenner {
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

class KaiyuanViewHolder extends RecyclerView.ViewHolder {

    TextView title, describe, time;

    public KaiyuanViewHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.os_item_title);
        describe = (TextView) itemView.findViewById(R.id.os_item_describe);
        time = (TextView) itemView.findViewById(R.id.os_item_time);
    }
}
