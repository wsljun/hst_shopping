package com.cj.reocrd.view.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.MyContract;
import com.cj.reocrd.model.entity.BannerData;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.HomeBean;
import com.cj.reocrd.model.entity.UserBean;
import com.cj.reocrd.model.entity.Zp;
import com.cj.reocrd.presenter.MyPrresenter;
import com.cj.reocrd.utils.GlideImageLoader;
import com.cj.reocrd.utils.ImageLoaderUtils;
import com.cj.reocrd.utils.SPUtils;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;
import com.cj.reocrd.utils.wechat.WeChatUtils;
import com.cj.reocrd.view.activity.CollectActivity;
import com.cj.reocrd.view.activity.GoodDetailActivity;
import com.cj.reocrd.view.activity.MyMoneyActivity;
import com.cj.reocrd.view.activity.PasswordActivity;
import com.cj.reocrd.view.activity.SearchActivity;
import com.cj.reocrd.view.activity.ShareActivity;
import com.cj.reocrd.view.activity.WebViewActivity;
import com.cj.reocrd.view.activity.ZPActivity;
import com.cj.reocrd.view.adapter.HomeMdssAdapter;
import com.cj.reocrd.view.adapter.HomeShhhAdapter;
import com.cj.reocrd.view.view.verificationCodeView.VerificationCodeView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;


/**
 * Created by Administrator on 2018/3/16.
 */

