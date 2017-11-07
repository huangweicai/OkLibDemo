//package com.oklib.util.glide;
//
//import android.os.Looper;
//import android.text.TextUtils;
//
//import com.bumptech.glide.Glide;
//import com.oklib.AppConfig;
//import com.oklib.OkLib;
//
//import java.io.File;
//import java.math.BigDecimal;
//
///**
// * 创建时间：2016/7/20
// * 编写者：黄伟才
// * 功能描述：Glide缓存工具类
// */
//public class GlideCatchUtil {
//
//    /**
//     * 清除图片所有缓存
//     */
//    public void clearImageAllCache() {
//        clearImageDiskCache();
//        clearImageMemoryCache();
//        //Glide存在自定义的文件夹中，应该删除文件夹中的图片文件
//        deleteFolderFile(AppConfig.IMAGE_NET_PATH, true);
//    }
//
//    /**
//     * 清除图片磁盘缓存
//     */
//    public void clearImageDiskCache() {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) {
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Glide.get(OkLib.appContext).clearDiskCache();
//                    }
//                });
//            } else {
//                Glide.get(OkLib.appContext).clearDiskCache();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 清除图片内存缓存
//     */
//    public void clearImageMemoryCache() {
//        try {
//            if (Looper.myLooper() == Looper.getMainLooper()) { //只能在主线程执行
//                Glide.get(OkLib.appContext).clearMemory();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * 获取Glide造成的缓存大小
//     *
//     * @return CacheSize
//     */
//    public String getCacheSize() {
//        try {
//            ///data/data/cn.glidedemo/cache /image_manager_disk_cache
//            //image_manager_disk_cache
//            //image_cache
//            return getFormatSize(getFolderSize(new File(AppConfig.IMAGE_NET_PATH)));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    /**
//     * 获取指定文件夹内所有文件大小的和
//     *
//     * @param file file
//     * @return size
//     * @throws Exception
//     */
//    public long getFolderSize(File file) throws Exception {
//        long size = 0;
//        try {
//            File[] fileList = file.listFiles();
//            for (File aFileList : fileList) {
//                if (aFileList.isDirectory()) {
//                    size = size + getFolderSize(aFileList);
//                } else {
//                    size = size + aFileList.length();
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return size;
//    }
//
//    /**
//     * 删除指定目录下的文件，这里用于缓存的删除
//     *
//     * @param filePath       filePath
//     * @param deleteThisPath deleteThisPath
//     */
//    public void deleteFolderFile(String filePath, boolean deleteThisPath) {
//        if (!TextUtils.isEmpty(filePath)) {
//            try {
//                //第一次读取目录
//                File file = new File(filePath);
//                if (file.isDirectory()) {
//                    File files[] = file.listFiles();
//                    for (File file1 : files) {
//                        deleteFolderFile(file1.getAbsolutePath(), true);
//                    }
//                }
//                //循环在进来删除目录中文件
//                if (deleteThisPath) {
//                    if (!file.isDirectory()) {
//                        file.delete();
//                    } else {
//                        //注意：这里不能把存储图片的文件夹删除了，否则在应用运行当中缓存图片会找不到缓存路径
////                        if (file.listFiles().length == 0) {
////                            file.delete();
////                        }
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    /**
//     * 格式化单位
//     *
//     * @param size size
//     * @return size
//     */
//    public String getFormatSize(double size) {
//
//        double kiloByte = size / 1024;
//        if (kiloByte < 1) {
//            return size + "Byte";
//        }
//
//        double megaByte = kiloByte / 1024;
//        if (megaByte < 1) {
//            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
//            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
//        }
//
//        double gigaByte = megaByte / 1024;
//        if (gigaByte < 1) {
//            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
//            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
//        }
//
//        double teraBytes = gigaByte / 1024;
//        if (teraBytes < 1) {
//            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
//            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
//        }
//        BigDecimal result4 = new BigDecimal(teraBytes);
//
//        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
//    }
//
//
//    //private final String ImageExternalCatchDir = App.getInstance().getExternalCacheDir() + "/image_cache";
//    private static GlideCatchUtil glideCatchUtil;
//
//    //控制实例入口在getInstance方法中
//    private GlideCatchUtil() {
//
//    }
//
//    //唯一实例入口
//    public static GlideCatchUtil getInstance() {
//        if (null == glideCatchUtil) {
//            synchronized (GlideCatchUtil.class) {
//                if (null == glideCatchUtil) {//确保单一实例
//                    glideCatchUtil = new GlideCatchUtil();
//                }
//            }
//        }
//        return glideCatchUtil;
//    }
//
//}
//
//
