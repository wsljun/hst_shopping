package com.cj.reocrd.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.base.BaseFragment;
import com.cj.reocrd.contract.GoodsDetailContract;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.model.entity.YongJINBean;
import com.cj.reocrd.presenter.GoodsDetailPresenter;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.view.activity.WebViewActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.cj.reocrd.base.BaseActivity.uid;

/**
 * Created by Administrator on 2018/3/18.
 */

public class YongJinFragment extends BaseFragment<GoodsDetailPresenter> implements GoodsDetailContract.View {

    @BindView(R.id.tv_xs)
    TextView tvXs;
    @BindView(R.id.tv_gl)
    TextView tvGl;
    @BindView(R.id.tv_dl)
    TextView tvDl;
    @BindView(R.id.tv_jq)
    TextView tvJq;
    @BindView(R.id.tv_cj)
    TextView tvCj;
    @BindView(R.id.tv_leader)
    TextView tvLeader;
    @BindView(R.id.tv_yj_total)
    TextView tvYjTotal;
    @BindView(R.id.tv_xz)
    TextView tvXZ;
    @BindView(R.id.tv_ghsztr)
    TextView tvGhsztr;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_yongjin;
    }


    @Override
    public void initView() {
        mPresenter.myYongJin(uid);
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this);
    }

    @Override
    public void onSuccess(Object data) {
        ApiResponse response = (ApiResponse) data;
        if (UrlConstants.SUCCESE_CODE.equals(response.getStatusCode())) {
            YongJINBean yj = (YongJINBean) response.getResults();
            if (yj != null) {
                tvXs.setText(yj.getXs());
                tvGl.setText(yj.getGl());
                tvDl.setText(yj.getDl());
                tvJq.setText(yj.getJq());
                tvCj.setText(yj.getCj());
                tvLeader.setText(yj.getLeader());
                tvXZ.setText(yj.getXz());
                tvGhsztr.setText(yj.getGhsztr());
                // 总佣金
                tvYjTotal.setText("总收益:" + yj.getTotal() + "元");
            }

        } else {
            ToastUtil.showToastS(mActivity, response.getMessage());
        }
    }

    private String getTotal(YongJINBean yj) {
        Double total = Double.valueOf(yj.getXs()) +
                Double.valueOf(yj.getGl()) +
                Double.valueOf(yj.getDl()) +
                Double.valueOf(yj.getJq()) +
                Double.valueOf(yj.getCj()) +
                Double.valueOf(yj.getLeader());
        return String.valueOf(total);
    }

    @Override
    public void onFailureMessage(String msg) {
        ToastUtil.showToastS(mActivity, msg);
    }

    @Override
    public Context getContext() {
        return mActivity;
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

}
