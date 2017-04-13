package com.zhaobo.dbmoudle.manager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zhaobo.dbmoudle.DBHelper;
import com.zhaobo.dbmoudle.model.DaoSession;

import org.greenrobot.greendao.AbstractDao;

/**
 * Created by zhaobo on 17/4/12.
 */

public abstract class AbstractDatabaseManager<M,K> implements IDatabase<M,K>{
    protected DaoSession daoSession;
    @Override
    public boolean insert(@NonNull M m) {
        getWriteDaoSession();
        try {
            getAbstractDao().insert(m);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("543",e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean deleteByKey(@NonNull K k) {
        getWriteDaoSession();
        try {
            getAbstractDao().deleteByKey(k);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void getReadDaoSession(){
        daoSession=DBHelper.getInstance().openReadableDb();
    }

    private void getWriteDaoSession(){
        daoSession=DBHelper.getInstance().openWritableDb();
    }

     /**
     * 获取Dao
     * @return
     */
     protected abstract AbstractDao<M, K> getAbstractDao();
}
