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
 * Created by shenhua on 2016/3/30.
 */
public class KaifazheAdapter extends RecyclerView.Adapter<KaifazheViewHolder> implements View.OnClickListener {

    private Context context;
    private List<MyDatasBean> datas;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

    public KaifazheAdapter(Context context, List<MyDatasBean> datas, ImageLoader imageLoader, DisplayImageOptions options) {
        this.context = context;
        this.datas = datas;
        this.imageLoader = imageLoader;
        this.options = options;
    }

    @Override
    public KaifazheViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.items_kaifazhe_reclist, parent, false);
        KaifazheViewHolder holder = new KaifazheViewHolder(view);
        view.setOnClickListener(this);//将创建的View注册点击事件
        return holder;
    }

    @Override
    public void onBindViewHolder(KaifazheViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        MyDatasBean d = datas.get(position);
        holder.title.setText(d.getTitle());
        holder.nick.setText(d.getNick());
        holder.comment.setText(d.getComment());
        holder.from.setText(d.getFrom());
        holder.thumb.setText(d.getLike());
        holder.itemView.setTag(d.getUrl());//将数据保存在Tag中，以便点击时进行获取
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

class KaifazheViewHolder extends RecyclerView.ViewHolder {
    TextView title, nick, comment, from, thumb;
    ImageView iv;
    public KaifazheViewHolder(View itemView) {
        super(itemView);
        //findviewbyid
        title = (TextView) itemView.findViewById(R.id.kf_item_title);
        nick = (TextView) itemView.findViewById(R.id.kf_item_drecit);
        comment = (TextView) itemView.findViewById(R.id.kf_item_comment);
        from = (TextView) itemView.findViewById(R.id.kf_item_from);
        thumb = (TextView) itemView.findViewById(R.id.kf_item_thumbs);
        iv= (ImageView) itemView.findViewById(R.id.kf_item_iv);
    }
}

