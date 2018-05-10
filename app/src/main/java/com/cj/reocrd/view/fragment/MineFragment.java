package com.cj.reocrd.view.fragment;

import android.app.AlertDialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.model.entity.Zp;
import com.cj.reocrd.utils.ActivityUtils;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.CollectActivity;
import com.cj.reocrd.view.activity.FuliActivity;
import com.cj.reocrd.view.activity.MyActivity;
import com.cj.reocrd.view.activity.MyTeamActivity;
import com.cj.reocrd.view.activity.OrderActivity;
import com.cj.reocrd.view.activity.MyFansActivity;
import com.cj.reocrd.view.activity.WalletActivity;
import com.cj.reocrd.view.activity.WalletGetActivity;
import com.cj.reocrd.view.activity.WebViewActivity;
import com.cj.reocrd.view.activity.YongJinActivity;
import com.cj.reocrd.view.activity.ZPActivity;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

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
    @BindView(R.id.mine_team_num)
    TextView mineTeamNum;
    int type;
    private String codeImg;
    private PopupWindow popWindow;
    private View qrView;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public void getArgumentData(Bundle arguments) {
        super.getArgumentData(arguments);
        ToastUtil.showShort(arguments.getString("key"));
    }

    @Override
    public void putArgumentData(BaseFragment baseFragment, int position) {
        super.putArgumentData(this, position);
    }

    @Override
    public void initData() {
        super.initData();
        type = 1;
        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
    }

    @Override
    public void initView() {
        titleCenter.setText(getString(R.string.mine));
        mineSwipe.setColorSchemeResources(R.color.colorButton, R.color.colorButton, R.color.colorButton);
        mineSwipe.setOnRefreshListener(this);
    }


    @OnClick({R.id.mine_team, R.id.mine_keep, R.id.mine_zp, R.id.mine_fuli, R.id.mine_fans, R.id.mine_userinfo_rl,
            R.id.title_rl, R.id.title_left, R.id.mine_icon, R.id.mine_all, R.id.mine_pay, R.id.mine_send,
            R.id.mine_confim, R.id.mine_evaluate, R.id.mine_return, R.id.mine_money, R.id.mine_collect,
            R.id.mine_history, R.id.mine_help, R.id.mine_about, R.id.mine_serve, R.id.mine_yongjin,
            R.id.mine_share_url, R.id.mine_userinfo})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                getMainActivity().getViewPager().setCurrentItem(0);
                break;
            case R.id.mine_all:
                OrderActivity.actionActivity(mActivity, OrderActivity.ORDER_STATUS_ALL);
                break;
            case R.id.mine_pay:
                OrderActivity.actionActivity(mActivity, OrderActivity.ORDER_STATUS_PAY);
                break;
            case R.id.mine_send:
                OrderActivity.actionActivity(mActivity, OrderActivity.ORDER_STATUS_SEND);
                break;
            case R.id.mine_confim:
                OrderActivity.actionActivity(mActivity, OrderActivity.ORDER_STATUS_CONFIM);
                break;
            case R.id.mine_evaluate:
                OrderActivity.actionActivity(mActivity, OrderActivity.ORDER_STATUS_EVALUATE);
                break;
            case R.id.mine_return:
                OrderActivity.actionActivity(mActivity, OrderActivity.ORDER_STATUS_REFUNDING);
                break;
            case R.id.mine_money:
                startActivity(WalletActivity.class);
                break;
            case R.id.mine_yongjin:
                startActivity(YongJinActivity.class);
                break;
            case R.id.mine_collect:
                Bundle bundleCollect = new Bundle();
                bundleCollect.putString("from", "collect");
                startActivity(CollectActivity.class, bundleCollect);
                break;
            case R.id.mine_history:
                Bundle bundleHistory = new Bundle();
                bundleHistory.putString("from", "history");
                startActivity(CollectActivity.class, bundleHistory);
                break;
            case R.id.mine_help:
                Bundle bundle = new Bundle();
                bundle.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_HELP);
                startActivity(WebViewActivity.class, bundle);
                break;
            case R.id.mine_about:
                Bundle b = new Bundle();
                b.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_ABOUT);
                startActivity(WebViewActivity.class, b);
                break;
            case R.id.mine_serve:
                break;
            case R.id.mine_share_url:
