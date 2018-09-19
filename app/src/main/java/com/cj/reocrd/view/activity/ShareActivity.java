package com.cj.reocrd.view.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/9/18.
 */

public class ShareActivity extends BaseActivity<MyPrresenter> implements MyContract.View, View.OnClickListener {
    @BindView(R.id.share_iv)
    ImageView shareIv;

    private String url;
    private Dialog dialog;

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_share;
    }

    @Override
    public void initView() {
        url = UrlConstants.BASE_URL + getIntent().getExtras().getString("shareImage");
        ImageLoaderUtils.display(this, shareIv, url);
        shareIv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showDialog();
                return false;
            }
        });
    }

    private void showDialog() {
        View view = getLayoutInflater().inflate(R.layout.share_choose_dialog, null);
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
            wl.y = getWindowManager().getDefaultDisplay().getHeight();
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

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onClick(View v) {
        // 保存，分享到朋友圈，分享给朋友，取消
        Bitmap bitmap = convertViewToBitmap(shareIv);
        switch (v.getId()) {
            case R.id.btn_save:
                ImageLoaderUtils.saveImageToGallery(this, bitmap);
                break;
            case R.id.btn_share_timeline:
                sharePicByFile(bitmap, true);
                break;
            case R.id.btn_share_fd:
                sharePicByFile(bitmap, false);
                break;
            case R.id.btn_cancel:
                break;
            default:
                break;
        }
        dialog.dismiss();
    }

    public void sharePicByFile(Bitmap pic, boolean isTimelineCb) {
        WXImageObject imgObj = new WXImageObject(pic);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBmp = Bitmap.createScaledBitmap(pic, 100, 100, true);
        pic.recycle();
        msg.thumbData = ImageLoaderUtils.getThumbData(thumbBmp);  // 设置所图；

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        BaseApplication.api.sendReq(req);
    }

    public static Bitmap convertViewToBitmap(View view) {
        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }
}
