package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.model.entity.BankBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/26.
 */

public class MyBankAdapter extends RecyclerView.Adapter<MyBankAdapter.MyHolder> {

    private List<BankBean.Bank> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private int selectedPosition = -5; //默认一个参数
    public MyBankAdapter(Context context, List<BankBean.Bank> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_wallet_mybank, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemMybankName.setText(mDatas.get(position).getName());
        holder.itemMybankCode.setText(mDatas.get(position).getCode());
        holder.itemMybankLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.itemClick(position);
                selectedPosition = position; //选择的position赋值给参数，
                notifyItemChanged(selectedPosition);//刷新当前点击item
            }
        });
        holder.itemView.setSelected(selectedPosition == position);
        if (selectedPosition == position) {
            holder.itemMybankLl.setBackgroundResource(R.color.colorWhite);
        } else {
            holder.itemMybankLl.setBackgroundResource(R.color.colorTextLineGrey);
        }
    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void itemClick(int position);
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_mybank_name)
        TextView itemMybankName;
        @BindView(R.id.item_mybank_code)
        TextView itemMybankCode;
        @BindView(R.id.item_mybank_ll)
        LinearLayout itemMybankLl;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
