package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;


import com.cj.reocrd.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/18.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {

    private List<String> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public AddressAdapter(Context context, List<String> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(inflater.inflate(R.layout.item_address, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.addressCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.checkClick(position);
            }
        });
        holder.addressDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.deleteClick(position);
            }
        });
        holder.addressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.editClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData( int position) {
        mDatas.add(position, "Insert One");
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mDatas.size());
    }

    public void removeData( int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mDatas.size());
    }
    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void checkClick(int position);

        void editClick(int position);

        void deleteClick(int position);
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_name)
        TextView addressName;
        @BindView(R.id.address_phone)
        TextView addressPhone;
        @BindView(R.id.address_detail)
        TextView addressDetail;
        @BindView(R.id.address_check)
        CheckBox addressCheck;
        @BindView(R.id.address_delete)
        TextView addressDelete;
        @BindView(R.id.address_edit)
        TextView addressEdit;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
