package com.cj.reocrd.view.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.model.entity.FriendsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/4/10.
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.MyHolder> {

    private Activity context;
    private final LayoutInflater inflater;
    private List<Friends> mList;

    public FriendsAdapter(Activity context, List<Friends> mList) {
        this.context = context;
        this.mList = mList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(inflater.inflate(R.layout.item_friends, parent, false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        Friends data = mList.get(position);
        if (!TextUtils.isEmpty(data.getPhoto())) {
            ImageLoaderUtils.displayRound(context, (ImageView) holder.friendsIcon, UrlConstants.BASE_URL + data.getPhoto());
        }

        if (!TextUtils.isEmpty(data.getName())) {
            holder.friendsUsername.setText(data.getName());
        }
        if (!TextUtils.isEmpty(data.getCreatetime())) {
            holder.friendsTime.setText(data.getCreatetime());
        }
        if (!TextUtils.isEmpty(data.getDetail())) {
            holder.friendsDetail.setText(data.getDetail());
        }
        List<Friends.UrlBean> list = data.getImgs();
        if (list != null && list.size() > 0) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 3);
            holder.friendsImgs.setLayoutManager(gridLayoutManager);
            ReleaseAdapter adapter = new ReleaseAdapter(context, list);
            holder.friendsImgs.setAdapter(adapter);
        }
        if (!TextUtils.isEmpty(data.getUid()) && data.getUid().equals(uid)) {
            holder.friendsDelete.setVisibility(View.VISIBLE);
        } else {
            holder.friendsDelete.setVisibility(View.GONE);
        }

        holder.friendsDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.deleteClick(position);
            }
        });
        holder.friendsLikeRl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.likeClick(position);
            }
        });
        holder.friendsItemLl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemListener.detailClick(position);
            }
        });
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position, List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            if (mList.get(position).getId().equals("n")) {//y:已点赞；n:未点赞
                holder.friendsLikeIv.setBackgroundResource(R.mipmap.zanweixuanzhong);
            } else {
                holder.friendsLikeIv.setBackgroundResource(R.mipmap.dianzanhong);
            }
        }
    }

    private OnItemListener mOnItemListener;

    public void setOnItemListener(OnItemListener onItemListener) {
        this.mOnItemListener = onItemListener;
    }

    public interface OnItemListener {
        void detailClick(int position);

        void likeClick(int position);

        void deleteClick(int position);
    }

    public void removeData(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.friends_icon)
        ImageView friendsIcon;
        @BindView(R.id.friends_username)
        TextView friendsUsername;
        @BindView(R.id.friends_time)
        TextView friendsTime;
        @BindView(R.id.friends_delete)
        ImageView friendsDelete;
        @BindView(R.id.friends_rl)
        RelativeLayout friendsRl;
        @BindView(R.id.friends_detail)
        TextView friendsDetail;
        @BindView(R.id.friends_imgs)
        RecyclerView friendsImgs;

        @BindView(R.id.friends_like_iv)
        ImageView friendsLikeIv;
        @BindView(R.id.friends_like_rl)
        RelativeLayout friendsLikeRl;
        @BindView(R.id.friends_detail_iv)
        ImageView friendsDetailIv;
        @BindView(R.id.friends_detail_rl)
        RelativeLayout friendsDetailRl;

        @BindView(R.id.friends_item_ll)
        LinearLayout friendsItemLl;
        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
