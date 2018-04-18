package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cj.reocrd.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private int type;
    public OrderAdapter(Context context, List<String> datas, int type) {
        this.mContext = context;
        this.mDatas = datas;
        this.type = type;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(inflater.inflate(R.layout.item_undone, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.undoneTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.takeClick(position);
            }
        });
        holder.undoneRefund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.refundClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void takeClick(int position);

        void refundClick(int position);
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.undone_time)
        TextView undoneTime;
        @BindView(R.id.undone_status)
        TextView undoneStatus;
        @BindView(R.id.undone_photos)
        RecyclerView undonePhotos;
        @BindView(R.id.undone_number)
        TextView undoneNumber;
        @BindView(R.id.undone_freight)
        TextView undoneFreight;
        @BindView(R.id.undone_price)
        TextView undonePrice;
        @BindView(R.id.undone_take)
        TextView undoneTake;
        @BindView(R.id.undone_refund)
        TextView undoneRefund;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
