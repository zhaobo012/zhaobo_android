package com.zhaobo.moudledevpro.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaobo.dbmoudle.manager.UserDBManager;
import com.zhaobo.dbmoudle.model.User;
import com.zhaobo.moudledevpro.R;
import com.zhaobo.moudledevpro.base.BaseActivity;
import com.zhaobo.moudledevpro.manager.SystemBarTintManager;
import com.zhaobo.moudledevpro.widget.*;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private static final String velocityTrackerFomat="XVelocityTracker=%f\nYVelocityTracker=%f";
    private Context mContext;
    private VelocityTracker mVelocityTracker;
    private TextView tv_velocityTracker;
    private int maximumFlingVelocity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext=this;
        setContentView(R.layout.activity_main);

        TextView tv= (TextView) findViewById(R.id.tv_hello);
        tv_velocityTracker= (TextView) findViewById(R.id.tv_velocity);

        tv_velocityTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SecondActivity.class));
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(PluginManager.skipOneActivity()!=null){
//                    PluginManager.skipOneActivity().startActivity(mContext);
//                }else{
//                    Toast.makeText(mContext,"null",Toast.LENGTH_SHORT).show();
//                }
                User user=new User();
//                user.setId((long) 1);
                user.setNickname("XiaoFang");
                user.setUsername("XiaoFang");
                User user1=new User();
//                user1.setId((long) 2);
                user1.setNickname("XiaoHong");
                user1.setUsername("XiaoHong");
                UserDBManager userDBManager=new UserDBManager();
                List<User> users=new ArrayList<User>();
                users.add(user);
                users.add(user1);
                User[] usersArr=new User[]{user,user1};
//                boolean success=userDBManager.insert(user);
                User XiaoFang=userDBManager.findUserByName("XiaoFang");
                if(XiaoFang!=null){
                    Toast.makeText(MainActivity.this,"insert:"+XiaoFang.getNickname(),Toast.LENGTH_SHORT).show();
                }

            }
        });

        initVelovityTracker();

    }

    @Override
    protected boolean translucentStatusBar() {
        //是否显示透明的状态栏
        return super.translucentStatusBar();
    }

    private void initVelovityTracker() {
        maximumFlingVelocity=ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(mVelocityTracker==null){
            mVelocityTracker=VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
        VelocityTracker velocityTracker=mVelocityTracker;
        int pointIndex=0;
        switch (event.getActionMasked()){
            case MotionEvent.ACTION_DOWN:

                pointIndex=event.getPointerId(event.getActionIndex());
                break;
            case MotionEvent.ACTION_MOVE:
                velocityTracker.computeCurrentVelocity(1000,maximumFlingVelocity);
                float xVelocity=velocityTracker.getXVelocity(pointIndex);
                float yVelocity=velocityTracker.getYVelocity(pointIndex);
                setText(xVelocity,yVelocity);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                releaseVelocityTraker();
                break;

        }

        return super.onTouchEvent(event);
    }

    private void releaseVelocityTraker() {
        if(null!=mVelocityTracker){
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker=null;
        }
    }

    private void setText(float fX,float fY){
        String velocityText=String.format(velocityTrackerFomat,fX,fY);
        tv_velocityTracker.setText(velocityText);
    }

}
