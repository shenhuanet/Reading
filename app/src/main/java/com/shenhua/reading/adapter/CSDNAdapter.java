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
 * Created by shenhua on 2016/3/30.
 */
public class CSDNAdapter extends RecyclerView.Adapter<CSDNAdapter.CsdnViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MyDatasBean> datas;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public CSDNAdapter(Context context, List<MyDatasBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public CsdnViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_csdn_reclist, parent, false);
        CsdnViewHolder holder = new CsdnViewHolder(view);
        view.setOnClickListener(this);//将创建的View注册点击事件
        return holder;
    }

    @Override
    public void onBindViewHolder(CsdnViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        MyDatasBean d = datas.get(position);
        holder.title.setText(d.getTitle());
        holder.describe.setText(d.getDescribe());
        holder.nick.setText(d.getNick());
        holder.time.setText(d.getTime());
        holder.comment.setText(d.getComment());
        holder.read.setText(d.getRead());
        holder.itemView.setTag(d.getUrl());//将数据保存在Tag中，以便点击时进行获取
    }

    public class CsdnViewHolder extends RecyclerView.ViewHolder {
        TextView title, describe, nick, time, comment, read;

        public CsdnViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.cs_item_title);
            describe = (TextView) itemView.findViewById(R.id.cs_item_describe);
            nick = (TextView) itemView.findViewById(R.id.cs_item_nick);
            time = (TextView) itemView.findViewById(R.id.cs_item_time);
            comment = (TextView) itemView.findViewById(R.id.cs_item_comment);
            read = (TextView) itemView.findViewById(R.id.cs_item_read);
        }
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String) v.getTag());//使用getTag方法获取数据
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
