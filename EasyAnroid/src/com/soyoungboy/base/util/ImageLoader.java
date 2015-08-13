package com.soyoungboy.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;
import java.util.WeakHashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.widget.TextView;

import com.soyoungboy.base.R;

/**
 * 
 * TODO: 图片下载工具类
 * 
 * @author soyoungboy
 * @date 2014-6-25 下午2:44:50
 * @version 1.0.0 
 */
public class ImageLoader {
	public static final String TAG = ImageLoader.class.getSimpleName();

	private Context context;
	private MemoryCache memoryCache = new MemoryCache();
	private int downloading_pic = R.drawable.pic_downloading_bg;
	private int fail_pic = R.drawable.pic_download_fail_bg;
	private FileCache fileCache;
	private Map<TextView, String> imageViews = Collections.synchronizedMap(new WeakHashMap<TextView, String>());
	/**
	 * 期望宽度
	 */
	private int expect_width;
	/**
	 * 期望高度
	 */
	private int expect_height;

	public ImageLoader(Context context, int expect_width, int expect_height) {
		this.context = context;
		photoLoaderThread.setPriority(Thread.NORM_PRIORITY - 1);
		fileCache = new FileCache(context);
		this.expect_width = expect_width;
		this.expect_height = expect_height;
	}

	public void setExpect_width(int expect_width) {
		this.expect_width = expect_width;
	}

	public void setExpect_height(int expect_height) {
		this.expect_height = expect_height;
	}

	public Bitmap DisplayImage(String url, Context activity, TextView imageView) {
		imageViews.put(imageView, url);
		Bitmap bitmap = memoryCache.get(url);
		if (bitmap != null) {
			/**
			 * 缓冲区找图片
			 */
			setImage(imageView, bitmap);
			return bitmap;
		} else {
			/**
			 * 缓冲区不存在图片先设置一张默认图片然后启动线程photoLoaderThread下载
			 */
			setDefaultImage(activity, imageView);
			queuePhoto(url, activity, imageView);
			return null;
		}
	}

	private void setImage(TextView imageView, Bitmap bitmap) {
		imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
	}

	/**
	 * 设置下载中图�?
	 * @param activity
	 * @param imageView
	 */
	private void setDefaultImage(Context activity, TextView imageView) {
		imageView.setBackgroundResource(downloading_pic);
	}

	/**
	 * 设置下载失败图片
	 * @param context
	 * @param imageView
	 */
	public void setFailImage(Context context, TextView imageView) {
		// TODO Auto-generated method stub
		imageView.setBackgroundResource(fail_pic);

	}

	public void DisplayImageNet(String url, Context activity, TextView imageView) {
		imageViews.put(imageView, url);
		setDefaultImage(activity, imageView);
		queuePhoto(url, activity, imageView);
	}

	private void queuePhoto(String url, Context activity, TextView imageView) {

		// This ImageView may be used for other images before. So there may be
		// some old tasks in the queue. We need to discard them.
		photosQueue.Clean(imageView);
		PhotoToLoad p = new PhotoToLoad(url, imageView);
		synchronized (photosQueue.photosToLoad) {
			photosQueue.photosToLoad.push(p);
			photosQueue.photosToLoad.notifyAll();
		}

		// start thread if it's not started yet
		if (photoLoaderThread.getState() == Thread.State.NEW)
			photoLoaderThread.start();
	}

	/**
	 * 下载图片
	 * 
	 * @param url
	 * @return
	 */
	public Bitmap getBitmap(String url) {
		File f = fileCache.getFile(url);
		// from SD cache
		Bitmap b = decodeFile(f);
		if (b != null)
			return b;
		// from web
		HttpURLConnection conn = null;
		try {
			Bitmap bitmap = null;
			URL imageUrl = new URL(url);
			conn = (HttpURLConnection) imageUrl.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			if (conn.getResponseCode() == 200) {
				InputStream is = conn.getInputStream();
				OutputStream os = new FileOutputStream(f);
				CopyStream(is, os);
				os.close();
				bitmap = decodeFile(f);
			}
			return bitmap;
		} catch (Exception ex) {
			Logger.w(TAG, "Exception =" + ex.getMessage());
			return null;
		} finally {
			if (conn != null)
				conn.disconnect();
		}
	}

	//
	//	/**
	//	 * 以最省内存的方式读取本地资源的图�?
	//	 * 
	//	 * @param context
	//	 * @param resId
	//	 * @return
	//	 */
	//	public Bitmap decodeFile(int resId) {
	//		BitmapFactory.Options opt = new BitmapFactory.Options();
	//		opt.inPreferredConfig = Bitmap.Config.RGB_565;
	//		opt.inPurgeable = true;
	//		opt.inInputShareable = true;
	//		// 获取资源图片
	//		InputStream is = context.getResources().openRawResource(resId);
	//		return BitmapFactory.decodeStream(is, null, opt);
	//	}

