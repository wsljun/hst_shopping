package com.cj.reocrd.utils.wechat;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseApplication;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.ToastUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;

import java.io.File;

/**
 * Created by Lyndon.Li on 2018/9/20.
 */

public class WeChatUtils implements View.OnClickListener{
    private boolean isTimelineCb;
    private byte[] bitmapByte;
    private static Bitmap shareGoodBitmap;
    public static WeChatUtils weChatUtils = new WeChatUtils();
    private GoodsBean goodsBean ;
    private String uid;

    public static WeChatUtils getInstance(){
        return weChatUtils;
    }


    private static Dialog dialog;
    public void showDialog(Activity activity,GoodsBean goodsBean,String uid) {
        this.goodsBean = goodsBean;
        this.uid = uid;
        View view = activity.getLayoutInflater().inflate(R.layout.share_choose_dialog, null);
        Button btn_save = (Button) view.findViewById(R.id.btn_save);
        Button btn_share_timeline = (Button) view.findViewById(R.id.btn_share_timeline);
        Button btn_share_fd = (Button) view.findViewById(R.id.btn_share_fd);
        Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
        btn_save.setVisibility(View.GONE);
        btn_share_timeline.setOnClickListener(this);
        btn_share_fd.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        dialog = new Dialog(activity, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        if (window != null) {
            window.setWindowAnimations(R.style.main_menu_animstyle);
            WindowManager.LayoutParams wl = window.getAttributes();
            wl.x = 0;
            wl.y = activity.getWindowManager().getDefaultDisplay().getHeight();
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


    /*
       * 分享链接
       */
    private void shareWebPage(String goodsID, String uid , String title, String desc,String imgpath,
                              boolean isTimelineCb) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = UrlConstants.URL_SHARE+goodsID+"&code="+uid;
        LogUtil.d("webpageUrl",webpage.webpageUrl);
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc ;

//        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher);
//        if(bitmapByte == null) {
//            if(null != shareGoodBitmap){
//                bitmapByte = ImageLoaderUtils.bitmap2Bytes(shareGoodBitmap,32);
//            }else{
//                Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
//            }
//        } else {
//            if(bitmapByte.length > 32){
//                bitmapByte = Util.bmpToByteArray(shareGoodBitmap, true);
//            }
//            msg.thumbData = bitmapByte;
//        }
//        if (null == shareFile) {return;}
        if(null == imgpath || "".equals(imgpath)){
            ToastUtil.showShort("imgpath == null");
          return;
        }
        Bitmap bmp = BitmapFactory.decodeFile(imgpath);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 100, 100, true);
        bmp.recycle();
        msg.thumbData = ImageLoaderUtils.getThumbData(thumbBmp);  // 设置所图；

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.message = msg;
        req.scene = isTimelineCb ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
        BaseApplication.api.sendReq(req);
    }


    @Override
    public void onClick(View v) {
        // 保存，分享到朋友圈，分享给朋友，取消
        int id = v.getId();
        switch (id){
            case R.id.btn_share_timeline:
                isTimelineCb = true;
                dialog.dismiss();
                shareWebPage(goodsBean.getId(),uid,goodsBean.getName(),goodsBean.getIntroduct(),
                        goodsBean.getImgSharePath(),isTimelineCb);
                break;
            case R.id.btn_share_fd:
                isTimelineCb = false;
                dialog.dismiss();
                shareWebPage(goodsBean.getId(),uid,goodsBean.getName(),goodsBean.getIntroduct(),
                        goodsBean.getImgSharePath(),isTimelineCb);
                break;
            case R.id.btn_cancel:
                dialog.dismiss();
                break;
            default:
                break;
        }
    }
}
