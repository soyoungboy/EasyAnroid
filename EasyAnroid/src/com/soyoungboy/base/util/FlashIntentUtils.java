package com.soyoungboy.base.util;

/**
 * TODO: Activity之间传递list,对象等工具类
 * 
 * @author soyoungboy
 */
public class FlashIntentUtils {
	private Object object;
	public void putExtra(Object object) {
		this.object = object;
	}
	public Object getExtra() {
		Object extra = object;
		this.object = null;
		return extra;
	}
}
