package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;


import com.cj.reocrd.R;
import com.cj.reocrd.model.entity.AddressBean;
import com.cj.reocrd.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/3/18.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {

    private List<AddressBean> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    private List<CheckBox> checkBoxes;

    public AddressAdapter(Context context, List<AddressBean> datas) {
        this.mContext = context;
        this.mDatas = datas;
        inflater = LayoutInflater.from(mContext);
        checkBoxes = new ArrayList<>();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder holder = new MyHolder(inflater.inflate(R.layout.item_address, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, final int position) {
        holder.addressConSignee.setText(mDatas.get(position).getConsignee());
        holder.addressPhone.setText(mDatas.get(position).getPhone());
        holder.addressDetail.setText(mDatas.get(position).getFuladdress());
        if(mDatas.get(position).getIsdefault().equals("1")){
            holder.addressCheck.setChecked(true);
        }else{
            holder.addressCheck.setChecked(false);
        }
        if(!checkBoxes.contains(holder.addressCheck)){
            checkBoxes.add(holder.addressCheck);
        }
        LogUtil.e("checkbox","size= "+checkBoxes.size());
        holder.addressCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (int i = 0; i < checkBoxes.size() ; i++) {
                    checkBoxes.get(i).setChecked(false);
                }
                buttonView.setChecked(isChecked);
                mOnItemListener.checkClick(position);
            }
        });
        holder.addressDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeData(position);
                mOnItemListener.deleteClick(mDatas.get(position).getId());
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

    public void updateData( List<AddressBean> datas) {
        mDatas = datas;
        notifyDataSetChanged();
    }

    public void addData(int position,AddressBean addressBean){
        mDatas.add(position, addressBean);
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
        void editClick(int position);

        void deleteClick(String id);

        void checkClick(int position);
    }


    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.address_consignee)
        TextView addressConSignee;
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
