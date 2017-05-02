package com.soyoungboy.base.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import java.util.List;

/**
 * @ClassName: ServiceUtils
 * @Description:service工具类
 * @Author: soyoungboy
 * @Date: 2014-1-7 下午5:09:11
 */
public class ServiceUtils {

    /**
     * 判断service是否正在运行
     */
    public static boolean isServiceRunning(Context context, String className) {
        boolean isRunning = false;
        ActivityManager activityManager =
            (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList =
            activityManager.getRunningServices(Integer.MAX_VALUE);

        if (serviceList.size() <= 0) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }

        return isRunning;
    }


    /**
     * 启动服务，不会重复启动
     */
    @SuppressWarnings("rawtypes")
    public static void startService(Context context, Class clazz, Intent intent) {
        if (!isServiceRunning(context, clazz.getName())) {
            context.startService(intent);
        }
    }


    /**
     * 关闭服务，会判断服务是否启动着
     */
    @SuppressWarnings("rawtypes")
    public static void stopService(Context context, Class clazz) {
        if (isServiceRunning(context, clazz.getName())) {
            context.stopService(new Intent(context, clazz));
        }
    }

}
