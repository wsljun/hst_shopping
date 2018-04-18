package com.cj.reocrd.view.view.InputOnKeyBoard;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cj.reocrd.R;
import com.cj.reocrd.utils.ToastUtil;
import com.cj.reocrd.utils.Utils;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/4/16.
 */

public class CommentPopupWindow extends PopupWindow {
    //点击“发送”回调
    private CommentSendClick sendClick;

    private RelativeLayout popupCommentParent;
    private EditText popupCommentEdt;
    private TextView popupCommentSendTv;
    private View window;
    private Activity context;

    //构造方法
    public CommentPopupWindow(Activity context, CommentSendClick sendClick) {
        super(context);
        this.context = context;
        this.sendClick = sendClick;
        foundPopup();
    }


    private void foundPopup() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        window = inflater.inflate(R.layout.view_input_keyboard, null);

        popupCommentParent = (RelativeLayout) window.findViewById(R.id.popup_comment_parent);
        popupCommentEdt = (EditText) window.findViewById(R.id.popup_comment_edt);
        popupCommentSendTv = (TextView) window.findViewById(R.id.popup_comment_send_tv);

        setContentView(window);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置弹出动画
//        setAnimationStyle(R.style.popWindow_animation_in2out);
        //使其聚集 ，要想监听菜单里控件的事件就必须要调用此方法
        setFocusable(true);
        //设置允许在外点击消失
        setOutsideTouchable(true);
        //这个是为了点击“Back”也能使其消失，不会影响背景
        setBackgroundDrawable(new BitmapDrawable());
        //显示在键盘上方
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        popupCommentSendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = popupCommentEdt.getText().toString().trim();
                if (result.length() <= 0) {
                    ToastUtil.showToastS(context, "还没有填写任何内容哦！");
                } else {
                    //第二个参数标记是否是弹幕
                    sendClick.onSendClick(v, result);
                    dismiss();
                }
            }
        });
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.hideSoftInput(context, popupCommentParent);
            }
        });
    }

    public void showReveal() {
        if (window == null) {
            ToastUtil.showToastS(context, "创建失败！");
        } else {
            //延时显示软键盘
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    Utils.showKeyboard(context);
                }
            }, 200);
            //显示并设置位置
            showAtLocation(window, Gravity.BOTTOM, 0, 0);
        }
    }

    public interface CommentSendClick {
        //第二个参数标记是不是弹幕，第三个是评论内容
        void onSendClick(View view, String comment);
    }
}
