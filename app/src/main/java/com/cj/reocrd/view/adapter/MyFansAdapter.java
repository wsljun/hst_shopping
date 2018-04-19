package com.cj.reocrd.view.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.FansBean;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/18.
 */

public class MyFansAdapter extends RecyclerView.Adapter<MyFansAdapter.MyHolder> {

    private Activity context;
    private final LayoutInflater inflater;
    private List<FansBean.Fans> mList;

    public MyFansAdapter(Activity context, List<FansBean.Fans> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_fans, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        FansBean.Fans data = mList.get(position);
        if (!TextUtils.isEmpty(data.getImg())) {
            ImageLoaderUtils.displayRound(context, (ImageView) holder.fansIcon, UrlConstants.BASE_URL + data.getImg());
        }
        if (!TextUtils.isEmpty(data.getName())) {
            holder.fansName.setText(data.getName());
        }
        holder.fansKeep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.keepClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    private MyFansAdapter.OnItemListener mOnItemListener;

    public void setOnItemListener(MyFansAdapter.OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void keepClick(int position);

    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.fans_icon)
        ImageView fansIcon;
        @BindView(R.id.fans_name)
        TextView fansName;
        @BindView(R.id.fans_keep)
        ImageView fansKeep;
        @BindView(R.id.fans_chat)
        ImageView fansChat;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
