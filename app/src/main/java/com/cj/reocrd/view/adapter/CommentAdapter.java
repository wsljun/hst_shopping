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
import com.cj.reocrd.model.entity.CommentBean;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/4/14.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.MyHolder> {

    private Activity context;
    private final LayoutInflater inflater;
    private List<CommentBean.Comment> mList;

    public CommentAdapter(Activity context, List<CommentBean.Comment> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_all_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        CommentBean.Comment data = mList.get(position);
        if (!TextUtils.isEmpty(data.getPhoto())) {
            ImageLoaderUtils.displayRound(context, (ImageView) holder.commentIcon, UrlConstants.BASE_URL + data.getPhoto());
        }
        if (!TextUtils.isEmpty(data.getName())) {
            holder.commentUsername.setText(data.getName());
        }
        if (!TextUtils.isEmpty(data.getCreatetime())) {
            holder.commentTime.setText(data.getCreatetime());
        }
        if (!TextUtils.isEmpty(data.getComment())) {
            holder.commentDetail.setText(data.getComment());
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment_icon)
        ImageView commentIcon;
        @BindView(R.id.comment_username)
        TextView commentUsername;
        @BindView(R.id.comment_time)
        TextView commentTime;
        @BindView(R.id.comment_detail)
        TextView commentDetail;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
