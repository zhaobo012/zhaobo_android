package com.zhaobo.dbmoudle.manager;

import android.support.annotation.NonNull;
import android.util.Log;

import com.zhaobo.dbmoudle.DBHelper;
import com.zhaobo.dbmoudle.model.DaoSession;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.Iterator;
import java.util.List;

/**
 * Created by zhaobo on 17/4/12.
 */

public abstract class AbstractDatabaseManager<M, K> implements IDatabase<M, K> {
    protected DaoSession daoSession;

    @Override
    public boolean insert(@NonNull M m) {
        try {
            getWriteDaoSession();
            getAbstractDao().insert(m);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("543", e.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public boolean insertInTx(M... entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().insertInTx(entities);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertInTx(List<M> entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().insertInTx(entities);
            return true;
        } catch (Exception e) {
            Log.e("543",e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertOrReplace(M entity) {
        try {
            getWriteDaoSession();
            getAbstractDao().insertOrReplace(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertOrReplaceInTx(M... entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().insertOrReplaceInTx(entities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertOrReplaceInTx(List<M> entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().insertOrReplaceInTx(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKey(@NonNull K k) {
        getWriteDaoSession();
        try {
            getWriteDaoSession();
            getAbstractDao().deleteByKey(k);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKeyInTx(K... entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().deleteByKeyInTx(entities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteByKeyInTx(List<K> entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().deleteByKeyInTx(entities);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteAll() {
        try {
            getWriteDaoSession();
            getAbstractDao().deleteAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(M entity) {
        try {
            getWriteDaoSession();
            getAbstractDao().delete(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteInTx(M... entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().deleteInTx(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteInTx(List<M> entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().deleteInTx(entities);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(M entity) {
        try {
            getWriteDaoSession();
            getAbstractDao().update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateInx(M... entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().updateInTx(entities);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateInx(List<M> entities) {
        try {
            getWriteDaoSession();
            getAbstractDao().updateInTx(entities);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<M> findAll() {

        try {
            getReadDaoSession();
            return getAbstractDao().loadAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public M findById(@NonNull K k) {
        try {
            getReadDaoSession();
            return getAbstractDao().load(k);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<M> findRaw(String where, String... selectionArgs) {
        try {
            getReadDaoSession();
            return getAbstractDao().queryRaw(where, selectionArgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public QueryBuilder<M> queryBuilder() {
        try {
            getReadDaoSession();
            return getAbstractDao().queryBuilder();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private void getReadDaoSession() {
        daoSession = DBHelper.getInstance().openReadableDb();
    }

    private void getWriteDaoSession() {
        daoSession = DBHelper.getInstance().openWritableDb();
    }

    /**
     * 获取Dao
     *
     * @return
     */
    protected abstract AbstractDao<M, K> getAbstractDao();
}
