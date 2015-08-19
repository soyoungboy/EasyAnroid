
package com.soyoungboy.base.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

import com.loopj.android.http.BuildConfig;

/**
 * @ClassName: Logger
 * @Description: TODO(日志工具类)
 * @Author: soyoungboy
 * @Date: 2014-2-19 下午10:24:00
 */
public class Logger {
	private final static String TAG = "Logger";

	private final static boolean isDebug = BuildConfig.DEBUG;

	private static int LOGLEVEL = 6; 

	private static int VERBISE = 1;

	private static int DEBUG = 2;

	private static int INFO = 3;

	private static int WARN = 4;

	private static int ERROR = 5; 

	// 根据�?��将Log存放到SD卡中
	private static String path;

	private static File file;

	private static FileOutputStream outputStream;

	private static String pattern = "yyyy-MM-dd HH:mm:ss";
	static {
		if (FileHelper.isSDCardReady()) {
			File externalStorageDirectory =
					Environment.getExternalStorageDirectory();
			path = externalStorageDirectory.getAbsolutePath() + "/crash/";
			File directory = new File(path);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			file = new File(new File(path), "crash.log");
			try {
				outputStream = new FileOutputStream(file, true);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void v(String tag, String msg) {
		if (LOGLEVEL > VERBISE && isDebug) {
			Log.v(tag, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (LOGLEVEL > DEBUG  && isDebug) {
			Log.d(tag, msg);
		}
	}

	public static void i(String tag, String msg) {
		if (LOGLEVEL > INFO && isDebug) {
			Log.i(tag, msg);
		}
	}

	public static void i(String tag, String field, String msg) {
		if (LOGLEVEL > INFO && isDebug) {
			Log.i(tag, field + " = " + msg);
		}
	}

	public static void w(String tag, String msg) {
		if (LOGLEVEL > WARN && isDebug) {
			Log.w(tag, msg);
		}
	}

	public static void e(String tag, String msg) {
		if (LOGLEVEL > ERROR && isDebug) {
			Log.e(tag, msg);
		}
	}
	public static void e(String tag, String field, String msg) {
		if (LOGLEVEL > INFO && isDebug) {
			Log.e(tag, field + " = " + msg);
		}
	}

	public static void i(String msg){
		if (isDebug) {
			Log.i(TAG, msg);
		}
	}

	public static void d(String msg){
		if (isDebug) {
			Log.d(TAG, msg);
		}
	}

	public static void e(String msg){
		if (isDebug) {
			Log.e(TAG, msg);
		}
	}

	public static void w(String msg){
		if (isDebug) {
			Log.w(TAG, msg);
		}
	}

	public static void v(String msg){
		if (isDebug) {
			Log.v(TAG, msg);
		}
	}

	/**
	 * 将错误信息保存到SD卡中去！可�?的操作！
	 * 
	 * @param msg 传�?的String类型
	 */
	public static void save2Sd(String msg) {
		Date date = new Date();
		String time = nowTimeString();
		save(time, msg);
	}

	/**
	 * 将错误信息保存到SD卡中去！可�?的操作！
	 * 
	 * @param e 传�?的是Exception类型
	 */
	public static void save2Sd(Exception e) {
		Date date = new Date();
		String time = nowTimeString();
		StringWriter writer = new StringWriter();
		PrintWriter pw = new PrintWriter(writer);
		e.printStackTrace(pw);
		String msg = writer.toString();
		save(time, msg);
	}
	public static String nowTimeString() {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(new Date());
	}

	/**
	 * 保存的核心方�?     * 
	 * @param time 保存的时�?     * @param msg 保存的信�?     */
	private static void save(String time, String msg) {
		if (FileHelper.isSDCardReady()) {
			if (outputStream != null) {
				try {
					outputStream.write(time.getBytes());
					outputStream.write("\r\n".getBytes());
					outputStream.write(msg.getBytes());
					outputStream.write("\r\n".getBytes());
					outputStream.flush();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} else {
				android.util.Log.i("SDCAEDTAG", "file is null");
			}
		}
	}
}
