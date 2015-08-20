
package com.soyoungboy.base.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;

/**
 * @ClassName: ContentResolverUtils
 * @Description:用这个工具类可以查询安卓共享的数据，例如手机通讯录
 * @Author: soyoungboy
 * @Date: 2014-1-7 下午12:39:39
 */
public abstract class ContentResolverUtils {
    
    /**
     * 获取实例
     * 
     * @param context
     * @return
     */
    public static ContentResolver getContentResolver(Context context) {
        return context.getContentResolver();
    }
    
    /**
     * 查询数据
     * 
     * @param context
     * @param uri 查询地址
     * @param projection 需要返回字段
     * @param selection 查询条件
     * @param selectionArgs 查询条件参数
     * @param sortOrder 排序
     * @return
     */
    public static final Cursor query(Context context, Uri uri,
        String[] projection, String selection, String[] selectionArgs,
        String sortOrder) {
        return getContentResolver(context).query(uri,
            projection,
            selection,
            selectionArgs,
            sortOrder);
    }
    
    /**
     * 插入到共享数据中
     * 
     * @param context
     * @param url 插入地址
     * @param values 插入参数
     * @return
     */
    public static final Uri insert(Context context, Uri url,
        ContentValues values) {
        return getContentResolver(context).insert(url, values);
    }
    
    /**
     * 更新共享数据
     * 
     * @param context
     * @param uri 地址
     * @param values 更新数据
     * @param where 查询条件
     * @param selectionArgs 查询条件数据
     * @return
     */
    public static final int update(Context context, Uri uri,
        ContentValues values, String where, String[] selectionArgs) {
        return getContentResolver(context).update(uri,
            values,
            where,
            selectionArgs);
    }
    
    /**
     * 删除共享数据
     * 
     * @param context
     * @param url 地址
     * @param where 条件
     * @param selectionArgs 条件参数
     * @return
     */
    public static final int delete(Context context, Uri url, String where,
        String[] selectionArgs) {
        return getContentResolver(context).delete(url, where, selectionArgs);
    }
    
    /**
     * 绑定观察者
     * 
     * @param context
     * @param uri
     * @param notifyForDescendents
     * @param observer
     */
    public static void registerContentObserver(Context context, Uri uri,
        boolean notifyForDescendents, ContentObserver observer) {
        getContentResolver(context).registerContentObserver(uri,
            notifyForDescendents,
            observer);
    }
    
    /**
     * 解绑观察者
     * 
     * @param context
     * @param observer
     */
    public static void unregisterContentObserver(Context context,
        ContentObserver observer) {
        getContentResolver(context).unregisterContentObserver(observer);
    }
    
}
