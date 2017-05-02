package com.soyoungboy.base.util.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @类名: ThreadUtils
 * @描述: TODO(线程池工具类)
 * @作者: soyoungboy
 */
public class ThreadUtils {
    /**
     * 单线程
     */
    static ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
    private static volatile ThreadUtils instance = null;
    /**
     * 初始化的线程数，有待历史的验证，暂时弄4个
     */
    public ExecutorService threadPool = Executors.newFixedThreadPool(4);
    /**
     * 执行延迟任务，类似Timer的效果
     */
    public ScheduledExecutorService scheduleThreadPool = Executors.newScheduledThreadPool(2);


    // private constructor suppresses
    private ThreadUtils() {

    }


    public static ThreadUtils getInstance() {
        // if already inited, no need to get lock everytime
        if (instance == null) {
            synchronized (ThreadUtils.class) {
                if (instance == null) {
                    instance = new ThreadUtils();
                }
            }
        }

        return instance;
    }


    /**
     * 立即执行任务
     *
     * @param task ThreadUtils.getInstance().excute(run);
     */
    public void excute(Runnable task) {
        threadPool.execute(task);
    }


    /**
     * 单线程持操作，主要用于数据库的读写异步操作
     *
     * @param task ThreadUtils.getInstance().excuteSingleThread(run);
     */
    public Future excuteSingleThread(Runnable task) {
        return singleThreadPool.submit(task);
    }


    ;


    /**
     * 延后执行任务
     *
     * @param delay ThreadUtils.getInstance().schedule(run,1000);
     */
    public void schedule(Runnable task, long delay) {
        scheduleThreadPool.schedule(task, delay, TimeUnit.MILLISECONDS);
    }


    public Future execuse(final Task task) {
        task.onstart();
        return excuteSingleThread(new Runnable() {
            @Override
            public void run() {
                try {
                    task.doInBackground();
                } catch (Exception e) {
                    task.transfer(null, Task.TRANSFER_DOERROR);
                    return;
                }
                task.transfer(null, Task.TRANSFER_DOUI);
            }
        });
    }


    /**
     * @return void 返回类型
     * @throws 在onDestory ()中执行[ThreadUtils.getInstance().shutdownThreadPool()]
     * @Title: shutdownThreadPool
     * @Description: TODO()
     */
    public void shutdownThreadPool() {
        threadPool.shutdownNow();
    }


    /**
     * @return void 返回类型
     * @throws 在onDestory ()中执行[ThreadUtils.getInstance().shutdownScheduleThreadPool()]
     * @Title: shutdownThreadPool
     * @Description: TODO()
     */
    public void shutdownScheduleThreadPool() {
        scheduleThreadPool.shutdownNow();

    }


    /**
     * @return void 返回类型
     * @throws 在onDestory ()中执行[ThreadUtils.getInstance().shutdownSingleThreadPool()]
     * @Title: shutdownSingleThreadPool
     * @Description: TODO(单线程池销毁操作)
     */
    public void shutdownSingleThreadPool() {
        singleThreadPool.shutdownNow();
    }
}
