package com.soyoungboy.base.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.soyoungboy.base.util.Logger;

/** 
* @ClassName: BaseFragment 
* @Description: 基础fragment
* @author soyoungboy
* @date 2014-6-18 下午5:23:38  
*/
public abstract class BaseFragment extends Fragment implements OnClickListener{
	protected Context context;
	protected View baseView;
	
	@Override
	public void onAttach(Activity activity) {
		Logger.d(this.getClass().getSimpleName()+"====>onAttach");
		context = activity;
		super.onAttach(activity);
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Logger.d(this.getClass().getSimpleName()+"====>onCreate");
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Logger.d(this.getClass().getSimpleName()+"====>onCreateView");
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Logger.d(this.getClass().getSimpleName()+"====>onActivityCreated");
		super.onActivityCreated(savedInstanceState);
	}
	
	/** 
	 * @Description: view初始�?   
	 * @return：void    
	 */
	protected abstract void initView();
	
	/** 
	 * @Description: 点击事件
	 * @param view    
	 * @return：void    
	 */
	protected abstract void click(View view);
	
	@Override
	public void onClick(View v) {
		click(v);
	}
	
	@Override
	public void onStart() {
		Logger.d(this.getClass().getSimpleName()+"====>onStart");
		super.onStart();
	}
	
	@Override
	public void onResume() {
		Logger.d(this.getClass().getSimpleName()+"====>onResume");
		super.onResume();
	}
	
	@Override
	public void onPause() {
		Logger.d(this.getClass().getSimpleName()+"====>onPause");
		super.onPause();
	}
	
	@Override
	public void onStop() {
		Logger.d(this.getClass().getSimpleName()+"====>onStop");
		super.onStop();
	}
	
	@Override
	public void onDestroyView() {
		Logger.d(this.getClass().getSimpleName()+"====>onDestroyView");
		super.onDestroyView();
	}
	
	@Override
	public void onDestroy() {
		Logger.d(this.getClass().getSimpleName()+"====>onDestroy");
		super.onDestroy();
	}
	
	@Override
	public void onDetach() {
		Logger.d(this.getClass().getSimpleName()+"====>onDetach");
		super.onDetach();
	}
}
