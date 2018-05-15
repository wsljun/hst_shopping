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

import java.util.ArrayList;
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
    private List<TextView> toolsTextViews;

    public interface  OnItemClickListener  {
       void onItemClick(View view, int position);
    }


    public AllTypeAdapter(Context context, List<GoodsType> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        toolsTextViews = new ArrayList<>();
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
        if(!toolsTextViews.contains(holder.tvTypeName)){
            holder.tvTypeName.setTag(holder.getLayoutPosition());
            toolsTextViews.add(holder.tvTypeName);
        }
        if(position == 0){
            changeTextColor(position);
        }
        if (mOnItemClickListener != null) {
            holder.tvTypeName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = holder.getLayoutPosition();
                    changeTextColor((Integer) holder.tvTypeName.getTag());
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
     * @param tag
     */
    private void changeTextColor(int tag) {
        for (int i = 0; i < toolsTextViews.size(); i++) {
            int t = (int) toolsTextViews.get(i).getTag();
            if (t != tag) {
                toolsTextViews.get(i).setBackgroundResource(android.R.color.transparent);
                toolsTextViews.get(i).setTextColor(0xff000000);
            }else{
                toolsTextViews.get(i).setBackgroundResource(android.R.color.white);
            }
        }
//        toolsTextViews.get(id).setTextColor(0xffff5d5e);
    }
}
