package com.cj.reocrd.contract;

import com.cj.reocrd.base.BasePresenter;
import com.cj.reocrd.base.BaseView;

import java.util.List;

import okhttp3.MultipartBody;

/**
 * Created by Administrator on 2018/4/10.
 */

public interface FriendsContract {
    interface View extends BaseView {
        //返回获取的数据
    }

    abstract static class Presenter extends BasePresenter<FriendsContract.View> {
        public abstract void friendKeep(String por, String uid, String fid);

        public abstract void friendUnKeep(String por, String uid, String fid);

        public abstract void friendGet(String por, String uid);

        public abstract void friendSend(String uid, String detail, List<MultipartBody.Part> part);

        public abstract void friendDelete(String por, String id);

        public abstract void friendList(String por, int size, int page);

        public abstract void friendDetail(String por, String id, String uid);

        public abstract void friendMessage(String por, String cid, int size, int page);

        public abstract void friendLike(String por, String cid, String uid);

        public abstract void friendComment(String por, String cid, String uid, String comment);
    }
}
