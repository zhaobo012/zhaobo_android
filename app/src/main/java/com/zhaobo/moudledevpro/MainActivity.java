package com.zhaobo.moudledevpro;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.zhaobo.dbmoudle.manager.UserDBManager;
import com.zhaobo.dbmoudle.model.User;
import com.zhaobo.dbmoudle.model.UserDao;


public class MainActivity extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        setContentView(R.layout.activity_main);
        TextView tv= (TextView) findViewById(R.id.tv_hello);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(PluginManager.skipOneActivity()!=null){
//                    PluginManager.skipOneActivity().startActivity(mContext);
//                }else{
//                    Toast.makeText(mContext,"null",Toast.LENGTH_SHORT).show();
//                }
                User user=new User();
                user.setId((long)0);
                user.setNickname("XiaoMing");
                user.setUsername("Xiaoming");
                UserDBManager userDBManager=new UserDBManager();
                boolean success=userDBManager.insert(user);
                Toast.makeText(MainActivity.this,"insert:"+success,Toast.LENGTH_SHORT).show();
            }
        });

    }
}
