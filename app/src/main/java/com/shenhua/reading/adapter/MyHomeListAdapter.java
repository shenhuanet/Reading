package com.shenhua.reading.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.reading.R;
import com.shenhua.reading.bean.HistoryData;

import java.util.List;

/**
 * Created by shenhua on 4/6/2016.
 */
public class MyHomeListAdapter extends RecyclerView.Adapter<MyHomeListAdapter.HomeHolder> implements View.OnClickListener {

    private Context context;
    private List<HistoryData> datas;
    private OnItemClickListener listener;

    public MyHomeListAdapter(Context context, List<HistoryData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public HomeHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.items_home_his, parent, false);
        HomeHolder hodler = new HomeHolder(convertView);
        convertView.setOnClickListener(this);
        return hodler;
    }

    @Override
    public void onBindViewHolder(HomeHolder holder, int position) {
        HistoryData data = datas.get(position);
        holder.title.setText(data.getTitle());
        holder.describe.setText(data.getDescribe());
        holder.time.setText(data.getTime());
        if (data.getType() == 1) {
            holder.iv.setImageResource(R.mipmap.list_item_bule);
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_item_bule));
        } else {
            holder.iv.setImageResource(R.mipmap.list_item_yellow);
            holder.layout.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_item_yellow));
        }
        holder.itemView.setTag(data.getUrl());
    }

    public class HomeHolder extends RecyclerView.ViewHolder {
        TextView title, describe, time;
        ImageView iv;
        View layout;

        public HomeHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.home_item_title);
            describe = (TextView) itemView.findViewById(R.id.home_item_describe);
            time = (TextView) itemView.findViewById(R.id.home_item_time);
            iv = (ImageView) itemView.findViewById(R.id.home_item_iv);
            layout = itemView.findViewById(R.id.home_item_layout);

        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public void onClick(View v) {
        if (listener != null)
            listener.onItemClick(v, (String) v.getTag());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

