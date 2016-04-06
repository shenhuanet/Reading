package com.shenhua.reading.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shenhua.reading.R;
import com.shenhua.reading.bean.HistoryData;

import java.util.List;

/**
 * Created by shenhua on 4/6/2016.
 */
public class MyListViewAdapter extends BaseAdapter {

    private Context context;
    private List<HistoryData> datas;

    public MyListViewAdapter(Context context, List<HistoryData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.items_home_his, parent, false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.home_item_title);
            holder.describe = (TextView) convertView.findViewById(R.id.home_item_describe);
            holder.time = (TextView) convertView.findViewById(R.id.home_item_time);
            holder.iv = (ImageView) convertView.findViewById(R.id.home_item_iv);
            holder.layout = convertView.findViewById(R.id.home_item_layout);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        HistoryData data = datas.get(position);
        holder.title.setText(data.getTitle());
        holder.describe.setText(data.getDescribe());
        holder.time.setText(data.getTime());
        if (data.getType() == 1) {
            holder.iv.setImageResource(R.mipmap.list_item_bule);
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemBule));
        } else {
            holder.iv.setImageResource(R.mipmap.list_item_yellow);
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.colorItemYellow));
        }
        return convertView;
    }

    class ViewHolder {
        TextView title, describe, time;
        ImageView iv;
        View layout;
    }
}
