package com.soyoungboy.base.view.viewpagerindicator;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.soyoungboy.base.R;
/**
 * 修改tab下面的下滑线滑动切换动画效果
 * 参考：http://blog.csdn.net/lancees/article/details/9164421
 * @author soyoungboy
 *
 */
public class UnderlinePageIndicatorEx extends UnderlinePageIndicator{

	public UnderlinePageIndicatorEx(Context context) {
		super(context, null);

	}

	public UnderlinePageIndicatorEx(Context context, AttributeSet attrs) {
		super(context, attrs, R.attr.vpiUnderlinePageIndicatorStyle);

	}

	public UnderlinePageIndicatorEx(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}


	@Override
	public void setViewPager(ViewPager viewPager) {
		if (mViewPager == viewPager) {
			return;
		}
		//	        if (mViewPager != null) {
		//	            //Clear us from the old pager.
		//	            mViewPager.setOnPageChangeListener(null);
		//	        }
		if (viewPager.getAdapter() == null) {
			throw new IllegalStateException("ViewPager does not have adapter instance.");
		}
		mViewPager = viewPager;
		//	        mViewPager.setOnPageChangeListener(this);
		invalidate();
		post(new Runnable() {
			@Override public void run() {
				if (mFades) {
					post(mFadeRunnable);
				}
			}
		});
	}
}
