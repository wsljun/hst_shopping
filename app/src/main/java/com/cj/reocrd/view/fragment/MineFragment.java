package com.cj.reocrd.view.fragment;

import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.Zp;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ActivityUtils;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.view.activity.CollectActivity;
import com.cj.reocrd.view.activity.FuliActivity;
import com.cj.reocrd.view.activity.InvoiceActivity;
import com.cj.reocrd.view.activity.MyActivity;
import com.cj.reocrd.view.activity.MyFansActivity;
import com.cj.reocrd.view.activity.MyMoneyActivity;
import com.cj.reocrd.view.activity.MyTeamActivity;
import com.cj.reocrd.view.activity.OrderActivity;
import com.cj.reocrd.view.activity.PasswordActivity;
import com.cj.reocrd.view.activity.PayActivity;
import com.cj.reocrd.view.activity.ShareActivity;
import com.cj.reocrd.view.activity.WalletActivity;
import com.cj.reocrd.view.activity.WebViewActivity;
import com.cj.reocrd.view.activity.YongJinActivity;
import com.cj.reocrd.view.activity.ZPActivity;
import com.cj.reocrd.view.view.VerificationCode.VerificationCodeInput;
import com.cj.reocrd.view.view.verificationCodeView.VerificationCodeView;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.zhy.autolayout.utils.L;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MineFragment extends BaseFragment<MyPrresenter> implements MyContract.View,
        SwipeRefreshLayout.OnRefreshListener {
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
    @BindView(R.id.mine_userinfo)
    TextView mineUserinfo;
    @BindView(R.id.mine_jilu)
    TextView mineJilu;
    @BindView(R.id.mine_wallet_frame)
    FrameLayout mineWalletFrame;
    @BindView(R.id.mine_shouyi_frame)
    FrameLayout mineShouyiFrame;
    @BindView(R.id.mine_qb_cv)
    CardView mineQbCv;
    @BindView(R.id.mine_dd_cv)
    CardView mineDdCv;
    @BindView(R.id.mine_sy)
    TextView mineSy;
    @BindView(R.id.mine_ye)
    TextView mineYe;
    @BindView(R.id.mine_share_url_iv)
    ImageView mineShareUrlIv;

    int type;
    private String codeImg;
    private PopupWindow popWindow;
    private View qrView;
    private boolean isTimelineCb; // 是否是发送朋友圈
    private File picFile;
    private UserBean userBean;
    WalletFragment walletFragment;
    YongJinFragment yongJinFragment;
    int form = 0;//1 钱包  2 收益
    private boolean isPause;

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
            R.id.mine_userinfo, R.id.mine_cz, R.id.mine_share_url_iv, R.id.mine_jilu})
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
//                startActivity(WalletActivity.class);
                form = 1;
                showPWDDialog();
//                initWalletFragment();
//                mineDdCv.setVisibility(View.GONE);
//                mineQbCv.setVisibility(View.GONE);
//                mineWalletFrame.setVisibility(View.VISIBLE);
                break;
            case R.id.mine_yongjin:
//                startActivity(YongJinActivity.class);
                form = 2;
                showPWDDialog();
//                initYongJinFragment();
//                mineDdCv.setVisibility(View.GONE);
//                mineQbCv.setVisibility(View.GONE);
//                mineShouyiFrame.setVisibility(View.VISIBLE);
//                mineUserinfo.setVisibility(View.GONE);
//                mineJilu.setVisibility(View.VISIBLE);
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
            case R.id.mine_share_url_iv:
                Bundle shareImage = new Bundle();
                shareImage.putString("shareImage", userBean.getCodeimg());
                startActivity(ShareActivity.class, shareImage);
                break;
            case R.id.mine_userinfo:
            case R.id.mine_icon:
//            case R.id.mine_userinfo_rl:
                Intent intent = new Intent(mActivity, MyActivity.class);
                startActivity(intent);
                break;
            case R.id.mine_fans://粉丝
                Bundle fans = new Bundle();
                fans.putInt("type", MyFansActivity.FROM_MYFANS);
                startActivity(MyFansActivity.class, fans);
                break;
            case R.id.mine_fuli://福利  2018/09/10 add
//                startActivity(FuliActivity.class);
//                startActivity(InvoiceActivity.class);
                break;
            case R.id.mine_zp://大转盘
                type = 2;
                mPresenter.lotteryCan(UrlConstants.UrLType.LOTTERY_CAN, uid);
