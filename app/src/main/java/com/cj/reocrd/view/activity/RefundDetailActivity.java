package com.cj.reocrd.view.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.ApiCallback;
import com.cj.reocrd.api.ApiResponse;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.BaseActivity;
import com.cj.reocrd.model.ApiModel;
import com.cj.reocrd.model.entity.RefundDetail;
import com.cj.reocrd.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;

/**
 * Created by Administrator on 2018/3/18.
 */

public class RefundDetailActivity extends BaseActivity {


    @BindView(R.id.title_left)
    TextView titleLeft;
    @BindView(R.id.title_center)
    TextView titleCenter;
    @BindView(R.id.title_rl)
    RelativeLayout titleRl;
    @BindView(R.id.title_fl)
    FrameLayout titleFl;
    @BindView(R.id.refund_detail_number)
    TextView refundDetailNumber;
    @BindView(R.id.refund_detail_cause)
    TextView refundDetailCause;
    @BindView(R.id.refund_detail_progress)
    TextView refundDetailProgress;
    @BindView(R.id.refund_detail_price)
    TextView refundDetailPrice;
    @BindView(R.id.refund_detail_recycler)
    RecyclerView refundDetailRecycler;
    private String oid, reason, refund_type = "1";
    RefundDetail refundDetail ;

    @Override
    public int getLayoutId() {
        return R.layout.activity_refund_detail;
    }

    @Override
    public void initData() {
        super.initData();
        oid = getIntent().getStringExtra("oid");
        getDetail();
    }

    @Override
    public void initView(){
        titleCenter.setText("退款详情");
        titleLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getDetail(){
        HashMap<String ,Object> map = new HashMap<>();
        map.put("oid",oid);
        ApiModel.getInstance().getData(UrlConstants.UrLType.URL_OREDER_REFUND_DETAIL,map, RefundDetail.class, new ApiCallback() {
            @Override
            public void onSuccess(ApiResponse apiResponse) {
                // 退货中的商品依然在 订单状态不应该显示可申请退货
                if(UrlConstants.SUCCESE_CODE.equals(apiResponse.getStatusCode())){
                    ToastUtil.showShort(apiResponse.getMessage());
                    refundDetail = (RefundDetail) apiResponse.getResults();
                    updateView();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                ToastUtil.showShort(t.toString());
            }
        });
    }

    private void updateView() {
        refundDetailNumber.setText(refundDetail.getOsn());
        refundDetailCause.setText(refundDetail.getReason());
        refundDetailPrice.setText(""+refundDetail.getMoney());
        int status = Integer.parseInt(refundDetail.getStatus());
        setStatus(status);
    }

    /**
     * //(1:已申请 2已取消  3已拒绝 4退款中 5:退款成功 6退款失败
     * @param status
     */
    private void setStatus(int status) {
        String str = "";
        switch (status){
            case 1:
                str = "已申请";
                break;
            case 2:
                str = "已取消";
                break;
            case 3:
                str = "已拒绝";
                break;
            case 4:
                str = "退款中";
                break;
            case 5:
                str = "退款成功";
                break;
            case 6:
                str = "退款失败";
                break;
                default:
                    break;
        }
        refundDetailProgress.setText(str);

    }

    @Override
    public void initPresenter() {

    }

}
