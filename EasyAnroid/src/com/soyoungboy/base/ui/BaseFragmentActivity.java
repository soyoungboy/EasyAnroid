package com.soyoungboy.base.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.soyoungboy.base.util.ImageLoadUtil;
import com.soyoungboy.base.util.Logger;
import com.soyoungboy.base.view.dialog.CommonProDialog;

/** 
* @ClassName: BaseActivity 
* @Description: 基础FragmentActivity
* @author soyoungboy
* @date 2014-6-18 下午5:23:25  
*/
public abstract class BaseFragmentActivity extends FragmentActivity implements OnClickListener{
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
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		// 竖屏锁定
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	}
	
	/** 
	 * @Description: 初始化UI(setContentView放在initView)    
	 * @return：void    
	 */
	protected abstract void initView();
	
	/** 
	 * @Description: 初始化顶部TitleView    
	 * @return：void    
	 */
	protected abstract void initTitleView();
	
	/** 
	 * @Description: 点击事件
	 * @param view    
	 * @return：void    
	 */
	protected abstract void click(View view);
	
	@Override
	 protected Dialog onCreateDialog(int id) {
	  switch (id) {
	  case DIALOG_PROGRESS:
		  waitingDialog = new CommonProDialog(BaseFragmentActivity.this, "正在加载...");
		  waitingDialog.setCanceledOnTouchOutside(true);
		  return waitingDialog;
		  /*progressDialog = new ProgressDialog(BaseActivity.this);
		  progressDialog.setMessage("加载�?..");
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
