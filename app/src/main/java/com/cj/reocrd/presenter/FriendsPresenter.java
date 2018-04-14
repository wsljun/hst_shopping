package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.FriendsContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.CommentBean;
import com.cj.reocrd.model.entity.Friends;
import com.cj.reocrd.model.entity.FriendsBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.UserBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/4/10.
 */

public class FriendsPresenter extends FriendsContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void friendKeep(String por, String uid, String fid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("fid", fid);
        ApiModel.getInstance().getData(por, map, FriendsBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }
        });
    }

    @Override
    public void friendUnKeep(String por, String uid, String fid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("fid", fid);
        ApiModel.getInstance().getData(por, map, FriendsBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }
        });
    }

    @Override
    public void friendGet(String por, String uid) {

    }

    @Override
    public void friendSend(String uid, String detail, List<MultipartBody.Part> parts) {
        ApiModel.getInstance().uploadPic(uid, detail, parts, String.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void friendDelete(String por, String id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        ApiModel.getInstance().getData(por, map, FriendsBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }
        });
    }

    @Override
    public void friendList(String por, int size, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("pagesize", size);
        map.put("pageno", page);
        ApiModel.getInstance().getData(por, map, FriendsBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void friendDetail(String por, String id, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, Friends.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void friendMessage(String por, String cid, int size, int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("size", size);
        map.put("page", page);
        ApiModel.getInstance().getData(por, map, CommentBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void friendLike(String por, String cid, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, CommentBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }

    @Override
    public void friendComment(String por, String cid, String uid, String comment) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("cid", cid);
        map.put("uid", uid);
        map.put("comment", comment);
        ApiModel.getInstance().getData(por, map, CommentBean.class, new ApiCallback<String>() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                if (null != apiResponse && isViewAttached()) {
                    mView.onSuccess(apiResponse);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                mView.onFailureMessage(t.toString());
            }

        });
    }
}
