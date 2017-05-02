package com.soyoungboy.base.util;

import android.content.Context;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BinaryHttpResponseHandler;
import com.loopj.android.http.BuildConfig;
import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;
import com.loopj.android.http.TextHttpResponseHandler;
import java.io.File;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @ClassName: AsyncHttpUtil
 * @Description: Http请求类，包括POST和GET请求及提交类
 * @author: soyoungboy
 * @date: 2014-4-28 上午11:02:35
 */
public class AsyncHttpUtil {
    private static final int TIME_OUT = 10000;
    private static String TAG = AsyncHttpUtil.class.getSimpleName();
    private static AsyncHttpClient client = new AsyncHttpClient();


    static {
        // 设置链接超时，如果不设置，默认为10s
        client.setTimeout(TIME_OUT);
        // 设置重连次数及两次重连的时间间隔
        // client.setMaxRetriesAndTimeout(3, 4000);
        //设置header
        //client.addHeader("", "");
    }


    private AsyncHttpUtil() {
    }


    /**
     * @Description: 同步session到webview
     * @return: void
     */
    public static void syncCookie(Context context, String remoteAddress) {
        HttpClient httpClient = client.getHttpClient();

        CookieStore cookieStore =
            ((AbstractHttpClient) httpClient).getCookieStore();
        Cookie cookie = null;
        List<Cookie> cookies = cookieStore.getCookies();
        if (!cookies.isEmpty()) {
            for (int i = 0; i < cookies.size(); i++) {
                cookie = cookies.get(i);
            }
        }
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();

        if (cookie != null) {
            cookieManager.removeSessionCookie();
            try {
                // webview第一次加载需�?0毫秒的延�?
                Thread.sleep(10);
            } catch (InterruptedException e) {
            }
            String cookieString =
                cookie.getName() + "=" + cookie.getValue() + "; domain="
                    + cookie.getDomain();
            cookieManager.setCookie(remoteAddress, cookieString);
            CookieSyncManager.getInstance().sync();
        }
    }

    /******************************************************************************************
     * ===================================以下为POST方式请求=======================================
     ******************************************************************************************/

    /**
     * @param url url地址
     * @param params 参数
     * @param handler TextHttpResponseHandler回调（返回String数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            TextHttpResponseHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler JsonHttpResponseHandler回调（返回json数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            JsonHttpResponseHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler JsonHttpHandler回调（返回json数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            JsonHttpHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler TextHttpHandler回调（返回string数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            TextHttpHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler FileHttpHandler回调（返回File数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            FileHttpHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler AsyHttpHandler回调（返回string数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            AsyHttpHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler AsyncHttpResponseHandler回调（返回String数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler FileAsyncHttpResponseHandler回调（返回File数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            FileAsyncHttpResponseHandler handler) {
        httpPost(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler BinaryHttpResponseHandler回调（返回byte[]数据�?
     * @Description: POST请求
     * @return: void
     */
    public static void post(String url, RequestParams params,
                            BinaryHttpResponseHandler handler) {
        httpPost(url, params, handler);
    }


