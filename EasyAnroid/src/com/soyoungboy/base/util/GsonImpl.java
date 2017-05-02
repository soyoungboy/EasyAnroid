package com.soyoungboy.base.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: json工具类
 *
 * @author 我只添加了最后一个方法，其他部分来自lite的工具类
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
        return gson.fromJson(json, type);
    }

}
