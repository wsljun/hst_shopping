package com.cj.reocrd.view.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.FriendsContract;
import com.cj.reocrd.model.entity.CommentBean;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.presenter.FriendsPresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.adapter.CommentAdapter;
import com.cj.reocrd.view.adapter.ReleaseAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2018/4/13.
 */

public class FriendDetailActivity extends BaseActivity<FriendsPresenter> implements FriendsContract.View {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.friends_icon)
    ImageView friendsIcon;
    @BindView(R.id.friends_username)
    TextView friendsUsername;
    @BindView(R.id.friends_time)
    TextView friendsTime;
    @BindView(R.id.friends_keep)
    ImageView friendsKeep;
    @BindView(R.id.friends_detail)
    TextView friendsDetail;
    @BindView(R.id.friends_imgs)
    RecyclerView friendsImgs;
    @BindView(R.id.friends_all)
    TextView friendsAll;
    @BindView(R.id.friends_message)
    RecyclerView friendsMessage;
    @BindView(R.id.friends_like_iv)
    TextView friendsLikeIv;
    @BindView(R.id.friends_like_rl)
    RelativeLayout friendsLikeRl;
    @BindView(R.id.friends_detail_iv)
    TextView friendsDetailIv;
    @BindView(R.id.friends_detail_rl)
    RelativeLayout friendsDetailRl;
    private int type;
    private int size = 20;  //pageSize
    private int pageno = 0; // 页码
    private List<CommentBean.Comment> cList;
    private CommentAdapter commentAdapter;

    private Friends friends;

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_friends_detail;
    }

    @Override
    public void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        String id = bundle.getString("id");
        type = 1;
        mPresenter.friendDetail(UrlConstants.UrLType.FRIEDNS_DETAIL, id, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.friends_title_detail));
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @OnClick({R.id.title_left, R.id.friends_keep, R.id.friends_like_rl, R.id.friends_detail_rl, R.id.friends_all_ll})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.friends_keep:
                if ("2".equals(friends.getIsfriend())) {
                    //关注
                    type = 4;
                    mPresenter.friendKeep(UrlConstants.UrLType.FRIEDNS_KEEP, uid, friends.getUid());
                } else {
                    //取消关注
                    type = 5;
                    mPresenter.friendUnKeep(UrlConstants.UrLType.FRIEDNS_UNKEEP, uid, friends.getUid());
                }
                break;
            case R.id.friends_like_rl:
                type = 3;
                mPresenter.friendLike(UrlConstants.UrLType.FRIEDNS_LIKE, friends.getId(), uid);
                break;
            case R.id.friends_detail_rl:
                break;
            case R.id.title_left:
                finish();
                break;
            case R.id.friends_all_ll:
                if (friends != null && Integer.parseInt(friends.getCommentnum()) > 0) {
                    type = 2;
                    mPresenter.friendMessage(UrlConstants.UrLType.FRIEDNS_MESSAGE, uid, size, pageno);
                } else {
                    if (friendsMessage.getVisibility() == View.GONE) {
                        friendsMessage.setVisibility(View.VISIBLE);
                    } else {
                        friendsMessage.setVisibility(View.GONE);
                    }
                }
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if ("1".equals(response.getStatusCode())) {
                    friends = (Friends) response.getResults();
                    if (friends != null) {
                        if (!TextUtils.isEmpty(friends.getPhoto())) {
                            ImageLoaderUtils.displayRound(this, friendsIcon, UrlConstants.BASE_URL + friends.getPhoto());
                        }
                        if (!TextUtils.isEmpty(friends.getName())) {
                            friendsUsername.setText(friends.getName());
                        }
                        if (!TextUtils.isEmpty(friends.getCreatetime())) {
                            friendsTime.setText(friends.getCreatetime());
                        }
                        if (!TextUtils.isEmpty(friends.getDetail())) {
                            friendsDetail.setText(friends.getDetail());
                        }
                        List<Friends.UrlBean> list = friends.getImgs();
                        if (list != null && list.size() > 0) {
                            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
                            friendsImgs.setLayoutManager(gridLayoutManager);
                            ReleaseAdapter adapter = new ReleaseAdapter(this, list);
                            friendsImgs.setAdapter(adapter);
                        }
                        if (!TextUtils.isEmpty(friends.getIsfriend())) {
                            if ("1".equals(friends.getIsfriend())) {
                                friendsKeep.setBackgroundResource(R.mipmap.yiguanzhu);
                            } else {
                                friendsKeep.setBackgroundResource(R.mipmap.guanzhu);
                            }
                        }
                        if (!TextUtils.isEmpty(friends.getIslike())) {
                            Drawable drawable;
                            if ("1".equals(friends.getIslike())) {
                                drawable = getResources().getDrawable(R.mipmap.dianzanhong);
                            } else {
                                drawable = getResources().getDrawable(R.mipmap.zanweixuanzhong);
                            }
                            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                            friendsLikeIv.setCompoundDrawables(drawable, null, null, null);
                        }
                        if (!TextUtils.isEmpty(friends.getCommentnum())) {
                            friendsDetailIv.setText(friends.getCommentnum());
                            friendsAll.setText("全部评论(" + friends.getCommentnum() + ")");
                        }
                        if (!TextUtils.isEmpty(friends.getLikenum())) {
                            friendsLikeIv.setText(friends.getLikenum());
                        }
                        if (!TextUtils.isEmpty(friends.getUid())) {
                            if (uid.equals(friends.getUid())) {
                                friendsKeep.setVisibility(View.GONE);
                            } else {
                                friendsKeep.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                } else {
                    ToastUtil.showToastS(this, UrlConstants.UrLType.FRIEDNS_DETAIL + response.getMessage());
                }
                break;
            case 2:
                if ("1".equals(response.getStatusCode())) {
                    CommentBean commentBean = (CommentBean) response.getResults();
                    if (commentBean != null && commentBean.getClist().size() > 0) {
                        commentAdapter = new CommentAdapter(this, commentBean.getClist());
                        friendsMessage.setLayoutManager(new LinearLayoutManager(this));
                        friendsMessage.setAdapter(commentAdapter);
                    }
                } else {
                    ToastUtil.showToastS(this, UrlConstants.UrLType.FRIEDNS_MESSAGE + response.getMessage());
                }
                break;
            case 3:
                if ("1".equals(response.getStatusCode())) {
                    Drawable drawable;
                    if ("1".equals(friends.getIslike())) {
                        friends.setIslike("2");
                        drawable = getResources().getDrawable(R.mipmap.zanweixuanzhong);
                    } else {
                        friends.setIslike("1");
                        drawable = getResources().getDrawable(R.mipmap.dianzanhong);
                    }
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    friendsLikeIv.setCompoundDrawables(drawable, null, null, null);
                } else {
                    ToastUtil.showToastS(this, UrlConstants.UrLType.FRIEDNS_LIKE + response.getMessage());
                }
                break;
            case 4:
                if ("1".equals(response.getStatusCode())) {
                    friendsKeep.setBackgroundResource(R.mipmap.yiguanzhu);
                    friends.setIsfriend("1");
                } else {
                    ToastUtil.showToastS(this, UrlConstants.UrLType.FRIEDNS_KEEP + response.getMessage());
                }
                break;
            case 5:
                if ("1".equals(response.getStatusCode())) {
                    friendsKeep.setBackgroundResource(R.mipmap.guanzhu);
                    friends.setIsfriend("2");
                } else {
                    ToastUtil.showToastS(this, UrlConstants.UrLType.FRIEDNS_UNKEEP + response.getMessage());
                }
                break;
        }

    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(this, msg);
    }


}
