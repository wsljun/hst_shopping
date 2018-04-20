package com.cj.reocrd.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.FriendsContract;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.model.entity.FriendsBean;
import com.cj.reocrd.presenter.FriendsPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.FriendDetailActivity;
import com.cj.reocrd.view.activity.FriendSendActivity;
import com.cj.reocrd.view.adapter.FriendsAdapter;
import com.cj.reocrd.view.view.ProgressWait.ProgressPopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/3/16.
 */

public class FriendsFragment extends BaseFragment<FriendsPresenter> implements FriendsContract.View, FriendsAdapter.OnItemListener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.friends_content)
    RecyclerView friendsContent;

    List<Friends> mDatas;

    private FriendsAdapter friendsAdapter;
    private int size = 20;  //pageSize
    private int pageno = 0; // 页码
    private final static String TAG = "FriendsFragment";
    LinearLayoutManager mLinearLayoutManager;
    private boolean loading = false;
    private int deletePosition;
    private int likePosition;
    private int type;
    ProgressPopupWindow progressPopupWindow;

    public static int PUBLISH = 111;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_friends;
    }

    @Override
    public void initData() {
        super.initData();
        type = 1;
        mPresenter.friendList(UrlConstants.UrLType.FRIEDNS_LIST, size, pageno);
    }

    @Override
    public void initView() {
        titleLeft.setVisibility(View.GONE);
        titleCenter.setText(getString(R.string.friends));
        titleRight.setText(getString(R.string.friends_publish));
        titleRight.setVisibility(View.VISIBLE);
        progressPopupWindow = new ProgressPopupWindow(mActivity);
    }

    private void initRecycleView() {
        friendsAdapter = new FriendsAdapter(mActivity, mDatas);
        mLinearLayoutManager = new LinearLayoutManager(mActivity);
        friendsContent.setLayoutManager(mLinearLayoutManager);
        friendsContent.setHasFixedSize(true);
        friendsContent.setAdapter(friendsAdapter);
        friendsContent.addOnScrollListener(scrollChangeListener);
        friendsContent.setItemAnimator(new DefaultItemAnimator());
        friendsAdapter.setOnItemListener(this);
    }

    RecyclerView.OnScrollListener scrollChangeListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (dy > 0) {
                int visibleItemCount = mLinearLayoutManager.getChildCount();    //得到显示屏幕内的list数量
                int totalItemCount = mLinearLayoutManager.getItemCount();    //得到list的总数量
                int pastVisiblesItems = mLinearLayoutManager.findFirstVisibleItemPosition();//得到显示屏内的第一个list的位置数position
                if (!loading && (visibleItemCount + pastVisiblesItems) >= totalItemCount && totalItemCount >= size * (pageno + 1)) {
                    loading = true;
                    pageno++;
                    initData();
                }
            }
        }
    };

    @OnClick({R.id.title_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_right:
                startActivityForResult(FriendSendActivity.class, FriendsFragment.PUBLISH);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FriendsFragment.PUBLISH) {
            pageno = 0;
            initData();
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (progressPopupWindow != null) {
            progressPopupWindow.dismiss();
        }
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if ("1".equals(response.getStatusCode())) {
                    FriendsBean friendsBean = (FriendsBean) response.getResults();
                    if (friendsBean != null && friendsBean.getList().size() > 0) {
                        if (loading) {
                            mDatas.addAll(friendsBean.getList());
                            friendsAdapter.notifyDataSetChanged();
                            loading = false;
                        } else {
                            mDatas = friendsBean.getList();
                            initRecycleView();
                        }
                    }
                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            case 2:
                if ("1".equals(response.getStatusCode())) {
                    friendsAdapter.removeData(deletePosition);
                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            case 3:
                if ("1".equals(response.getStatusCode())) {
                    friendsAdapter.notifyItemChanged(likePosition, "y");
                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
        }

    }

    @Override
    public void onFailureMessage(String msg) {
        if (progressPopupWindow != null) {
            progressPopupWindow.dismiss();
        }
        ToastUtil.showToastS(mActivity, msg);
    }

    @Override
    public void detailClick(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("id", mDatas.get(position).getId());
        startActivity(FriendDetailActivity.class, bundle);
    }

    @Override
    public void likeClick(int position) {
        likePosition = position;
        type = 3;
        mPresenter.friendKeep(UrlConstants.UrLType.FRIEDNS_LIKE, uid, mDatas.get(position).getId());
    }

    @Override
    public void deleteClick(int position) {
        progressPopupWindow.showPopupWindow();
        deletePosition = position;
        type = 2;
        mPresenter.friendDelete(UrlConstants.UrLType.FRIEDNS_DELETE, mDatas.get(position).getId());
    }
}
