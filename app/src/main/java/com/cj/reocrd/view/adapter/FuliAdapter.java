package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.model.entity.FuliBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/20.
 */
public class FuliAdapter extends RecyclerView.Adapter<FuliAdapter.MyHolder> {


    private List<FuliBean.Fuli> wlist;
    private List<FuliBean.Fuli> rlist;
    private Context mContext;
    private LayoutInflater inflater;

    public FuliAdapter(Context context, List<FuliBean.Fuli> wlist, List<FuliBean.Fuli> rlist) {
        this.mContext = context;
        this.wlist = wlist;
        this.rlist = rlist;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_fuli, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        FuliBean.Fuli fuli = wlist.get(position);
        holder.fuliDetail.setText(fuli.getDetail());
        holder.fuliStock.setText("所需股份：" + fuli.getStock());
        holder.fuliDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.doClick(position);
            }
        });
        if (rlist != null && rlist.size() > 0) {
            for (FuliBean.Fuli r : rlist) {
                if (fuli.getId().equals(r.getId())) {
                    holder.fuliDo.setClickable(false);
                    holder.fuliBg.setBackgroundResource(R.color.colorTextLineGrey);
                }
            }
        }

    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void doClick(int position);
    }

    @Override
    public int getItemCount() {
        return wlist.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fuli_detail)
        TextView fuliDetail;
        @BindView(R.id.fuli_stock)
        TextView fuliStock;
        @BindView(R.id.fuli_do)
        TextView fuliDo;
        @BindView(R.id.fuli_bg)
        RelativeLayout fuliBg;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
