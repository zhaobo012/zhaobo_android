package com.zhaobo.dbmoudle;

import android.accessibilityservice.GestureDescription;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zhaobo.dbmoudle.model.DaoMaster;
import com.zhaobo.dbmoudle.model.DaoSession;
import com.zhaobo.dbmoudle.model.User;
import com.zhaobo.dbmoudle.model.UserDao;

import org.greenrobot.greendao.AbstractDao;

import java.util.List;

/**
 * Created by zhaobo on 17/4/11.
 */

public class DBHelper {
    private static final String DB_NAME = "test.db";

    public static DBHelper mInstance;

    private DaoMaster.DevOpenHelper mHelper;
    protected DaoSession daoReadSession;
    protected DaoSession daoWriteSession;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (null == mInstance) {
            synchronized (DBHelper.class) {
                if (null == mInstance) {
                    mInstance = new DBHelper();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化OpenHelper
     *
     * @param context
     */
    public void initOpenHelper(@NonNull Context context) {
        try {
            mHelper = getOpenHelper(context, DB_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("543","exception:"+e.getMessage());
        }
        //openWritableDb();
    }

    /**
     * 初始化OpenHelper     *
     *
     * @param context
     * @param dataBaseName
     */
    public void initOpenHelper(@NonNull Context context, @NonNull String dataBaseName) {
        mHelper = getOpenHelper(context, dataBaseName);
        //openWritableDb();
    }

    /**
     * 在applicaiton中初始化DatabaseHelper
     */
    private DaoMaster.DevOpenHelper getOpenHelper(@NonNull Context context, @Nullable String dataBaseName) {
        closeDbConnections();
        return new DaoMaster.DevOpenHelper(new GreenDaoContext(context), dataBaseName, null);
    }

    /**
     * Query for readable DB
     */
    public DaoSession openReadableDb() throws SQLiteException {
        if (daoReadSession == null) {
            daoReadSession = new DaoMaster(getReadableDatabase()).newSession();
        }
        return daoReadSession;
    }

    /**
     * Query for writable DB
     */
    public DaoSession openWritableDb() throws SQLiteException {
        if (daoWriteSession == null) {
            daoWriteSession = new DaoMaster(getWritableDatabase()).newSession();
        }
        return daoWriteSession;
    }

    /**
     * 拿到可写的数据库对象
     */
    private SQLiteDatabase getWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    /**
     * 拿到可读的数据库对象
     */
    private SQLiteDatabase getReadableDatabase() {
        return mHelper.getReadableDatabase();
    }


    /**
     * 只关闭helper就好,看源码就知道helper关闭的时候会关闭数据库
     */
    public void closeDbConnections() {
        if (mHelper != null) {
            mHelper.close();
            mHelper = null;
        }
        clearDaoSession();
    }

    /**
     * 删除数据库的操作
     */
    public boolean dropDatabase() {
        try {
            openWritableDb();
            // DaoMaster.dropAllTables(database, true); // drops all tables
            // mHelper.onCreate(database); // creates the tables
//          daoSession.deleteAll(BankCardBean.class); // clear all elements
            // from
            // a table
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void clearDaoSession() {
        if (daoReadSession != null) {
            daoReadSession.clear();
            daoReadSession = null;
        }
        if (daoWriteSession != null) {
            daoWriteSession.clear();
            daoWriteSession = null;
        }
    }


}
