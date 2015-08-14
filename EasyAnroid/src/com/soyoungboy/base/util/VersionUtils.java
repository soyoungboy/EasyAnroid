
package com.soyoungboy.base.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * @ClassName: VersionUtils
 * @Description:读取本应用的版本信息
 * @Author: soyoungboy
 * @Date: 2014-1-7 下午5:16:04
 */
public abstract class VersionUtils {
    
    /**
     * 得到版本代码versionCode（配在AndroidManifest.xml中），主版本号，用于升级应用
     * 
     * @param context
     * @return
     */
    public static int getVersionCode(Context context, Class<?> clazz) {
        int versionCode = -1;
        try {
            PackageInfo packageInfo =
                context.getPackageManager().getPackageInfo(clazz.getPackage()
                    .getName(),
                    0);
            versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        
        return versionCode;
    }
    
    /**
     * 获得应用程序图标
     * 
     * @param context
     * @return
     */
    public static int getApplicationIcon(Context context, Class<?> clazz) {
        try {
            PackageInfo packageInfo =
                context.getPackageManager().getPackageInfo(clazz.getPackage()
                    .getName(),
                    0);
            return packageInfo.applicationInfo.icon;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        
        return -1;
    }
    
    /**
     * 得到版本代码versionName（配在AndroidManifest.xml中），给用户看的
     * 
     * @return
     */
    public static String getVersionName(Context context, Class<?> clazz) {
        String versionName = "";
        try {
            PackageInfo packageInfo =
                context.getPackageManager().getPackageInfo(clazz.getPackage()
                    .getName(),
                    0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }
    
}