//                ClipboardManager cm = (ClipboardManager) mActivity.getSystemService(Context.CLIPBOARD_SERVICE);
//                // 将文本内容放到系统剪贴板里。
//                cm.setText(UrlConstants.URL_SHARE_REGISTER + BaseActivity.uid);
//                ToastUtil.showShort("复制成功，可以发给朋友们了。");
                showQRCode();
                break;
            case R.id.mine_userinfo:
            case R.id.mine_icon:
            case R.id.mine_userinfo_rl:
                Intent intent = new Intent(mActivity, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_fans://粉丝
                Bundle fans = new Bundle();
                fans.putInt("type", MyFansActivity.FROM_MYFANS);
                startActivity(MyFansActivity.class, fans);
                break;
            case R.id.mine_fuli://福利
                startActivity(FuliActivity.class);
                break;
            case R.id.mine_zp://大转盘
                type = 2;
//                mPresenter.lotteryCan(UrlConstants.UrLType.LOTTERY_CAN, uid);
                startActivity(ZPActivity.class);
                break;
            case R.id.mine_keep://以关注
                Bundle keep = new Bundle();
                keep.putInt("type", MyFansActivity.FROM_MYKEEP);
                startActivity(MyFansActivity.class, keep);
                break;
            case R.id.mine_team:
                startActivity(MyTeamActivity.class);
                break;
            default:
                break;
        }
    }

    private void showQRCode(){
        if(null == qrView){
            qrView = getLayoutInflater().inflate(R.layout.dialog_qrcode, null,false);
            popWindow=new PopupWindow(qrView, ActivityUtils.getWidth(mActivity)-300,
                    ActivityUtils.getWidth(mActivity)-300,true);
//            popWindow=new PopupWindow(qrView, WindowManager.LayoutParams.MATCH_PARENT,
//                    WindowManager.LayoutParams.MATCH_PARENT,true);
            ImageView img = qrView.findViewById(R.id.img_qrcode);
            ImageLoaderUtils.display(getContext(), img, UrlConstants.BASE_URL + codeImg);
            ImageView close = qrView.findViewById(R.id.img_qr_close);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popWindow.dismiss();
                }
            });
//            popWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            // 设置PopupWindow是否能响应外部点击事件
            popWindow.setOutsideTouchable(true);
            // 设置PopupWindow是否能响应点击事件
            popWindow.setTouchable(true);
        }
        popWindow.showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }



    @Override
    public void onSuccess(Object data) {
        if (mineSwipe.isRefreshing()) {
            mineSwipe.setRefreshing(false);
        }
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if ("1".equals(response.getStatusCode())) {
                    UserBean userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        if (!TextUtils.isEmpty(userBean.getPhoto())) {
                            ImageLoaderUtils.displayRound(mActivity, mineIcon, UrlConstants.BASE_URL + userBean.getPhoto());
                        }
                        if (!TextUtils.isEmpty(userBean.getCodeimg())) {
                            codeImg = userBean.getCodeimg();
                        }
                        if (!TextUtils.isEmpty(userBean.getName())) {
                            mineUsername.setText(userBean.getName());
                        }
                        if (!TextUtils.isEmpty(userBean.getAlipay())) {
                            SPUtils.put(mActivity, SPUtils.SpKey.ALIPAY_NAME, userBean.getAlipay());
                        }
                        if (!TextUtils.isEmpty(userBean.getNum())) {
                            int num = Integer.parseInt(userBean.getNum());
                            if(num>0F){
                                mineTeamNum.setText("我的消费商（" + userBean.getNum() + "）人");
                            }else{

                            }
                        }

                    }
                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            case 2:
                if ("1".equals(response.getStatusCode())) {
                    Zp zp = (Zp) response.getResults();
                    if (zp != null && !TextUtils.isEmpty(zp.getCan())) {
                        if ("1".equals(zp.getCan())) {
                            startActivity(ZPActivity.class);
                        }
                        if ("2".equals(zp.getCan())) {
                            ToastUtil.showToastS(mActivity, "奖池金额不足");
                        }
                    }

                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
        }

    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    @Override
    public void onRefresh() {
        mineSwipe.setRefreshing(true);
        type = 1;
        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
    }
}
