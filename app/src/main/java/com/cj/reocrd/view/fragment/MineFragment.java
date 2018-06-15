package com.cj.reocrd.view.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.cj.reocrd.view.activity.CollectActivity;
import com.cj.reocrd.view.activity.FuliActivity;
import com.cj.reocrd.view.activity.MyActivity;
import com.cj.reocrd.view.activity.MyFansActivity;
import com.cj.reocrd.view.activity.MyMoneyActivity;
import com.cj.reocrd.view.activity.MyTeamActivity;
import com.cj.reocrd.view.activity.OrderActivity;
import com.cj.reocrd.view.activity.WalletActivity;
import com.cj.reocrd.view.activity.WebViewActivity;
import com.cj.reocrd.view.activity.YongJinActivity;
import com.cj.reocrd.view.activity.ZPActivity;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendMessageToWX;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.mm.sdk.openapi.WXImageObject;
import com.tencent.mm.sdk.openapi.WXMediaMessage;
import com.tencent.mm.sdk.platformtools.Util;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/3/16.
 */

public class MineFragment extends BaseFragment<MyPrresenter> implements MyContract.View,
        SwipeRefreshLayout.OnRefreshListener ,View.OnClickListener {
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
    @BindView(R.id.img_share)
    ImageView imgShare;

    int type;
    private String codeImg;
    private PopupWindow popWindow;
    private View qrView;
    private boolean isTimelineCb; // 是否是发送朋友圈
    private File picFile;
    private UserBean userBean;

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
            R.id.mine_share_url, R.id.mine_userinfo,R.id.mine_cz})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.title_left:
                if(View.VISIBLE == imgShare.getVisibility()){
                    imgShare.setVisibility(View.GONE);
                }else{
                    getMainActivity().getViewPager().setCurrentItem(0);
                }
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
//                ImageLoaderUtils.display(getContext(), imgShare, UrlConstants.BASE_URL + codeImg);
                imgShare.setVisibility(View.VISIBLE);
                imgShare.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        showDialog();
                        return false;
                    }
                });
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
                Bundle bd = new Bundle();
                bd.putSerializable("user",userBean);
                startActivity(MyMoneyActivity.class,bd);
                break;
            default:
                break;
        }
    }
    private Dialog dialog;
    private void showDialog() {
        View view = mActivity.getLayoutInflater().inflate(R.layout.share_choose_dialog, null);
        Button btn_save = (Button) view.findViewById(R.id.btn_save);
        Button btn_share_timeline = (Button) view.findViewById(R.id.btn_share_timeline);
        Button btn_share_fd = (Button) view.findViewById(R.id.btn_share_fd);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);

        btn_save.setOnClickListener(this);
        btn_share_timeline.setOnClickListener(this);
        btn_share_fd.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        dialog = new Dialog(getContext(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = mActivity.getWindowManager().getDefaultDisplay().getHeight();
            // 以下这两句是为了保证按钮可以水平满屏
            wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
            wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            // 设置显示位置
            dialog.onWindowAttributesChanged(wl);
        }
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }



    // todo 分享到微信
    private void showQRCode(){
        if(null == qrView){
            qrView = getLayoutInflater().inflate(R.layout.dialog_qrcode, null,false);
            popWindow=new PopupWindow(qrView, ActivityUtils.getWidth(mActivity),
                    ActivityUtils.getWidth(mActivity),true);
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

    public void sharePicByFile() {
        if (null == picFile) {return;}
        Bitmap bmp = BitmapFactory.decodeFile(picFile.getAbsolutePath());
//        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, true);  // 设置所图；

        SendMessageToWX.Req req = new SendMessageToWX.Req();
//        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        BaseApplication.api.sendReq(req);
        imgShare.setVisibility(View.GONE);
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
                            codeImg = UrlConstants.BASE_URL + userBean.getCodeimg();
                            ImageLoaderUtils.display(getContext(),imgShare,codeImg);
                            saveImage(false);
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
            default:
                break;
        }

    }

    private void saveImage(boolean isToast) {
        if(TextUtils.isEmpty(codeImg)){
            if(isToast){
                ToastUtil.showShort("无法获取图片地址！");
            }
            return;
        }
//        String imgUri  = UrlConstants.BASE_URL + codeImg;
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(null == picFile){
                    Bitmap b = ImageLoaderUtils.getbitmap(codeImg);
                    if(null != b){
                        picFile = ImageLoaderUtils.saveImage(b);
                    }else{
                        if(isToast){
                            ToastUtil.showShort("保存图片失败！");
                        }
                    }
                }
            }
        }).start();
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

    @Override
    public void onClick(View v) {
        // 保存，分享到朋友圈，分享给朋友，取消
        int id = v.getId();
        switch (id){
            case R.id.btn_save:
                if (null == picFile) {
                    //  save img
                    saveImage(true);
                }else{
                    ToastUtil.showShort("保存成功");
                    imgShare.setVisibility(View.GONE);
                }
//                File appDir = new File(Environment.getExternalStorageDirectory(), "hst");
//                File [] fs = appDir.listFiles();
//                picFile = fs[0];
                break;
            case R.id.btn_share_timeline:
                isTimelineCb = true;
                sharePicByFile();
                break;
            case R.id.btn_share_fd:
                isTimelineCb = false;
                sharePicByFile();
                break;
            case R.id.btn_cancel:
                imgShare.setVisibility(View.GONE);
                break;
            default:
                break;
        }
        dialog.dismiss();
    }
}
