package com.soyoungboy.base.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

/**
 * @类名: ToastUtils
 * @描述: TODO(吐司工具类)
 * @作�?: soyoungboy
 */
public class ToastUtil {
    /**
     * @throws
     * @方法�? toastL
     * @描述: TODO(这里用一句话描述这个方法的作�?
     * @设定: @param context
     * @设定: @param message 吐司内容，为string里面的内容id
     * @返回: void 返回类型
     * @日期: 2014-5-28 上午10:03:46
     */
    public static void toastL(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


    /**
     * 显示吐司信息（较长时间）
     */
    public static void toastL(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }


    /**
     * 显示吐司信息（较短时间）
     */
    public static void toasts(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }


    /**
     * 显示吐司信息交给handler处理（较长时间）
     */
    public static void toastLInThread(final Context context, final String text,
                                      Handler handler) {

        handler.post(new Runnable() {
            @Override
            public void run() {
                ToastUtil.toastL(context, text);
            }
        });
    }


    /**
     * 显示吐司信息交给handler处理（较短时间）
     */
    public static void toastsInThread(final Context context, final String text,
                                      Handler handler) {

        handler.post(new Runnable() {

            @Override
            public void run() {
                ToastUtil.toasts(context, text);
            }
        });
    }


    /**
     * @throws
     * @方法�? toasts
     * @描述: TODO(短时间吐�?
     * @设定: @param context
     * @设定: @param message 吐司内容，为string里面的内容id
     * @返回: void 返回类型
     * @日期: 2014-5-26 上午9:55:09
     */
    public static void toasts(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
