package com.soyoungboy.base.util;

import java.util.List;

/**
 * List工具类
 *
 * @author wangfubin
 */
public class ListUtil {
    /**
     * @param @param sourceList
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isEmpty
     * @Description: TODO(判断List是否为null)
     */
    public static <V> boolean isEmpty(List<V> sourceList) {
        return (sourceList == null || sourceList.size() == 0);
    }
}
