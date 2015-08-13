package com.soyoungboy.base.util;

import org.json.JSONException;
import org.json.JSONObject;

/** 
* @ClassName: JsonUtils 
* @Description: json工具类
* @author soyoungboy
* @date 2014-6-19 上午10:02:47  
*/
public class JsonUtil {
	/** 
	 * @Description: 获取指定key的string
	 * @param jObject
	 * @param key
	 * @return：String    
	 */
	public static String getString(JSONObject jObject, String key){
		String result = "";
		try {
			result = jObject.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/** 
	 * @Description: 获取指定key的int
	 * @param jObject
	 * @param key
	 * @return：int    
	 */
	public static int getInt(JSONObject jObject, String key){
		int result = -1;
		try {
			result = jObject.getInt(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/** 
	 * @Description: 获取指定key的double
	 * @param jObject
	 * @param key
	 * @return：double    
	 */
	public static double getDouble(JSONObject jObject, String key){
		double result = -1;
		try {
			result = jObject.getDouble(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	/** 
	 * @Description: 是否存在指定key
	 * @param obj
	 * @param key
	 * @return：boolean    
	 */
	public static boolean isExistObj(JSONObject obj, String key){
		try {
			if(obj.has(key) && !obj.isNull(key)){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
