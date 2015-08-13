package com.soyoungboy.base.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @ClassName: LoadingView
 * @Description: 加载等待类
 * @author: soyoungboy
 * @date: 2014-5-13 下午4:32:36
 */
public class LoadingView extends RelativeLayout {
	private static final int LOADING_BG_ID = 101;
	private static final int LOADING_PGBAR_ID = 102;
	private TextView localTextView;

	public LoadingView(Context paramContext) {
		super(paramContext);
		init(paramContext);
	}

	public LoadingView(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		init(paramContext);
	}

	public LoadingView(Context paramContext,
			AttributeSet paramAttributeSet, int paramInt) {
		super(paramContext, paramAttributeSet, paramInt);
		init(paramContext);
	}

	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	private void init(Context paramContext) {
		float f1 = paramContext.getResources().getDisplayMetrics().density;
		float f2 = f1 * 20.0F;
		View localView = new View(paramContext);
		localView.setId(LOADING_BG_ID);
		GradientDrawable localGradientDrawable = new GradientDrawable();
		localGradientDrawable.setCornerRadius(f2);
		localGradientDrawable.setColor(Color.BLACK);
		localGradientDrawable.setAlpha(150);
		if (Build.VERSION.SDK_INT >= 16) {
			localView.setBackground(localGradientDrawable);
		} else {
			localView.setBackgroundDrawable(localGradientDrawable);
		}

		int i = (int) (120.0F * f1);
		RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(
				i, i);
		localLayoutParams1.addRule(RelativeLayout.CENTER_IN_PARENT);
		addView(localView, localLayoutParams1);
		ProgressBar localProgressBar = new ProgressBar(paramContext);
		localProgressBar.setId(LOADING_PGBAR_ID);
		RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		localLayoutParams2.addRule(RelativeLayout.CENTER_HORIZONTAL);
		localLayoutParams2.addRule(RelativeLayout.ALIGN_TOP, LOADING_BG_ID);
		localLayoutParams2.topMargin = (10 + (int) (f1 * 20.0F));
		addView(localProgressBar, localLayoutParams2);
		localTextView = new TextView(paramContext);
		localTextView.setText("正在加载...");
		localTextView.setTextColor(Color.WHITE);
		RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		localLayoutParams3.addRule(RelativeLayout.CENTER_HORIZONTAL);
		localLayoutParams3.addRule(RelativeLayout.BELOW, LOADING_PGBAR_ID);
		addView(localTextView, localLayoutParams3);

	}

	public TextView getLocalTextView() {
		return localTextView;
	}

	public void setLocalTextView(TextView localTextView) {
		this.localTextView = localTextView;
	}	
}