//                startActivity(ZPActivity.class);
                break;
            case R.id.mine_keep://以关注
                Bundle keep = new Bundle();
                keep.putInt("type", MyFansActivity.FROM_MYKEEP);
                startActivity(MyFansActivity.class, keep);
                break;
            case R.id.mine_team:
                startActivity(MyTeamActivity.class);
                break;
            case R.id.mine_cz:
                form = 3;
                showPWDDialog();
                break;
            case R.id.mine_jilu:
                Bundle jilu = new Bundle();
                jilu.putString(WebViewActivity.BUNDLE_WEBVIEW_URL, UrlConstants.URL_WEB_YJ + uid);
                jilu.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_YJ);
                startActivity(WebViewActivity.class, jilu);
                break;
            default:
                break;
        }
    }

    //二级密码对话框
    public void showPWDDialog() {
        if(userBean!=null){
            if ("1".equals(userBean.getIspaypwd())) {
                VerificationCodeView codeView = new VerificationCodeView(mActivity);
                codeView.setEtNumber(6);
                codeView.setPwdMode(true);
                codeView.setmEtWidth(80);
                new AlertDialog.Builder(mActivity)
                        .setTitle("输入交易密码")
                        .setView(codeView)
                        .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                type = 3;
                                mPresenter.checkPwd("111", uid, codeView.getInputContent());
                            }
                        })
                        .setNeutralButton("忘记密码", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", userBean.getPhone());
                                startActivity(PasswordActivity.class, bundle);
                            }
                        })
                        .show();
            } else {
                new AlertDialog.Builder(mActivity)
                        .setMessage("为保障您的资金安全，请先设置交易密码")
                        .setNegativeButton("设置密码", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Bundle bundle = new Bundle();
                                bundle.putString("phone", userBean.getPhone());
                                startActivity(PasswordActivity.class, bundle);
                            }
                        })
                        .setNeutralButton("取消", null)
                        .show();
            }
        }

    }

    //加载钱包的fragment
    public void initWalletFragment() {
        walletFragment = new WalletFragment();
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mine_wallet_frame, walletFragment, "walletFragment");
        ft.commit();
    }

    //加载收益的fragment
    public void initYongJinFragment() {
        yongJinFragment = new YongJinFragment();
        android.support.v4.app.FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.mine_shouyi_frame, yongJinFragment, "yongJinFragment");
        ft.commit();
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
                    userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        if (!TextUtils.isEmpty(userBean.getPhoto())) {
                            ImageLoaderUtils.displayRound(mActivity, mineIcon, UrlConstants.BASE_URL + userBean.getPhoto());
                        }
                        if (!TextUtils.isEmpty(userBean.getCodeimg())) {
                            mineShareUrlIv.setVisibility(View.VISIBLE);
                        } else {
                            mineShareUrlIv.setVisibility(View.GONE);
                        }
                        if (!TextUtils.isEmpty(userBean.getName())) {
                            mineUsername.setText(userBean.getName());
                        }
                        if (!TextUtils.isEmpty(userBean.getAlipay())) {
                            SPUtils.put(mActivity, SPUtils.SpKey.ALIPAY_NAME, userBean.getAlipay());
                        }
                        if (!TextUtils.isEmpty(userBean.getNum())) {
                            int num = Integer.parseInt(userBean.getNum());
                            if (num > 0F) {
                                mineTeamNum.setText("我的消费商（" + userBean.getNum() + "）人");
                            } else {

                            }
                        }
                        if (!TextUtils.isEmpty(userBean.getSy())) {
                            mineSy.setText(Utils.strDivide(userBean.getSy()));
                        } else {
                            mineSy.setText("0.0");
                        }
                        if (!TextUtils.isEmpty(userBean.getYe())) {
                            mineYe.setText(Utils.strDivide(userBean.getYe()));
                        } else {
                            mineYe.setText("0.0");
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
            case 3:
                if ("1".equals(response.getStatusCode())) {
                    if (form == 1) {
                        initWalletFragment();
                        mineDdCv.setVisibility(View.GONE);
                        mineQbCv.setVisibility(View.GONE);
                        mineWalletFrame.setVisibility(View.VISIBLE);
                    }
                    if (form == 2) {
                        initYongJinFragment();
                        mineDdCv.setVisibility(View.GONE);
                        mineQbCv.setVisibility(View.GONE);
                        mineShouyiFrame.setVisibility(View.VISIBLE);
                        mineUserinfo.setVisibility(View.GONE);
                        mineJilu.setVisibility(View.VISIBLE);
                    }
                    if (form == 3) {
                        Bundle bd = new Bundle();
                        bd.putSerializable("user", userBean);
                        startActivity(MyMoneyActivity.class, bd);
                    }
                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
            default:
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
        if (mineQbCv.getVisibility() == View.GONE) {
            mineQbCv.setVisibility(View.VISIBLE);
        }
        if (mineDdCv.getVisibility() == View.GONE) {
            mineDdCv.setVisibility(View.VISIBLE);
        }
        if (mineWalletFrame.getVisibility() == View.VISIBLE) {
            mineWalletFrame.setVisibility(View.GONE);
        }
        if (mineShouyiFrame.getVisibility() == View.VISIBLE) {
            mineShouyiFrame.setVisibility(View.GONE);
        }
        if (mineJilu.getVisibility() == View.VISIBLE) {
            mineJilu.setVisibility(View.GONE);
        }
        if (mineUserinfo.getVisibility() == View.GONE) {
            mineUserinfo.setVisibility(View.VISIBLE);
        }
        type = 1;
        mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
    }

    @Override
    public void onPause() {
        super.onPause();
        isPause = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(isPause){
            onRefresh();
        }
    }
}
