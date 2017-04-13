package com.zhaobo.utilslib;

import android.content.Context;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;

/**
 * Created by zhaobo on 17/4/13.
 */

public class SdCardUtils {

    /**
     * 获取存储卡下的爱驴的路径
     * @return
     */
    public static String getExternalStorageDir(){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String ailvPath=Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"ailvgo";
            FileUtils.makeFolders(ailvPath);
            return ailvPath;
        }
        return null;
    }

    /**
     * 获取资源目录
     * @return
     */
    public static String getSdcardResDir(@NonNull Context context){
        String resPath=getExternalStorageDir();
        if(resPath==null){
            resPath=getAppCacheDir(context);
        }
        resPath=resPath+File.separator+"res";
        FileUtils.makeFolders(resPath);
        return resPath;
    }

    /**
     * 获取存储卡mp3目录
     * @return
     */
    public static String getSdcardMp3Dir(@NonNull Context context){
        String mp3Path=getExternalStorageDir();
        if(mp3Path==null){
            mp3Path=getAppCacheDir(context);
        }
        mp3Path=mp3Path+File.separator+"mp3";
        FileUtils.makeFolders(mp3Path);
        return mp3Path;
    }

    public static String getAppCacheDir(@NonNull Context context) {

        // 获取外置sd卡
        File file = context.getExternalCacheDir();
        if(file != null){
            String sdCacheDirectory = file.getPath();
            return sdCacheDirectory;
        }

        // 获取系统存储
        String cacheDirectory = context.getCacheDir().getPath();
        return cacheDirectory;
    }

    public static String getImageCacheDir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "image";
        FileUtils.makeFolders(path);
        return path;
    }

    public static String getApkCacheDir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "apk";
        FileUtils.makeFolders(path);
        return path;
    }

    public static String getPicCacheDir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "pic";
        FileUtils.makeFolders(path);
        return path;
    }

    public static String getLogCacheDir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "log";
        FileUtils.makeFolders(path);
        return path;
    }
    public static String getDatabaseDir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "database";
        FileUtils.makeFolders(path);
        return path;
    }

    public static String getMapDir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "map";
        FileUtils.makeFolders(path);
        return path;
    }

    public static String getMp3Dir(@NonNull Context context) {
        String rootDir = getAppCacheDir(context);
        if(TextUtils.isEmpty(rootDir)) return null;

        String path = rootDir + File.separator + "mp3";
        FileUtils.makeFolders(path);
        return path;
    }

}
