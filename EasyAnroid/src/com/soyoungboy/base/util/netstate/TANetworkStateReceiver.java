/*
 * Copyright (C) 2013  WhiteCat 白猫 (www.thinkandroid.cn)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.soyoungboy.base.util.netstate;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.soyoungboy.base.util.Logger;
import com.soyoungboy.base.util.ToastUtil;
import com.soyoungboy.base.util.netstate.TANetWorkUtil.netType;
import java.util.ArrayList;

/**
 * @author 白猫
 * @version V1.2
 * @Title NetworkStateReceiver
 * @Package com.ta.util.netstate
 * @Description 是一个检测网络状态改变的，需要配置 <receiver android:name="com.frame.android.util.netstate.TANetworkStateReceiver"
 * >
 * <intent-filter> <action android:name="android.net.conn.CONNECTIVITY_CHANGE" /> <action
 * android:name="android.gzcpc.conn.CONNECTIVITY_CHANGE" /> </intent-filter> </receiver> 需要开启权限
 * <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" /> <uses-permission
 * android:name="android.permission.CHANGE_WIFI_STATE" /> <uses-permission
 * android:name="android.permission.ACCESS_NETWORK_STATE" /> <uses-permission
 * android:name="android.permission.ACCESS_WIFI_STATE" />
 * @date 2013-5-5 下午 22:47
 */
public class TANetworkStateReceiver extends BroadcastReceiver {
    public final static String TA_ANDROID_NET_CHANGE_ACTION =
        "ta.android.net.conn.CONNECTIVITY_CHANGE";
    private static final String TAG = "TANetworkStateReceiver";
    private final static String ANDROID_NET_CHANGE_ACTION =
        "android.net.conn.CONNECTIVITY_CHANGE";
    private static Boolean networkAvailable = false;
    private static netType netType;
    private static ArrayList<TANetChangeObserver> netChangeObserverArrayList =
        new ArrayList<TANetChangeObserver>();
    private static BroadcastReceiver receiver;


    private static BroadcastReceiver getReceiver() {
        if (receiver == null) {
            receiver = new TANetworkStateReceiver();
        }
        return receiver;
    }


    /**
     * 注册网络状态广播
     */
    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(TA_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext()
            .registerReceiver(getReceiver(), filter);
    }


    /**
     * 检查网络状态
     */
    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(TA_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }


    /**
     * 注销网络状态广播
     */
    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (receiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(receiver);
            } catch (Exception e) {
                // TODO: handle exception
                // TALogger.d("TANetworkStateReceiver", e.getMessage());
                Logger.i(TAG, e.getMessage());
            }
        }

    }


    /**
     * 获取当前网络状态，true为网络连接成功，否则网络连接失败
     */
    public static Boolean isNetworkAvailable() {
        return networkAvailable;
    }


    public static netType getAPNType() {
        return netType;
    }


    /**
     * 注册网络连接观察者
     *
     * @param observerKey observerKey
     */
    public static void registerObserver(TANetChangeObserver observer) {
        if (netChangeObserverArrayList == null) {
            netChangeObserverArrayList = new ArrayList<TANetChangeObserver>();
        }
        netChangeObserverArrayList.add(observer);
    }


    /**
     * 注销网络连接观察者
     *
     * @param resID observerKey
     */
    public static void removeRegisterObserver(TANetChangeObserver observer) {
        if (netChangeObserverArrayList != null) {
            netChangeObserverArrayList.remove(observer);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        receiver = TANetworkStateReceiver.this;
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)
            || intent.getAction()
            .equalsIgnoreCase(TA_ANDROID_NET_CHANGE_ACTION)) {
            Logger.i(TAG, "网络状态改变.");
            if (!TANetWorkUtil.isNetworkAvailable(context)) {
                Logger.i(TAG, "没有网络连接.");
                ToastUtil.toasts(context, "网络断开，请检查网络");
                networkAvailable = false;
            } else {
                Logger.i(TAG, "网络连接成功.");
                ToastUtil.toastL(context, "网络连接成功");
                netType = TANetWorkUtil.getAPNType(context);
                networkAvailable = true;
            }
            notifyObserver();
        }
    }


    private void notifyObserver() {

        for (int i = 0; i < netChangeObserverArrayList.size(); i++) {
            TANetChangeObserver observer = netChangeObserverArrayList.get(i);
            if (observer != null) {
                if (isNetworkAvailable()) {
                    /* observer.onConnect(netType); */
                    observer.onConnect(netType);
                } else {
                    observer.onDisConnect();
                }
            }
        }

    }

}