package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/3/30.
 */

public interface MyContract {
    interface View extends BaseView {
        //返回获取的数据

    }

    abstract static class Presenter extends BasePresenter<MyContract.View> {
        public abstract void getMYHome(String por,String uid);

        public abstract void getUserInfo(String por,String uid);

        public abstract void updatePhoto(String uid, MultipartBody.Part filePart);

        public abstract void updateName(String por,String uid, String name);

        public abstract void updateSex(String por,String uid, String sex);

        public abstract void updatePhone(String por,String uid, String phone, String code);

        public abstract void getCode(String por,String  phoneName,String type);

        public abstract void updateIc(String por,String uid,String ic);

        public abstract void getRatio(String por,String uid);
        public abstract void cardList(String por,String uid);
        public abstract void myCard(String por,String uid);
        public abstract void bindCard(String por,String uid,String bid,String rid,String username,String bankname,String cardsn,String phone);
        public abstract void walletGet(String por, String uid, String bid, String money, String type, String moneyType);
        public abstract void wealList(String por,String uid);
        public abstract void wealConvert(String por,String uid,String wid);
        public abstract void lotteryLevel(String por,String uid);
        public abstract void lotteryCan(String por,String uid);
        public abstract void lotterySaveResult(String por,String uid,String level,String money);
        public abstract void lotteryGetResult(String por,String uid);
        public abstract void bindZfb(String por,String uid,String alipay);
        public abstract void myTeam(String por,int pagesize,int pageno,String uid);

        public abstract void updatePwd(String por,String uid,String code,String pwd);
        public abstract void checkPwd(String por,String uid,String pwd);
    }
}
