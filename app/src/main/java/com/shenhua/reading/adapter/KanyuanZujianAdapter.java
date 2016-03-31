package com.shenhua.reading.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.shenhua.reading.R;
import com.shenhua.reading.bean.KanyuanZujianBean;

import org.apache.http.Header;

import java.io.IOException;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

/**
 * Created by shenhua on 3/31/2016.
 */
public class KanyuanZujianAdapter extends RecyclerView.Adapter<KanyuanZujianViewHolder> implements View.OnClickListener {

    private Context context;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<KanyuanZujianBean> datas;
    private AsyncHttpClient asyncHttpClient;

    public KanyuanZujianAdapter(Context context, List<KanyuanZujianBean> datas) {
        this.context = context;
        this.datas = datas;
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
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        KanyuanZujianBean d = datas.get(position);
        holder.title.setText(d.getTitle());
        holder.describe.setText(d.getDescribe());
        holder.from.setText(d.getFrom());
        holder.other.setText(d.getOtherInfo());
        holder.itemView.setTag(d.getUrlDetail());//标志
        holder.iv.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.gif));
//        asyncHttpClient = new AsyncHttpClient();
//        asyncHttpClient
//                .get(d.getUrlImg(),
//                        new AsyncHttpResponseHandler() {
//
//                            @Override
//                            public void onSuccess(int arg0, Header[] arg1,
//                                                  byte[] arg2) {
//                                // TODO Auto-generated method stub
//                                GifDrawable drawable = null;
//                                try {
//                                    drawable = new GifDrawable(arg2);
//                                } catch (IOException e) {
//                                    // TODO Auto-generated catch block
//                                    e.printStackTrace();
//                                    System.out.println("错误11111IOException");
//                                }
//                                holder.network_gifimageview
//                                        .setBackgroundDrawable(drawable);
//                            }
//
//                            @Override
//                            public void onFailure(int arg0, Header[] arg1,
//                                                  byte[] arg2, Throwable arg3) {
//                                // TODO Auto-generated method stub
//                                System.out.println("错误22222 onFailure 方法");
//
//                            }
//                        });

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
    GifImageView network_gifimageview;

    public KanyuanZujianViewHolder(View itemView) {
        super(itemView);
        //findviewbyid
        title = (TextView) itemView.findViewById(R.id.kyzj_item_title);
        describe = (TextView) itemView.findViewById(R.id.kyzj_item_describe);
        from = (TextView) itemView.findViewById(R.id.kyzj_item_from);
        other = (TextView) itemView.findViewById(R.id.kyzj_item_other);
        iv = (ImageView) itemView.findViewById(R.id.kyzj_item_iv);
        network_gifimageview = (GifImageView) itemView.findViewById(R.id.network_gifimageview);
    }
}
