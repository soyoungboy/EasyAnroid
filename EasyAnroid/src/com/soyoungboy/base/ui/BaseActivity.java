package com.soyoungboy.base.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @类名: BaseActivity
 * @描述: TODO(Activity基类，所有的Activity都要继承自该Activity)
 * @作者: soyoungboy
 */
public class BaseActivity extends Activity {

    // 为0 短时间
    // 为1 长时间
    public static final int LENGTH_TIME = 0;
    Resources res; // 通用资源缩写
    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示标题
        super.onCreate(savedInstanceState);

        res = getResources(); // 通用资源缩写
        context = getApplicationContext();
        // 优化输入法模式
        int inputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN;
        getWindow().setSoftInputMode(inputMode);
    }


    /**
     * 检查字符串是否是空对象或空字符串
     *
     * @return 为空返回true, 不为空返回false
     */
    public boolean isNull(String str) {
        return TextUtils.isEmpty(str);
    }


    /**
     * 检查字符串是否是字符串
     *
     * @return 为空返回true, 不为空返回false
     */
    public boolean isStr(String str) {
        return !isNull(str);
    }


    /**
     * 从当前activity跳转到目标activity,<br>
     * 如果目标activity曾经打开过,就重新展现,<br>
     * 如果从来没打开过,就新建一个打开
     */
    public void gotoExistActivity(Class<?> cls) {
        Intent intent;
        intent = new Intent(this.getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }


    /**
     * 新建一个activity打开
     */
    public void gotoActivity(Class<?> cls) {
        Intent intent;
        intent = new Intent(this.getApplicationContext(), cls);
        startActivity(intent);
    }


    /**
     * @param @param context
     * @param @param message 要显示的内容
     * @return void 返回类型
     * @throws
     * @Title: toast
     * @Description: TODO(土司操作)
     */
    public void toast(String message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT)
            .show();
    }


    /**
     * @param @param context
     * @param @param message 设定文件
     * @return void 返回类型
     * @throws
     * @Title: toast
     * @Description: TODO(土司操作)
     */
    public void toast(int message) {
        Toast.makeText(this.getApplicationContext(), message, Toast.LENGTH_SHORT)
            .show();
    }


    /**
     * 从资源获取字符串
     */
    public String getStr(int resId) {
        return res.getString(resId);
    }


    /**
     * @throws
     * @方法名: getDemon
     * @描述: TODO(获取资源文件中的尺寸内容)
     * @设定: @param mRid
     * @设定: @return 设定文件
     * @返回: int 返回类型
     * @日期: 2014-6-30 下午2:14:11
     */
    protected int getDemon(int mRid) {
        return (int) res.getDimension(mRid);
    }


    /**
     * 从EditText 获取字符串
     */
    public String getStr(EditText editText) {
        return editText.getText().toString();
    }


    /**
     * 获取boolean值
     */
    public boolean getBoolean(int resId) {
        return getResources().getBoolean(resId);
    }


    /**
     * 获取颜色值
     */
    public int getColor(int resId) {
        return getResources().getColor(resId);
    }


    public ColorStateList getColorStateList(int resId) {
        return getResources().getColorStateList(resId);
    }


    /**
     * 获取尺寸信息
     */
    public float getDimension(int resId) {
        return getResources().getDimension(resId);
    }


    public float getDimensionPixelOffset(int resId) {
        return getResources().getDimensionPixelOffset(resId);
    }


    public float getDimensionPixelSize(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }


    public Drawable getImgDrawable(int resId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return getResources().getDrawable(resId, context.getTheme());
        } else {
            return getResources().getDrawable(resId);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
