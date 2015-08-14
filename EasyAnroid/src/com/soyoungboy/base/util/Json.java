package com.soyoungboy.base.util;

import java.util.List;

/**
 * TODO: json工具类
 * 
 * @author soyoungboy
 * @date 2014-11-8 下午2:32:24
 */
public abstract class Json {
	private static Json json;

	Json() {
	}

	public static Json get() {
		if (json == null) {
			json = new GsonImpl();
		}
		return json;
	}

	public abstract String toJson(Object src);

	public abstract <T> T toObject(String json, Class<T> claxx);

	public abstract <T> T toObject(byte[] bytes, Class<T> claxx);

	public abstract <T> List<T> toList(String json, Class<T> claxx);
}
