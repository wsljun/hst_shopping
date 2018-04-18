package com.cj.reocrd.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.cj.reocrd.R;
import com.cj.reocrd.base.BaseActivity;

public class ConfirmOrderActivity extends BaseActivity {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_confirm_order);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }


    private String oid = "";

    public static Intent newIntent(Context context, String oid) {
        Intent intent = new Intent(context, GoodDetailActivity.class);
        intent.putExtra("oid", oid);
        return intent;
    }

    /**
     * @param context
     * @param oid 订单id
     */
    public static void intentTo(Context context, String oid) {
        context.startActivity(newIntent(context, oid));
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_confirm_order;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        oid = getIntent().getStringExtra("oid");
    }

    @Override
    public void initPresenter() {

    }

}
