package com.zhaobo.utilslib;

import android.os.StatFs;
import android.text.TextUtils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;

/**
 * Created by zhaobo on 16/10/27.
 */

public class FileUtils {

    /**
     * 缓存文件大小
     */
    public static long cacheFileSize(File file) {
        long total = 0;
        if (!file.exists()) {
            return total;
        }

        if (file.isFile())
            return file.length();
        final File[] children = file.listFiles();
        if (children != null)
            for (final File child : children)
                total += cacheFileSize(child);
        return total;
    }

    /**
     * 获取Sdcard的剩余存储空间
     * @return
     */
    public static long getSdcardUsableSpace(){
        String sdCardPath=SdCardUtils.getExternalStorageDir();
        File file=new File(sdCardPath);
        if(file.exists()){
            return getUsableSpace(file);
        }
        return 0;
    }

    /**
     * 文件剩余空间
     * @param file
     * @return
     */
    public static long getUsableSpace(File file){
        final StatFs statFs=new StatFs(file.getPath());
        return statFs.getAvailableBlocks()*statFs.getBlockSize();
    }

    /**
     * 获取路径下文件的大小
     *
     * @param filePath 文件路径
     * @return 文件的大小
     */
    public static long cacheFileSizeByPath(String filePath) {
        if (filePath == null) {
            return 0;
        }
        File file = new File(filePath);
        if (file.exists()) {
            return cacheFileSize(file);
        }
        return 0;
    }

    /**
     * 将字节转成M，G
     *
     * @param sizeL 文件大小
     */
    public static String fomatSize(long sizeL) {
        Double size = Double.parseDouble(sizeL+"");
        DecimalFormat df = new DecimalFormat("0.##");
        if(size<=0){
            return "0.00b";
        }else if (size < 1024) {
            return df.format(size) + "b";
        } else if (size < 1024 * 1024) {
            return df.format(size / 1024) + "k";
        } else if (size < 1024 * 1024 * 1024) {
            return df.format(size / 1024 / 1024) + "M";
        } else if (size < 1024 * 1024 * 1024 * 1024) {
            return df.format(size / 1024 / 1024 / 1024) + "G";
        }
        return "0.00b";
    }

    /**
     * 清除缓存
     */
    public static boolean clearCache(File file) {

        if (!file.exists()) {
            return false;
        }
        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {

            File[] childFiles = file.listFiles();
            if (childFiles == null || childFiles.length == 0) {
                return false;
            }
            for (File childFile : childFiles) {
                clearCache(childFile);
            }
        }
        return true;
    }

    /**
     * 根据文件的路径清理缓存
     * @param filePath
     * @return
     */
    public static boolean clearCacheByPath(String filePath){
        File file=new File(filePath);
        return clearCache(file);
    }


    /**
     * 根据文件夹路径，创建目录
     * @param fileDir
     * @return
     */
    public static boolean makeFolders(String fileDir) {
        if (TextUtils.isEmpty(fileDir)) {
            return false;
        }

        File folder = new File(fileDir);
        return (folder.exists() && folder.isDirectory()) ? true : folder.mkdirs();
    }

    /**
     * 读取缓存文件
     *
     * @param fileName
     * @return
     */
    public static String readFileFromSdcard(String fileName) {
        String res = "";
        try {
            FileInputStream fin = new FileInputStream(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            res=new String(buffer,"UTF-8");
//            res = EncodingUtils.getString(buffer, "UTF-8");
            fin.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return res;

    }

    /**
     * 保存缓存文件
     *
     * @param jsonString
     */
    public static void save2SD(String jsonString, String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                File dir = new File(file.getParent());
                dir.mkdirs();
                file.createNewFile();
            }
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(jsonString.getBytes());
            outStream.flush();
            outStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

}
