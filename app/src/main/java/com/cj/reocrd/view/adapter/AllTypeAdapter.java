package com.cj.reocrd.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.cj.reocrd.R;
import com.cj.reocrd.model.entity.GoodsType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/19.
 */

public class AllTypeAdapter extends RecyclerView.Adapter<AllTypeAdapter.MyHolder> {

    private List<GoodsType> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener;

    public interface  OnItemClickListener  {
       void onItemClick(View view, int position);
    }


    public AllTypeAdapter(Context context, List<GoodsType> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(inflater.inflate(R.layout.item_all_one, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvTypeName.setText(mDatas.get(position).getName());
        if (mOnItemClickListener != null) {
            holder.tvTypeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.goods_type_name)
        TextView tvTypeName;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    /**
     * 改变textView的颜色
     *
     * @param id
     */
    private void changeTextColor(int id) {
//        for (int i = 0; i < toolsTextViews.length; i++) {
//            if (i != id) {
//                toolsTextViews[i].setBackgroundResource(android.R.color.transparent);
//                toolsTextViews[i].setTextColor(0xff000000);
//            }
//        }
//        toolsTextViews[id].setBackgroundResource(android.R.color.white);
//        toolsTextViews[id].setTextColor(0xffff5d5e);
    }
}