public class HomeFragment extends BaseFragment<MyPrresenter> implements MyContract.View, OnBannerListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.home_shhh)
    LinearLayout homeShhh;
    @BindView(R.id.home_shhh_recycler)
    RecyclerView homeShhhRecycler;
    @BindView(R.id.home_mdss)
    LinearLayout homeMdss;
    @BindView(R.id.home_lmsj)
    LinearLayout homeLmsj;
    @BindView(R.id.home_mdss_recycler)
    RecyclerView homeMdssRecycler;
    @BindView(R.id.home_lmsj_recycler)
    RecyclerView homeLmsjRecycler;
    @BindView(R.id.home_pzsh)
    LinearLayout homePzsh;
    @BindView(R.id.home_pzsh_recycler)
    RecyclerView homePzshRecycler;
    @BindView(R.id.home_chwl)
    LinearLayout homeChwl;
    @BindView(R.id.home_chwl_recycler)
    RecyclerView homeChwlRecycler;
    @BindView(R.id.home_icon)
    ImageView homeIcon;
    @BindView(R.id.home_refresh)
    SwipeRefreshLayout homeRefresh;
    @BindView(R.id.home_share)
    ImageView homeShare;

    private HomeShhhAdapter homeShhhAdapter;
    private List<GoodsBean> shhhList;
    private HomeMdssAdapter homeMdssAdapter;
    private List<GoodsBean> mdssList;
    private HomeMdssAdapter homePzshAdapter;
    private List<GoodsBean> pzshList;
    private HomeMdssAdapter homeChwlAdapter;
    private List<GoodsBean> chwlList;
    private HomeMdssAdapter homeLmsjAdapter;
    private List<GoodsBean> lmsjList;

    private int size = 20;  //pageSize
    private int pageno = 0; // 页码
    private final static String TAG = "HomeFragment";
    private Bundle goodBundle = new Bundle();
    private List<BannerData> bannerData;
    List<String> images;
    int type;
    private UserBean userBean;

    @Override
    protected void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        super.initData();
        shhhList = new ArrayList<>();
        mdssList = new ArrayList<>();
        pzshList = new ArrayList<>();
        chwlList = new ArrayList<>();
        images = new ArrayList<>();
        lmsjList = new ArrayList<>();
    }

    @Override
    public void initView() {
        homeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                type = 1;
                mPresenter.getHomeData(size, pageno);
            }
        });
        type = 1;
        mPresenter.getHomeData(size, pageno);
        homeRefresh.setRefreshing(true);
    }

    @Override
    public void getArgumentData(Bundle arguments) {
        super.getArgumentData(arguments);
//        ToastUtil.showShort(arguments.getString("key"));
    }

    @Override
    public void putArgumentData(BaseFragment baseFragment, int position) {
        super.putArgumentData(this, position);
        Bundle b = new Bundle();
        b.putCharSequence("key", position + "; This is Home Fragment");
        baseFragment.setArguments(b);
    }

    private void setBannerView(List<String> images) {
        //轮播图
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(images);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setOnBannerListener(this);
        banner.start();
    }

    private void initShhh() {
        homeShhhAdapter = new HomeShhhAdapter(mActivity, shhhList);
        homeShhhRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        homeShhhRecycler.setNestedScrollingEnabled(false);
        homeShhhRecycler.setAdapter(homeShhhAdapter);
        homeShhhAdapter.setOnItemListener(new HomeShhhAdapter.OnItemListener() {
            @Override
            public void doDetailClick(int position) {
                goodBundle.clear();
                goodBundle.putString("goodsID", shhhList.get(position).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
            }

            @Override
            public void doCarClick(int position) {

            }

            @Override
            public void doShareClick(int position) {
                // TODO: 2018/9/20
                WeChatUtils.getInstance().showDialog(getBaseActivity(),shhhList.get(position),uid);
                // shhhList.get(position).getId()
            }
        });
    }

    private void initMdss() {
        homeMdssAdapter = new HomeMdssAdapter(mActivity, mdssList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 4) {
                    return 2;
                }
                if (position >= 4) {
                    return 1;
                }
                return 0;
            }
        });
        homeMdssRecycler.setLayoutManager(gridLayoutManager);
        homeMdssRecycler.setNestedScrollingEnabled(false);
        homeMdssRecycler.setAdapter(homeMdssAdapter);
        homeMdssAdapter.setOnItemListener(new HomeMdssAdapter.OnItemListener() {
            @Override
            public void doDetailClick(int position) {
                goodBundle.clear();
                goodBundle.putString("goodsID", mdssList.get(position).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
            }

        });
    }
    private void initLmsj() {
        homeLmsjAdapter = new HomeMdssAdapter(mActivity, lmsjList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 4) {
                    return 2;
                }
                if (position >= 4) {
                    return 1;
                }
                return 0;
            }
        });
        homeLmsjRecycler.setLayoutManager(gridLayoutManager);
        homeLmsjRecycler.setNestedScrollingEnabled(false);
        homeLmsjRecycler.setAdapter(homeLmsjAdapter);
        homeLmsjAdapter.setOnItemListener(new HomeMdssAdapter.OnItemListener() {
            @Override
            public void doDetailClick(int position) {
                goodBundle.clear();
                goodBundle.putString("goodsID", lmsjList.get(position).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
            }

        });
    }

    private void initPzsh() {
        homePzshAdapter = new HomeMdssAdapter(mActivity, pzshList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 4) {
                    return 2;
                }
                if (position >= 4) {
                    return 1;
                }
                return 0;
            }
        });
        homePzshRecycler.setLayoutManager(gridLayoutManager);
        homePzshRecycler.setNestedScrollingEnabled(false);
        homePzshRecycler.setAdapter(homeMdssAdapter);
        homePzshAdapter.setOnItemListener(new HomeMdssAdapter.OnItemListener() {
            @Override
            public void doDetailClick(int position) {
                goodBundle.clear();
                goodBundle.putString("goodsID", pzshList.get(position).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
            }

        });
    }

    private void initChwl() {
        homeChwlAdapter = new HomeMdssAdapter(mActivity, chwlList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position < 4) {
                    return 2;
                }
                if (position >= 4) {
                    return 1;
                }
                return 0;
            }
        });
        homeChwlRecycler.setLayoutManager(gridLayoutManager);
        homeChwlRecycler.setNestedScrollingEnabled(false);
        homeChwlRecycler.setAdapter(homeChwlAdapter);
        homeChwlAdapter.setOnItemListener(new HomeMdssAdapter.OnItemListener() {
            @Override
            public void doDetailClick(int position) {
                goodBundle.clear();
                goodBundle.putString("goodsID", chwlList.get(position).getId());
                startActivity(GoodDetailActivity.class, goodBundle);
            }

        });
    }


    @OnClick({R.id.home_icon, R.id.home_share, R.id.home_search, R.id.home_index1, R.id.home_index2, R.id.home_index3, R.id.home_index4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.home_icon:
                getMainActivity().getViewPager().setCurrentItem(4);
                break;
            case R.id.home_share:
                Bundle shareImage = new Bundle();
                shareImage.putString("shareImage", userBean.getCodeimg());
                startActivity(ShareActivity.class, shareImage);
                break;
            case R.id.home_search:
                startActivity(SearchActivity.class);
                break;
            case R.id.home_index1:
                getMainActivity().getViewPager().setCurrentItem(1);
                break;
            case R.id.home_index2:
                showPWDDialog();
                break;
            case R.id.home_index3:
                type = 3;
                mPresenter.lotteryCan(UrlConstants.UrLType.LOTTERY_CAN, uid);
                break;
            case R.id.home_index4:
                Bundle bundleCollect = new Bundle();
                bundleCollect.putString("from", "collect");
                startActivity(CollectActivity.class, bundleCollect);
                break;
        }
    }

    //二级密码对话框
    public void showPWDDialog() {
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
                            type = 4;
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

    @Override
    public void onSuccess(Object data) {
        if (homeRefresh.isRefreshing()) {
            homeRefresh.setRefreshing(false);
        }
        ApiResponse response = (ApiResponse) data;
        switch (type) {
            case 1:
                if ("1".equals(response.getStatusCode())) {
                    HomeBean homeBean = (HomeBean) response.getResults();
                    //实惠好货
                    if (null != homeBean.getShhh() && homeBean.getShhh().size() > 0) {
                        shhhList.clear();
                        shhhList.addAll(homeBean.getShhh());
                        initShhh();
                        homeShhh.setVisibility(View.VISIBLE);
                    } else {
                        homeShhh.setVisibility(View.GONE);
                    }
                    //联盟商家
                    if (null != homeBean.getMdss() && homeBean.getMdss().size() > 0) {
                        lmsjList.clear();
                        lmsjList.addAll(homeBean.getMdss());
                        initLmsj();
                        homeLmsj.setVisibility(View.VISIBLE);
                    } else {
                        homeLmsj.setVisibility(View.GONE);
                    }
                    //摩登时尚
                    if (null != homeBean.getMdss() && homeBean.getMdss().size() > 0) {
                        mdssList.clear();
                        mdssList.addAll(homeBean.getMdss());
                        initMdss();
                        homeMdss.setVisibility(View.VISIBLE);
                    } else {
                        homeMdss.setVisibility(View.GONE);
                    }
                    //品质生活
                    if (null != homeBean.getPzsh() && homeBean.getPzsh().size() > 0) {
                        pzshList.clear();
                        pzshList.addAll(homeBean.getPzsh());
                        initPzsh();
                        homePzsh.setVisibility(View.VISIBLE);
                    } else {
                        homePzsh.setVisibility(View.GONE);
                    }
                    //吃喝玩乐
                    if (null != homeBean.getChwl() && homeBean.getChwl().size() > 0) {
                        chwlList.clear();
                        chwlList.addAll(homeBean.getChwl());
                        initChwl();
                        homeChwl.setVisibility(View.VISIBLE);
                    } else {
                        homeChwl.setVisibility(View.GONE);
                    }
                    //轮播图
                    if (null != homeBean.getBlist() && homeBean.getBlist().size() > 0) {
                        images.clear();
                        bannerData = homeBean.getBlist();
                        for (BannerData uri : homeBean.getBlist()) {
                            images.add(UrlConstants.BASE_URL + uri.getImgurl());
                        }
                        setBannerView(images);
                    }
                    //请求个人信息
                    type = 2;
                    mPresenter.getMYHome(UrlConstants.UrLType.MY_HOME, uid);
                }
                break;
            case 2:
                if ("1".equals(response.getStatusCode())) {
                    userBean = (UserBean) response.getResults();
                    if (userBean != null) {
                        if (!TextUtils.isEmpty(userBean.getPhoto())) {
                            ImageLoaderUtils.displayRound(mActivity, homeIcon, UrlConstants.BASE_URL + userBean.getPhoto());
                        }
                        if (!TextUtils.isEmpty(userBean.getCodeimg())) {
                            homeShare.setVisibility(View.VISIBLE);
                        } else {
                            homeShare.setVisibility(View.GONE);
                        }
                    }
                }
                break;
            case 3:
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
            case 4:
                if ("1".equals(response.getStatusCode())) {
                    Bundle bd = new Bundle();
                    bd.putSerializable("user", userBean);
                    startActivity(MyMoneyActivity.class, bd);
                } else {
                    ToastUtil.showToastS(mActivity, response.getMessage());
                }
                break;
        }

    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showShort(msg);
    }

    @Override
    public void OnBannerClick(int position) {
        Bundle b = new Bundle();
        b.putInt(WebViewActivity.BUNDLE_WEBVIEW_TYPE, WebViewActivity.TYPE_HOME_BANNER);
        b.putString(WebViewActivity.BUNDLE_WEBVIEW_URL, bannerData.get(position).getUrl());
        startActivity(WebViewActivity.class, b);
    }


}
