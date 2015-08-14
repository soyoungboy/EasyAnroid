package com.soyoungboy.base.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 
 * TODO: json工具类
 * 
 * @author soyoungboy
 * @date 2014-11-8 下午2:32:43
 */
public class GsonImpl extends Json {
	private Gson gson = new Gson();

	@Override
	public String toJson(Object src) {
		return gson.toJson(src);
	}

	@Override
	public <T> T toObject(String json, Class<T> claxx) {
		return gson.fromJson(json, claxx);
	}

	@Override
	public <T> T toObject(byte[] bytes, Class<T> claxx) {
		return gson.fromJson(new String(bytes), claxx);
	}

	@Override
	public <T> List<T> toList(String json, Class<T> claxx) {
		  Type type = new TypeToken<ArrayList<T>>() {}.getType();  
	         List<T> list = gson.fromJson(json, type);  
		return list;
	}

}