	//	/**
	//	 * 直接调用JNI>>nativeDecodeAsset()来完成decode�?无需再使用java层的createBitmap 效率会好很多
	//	 * 
	//	 * @Title: getImage
	//	 * @param @param imagePath
	//	 * @param @return 设定文件
	//	 * @return Bitmap 返回类型
	//	 * @throws
	//	 */
	//	public static Bitmap decodeFile(String imagePath) {
	//		BitmapFactory.Options options = new BitmapFactory.Options();
	//		options.inJustDecodeBounds = true;
	//		// 获取这个图片的宽和高
	//		Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options); // 此时返回bm为空
	//		// 计算缩放�?
	//		int be = (int) (options.outHeight / (float) maxH);
	//		int ys = options.outHeight % maxH;// 求余�?
	//		float fe = ys / (float) maxH;
	//		if (fe >= 0.5)
	//			be = be + 1;
	//		if (be <= 0)
	//			be = 1;
	//		options.inSampleSize = be;
	//
	//		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false
	//		options.inJustDecodeBounds = false;
	//		bitmap = BitmapFactory.decodeFile(imagePath, options);
	//		return bitmap;
	//	}

	// decodes image and scales it to reduce memory consumption
	private Bitmap decodeFile(File f) {
		try {
			BitmapFactory.Options o = new BitmapFactory.Options();
			o.inJustDecodeBounds = true;
			BitmapFactory.decodeStream(new FileInputStream(f), null, o);
			int width_tmp = o.outWidth, height_tmp = o.outHeight;
			int scale = 1;
			while (true) {
				if (width_tmp / 2 < expect_width || height_tmp / 2 < expect_height)
					break;
				width_tmp /= 2;
				height_tmp /= 2;
				scale *= 2;
			}
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
		} catch (FileNotFoundException e) {
		}
		return null;
	}

	// Task for the queue
	private class PhotoToLoad {
		public String url;
		public TextView imageView;

		public PhotoToLoad(String u, TextView i) {
			url = u;
			imageView = i;
		}
	}

	PhotosQueue photosQueue = new PhotosQueue();

	public void stopThread() {
		photoLoaderThread.interrupt();
	}

	// stores list of photos to download
	class PhotosQueue {
		private Stack<PhotoToLoad> photosToLoad = new Stack<PhotoToLoad>();

		// removes all instances of this ImageView
		public void Clean(TextView image) {
			for (int j = 0; j < photosToLoad.size();) {
				try {
					if (photosToLoad.get(j).imageView == image)
						photosToLoad.remove(j);
					else
						++j;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	class PhotosLoader extends Thread {
		public void run() {
			try {
				while (true) {
					// thread waits until there are any images to load in the
					// queue
					if (photosQueue.photosToLoad.size() == 0)
						synchronized (photosQueue.photosToLoad) {
							photosQueue.photosToLoad.wait();
						}
					if (photosQueue.photosToLoad.size() != 0) {
						PhotoToLoad photoToLoad;
						synchronized (photosQueue.photosToLoad) {
							if (!photosQueue.photosToLoad.empty()) {
								photoToLoad = photosQueue.photosToLoad.pop();
								Bitmap bmp = getBitmap(photoToLoad.url);
								memoryCache.put(photoToLoad.url, bmp);// 将下载好的图片放入缓冲区
								String tag = imageViews.get(photoToLoad.imageView);
								if (tag != null && tag.equals(photoToLoad.url)) {
									BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad.imageView);
									Activity a = (Activity) photoToLoad.imageView.getContext();
									a.runOnUiThread(bd);
								}
							}
						}

					}
					if (Thread.interrupted())
						break;
				}
			} catch (InterruptedException e) {
				// allow thread to exit
			}
		}
	}

	PhotosLoader photoLoaderThread = new PhotosLoader();

	// Used to display bitmap in the UI thread
	class BitmapDisplayer implements Runnable {
		Bitmap bitmap;
		TextView imageView;

		public BitmapDisplayer(Bitmap b, TextView i) {
			bitmap = b;
			imageView = i;
		}

		public void run() {
			if (bitmap != null)
				//				imageView.setBackgroundDrawable(new BitmapDrawable(bitmap));
				setImage(imageView, bitmap);
			else {
				// 图片获取不到
				//				imageView.setBackgroundResource(downloading_pic);
				setFailImage(context, imageView);
			}
		}
	}

	public void clearCache() {
		memoryCache.clear();
		fileCache.clear();
	}

	public static void CopyStream(InputStream is, OutputStream os) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = is.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				os.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}
	}

	public int getDownloading_pic() {
		return downloading_pic;
	}

	public void setDownloading_pic(int downloading_pic) {
		this.downloading_pic = downloading_pic;
	}

	public int getFail_pic() {
		return fail_pic;
	}

	public void setFail_pic(int fail_pic) {
		this.fail_pic = fail_pic;
	}

}
