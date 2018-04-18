package com.cj.reocrd.view.view.ProgressWait;

import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;


import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;
import com.pnikosis.materialishprogress.ProgressWheel;

import razerdp.basepopup.BasePopupWindow;


public class ProgressPopupWindow extends BasePopupWindow {


    public ProgressPopupWindow(BaseActivity context) {
        super(context);
        setPopupWindowFullScreen(true);
        ProgressWheel progressWheel = (ProgressWheel) findViewById(R.id.progress_wheel);
    }


    @Override
    protected Animation initShowAnimation() {
        return null;
    }

    @Override
    public View getClickToDismissView() {
        return null;
    }


    @Override
    public View onCreatePopupView() {
        View popupById = createPopupById(R.layout.popupwindow_progress);
        popupById.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {

                return false;
            }
        });
        return popupById;
    }

    @Override
    public View initAnimaView() {
        return findViewById(R.id.linear_progress_wheel);
    }


}
