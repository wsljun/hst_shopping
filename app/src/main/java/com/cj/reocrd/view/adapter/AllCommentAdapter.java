package com.cj.reocrd.view.adapter;

import android.content.Context;
import android.view.View;

import com.cj.reocrd.R;
import com.cj.reocrd.api.UrlConstants;
import com.cj.reocrd.base.baseadapter.BaseQuickAdapter;
import com.cj.reocrd.base.baseadapter.BaseViewHolder;
import com.cj.reocrd.model.entity.GoodsCommentBean;
import com.cj.reocrd.utils.ImageLoaderUtils;

import java.net.URLConnection;
import java.util.List;

/**
 * Created by Lyndon.Li on 2018/4/25.
 */

public class AllCommentAdapter extends BaseQuickAdapter {
    private List<GoodsCommentBean> mDatas;
    private GoodsCommentBean goodsCommentBean;
    private Context mContext;
    public AllCommentAdapter(Context context,int contentView, List<GoodsCommentBean> data) {
        super(contentView, data);
        this.mContext = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, Object item, int position) {
        goodsCommentBean = (GoodsCommentBean) item;
        ImageLoaderUtils.display(mContext, helper.getView(R.id.comment_icon), UrlConstants.BASE_URL+goodsCommentBean.getPhoto());
        ImageLoaderUtils.display(mContext, helper.getView(R.id.reply_icon), UrlConstants.BASE_URL+goodsCommentBean.getRphoto());
        helper.setText(R.id.comment_username,goodsCommentBean.getName());
        helper.setText(R.id.comment_content,goodsCommentBean.getContent());
        helper.setText(R.id.comment_time,goodsCommentBean.getCreatetime());
        helper.setText(R.id.reply_name,goodsCommentBean.getRname()+"回复：");
        helper.setText(R.id.reply_content,goodsCommentBean.getReply());

    }
}
