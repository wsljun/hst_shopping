package com.cj.reocrd.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * tv3.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
 */

public class ChongzhiActivity extends BaseActivity<GoodsDetailPresenter> implements GoodsDetailContract.View {

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
    @BindView(R.id.chongzhi_id)
    TextView chongzhiId;
    @BindView(R.id.chongzhi_name)
    TextView chongzhiName;
    @BindView(R.id.chongzhi_card_iv)
    ImageView chongzhiCardIv;
    @BindView(R.id.chongzhi_card_tv)
    TextView chongzhiCardTv;
    @BindView(R.id.chongzhi_card)
    TextView chongzhiCard;
    @BindView(R.id.chongzhi_zhifubao_cb)
    CheckBox chongzhiZhifubaoCb;
    @BindView(R.id.chognzhi_cneter)
    View chognzhiCneter;
    @BindView(R.id.chognzhi_jifen)
    TextView chognzhiJifen;
    @BindView(R.id.chognzhi_dianzibi)
    TextView chognzhiDianzibi;
    @BindView(R.id.chognzhi_fuhao)
    TextView chognzhiFuhao;
    @BindView(R.id.chognzhi_money)
    EditText chognzhiMoney;
    @BindView(R.id.chognzhi_chongzhi)
    TextView chognzhiChongzhi;

    @Override
    public int getLayoutId() {
        return R.layout.activity_chongzhi;
    }

    @Override
    public void initView() {
        titleCenter.setText("转账");
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onFailureMessage(String msg) {

    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void acticonToSubmitOrder(ApiResponse apiResponse) {

    }

    @Override
    public void setCollectImg(boolean stuats) {

    }

    @Override
    public void showComment(List<GoodsCommentBean> goodsCommentBeanList) {

    }


    @OnClick({R.id.chongzhi_card, R.id.chongzhi_zhifubao_cb, R.id.chognzhi_dianzibi, R.id.chognzhi_fuhao, R.id.chognzhi_chongzhi})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chongzhi_card:
                break;
            case R.id.chongzhi_zhifubao_cb:
                break;
            case R.id.chognzhi_dianzibi:
                break;
            case R.id.chognzhi_fuhao:
                break;
            case R.id.chognzhi_chongzhi:
                break;
        }
    }

}
