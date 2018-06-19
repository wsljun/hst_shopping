package com.cj.reocrd.view.adapter;


import android.text.TextUtils;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsBean;
import com.cj.reocrd.model.entity.Team;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.util.List;


/**
 */
public class TeamAdapter extends BaseQuickAdapter {

    public TeamAdapter(int layoutResId, List<Team> listData) {
        super(layoutResId, listData);
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        Team data = (Team) item;
        ImageLoaderUtils.displayRound(mContext, helper.getView(R.id.team_icon), UrlConstants.BASE_URL + data.getPhoto());
        if (TextUtils.isEmpty(data.getName()) || "null".equals(data.getName())) {
            helper.setText(R.id.team_name, "");
        } else {
            helper.setText(R.id.team_name, data.getName());

        }
        helper.setText(R.id.team_phone, data.getPhone());
        helper.setText(R.id.team_p, (position + 1) + "");
        if("1".equals(data.getType())){
            helper.setText(R.id.team_type, "消费商");
        }else{
            helper.setText(R.id.team_type, "普通会员");
        }
    }
}
