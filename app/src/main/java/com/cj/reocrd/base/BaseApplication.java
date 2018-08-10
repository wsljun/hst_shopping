package com.cj.reocrd.base;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;

import com.cj.reocrd.BuildConfig;
import com.cj.reocrd.R;
import com.cj.reocrd.utils.LogUtil;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.view.WelcomeActivity;
import com.cj.reocrd.view.activity.MsgToPActivity;
import com.cj.reocrd.view.activity.MyFansActivity;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.api.model.SimpleCallback;
import com.netease.nim.uikit.api.model.contact.ContactProvider;
import com.netease.nim.uikit.api.model.user.IUserInfoProvider;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.netease.nim.uikit.common.util.sys.ScreenUtil;
import com.netease.nim.uikit.impl.cache.FriendDataCache;
import com.netease.nim.uikit.impl.cache.NimUserInfoCache;
import com.netease.nim.uikit.impl.cache.TeamDataCache;
import com.netease.nim.uikit.impl.preference.UserPreferences;
import com.netease.nim.uikit.support.glide.ImageLoaderKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.mixpush.NIMPushClient;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;
import com.netease.nimlib.sdk.uinfo.model.UserInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.ArrayList;
import java.util.List;

/**
 * MultiDexApplication防止方法数过多
 */
public class BaseApplication extends MultiDexApplication {
    private static BaseApplication baseApplication;
    public static final String APP_ID = "wxb02990e3e223e34a";    //这个APP_ID就是注册APP的时候生成的

    private static final String APP_SECRET = "0a77fdf8f447cee2bdecd66d7e6dd266";

    public static IWXAPI api;      //这个对象是专门用来向微信发送数据的一个重要接口,使用强引用持有,所有的信息发送都是基于这个对象的

    public void registerWeChat(Context context) {   //向微信注册app
        api = WXAPIFactory.createWXAPI(context, APP_ID, true);
        api.registerApp(APP_ID);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();
//        ZXingLibrary.initDisplayOpinion(this);
        // SDK初始化（启动后台服务，若已经存在用户登录信息， SDK 将完成自动登录）
        NIMClient.init(this, loginInfo(), options());
        if (NIMUtil.isMainProcess(this)) {
            // 注意：以下操作必须在主进程中进行
            // 1、UI相关初始化操作
            // 初始化，需要传入用户信息提供者
            NimUIKit.init(this, infoProvider, null);
            // 初始化消息提醒
//            NIMClient.toggleNotification(UserPreferences.getNotificationToggle());
        }
        //微信分享
        registerWeChat(this);
        if (BuildConfig.DEBUG) {
            LogUtil.setShow(true);
        } else {
            LogUtil.setShow(false);
        }
    }

    public static Context getAppContext() {
        return baseApplication;
    }

    // 如果已经存在用户登录信息，返回LoginInfo，否则返回null即可
    private LoginInfo loginInfo() {
        // 从本地读取上次登录成功时保存的用户登录信息
        String account = (String) SPUtils.get(this, SPUtils.SpKey.IM_ACCID, "");
        String token = (String) SPUtils.get(this, SPUtils.SpKey.IM_TOKEN, "");
        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    private IUserInfoProvider infoProvider = new IUserInfoProvider() {
        @Override
        public UserInfo getUserInfo(String account) {
            UserInfo user = NimUserInfoCache.getInstance().getUserInfo(account);
            if (user == null) {
                NimUserInfoCache.getInstance().getUserInfoFromRemote(account, null);
            }

            return user;
        }

        @Override
        public void getUserInfoAsync(String account, SimpleCallback callback) {

        }

        @Override
        public void getUserInfoAsync(List accounts, SimpleCallback callback) {

        }

        @Override
        public List getUserInfo(List accounts) {
            return null;
        }


    };


    // 如果返回值为null，则全部使用默认参数。
    private SDKOptions options() {
        SDKOptions options = new SDKOptions();

        // 如果将新消息通知提醒托管给SDK完成，需要添加以下配置。否则无需设置。
        // 其中notificationSmallIconId必须提供

        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = MsgToPActivity.class;
        config.notificationSmallIconId = R.drawable.ic_stat_notify_msg;
        options.statusBarNotificationConfig = config;

        // 配置保存图片，文件，log等数据的目录
        // 如果options中没有设置这个值，SDK会使用下面代码示例中的位置作为sdk的数据目录。
        // 该目录目前包含log, file, image, audio, video, thumb这6个目录。
        // 如果第三方APP需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

        // 配置是否需要预下载附件缩略图，默认为true
        options.preloadAttach = true;
        return options;
    }

}
