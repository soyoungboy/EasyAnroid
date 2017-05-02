package com.soyoungboy.base.util.thread;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: 异步操作相关类,參考DNroid
 *
 * @author soyoungboy
 * @date 2014-11-13 上午11:39:41
 */
public abstract class Task {

    /**
     * 线程ui传递
     */
    public static final int TRANSFER_DOUI = -400;

    /**
     * 线程取消传递
     */
    public static final int TRANSFER_DOCANCEL = -401;

    /**
     * 线程错误传递
     */
    public static final int TRANSFER_DOERROR = -800;
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) msg.obj;
            Task task = (Task) map.get("task");
            Object obj = map.get("obj");
            switch (msg.what) {
                case TRANSFER_DOUI:
                    task.doInUI(obj, TRANSFER_DOUI);
                    task.finish();
                    break;
                case TRANSFER_DOERROR:
                    task.onErray(obj, TRANSFER_DOERROR);
                    break;
                default:
                    task.doInUI(obj, msg.what);
                    break;
            }
        }
    };
    /**
     *
     */
    Context mContext;


    public Task(Context mContext) {
        super();
        this.mContext = mContext;
    }


    /**
     * 后台运行 中间可调用 transfer通知是更新 通知的
     */
    public abstract void doInBackground();


    /**
     * @return void    返回类型
     * @throws
     * @Title: finish
     * @Description: TODO(在onstart()里面的UI操作，在这里进行结束)
     */
    public void finish() {

    }


    /**
     * 错误处理
     */
    public void onErray(Object obj, Integer what) {

    }


    /**
     * 前台ui处理 transfer
     */
    public abstract void doInUI(Object obj, Integer what);


    /**
     * 线程传递 将数据由后台线程传到前台 <br/>
     * 自己调用时不可 what 必须>0 防止和系统自定的冲突
     */
    public void transfer(Object obj, Integer what) {
        Message msg = handler.obtainMessage();
        msg.what = what;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("task", this);
        map.put("obj", obj);
        msg.obj = map;
        handler.sendMessage(msg);
    }


    /**
     * @return void    返回类型
     * @throws
     * @Title: onstart
     * @Description: TODO(异步操作前的UI处理)
     */
    public void onstart() {

    }

}
