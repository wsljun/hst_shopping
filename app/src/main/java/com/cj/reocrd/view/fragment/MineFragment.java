package com.cj.reocrd.view.fragment;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.MyActivity;
import com.cj.reocrd.view.activity.MyFansActivity;
import com.cj.reocrd.view.activity.UndoneActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;
import static com.cj.reocrd.view.activity.MyActivity.pNumber;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MineFragment extends BaseFragment<MyPrresenter> implements MyContract.View, SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_right)
    TextView titleRight;
    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.title_fl)
    FrameLayout titleFl;
    @BindView(R.id.mine_icon)
    ImageView mineIcon;
    @BindView(R.id.mine_username)
    TextView mineUsername;
    @BindView(R.id.mine_keep)
    TextView mineKeep;
    @BindView(R.id.mine_fans)
    TextView mineFans;
    @BindView(R.id.mine_all)
    TextView mineAll;
    @BindView(R.id.mine_money_tv)
    TextView mineMoneyTv;
    @BindView(R.id.mine_money)
    FrameLayout mineMoney;
    @BindView(R.id.mine_price_tv)
    TextView minePriceTv;
    @BindView(R.id.mine_price)
    FrameLayout minePrice;
    @BindView(R.id.mine_collect_tv)
    TextView mineCollectTv;
    @BindView(R.id.mine_collect)
    FrameLayout mineCollect;
    @BindView(R.id.mine_history_tv)
    TextView mineHistoryTv;
    @BindView(R.id.mine_history)
    FrameLayout mineHistory;
    @BindView(R.id.mine_help_tv)
    TextView mineHelpTv;
    @BindView(R.id.mine_help)
    FrameLayout mineHelp;
    @BindView(R.id.mine_about_tv)
    TextView mineAboutTv;
    @BindView(R.id.mine_about)
    FrameLayout mineAbout;
    @BindView(R.id.mine_serve_tv)
    TextView mineServeTv;
    @BindView(R.id.mine_serve)
    FrameLayout mineServe;
    @BindView(R.id.mine_pay)
    RelativeLayout minePay;
    @BindView(R.id.mine_send)
    RelativeLayout mineSend;
    @BindView(R.id.mine_confim)
    RelativeLayout mineConfim;
    @BindView(R.id.mine_evaluate)
    RelativeLayout mineEvaluate;
    @BindView(R.id.mine_return)
    RelativeLayout mineReturn;
    @BindView(R.id.mine_swipe)
    SwipeRefreshLayout mineSwipe;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initData() {
        super.initData();
        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.mine));
        mineSwipe.setColorSchemeResources(R.color.colorButton, R.color.colorButton, R.color.colorButton);
        mineSwipe.setOnRefreshListener(this);
    }


    @OnClick({R.id.mine_fans, R.id.mine_userinfo_rl, R.id.title_rl, R.id.title_left, R.id.mine_icon, R.id.mine_all, R.id.mine_pay, R.id.mine_send, R.id.mine_confim, R.id.mine_evaluate, R.id.mine_return, R.id.mine_money, R.id.mine_price, R.id.mine_collect, R.id.mine_history, R.id.mine_help, R.id.mine_about, R.id.mine_serve})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMainActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.mine_icon:
                break;
            case R.id.mine_all:
                break;
            case R.id.mine_pay:
                UndoneActivity.actionActivity(mActivity, UndoneActivity.PAY);
                break;
            case R.id.mine_send:
                UndoneActivity.actionActivity(mActivity, UndoneActivity.SEND);
                break;
            case R.id.mine_confim:
                UndoneActivity.actionActivity(mActivity, UndoneActivity.CONFIM);
                break;
            case R.id.mine_evaluate:
                UndoneActivity.actionActivity(mActivity, UndoneActivity.EVALUATE);
                break;
            case R.id.mine_return:
                break;
            case R.id.mine_money:
                break;
            case R.id.mine_price:
                break;
            case R.id.mine_collect:
                break;
            case R.id.mine_history:
                break;
            case R.id.mine_help:
                break;
            case R.id.mine_about:
                break;
            case R.id.mine_serve:
                break;
            case R.id.mine_userinfo_rl:
                Intent intent = new Intent(mActivity, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_fans:
                startActivity(MyFansActivity.class);
                break;
        }
    }

    @Override
    public void onSuccess(Object data) {
        if (mineSwipe.isRefreshing()) {
            mineSwipe.setRefreshing(false);
        }
        ApiResponse response = (ApiResponse) data;
        if ("1".equals(response.getStatusCode())) {
            UserBean userBean = (UserBean) response.getResults();
            if (userBean != null) {
                if (!TextUtils.isEmpty(userBean.getPhoto())) {
                    ImageLoaderUtils.displayRound(mActivity, mineIcon, UrlConstants.BASE_URL + userBean.getPhoto());
                }
                if (!TextUtils.isEmpty(userBean.getName())) {
                    mineUsername.setText(userBean.getName());
                }
            }
        } else {
            ToastUtil.showToastS(mActivity, response.getMessage());
        }
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    @Override
    public void onRefresh() {
        mineSwipe.setRefreshing(true);
        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
    }
}
