
package com.soyoungboy.base.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;

/** 
* @ClassName: NetUtil 
* @Description: 网络连接工具類
* @author soyoungboy
* @date 2014-6-17 下午2:17:01  
*/
public class NetUtil {
	public static enum netType {
        wifi, CMNET, CMWAP, noneNet
    }
    public static final int NETWORN_NONE = 0;
    
    public static final int NETWORN_WIFI = 1;
    
    public static final int NETWORN_MOBILE = 2;
    
    /** 
     * @Description: 获取网络连接类型
     * @param context
     * @return int    
     */
    public static int getNetworkState(Context context) {
    	State state = null;
        ConnectivityManager connManager =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo wifiNetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        
        if(null != wifiNetworkInfo){
        	state = wifiNetworkInfo.getState();
        	 if (state == State.CONNECTED || state == State.CONNECTING) {
                 return NETWORN_WIFI;
             }
        }
        
        NetworkInfo mobileNetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if(null != mobileNetworkInfo){
        	state = mobileNetworkInfo.getState();
        	 if (state == State.CONNECTED || state == State.CONNECTING) {
                 return NETWORN_MOBILE;
             }
        }
        return NETWORN_NONE;
    }
    
    /**
     * 网络是否可用
     * @param context
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context != null) {
			ConnectivityManager mgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo[] info = mgr.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
    }
    
    /**
     * 判断是否有网络连�?
     * @param context
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
    }
    
    /**
     * 判断WIFI网络是否连接
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWiFiNetworkInfo =
                mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    
    /**
     * 判断MOBILE网络是否连接
     * @param context
     * @return
     */
    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mMobileNetworkInfo =
                mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (mMobileNetworkInfo != null) {
                return mMobileNetworkInfo.isAvailable();
            }
        }
        return false;
    }
    
    /**
     * 获取当前的网络状�?同getNetworkState()类似)
     * @param context
     * @return NetUtil.netType
     */
    public static netType getConnectType(Context context) {
    	if (context == null) 
    		return netType.noneNet;
        ConnectivityManager connMgr =
            (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo == null) {
            return netType.noneNet;
        }
        int nType = networkInfo.getType();
        
        if (nType == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return netType.CMNET;
            }           
            else {
                return netType.CMWAP;
            }
        } else if (nType == ConnectivityManager.TYPE_WIFI) {
            return netType.wifi;
        }
        return netType.noneNet;
    }
}
