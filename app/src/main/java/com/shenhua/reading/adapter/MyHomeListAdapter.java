package com.shenhua.reading.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.shenhua.reading.R;
import com.shenhua.reading.bean.HistoryData;

import java.util.List;

/**
 * Created by shenhua on 4/6/2016.
 */
public class MyHomeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener, View.OnLongClickListener {

    private Context context;
    private List<HistoryData> datas;
    private OnItemClickListener clickListener;
    private OnItemLongClickListener longClickListener;
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    private int mHeaderCount = 1;

    public MyHomeListAdapter(Context context, List<HistoryData> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            View headView = LayoutInflater.from(context).inflate(R.layout.items_home_head, parent, false);
            HomeHeaderHolder holder = new HomeHeaderHolder(headView);
            return holder;
        } else {
            View contentView = LayoutInflater.from(context).inflate(R.layout.items_home_his, parent, false);
            HomeContentHolder holder = new HomeContentHolder(contentView);
            contentView.setOnClickListener(this);
            contentView.setOnLongClickListener(this);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HomeContentHolder) {
            HistoryData data = datas.get(position - mHeaderCount);
            ((HomeContentHolder) holder).title.setText(data.getTitle());
            ((HomeContentHolder) holder).describe.setText(data.getUrl());
            ((HomeContentHolder) holder).time.setText(data.getTime());
            if (position % 2 == 0) {
                ((HomeContentHolder) holder).iv.setImageResource(R.mipmap.list_item_bule);
                ((HomeContentHolder) holder).layout.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_item_bule));
            } else {
                ((HomeContentHolder) holder).iv.setImageResource(R.mipmap.list_item_yellow);
                ((HomeContentHolder) holder).layout.setBackground(ContextCompat.getDrawable(context, R.drawable.corners_item_yellow));
            }
            holder.itemView.setTag(data.getUrl());
        } else {
            ((HomeHeaderHolder) holder).btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Click me", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public class HomeContentHolder extends RecyclerView.ViewHolder {
        TextView title, describe, time;
        ImageView iv;
        View layout;

        public HomeContentHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.home_item_title);
            describe = (TextView) itemView.findViewById(R.id.home_item_describe);
            time = (TextView) itemView.findViewById(R.id.home_item_time);
            iv = (ImageView) itemView.findViewById(R.id.home_item_iv);
            layout = itemView.findViewById(R.id.home_item_layout);
        }
    }

    public class HomeHeaderHolder extends RecyclerView.ViewHolder {
        EditText et;
        TextView btn;

        public HomeHeaderHolder(View itemView) {
            super(itemView);
            et = (EditText) itemView.findViewById(R.id.home_et);
            btn = (TextView) itemView.findViewById(R.id.home_btn);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount();
    }

    public int getContentItemCount() {
        return datas.size();
    }

    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderCount != 0 && position < mHeaderCount)
            return ITEM_TYPE_HEADER;
        else
            return ITEM_TYPE_CONTENT;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public void onClick(View v) {
        if (clickListener != null)
            clickListener.onItemClick(v, (String) v.getTag());
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view);
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.longClickListener = listener;
    }

    @Override
    public boolean onLongClick(View v) {
        if (longClickListener != null)
            longClickListener.onItemLongClick(v);
        return true;
    }
}

