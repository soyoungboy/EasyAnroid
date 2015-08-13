package com.soyoungboy.base.util;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

/** 
* @ClassName: ImageLoadUtil 
* @Description: image加载util
* @author soyoungboy
* @date 2014-6-18 下午4:07:02  
*/
public class ImageLoadUtil {
	private static ImageLoadUtil imageLoadUtil = null;
	private ImageLoaderConfiguration config = null;
	private DisplayImageOptions options = null;
	private ImageLoadUtil(Context context){
		options = new DisplayImageOptions.Builder()  
				//.showImageOnLoading(R.drawable.ic_launcher) //设置图片在下载期间显示的图片  
				//.showImageForEmptyUri(R.drawable.ic_launcher)//设置图片Uri为空或是错误的时候显示的图片  
				//.showImageOnFail(R.drawable.ic_launcher)  //设置图片加载/解码过程中错误时候显示的图片
				.cacheInMemory(true)//设置下载的图片是否缓存在内存�? 
				.cacheOnDisc(true)//设置下载的图片是否缓存在SD卡中  
				.considerExifParams(true)  //是否考虑JPEG图像EXIF参数（旋转，翻转�?
				.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)//设置图片以如何的编码方式显示  
				.bitmapConfig(Bitmap.Config.RGB_565)//设置图片的解码类�?/  
				//.delayBeforeLoading(int delayInMillis)//int delayInMillis为你设置的下载前的延迟时�?
				//设置图片加入缓存前，对bitmap进行设置  
				//.preProcessor(BitmapProcessor preProcessor)  
				.resetViewBeforeLoading(true)//设置图片在下载前是否重置，复�? 
				.displayer(new RoundedBitmapDisplayer(20))//是否设置为圆角，弧度为多�? 
				.displayer(new FadeInBitmapDisplayer(100))//是否图片加载好后渐入的动画时�? 
				.build();//构建完成  
		
		config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)// 加载图片的线程数
				.denyCacheImageMultipleSizesInMemory() // 解码图像的大尺寸将在内存中缓存先前解码图像的小尺寸�?
				.discCacheFileNameGenerator(new Md5FileNameGenerator())// 设置磁盘缓存文件名称
				.tasksProcessingOrder(QueueProcessingType.LIFO)// 设置加载显示图片队列进程
				.writeDebugLogs() // Remove for release app
				.discCache(new UnlimitedDiscCache(new File(FileCacheUtil.getPicCacheDir()))) // 文件缓存目录
				.defaultDisplayImageOptions(options)// 创建配置过得DisplayImageOption对象  
				.build();                                   
		/*config = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.writeDebugLogs() 
				.build();*/
		ImageLoader.getInstance().init(config);
	}
	
	public static ImageLoadUtil init(Context context){
		if (imageLoadUtil == null) {
			imageLoadUtil = new ImageLoadUtil(context);
		}
		return imageLoadUtil;
	}
}
