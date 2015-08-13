
package com.soyoungboy.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;

/**
 * @ClassName: TimeUtil
 * @Description: 时间工具 类
 * @author soyoungboy
 * @date 2014-6-17 下午1:53:30
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

	public static String getData(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(time));
	}

	public static String getTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String getChatTime(long timesamp) {
		String result = "";
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		Date today = new Date(System.currentTimeMillis());
		Date otherDay = new Date(timesamp);
		int temp =
				Integer.parseInt(sdf.format(today))
				- Integer.parseInt(sdf.format(otherDay));

		switch (temp) {
		case 0:
			result = "今天 " + getHourAndMin(timesamp);
			break;
		case 1:
			result = "昨天 " + getHourAndMin(timesamp);
			break;
		case 2:
			result = "前天 " + getHourAndMin(timesamp);
			break;

		default:
			result = getTime(timesamp);
			break;
		}
		return result;
	}

	/**
	 * @方法�? twoDateDistance
	 * @描述: 比较两个日期相差几天
	 * @设定: @param startDate
	 * @设定: @param endDate
	 * @设定: @return 设定文件
	 * @返回: long 返回类型
	 * @日期: 2014-7-8 下午3:09:31
	 * @throws
	 */
	public static long twoDateDistance(long startDate, long endDate) {
		long timeLong = endDate - startDate;
		if (timeLong < 60 * 60 * 24 * 1000 * 7) {
			timeLong = timeLong / 1000 / 60 / 60 / 24;
			Logger.d(timeLong + "天前");
			return timeLong;
		}

		// if (timeLong < 60 * 60 * 1000) {
		// timeLong = timeLong / 1000 / 60;
		// Logger.d(timeLong + "分钟�?);
		// return timeLong;
		// }
		return -1;
	}

	/**
	 * @Description 指定年月的最后一天日�?     * @param year
	 * @param month
	 * @param simpleDateFormat
	 * @return String
	 */
	public static String getMonthLastDayDate(int year, int month,
			SimpleDateFormat simpleDateFormat) {
		if (year < 0) return null;
		Calendar cal = Calendar.getInstance();
		if (month < 0) {
			cal.set(Calendar.YEAR, year - 1);
			cal.set(Calendar.MONTH, 12 + month % 11);
		} else if (month > 11) {
			cal.set(Calendar.YEAR, year + 1);
			cal.set(Calendar.MONTH, month % 11);
		} else {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
		}
		cal.set(Calendar.DAY_OF_MONTH,
				cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String date = simpleDateFormat.format(cal.getTime());
		return date;
	}

	/**
	 * @Description 指定年月的第�?��日期
	 * @param year
	 * @param month
	 * @param simpleDateFormat
	 * @return String
	 */
	public static String getMonthFirstDayDate(int year, int month,
			SimpleDateFormat simpleDateFormat) {
		if (year < 0) return null;
		Calendar cal = Calendar.getInstance();

		if (month < 0) {
			cal.set(Calendar.YEAR, year - 1);
			cal.set(Calendar.MONTH, 12 + month);
		} else if (month > 11) {
			cal.set(Calendar.YEAR, year + 1);
			cal.set(Calendar.MONTH, month % 11);
		} else {
			cal.set(Calendar.YEAR, year);
			cal.set(Calendar.MONTH, month);
		}
		cal.set(Calendar.DAY_OF_MONTH, 1);
		String date = simpleDateFormat.format(cal.getTime());
		return date;
	}

	/**
	 * @Description: 指定日期当周第一天或�?���?��
	 * @param cal
	 * @param flag
	 * @return：String
	 */
	public static String getFirstLastWeek(Calendar cal, boolean flag) {
		// flag true周第�?�� false 周最后一�?        
		int dw = cal.get(Calendar.DAY_OF_WEEK);
		if (!flag) cal.setTimeInMillis(cal.getTimeInMillis() + (7 - dw) * 24
				* 60 * 60 * 1000);
		else cal.setTimeInMillis(cal.getTimeInMillis() - (dw - 1) * 24 * 60
				* 60 * 1000);

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		String showTime = formatter.format(cal.getTime());
		return showTime.toString();
	}

	/**
	 * @Description: 指定日期当月第一天或�?���?��
	 * @param cal
	 * @param flag
	 * @return：String
	 */
	public static String getFirstLastMoonth(Calendar cal, boolean flag) {
		// flag true月第�?�� false 月最后一�?        cal.set(Calendar.DAY_OF_MONTH, 1);
		if (!flag) {
			cal.roll(Calendar.DAY_OF_MONTH, -1);
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String showTime = formatter.format(cal.getTime());
		return showTime.toString();
	}

	/**
	 * @Description: 指定日期string格式
	 * @param cal
	 * @return：String
	 */
	public static String getDateString(Calendar cal) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String showTime = formatter.format(cal.getTime());
		return showTime.toString();
	}
}
