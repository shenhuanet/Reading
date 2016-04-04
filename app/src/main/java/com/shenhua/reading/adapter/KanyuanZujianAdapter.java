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
 * Created by shenhua on 3/31/2016.
 */
public class KanyuanZujianAdapter extends RecyclerView.Adapter<KanyuanZujianViewHolder> implements View.OnClickListener {

    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
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
        KanyuanZujianViewHolder holder = new KanyuanZujianViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(final KanyuanZujianViewHolder holder, int position) {
        MyDatasBean d = datas.get(position);
        holder.title.setText(d.getTitle());
        holder.describe.setText(d.getDescribe());
        holder.from.setText(d.getFrom());
        holder.other.setText(d.getOtherInfo());
        holder.itemView.setTag(d.getUrl());//标志
        imageLoader.displayImage(d.getImgUrl(), holder.iv, options);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    /**
     * declare interface
     */
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String data);
    }

    /**
     * override onClick
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String) v.getTag());//使用getTag方法获取数据
        }
    }

    /**
     * OnItemClickListener outside call
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

}

class KanyuanZujianViewHolder extends RecyclerView.ViewHolder {
    TextView title, describe, from, other;
    ImageView iv;

    public KanyuanZujianViewHolder(View itemView) {
        super(itemView);
        //findviewbyid
        title = (TextView) itemView.findViewById(R.id.kyzj_item_title);
        describe = (TextView) itemView.findViewById(R.id.kyzj_item_describe);
        from = (TextView) itemView.findViewById(R.id.kyzj_item_from);
        other = (TextView) itemView.findViewById(R.id.kyzj_item_other);
        iv = (ImageView) itemView.findViewById(R.id.kyzj_item_iv);
    }
}