    public static void httpPost(String url, RequestParams params,
                                ResponseHandlerInterface handler) {
        Log.i(TAG, "request post url=" + url);
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "request post url=" + url);
        }
        if (params == null) {
            client.post(url, handler);
        } else {
            client.post(url, params, handler);
        }
    }

    /******************************************************************************************
     * ===================================以下为GET方式请求========================================
     ******************************************************************************************/

    /**
     * @param url url地址
     * @param params 参数
     * @param asyncHttpResponseHandler TextHttpResponseHandler回调（返回String数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler asyncHttpResponseHandler) {
        httpGet(url, params, asyncHttpResponseHandler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler AsyHttpHandler回调（返回String数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           AsyHttpHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler TextHttpResponseHandler回调（返回String数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           TextHttpResponseHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler TextHttpHandler回调（返回String数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           TextHttpHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler JsonHttpResponseHandler回调（返回json数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           JsonHttpResponseHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler JsonHttpHandler回调（返回json数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           JsonHttpHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler FileAsyncHttpResponseHandler回调（返回File数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           FileAsyncHttpResponseHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler FileHttpHandler回调（返回json数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           FileHttpHandler handler) {
        httpGet(url, params, handler);
    }


    /**
     * @param url url地址
     * @param params 参数
     * @param handler BinaryHttpResponseHandler回调（返回byte[]数据�?
     * @Description: GET请求
     * @return: void
     */
    public static void get(String url, RequestParams params,
                           BinaryHttpResponseHandler handler) {
        httpGet(url, params, handler);
    }


    public static void httpGet(String url, RequestParams params,
                               ResponseHandlerInterface handler) {
        if (BuildConfig.DEBUG) {
            Log.i(TAG, "request get url=" + url);
        }

        if (params == null) {
            client.get(url, handler);
        } else {
            Log.i(TAG, "request get url=" + url + "?" + params.toString());
            client.get(url, params, handler);
        }
    }

    /******************************************************************************************
     * ===================================以下为请求handler====================================
     ******************************************************************************************/

    /**
     * @author li.xy
     * @ClassName: TextHttpHandler
     * @Description: 返回string
     * @date 2014-6-18 下午2:07:18
     */
    public static abstract class TextHttpHandler extends TextHttpResponseHandler {
        public abstract void start();
        public abstract void finish();
        public abstract void success(String content);
        public abstract void fail(Throwable error, String content);


        @Override
        public void onStart() {
            start();
        }


        @Override
        public void onFinish() {
            finish();
        }


        @Override
        public void onSuccess(int arg0, Header[] arg1, String arg2) {
            success(arg2);
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, String arg2,
                              Throwable arg3) {
            fail(arg3, arg2);
        }
        /*@Override
        public void onSuccess(int statusCode, String content) {
    		success(content);
    	}
    	@Override
    	public void onFailure(Throwable error, String content) {
    		fail(error, content);
    	}*/
    }


    /**
     * @author li.xy
     * @ClassName: JsonHttpHandler
     * @Description: 返回json
     * @date 2014-6-18 下午2:07:18
     */
    public static abstract class JsonHttpHandler extends JsonHttpResponseHandler {
        public abstract void start();
        public abstract void finish();
        public abstract void success(JSONObject jsonObject);
        public abstract void success(JSONArray jsonArray);
        public abstract void fail(Throwable error);


        @Override
        public void onStart() {
            start();
        }


        @Override
        public void onFinish() {
            finish();
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONArray response) {
            success(response);
            super.onSuccess(statusCode, headers, response);
        }


        @Override
        public void onSuccess(int statusCode, Header[] headers,
                              JSONObject response) {
            success(response);
            super.onSuccess(statusCode, headers, response);
        }


        @Override
        public void onFailure(int statusCode, Header[] headers,
                              String responseString, Throwable throwable) {
            fail(throwable);
            super.onFailure(statusCode, headers, responseString, throwable);
        }
        /*@Override
        public void onSuccess(int statusCode, JSONObject response) {
    		success(response);
    	}
    	
    	@Override
    	public void onSuccess(int statusCode, JSONArray response) {
    		success(response);
    	}
    	
    	@Override
    	public void onFailure(Throwable error) {
    		fail(error);
    	}*/
    }


    /**
     * @author li.xy
     * @ClassName: FileHttpHandler
     * @Description: 返回file
     * @date 2014-6-18 下午2:07:18
     */
    public static abstract class FileHttpHandler extends FileAsyncHttpResponseHandler {
        public FileHttpHandler(File file) {
            super(file);
        }


        public abstract void start();
        public abstract void finish();
        public abstract void success(File file);
        public abstract void progress(int bytesWritten, int totalSize);
        public abstract void fail(Throwable error);


        @Override
        public void onStart() {
            start();
        }


        @Override
        public void onFinish() {
            finish();
        }


        @Override
        public void onProgress(int bytesWritten, int totalSize) {
            progress(bytesWritten, totalSize);
        }


        @Override
        public void onSuccess(int arg0, Header[] arg1, File arg2) {
            success(arg2);
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, Throwable arg2, File arg3) {
            fail(arg2);
        }
    	/*@Override
    	public void onSuccess(int statusCode, File file) {
    		success(file);
    	}
    	
    	@Override
    	public void onFailure(Throwable error) {
    		fail(error);
    	}*/
    }


    /**
     * @author li.xy
     * @ClassName: AsyHttpHandler
     * @Description: 返回string
     * @date 2014-6-18 下午2:07:18
     */
    public static abstract class AsyHttpHandler extends AsyncHttpResponseHandler {
        public abstract void start();
        public abstract void finish();
        public abstract void success(String content);
        public abstract void fail(Throwable error);


        @Override
        public void onStart() {
            start();
        }


        @Override
        public void onFinish() {
            finish();
        }


        @Override
        public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
            success(new String(arg2));
        }


        @Override
        public void onFailure(int arg0, Header[] arg1, byte[] arg2,
                              Throwable arg3) {
            fail(arg3);
        }
    	
    	/*@Override
    	public void onSuccess(int statusCode, String content) {
    		success(content);
    	}
    	
    	@Override
    	public void onFailure(Throwable error) {
    		fail(error);
    	}*/
    }
}
