package com.soyoungboy.base.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * viewpage中加载的basefragment，数据缓存等
 * @author soyoungboy
 *
 */
public abstract class BaseViewPagerFragment extends BaseFragment {
	private boolean isDataInit = false;
	private boolean isViewInit = false;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		if (baseView == null) {
			baseView = setBaseView();
			initView();
			isViewInit = true;
			setUserVisibleHint(true);
		}else if (baseView.getParent() != null) {
			((ViewGroup)baseView.getParent()).removeAllViews();
		}	
		return baseView;
	}

	@Override
	public void onResume() {
		super.onResume();
		initData();
	}

	/**
	 * 设置baseview
	 * @return fragment baseview
	 */
	protected abstract View setBaseView();

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		if (isVisibleToUser && isViewInit && !isDataInit) {
			initData();
			isDataInit = true;
		}
		super.setUserVisibleHint(isVisibleToUser);
	}

	/**
	 * 数据加载
	 */
	protected abstract void initData();

	/* (non-Javadoc)
	 * UI初始化
	 * @see com.wonhigh.base.BaseFragment#initView()
	 */
	@Override
	protected abstract void initView();

}
