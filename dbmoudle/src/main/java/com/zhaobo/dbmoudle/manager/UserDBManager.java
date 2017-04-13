package com.zhaobo.dbmoudle.manager;

import com.zhaobo.dbmoudle.model.User;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by zhaobo on 17/4/12.
 */

public class UserDBManager extends AbstractDatabaseManager<User,Long> {
    @Override
    protected AbstractDao getAbstractDao() {
        if(daoSession!=null){
            return daoSession.getUserDao();
        }
        return null;
    }
}
