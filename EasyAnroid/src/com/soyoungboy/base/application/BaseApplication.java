
package com.soyoungboy.base.application;

import android.app.Application;
import android.content.Intent;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.soyoungboy.base.exception.CrashHandler;
import com.soyoungboy.base.util.ImageLoadUtil;

/**
 * @类名: BaseApplication
 * @描述: TODO(自定义Application,存放全局变量)
 * @作者: soyoungboy
 */

public class BaseApplication extends Application {
	@SuppressWarnings("unused")
	private ImageLoader imageLoader;

	@Override
	public void onCreate() {
		super.onCreate();

		/**
		 * 实现程序异常终止时候优雅的关闭以及重新启动app
		 */
		 CrashHandler handler = CrashHandler.getInstance();
		 handler.init(getApplicationContext());
		 Thread.currentThread().setUncaughtExceptionHandler(handler);

		/**
		 * 使用Universal_ImageLoader的准备工作！
		 */
		ImageLoadUtil.init(getApplicationContext());

	}

	/**
	 * 低内存的时候发送广播：关闭正在访问的activity，清理内存！ 目的避免因为OOM异常造成程序强行退出！
	 */
	@Override
	public void onLowMemory() {
		Intent intent = new Intent();
		intent.setAction("Low_Memory_Kill");
		sendBroadcast(intent);
		super.onLowMemory();
	}
}
