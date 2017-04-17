package com.zhaobo.dbmoudle;

import android.content.Context;
import android.content.ContextWrapper;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.test.suitebuilder.TestMethod;
import android.text.TextUtils;

import com.zhaobo.utilslib.FileUtils;
import com.zhaobo.utilslib.SdCardUtils;

import java.io.File;

/**
 * Created by zhaobo on 17/4/13.
 */

public class GreenDaoContext extends ContextWrapper {

    private String currentUserId = "default";
    private Context mContext;

    /**
     * 可以根据用户名字来创建不同的数据库，防止出现调用错误的现象
     * @param context 系统上下文
     * @param userId 用户的标志
     */
    public GreenDaoContext(Context context,String userId) {
        super(context);
        this.mContext = context;
        if(!TextUtils.isEmpty(userId)){
            this.currentUserId=userId;
        }
    }

    /**
     * 获得数据库路径，如果不存在，则创建对象
     *
     * @param dbName
     */
    @Override
    public File getDatabasePath(String dbName) {
        String path = SdCardUtils.getDatabaseDir(mContext);
        StringBuffer buffer = new StringBuffer();
        buffer.append(path);
        buffer.append(File.separator);
        buffer.append(currentUserId);
        buffer.append(File.separator);
        buffer.append(dbName);
        File file=new File(buffer.toString());
        if(!file.getParentFile().exists()){
            FileUtils.makeFolders(file.getParentFile().getAbsolutePath());
        }
        return new File(buffer.toString());
    }

    /**
     * 重载这个方法，是用来打开SD卡上的数据库的，android 2.3及以下会调用这个方法。
     *
     * @param name
     * @param mode
     * @param factory
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                                               SQLiteDatabase.CursorFactory factory) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);
        return result;
    }

    /**
     * Android 4.0会调用此方法获取数据库。
     *
     * @param name
     * @param mode
     * @param factory
     * @param errorHandler
     * @see android.content.ContextWrapper#openOrCreateDatabase(java.lang.String, int,
     * android.database.sqlite.SQLiteDatabase.CursorFactory,
     * android.database.DatabaseErrorHandler)
     */
    @Override
    public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory,
                                               DatabaseErrorHandler errorHandler) {
        SQLiteDatabase result = SQLiteDatabase.openOrCreateDatabase(getDatabasePath(name), factory);

        return result;
    }

}
