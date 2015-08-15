
package com.soyoungboy.base.util.thread;

/**
 * @类名: BaseTask
 * @描述: TODO(线程工具类，采用单例模式，减少对象创建)
 * @作者: soyoungboy
 */
public class BaseTask implements Runnable {
	private static final String TAG = BaseTask.class.getSimpleName();

	private static volatile BaseTask instance = null;

	private BaseTask() {

	}

	public static BaseTask getInstance() {

		if (instance == null) {
			synchronized (BaseTask.class) {
				if (instance == null) {
					instance = new BaseTask();
				}
			}
		}

		return instance;
	}

	/**
	 * @Name run
	 * @Description TODO(此处进行耗时操作--比如数据库数据的读取)
	 * @see java.lang.Runnable#run()
	 * @Date 2014-2-6 下午6:50:03
	 */
	@Override
	public void run() {

	}

}
