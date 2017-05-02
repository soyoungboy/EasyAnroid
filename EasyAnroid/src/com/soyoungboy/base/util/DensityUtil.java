package com.soyoungboy.base.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

/**
 * @author soyoungboy
 * @ClassName: DensityUtil
 * @Description: 屏幕像素转换类
 * @date 2014-6-17 下午4:23:26
 */
public class DensityUtil {
    private static final String TAG = DensityUtil.class.getSimpleName();
    private static DensityUtil densityUtil = null;
    // 当前屏幕的densityDpi
    private static float dmDensityDpi = 0.0f;

    private static DisplayMetrics dm;

    private static float scale = 0.0f;


    private DensityUtil(Context context) {
        // 获取当前屏幕
        dm = new DisplayMetrics();
        // 返回当前资源对象的DispatchMetrics信息�?
        //dm = context.getApplicationContext().getResources().getDisplayMetrics();
        if (context instanceof Activity) {
            ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        } else {
            Logger.e(TAG, "your context must is instance of Activity!");
            return;
        }
        // 设置DensityDpi
        setDmDensityDpi(dm.densityDpi);
        // 密度因子
        //scale = getDmDensityDpi() / 160;// 等于 scale=dm.density;
        scale = dm.density;
    }


    public static DensityUtil getInstance(Context context) {
        if (densityUtil == null) {
            densityUtil = new DensityUtil(context);
        }
        return densityUtil;
    }


    /**
     * @Description: dip转像�?
     * @return：int
     */
    public static int dipToPixels(Context context, int dip) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        return (int) ((float) dip * SCALE + 0.5f);
    }


    /**
     * @Description: 像素转dip
     * @return：float
     */
    public static float pixelsToDip(Context context, int Pixels) {
        final float SCALE = context.getResources().getDisplayMetrics().density;
        return Pixels / SCALE;
    }


    /**
     * @Description: 屏幕分辨率宽
     */
    public int getWindowWidth() {
        if (dm != null) {
            return dm.widthPixels;
        }
        return 0;
    }


    /**
     * @Description: 屏幕分辩类高
     */
    public int getWindowHeight() {
        if (dm != null) {
            return dm.heightPixels;
        }
        return 0;
    }


    /**
     * @Description: 屏幕的ppi
     * @return：float
     */
    public float getDmDensityDpi() {
        return dmDensityDpi;
    }


    public void setDmDensityDpi(float dmDensityDpi) {
        DensityUtil.dmDensityDpi = dmDensityDpi;
    }


    public int dip2px(float dipValue) {

        return (int) (dipValue * scale + 0.5f);

    }


    public int px2dip(float pxValue) {
        return (int) (pxValue / scale + 0.5f);
    }
}