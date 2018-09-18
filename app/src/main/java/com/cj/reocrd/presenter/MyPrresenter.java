package com.cj.reocrd.presenter;

import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.BankBean;
import com.cj.reocrd.model.entity.FuliBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.Zp;

import java.util.HashMap;

import okhttp3.MultipartBody;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/30.
 */

public class MyPrresenter extends MyContract.Presenter {
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void getMYHome(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void getUserInfo(String por, String uid) {

    }

    @Override
    public void updatePhoto(String uid, MultipartBody.Part filePart) {
        ApiModel.getInstance().uploadPhoto(uid, filePart, UserBean.class, new ApiCallback<String>() {
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
    public void updateName(String por, String uid, String name) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("name", name);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void updateSex(String por, String uid, String sex) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("sex", sex);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void updatePhone(String por, String uid, String phone, String code) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);   //
        map.put("phone", phone);   //
        map.put("code", code); //
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void getCode(String por, String phoneName, String type) {
        // TODO 获取验证码
        HashMap<String, Object> map = new HashMap<>();
        map.put("phone", phoneName);   //
        map.put("type", type); //
        // TODO test
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void updateIc(String por, String uid, String ic) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);   //
        map.put("ic", ic); //
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void getRatio(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);   //
        ApiModel.getInstance().getData(por, map, BankBean.class, new ApiCallback<String>() {
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
    public void cardList(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);   //
        ApiModel.getInstance().getData(por, map, BankBean.class, new ApiCallback<String>() {
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
    public void myCard(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);   //
        ApiModel.getInstance().getData(por, map, BankBean.class, new ApiCallback<String>() {
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
    public void bindCard(String por, String uid, String bid, String rid, String username, String bankname, String cardsn, String phone) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("bid", bid);
        map.put("rid", rid);
        map.put("username", username);
        map.put("bankname", bankname);
        map.put("cardsn", cardsn);
        map.put("phone", phone);
        ApiModel.getInstance().getData(por, map, BankBean.class, new ApiCallback<String>() {
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
    public void walletGet(String por, String uid, String bid, String money, String type, String moneyType) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        if(null != bid){
            map.put("bid", bid);
        }
        map.put("money", money);
        map.put("type", type);
        map.put("moneytype", moneyType);
        ApiModel.getInstance().getData(por, map, BankBean.class, new ApiCallback<String>() {
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
    public void wealList(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, FuliBean.class, new ApiCallback<String>() {
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
    public void wealConvert(String por, String uid, String wid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("wid", wid);
        ApiModel.getInstance().getData(por, map, FuliBean.class, new ApiCallback<String>() {
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
    public void lotteryLevel(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, Zp.class, new ApiCallback<String>() {
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
    public void lotteryCan(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, Zp.class, new ApiCallback<String>() {
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
    public void lotterySaveResult(String por, String uid, String level, String money) {

    }

    @Override
    public void lotteryGetResult(String por, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        ApiModel.getInstance().getData(por, map, Zp.class, new ApiCallback<String>() {
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
    public void bindZfb(String por, String uid, String alipay) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("alipay", alipay);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void myTeam(String por, int pagesize, int pageno, String uid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("pagesize", pagesize);
        map.put("pageno", pageno);
        ApiModel.getInstance().getData(por, map, HomeBean.class, new ApiCallback<String>() {
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
    public void updatePwd(String por, String uid, String code, String pwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("code", code);
        map.put("pwd", pwd);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void checkPwd(String por, String uid, String pwd) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uid", uid);
        map.put("pwd", pwd);
        ApiModel.getInstance().getData(por, map, UserBean.class, new ApiCallback<String>() {
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
    public void getHomeData(int pageSize, int pageno) {
        HashMap<String,Object> map = new HashMap<>();
        map.put("pagesize",pageSize);
        map.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.GET_HOME_DATA, map
                , HomeBean.class, new ApiCallback<HomeBean>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if (null != apiResponse && isViewAttached()) {
                            mView.onSuccess(apiResponse);
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        if(isViewAttached()){
                            mView.onFailureMessage(t.toString());
                        }
                    }
                });
    }

    @Override
    public void checkUpdate(String version) {
        baseMap.clear();
        baseMap.put("type","1"); // 1,android .2.ios
        baseMap.put("version",version);
        ApiModel.getInstance().getData(UrlConstants.UrLType.CHECK_APP_UPDATE, baseMap
                , HomeBean.class, new ApiCallback<HomeBean>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if(null != apiResponse && isViewAttached()){
                            // "statusCode":“1”,     //1.暂无新版本 2 请更新App
                            if("2".equals(apiResponse.getStatusCode())){
                                HomeBean homeBean = (HomeBean) apiResponse.getResults();
                                mView.onSuccess(homeBean.getAppInfo());
                            }else{
//                                mView.onFailureMessage(apiResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        if(isViewAttached()){
                            mView.onFailureMessage(t.toString());
                        }
                    }
                });
    }

    /**
     * @param search  搜索内容
     * @param sortlabel  排序
     * @param label    标签
     * @param pageSize
     * @param pageno
     */
    public void getSearchData(String search,String sortlabel,String label,int pageSize, int pageno) {
        baseMap.clear();
        baseMap.put("search",search);
        baseMap.put("price",sortlabel);
        baseMap.put("label",label);
        baseMap.put("pagesize",pageSize);
        baseMap.put("pageno",pageno);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_SEARCH, baseMap
                , HomeBean.class, new ApiCallback<HomeBean>() {
                    @Override
                    public void onSuccess(ApiResponse apiResponse) {
                        if(null != apiResponse && isViewAttached()){
                            if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                                HomeBean homeBean = (HomeBean) apiResponse.getResults();
                                mView.onSuccess(homeBean);
                            }else{
                                mView.onFailureMessage(apiResponse.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call call, Throwable t) {
                        if(isViewAttached()){
                            mView.onFailureMessage(t.toString());
                        }
                    }
                });
    }
}
