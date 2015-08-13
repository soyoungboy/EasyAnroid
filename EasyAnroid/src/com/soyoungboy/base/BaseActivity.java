package com.soyoungboy.base;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.soyoungboy.base.dialog.CommonProDialog;
import com.soyoungboy.base.util.ImageLoadUtil;
import com.soyoungboy.base.util.Logger;

/**
 * 
 * @author soyoungboy
 *
 */
public abstract class BaseActivity extends Activity implements OnClickListener{
    protected String TAG = "belle";
    private final static int DIALOG_PROGRESS = 1;
    private ProgressDialog progressDialog;
    private CommonProDialog waitingDialog;
    protected Resources res;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        Logger.d(this.getClass().getSimpleName() + "====>onCreate");
        ImageLoadUtil.init(getApplicationContext());
        
        TAG = this.getClass().getSimpleName();
        
        res = getResources();
        // 鍙栨秷鏍囬
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        // 绔栧睆閿佸畾
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);		
    }
    
    /** 
     * @Description: 鍒濆鍖朥I(setContentView鏀惧湪initView)    
     * @return锛歷oid    
     */
    protected abstract void initView();
    
    /** 
     * @Description: 鍒濆鍖栭《閮═itleView    
     * @return锛歷oid    
     */
    protected abstract void initTitleView();
    
    /** 
     * @Description: 鐐瑰嚮浜嬩欢
     * @param view    
     * @return锛歷oid    
     */
    protected abstract void click(View view);
    
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_PROGRESS:
                waitingDialog = new CommonProDialog(BaseActivity.this, "姝ｅ湪鍔犺浇...");
                waitingDialog.setCanceledOnTouchOutside(false);
                return waitingDialog;
                /*progressDialog = new ProgressDialog(BaseActivity.this);
		  progressDialog.setMessage("鍔犺浇涓?..");
		  return progressDialog;*/
            default:
                break;
        }
        return super.onCreateDialog(id);  
    }
    
    protected void startProgressDialog() {
        showDialog(DIALOG_PROGRESS);
    }
    
    protected void dismissProgressDialog() {
        removeDialog(DIALOG_PROGRESS);
    }
    
    protected void showToast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
    
    protected void showToast(int msgId){
        Toast.makeText(getApplicationContext(), msgId, Toast.LENGTH_SHORT).show();
    }
    
    protected void getTextStr(TextView view) {
        view.getText().toString().trim();
    }
    
    protected String getResString(int id){
        return getResources().getString(id);
    }
    /**
     * 妫?煡瀛楃涓叉槸鍚︽槸绌哄璞℃垨绌哄瓧绗︿覆
     * @author wang.fb 
     * @param str
     * @return 涓虹┖杩斿洖true,涓嶄负绌鸿繑鍥瀎alse
     */
    public boolean isNull(String str) {
        if (TextUtils.isEmpty(str)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 浠庡綋鍓峚ctivity璺宠浆鍒扮洰鏍嘺ctivity,<br>
     * 濡傛灉鐩爣activity鏇剧粡鎵撳紑杩?灏遍噸鏂板睍鐜?<br>
     * 濡傛灉浠庢潵娌℃墦寮?繃,灏辨柊寤轰竴涓墦寮?
     * @author wang.fb
     * @time 2014_4_10
     * @param cls
     */
    @SuppressLint("InlinedApi")
    public void gotoExistActivity(Class<?> cls) {
        Intent intent;
        intent = new Intent(this.getApplicationContext(), cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }
    
    /**
     * 鏂板缓涓?釜activity鎵撳紑
     * @author wang.fb
     * @time 2014_4_10
     * @param cls
     */
    public void gotoActivity(Class<?> cls) {
        Intent intent;
        intent = new Intent(this.getApplicationContext(), cls);
        startActivity(intent);
    }
    
    @Override
    public void onClick(View v) {
        click(v);
    }
    
    @Override
    protected void onStart() {
        Logger.d(this.getClass().getSimpleName() + "====>onStart");
        super.onStart();
    }
    
    @Override
    protected void onRestart() {
        Logger.d(this.getClass().getSimpleName() + "====>onRestart");
        super.onRestart();
    }
    
    @Override
    protected void onResume() {
        Logger.d(this.getClass().getSimpleName() + "====>onResume");
        super.onResume();
    }	
    
    @Override
    protected void onPause() {
        Logger.d(this.getClass().getSimpleName() + "====>onPause");
        super.onPause();
    }
    
    @Override
    protected void onStop() {
        Logger.d(this.getClass().getSimpleName() + "====>onStop");
        super.onStop();
    }
    
    @Override
    protected void onDestroy() {
        Logger.d(this.getClass().getSimpleName() + "====>onDestroy");
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Logger.d(this.getClass().getSimpleName() + "====>onSaveInstanceState");
        super.onSaveInstanceState(outState);
    }
}
