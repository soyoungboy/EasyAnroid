package com.soyoungboy.base.util;

import android.text.TextUtils;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 字符串处理工具类
 *
 * @author xuan
 * @version $Revision: 1.0 $, $Date: 2013-3-25 上午9:25:38 $
 */
public class StringUtil {
    private static final String TAG = "StringUtil";


    private StringUtil() {
    }


    public static int StringToInt(String str, int def) {
        int intRet = def;
        try {
            if (str == null || str.trim().equals("")) {
                str = def + "";
            }
            intRet = Integer.parseInt(str);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return intRet;
    }


    /**
     * 将传入的金额（单位分）格式化为元，保留两位小数 格式如：5.00
     */
    public static String formatMoneyNoUnit(String s) {
        if (TextUtils.isEmpty(s)) {
            return "0.00";
        }
        double num = Double.parseDouble(s);
        DecimalFormat formater = new DecimalFormat("###,##0.00");
        return formater.format(num / 100.0d);
    }


    /**
     * 格式化金额
     */
    public static String formatMoney(String s, int len) {
        if (s == null || s.length() < 1) {
            return "";
        }
        NumberFormat formater = null;
        double num = Double.parseDouble(s);
        if (len == 0) {
            formater = new DecimalFormat("###,###");

        } else {
            StringBuffer buff = new StringBuffer();
            buff.append("###,###.");
            for (int i = 0; i < len; i++) {
                buff.append("#");
            }
            formater = new DecimalFormat(buff.toString());
        }
        String result = formater.format(num);
        if (result.indexOf(".") == -1) {
            result = "￥" + result + ".00";
        } else {
            result = "￥" + result;
        }
        return result;
    }


    /**
     * @throws
     * @name: isNumeric
     * @description: TODO(判断字符中是否含有数字“0~9”之间的)
     * @set: @param cs
     * @set: @return 设定文件
     * @return: boolean 返回类型
     * @time: 2014-6-13 下午1:44:28
     */
    public static boolean isNumeric(CharSequence cs) {
        if ((cs == null) || (cs.length() == 0)) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (!Character.isDigit(cs.charAt(i))) {
                return false;
            }
        }
        return true;
    }


    /**
     * CalendarActivity专用 描述：不足2个字符的在前面补“0”.
     *
     * @param str 指定的字符串
     * @return 至少2个字符的字符串
     */
    public static String strFormat2(String str) {
        try {
            if (str.length() <= 1) {
                str = "0" + str;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }


    public static String newString(byte[] bs, String charset) {
        try {
            return new String(bs, charset);
        } catch (UnsupportedEncodingException e) {
            Logger.e(TAG, e.getMessage());
        }
        return null;
    }


    public static byte[] getBytes(String str, String charsetName) {
        try {
            return str.getBytes(charsetName);
        } catch (UnsupportedEncodingException e) {
            Logger.e(TAG, e.getMessage());
        }
        return null;
    }


    /**
     * 截取固定长度的字符串，超长部分用suffix代替，最终字符串真实长度不会超过maxLength.
     */
    public static String cutOut(String str, int maxLength, String suffix) {
        if (Validators.isEmpty(str)) {
            return str;
        }

        int byteIndex = 0;
        int charIndex = 0;

        while (charIndex < str.length() && byteIndex <= maxLength) {
            char c = str.charAt(charIndex);
            if (c >= 256) {
                byteIndex += 2;
            } else {
                byteIndex++;
            }
            charIndex++;
        }

        if (byteIndex <= maxLength) {
            return str;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(str.substring(0, charIndex));
        sb.append(suffix);

        while (getRealLength(sb.toString()) > maxLength) {
            sb.deleteCharAt(--charIndex);
        }

        return sb.toString();
    }


    /**
     * 取得字符串的真实长度，一个汉字长度为两个字节。
     *
     * @param str 字符串
     * @return 字符串的字节数
     */
    public static int getRealLength(String str) {
        if (str == null) {
            return 0;
        }

        char separator = 256;
        int realLength = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) >= separator) {
                realLength += 2;
            } else {
                realLength++;
            }
        }
        return realLength;
    }


    /**
     * 字符串过滤null
     */
    public static String filterNull(String oldStr) {
        if (null == oldStr || "null".equals(oldStr)) {
            return "";
        }

        return oldStr;
    }


    /**
     * @param @param str
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isEmpty
     * @Description: TODO(判断字符串是否为空或长度为0)
     */
    public static boolean isEmpty(String str) {
        return (str == null || str.length() == 0);
    }


    /**
     * @param @param str
     * @param @return 设定文件
     * @return boolean 返回类型
     * @throws
     * @Title: isBlank
     * @Description: TODO(判断字符串是否为空或长度为0 或由空格组成)
     */
    public static boolean isBlank(String str) {
        return (str == null || str.trim().length() == 0);
    }


    /**
     * 编码UTF-8
     */
    public static String encodeToUTF8(String str) {
        if (!isEmpty(str) && str.getBytes().length != str.length()) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(
                    "UnsupportedEncodingException occurred. ", e);
            }
        }
        return str;
    }


    /**
     * @param @param str
     * @param @return 设定文件
     * @return String 返回类型
     * @throws
     * @Title: capitalizeFirstLetter
     * @Description: TODO(首字母大写)
     */
    public static String capitalizeFirstLetter(String str) {
        if (isEmpty(str)) {
            return str;
        }
        char c = str.charAt(0);
        return (!Character.isLetter(c) || Character.isUpperCase(c)) ? str
                                                                    :
               String.valueOf(Character.toUpperCase(c)) +
                   str.substring(1);
    }
}
