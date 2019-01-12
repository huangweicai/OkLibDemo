package com.oklib.util;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import java.util.UUID;

/**
  * 时间：2017/9/23
  * 作者：蓝天
  * 描述：设备信息工具类
  */
public class DeviceInfo {

   public enum UUIDType {
       MAC, ANDROID_ID, RANDOM_UUID
   }

   /**
    * 获取设备id
    * @param context
    * @param type
    * @return
    */
   public static String getDeviceId(Context context, UUIDType type) {
       String deviceId = "";
       if (type == UUIDType.MAC) {
           deviceId = getLocalMac(context).replace(":", "");
       } else if (type == UUIDType.ANDROID_ID) {
           deviceId = getAndroidId(context);
       } else if (type == UUIDType.RANDOM_UUID) {
           UUID uuid = UUID.randomUUID();
           deviceId = uuid.toString().replace("-", "");
       }
       return deviceId;
   }

   // IMEI码
   private static String getIMIEStatus(Context context) {
       TelephonyManager tm = (TelephonyManager) context
               .getSystemService(Context.TELEPHONY_SERVICE);
       String deviceId = tm.getDeviceId();
       return deviceId;
   }

   // Mac地址
   private static String getLocalMac(Context context) {
       WifiManager wifi = (WifiManager) context
               .getSystemService(Context.WIFI_SERVICE);
       WifiInfo info = wifi.getConnectionInfo();
       return info.getMacAddress();
   }

   // Android Id
   private static String getAndroidId(Context context) {
       String androidId = Settings.Secure.getString(
               context.getContentResolver(), Settings.Secure.ANDROID_ID);
       return androidId;
   }

}
