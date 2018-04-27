package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.cj.reocrd.R;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.SimpleClickListener;
import com.cj.reocrd.model.entity.OrderBean;
import com.cj.reocrd.utils.CollectionUtils;
import com.cj.reocrd.view.activity.OrderActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/18.
 */

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyHolder> {

    private List<OrderBean> mDatas;
    private List<OrderBean.OdlistBean> orderGoodsList ;
    private Context mContext;
    private LayoutInflater inflater;
    private int type;
    public OrderAdapter(Context context, List<OrderBean> datas, int type) {
        this.mContext = context;
        this.mDatas = datas;
        this.type = type;
        inflater = LayoutInflater.from(mContext);
    }

    public void updateData(List<OrderBean> orderBeans){
        if(!CollectionUtils.isNullOrEmpty(mDatas)){
            mDatas.clear();
        }
        mDatas.addAll(orderBeans);
        notifyDataSetChanged();
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(inflater.inflate(R.layout.item_undone, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.undoneTime.setText("下单时间: "+mDatas.get(position).getCreatetime());
        holder.undoneNumber.setText("共"+mDatas.get(position).getOdlist().size()+"件商品");
        holder.undonePrice.setText("¥"+mDatas.get(position).getAllamount()+"(包含运费¥"+mDatas.get(position).getExamount()
                +")");
        int status = Integer.parseInt(mDatas.get(position).getStatus());
        //(1.未付款   2 待发货  3待确认，4待评价 5完成  6退款中 7 退款完成 8自行取消)
        switch (status){
            case OrderActivity.ALL:
                break;
            case OrderActivity.PAY:
                holder.tvOrderStatus.setText("待付款");
                holder.tvCancleOrDel.setText("取消订单");
                holder.tvToGo.setText("去付款");
                break;
            case OrderActivity.SEND:
                holder.tvOrderStatus.setText("待发货");
                holder.tvCancleOrDel.setVisibility(View.GONE);
                holder.tvToGo.setText(R.string.undone_take);
                break;
            case OrderActivity.CONFIM:
                holder.tvOrderStatus.setText("待确认");
                holder.tvCancleOrDel.setVisibility(View.GONE);
                holder.tvToGo.setText(R.string.undone_take);
                break;
            case OrderActivity.EVALUATE:
                holder.tvOrderStatus.setText("待评价");
                holder.tvCancleOrDel.setVisibility(View.GONE);
                holder.tvToGo.setText("去评价");
                break;
            default:
                break;

        }
        //使用view tag 保存 status 以方便在点击时做判断
        holder.tvCancleOrDel.setTag(status);
        holder.tvToGo.setTag(status);
        holder.tvCancleOrDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.takeClick(position);
            }
        });
        holder.tvToGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.refundClick(position);
            }
        });
        // 订单中 不同商品图片展示
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.orderPhotosRecycleView.setLayoutManager(linearLayoutManager);
        //设置适配器
        orderGoodsList = new ArrayList<>();
        orderGoodsList.addAll(mDatas.get(position).getOdlist());
        GalleryAdapter mAdapter = new GalleryAdapter(mContext, orderGoodsList);
        holder.orderPhotosRecycleView.setAdapter(mAdapter);
        holder.orderPhotosRecycleView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
                return true;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
                switch (e.getAction()){
                    case MotionEvent.ACTION_UP:
                        mOnItemListener.orderDetail(position);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        holder.orderItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.orderDetail(position);
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
        void orderDetail(int position);
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.undone_time)
        TextView undoneTime;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.order_photos)
        RecyclerView orderPhotosRecycleView;
        @BindView(R.id.undone_number)
        TextView undoneNumber;
        @BindView(R.id.undone_freight)
        TextView undoneFreight;
        @BindView(R.id.undone_price)
        TextView undonePrice;
        @BindView(R.id.tv_btn_togo)
        TextView tvToGo;
        @BindView(R.id.tv_btn_cancle_or_del)
        TextView tvCancleOrDel;
        @BindView(R.id.order_item_ll)
        LinearLayout orderItemLl;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
