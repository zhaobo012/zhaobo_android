package com.zhaobo.moudledevpro.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.zhaobo.moudledevpro.R;
import com.zhaobo.moudledevpro.widget.SwipeBackLayout;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        SwipeBackLayout swipeBackLayout=new SwipeBackLayout(this);
        swipeBackLayout.attachActivity(this);
        swipeBackLayout.setSwipeBackLayoutEnable(true);
    }
}
