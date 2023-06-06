/**
 * project: ecl
 * DateTimeUtil.java 2009-2-17 上午10:39:14
 * Shanghua Times, All right reserved.
 */
package com.mytest.myservlet;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.joda.time.DateTime;

/**
 * 此中的方法有问题的地方就是必须新建一个DateTime对像不是太好（存一个DateTime属性）
 * 
 * @author guoguo Email:jiaguo.tian#echineselearning.com 2009-2-17 上午10:39:14
 */
public class DateTimeUtil {
	private static final DateTimeService dateTimeService = new JdkDateTimeService();

	/**
	 * 取得09:30或09:30AM <br>
	 * 2009-2-23 上午09:03:33
	 * 
	 * @param dateTime
	 * @param is24Hour
	 * @return
	 */
	public static String getTimeRawShow(Date dateTime, boolean is24Hour) {
		if (is24Hour) {
			return dateTimeService.toString(dateTime, "HH:mm");
		} else {
			return dateTimeService.toString(dateTime, "hh:mm aa", Locale.US);
		}
	}
	/**
	 * 取得09:30 <br>
	 * 不显示 AM 或者PM
	 * @param dateTime
	 * @param is24Hour
	 * @return
	 */
	public static String getTimeRawNoShowAPM(Date dateTime, boolean is24Hour) {
		if (is24Hour) {
			return dateTimeService.toString(dateTime, "HH:mm");
		} else {
			return dateTimeService.toString(dateTime, "hh:mm", Locale.US);
		}
	}

	/**
	 * 格式化时间为字串 <br>
	 * 2009-2-23 上午09:04:31
	 * 
	 * @param datetime
	 * @param pattern
	 * @return
	 */
	public static String toString(Date datetime, String pattern) {
		return dateTimeService.toString(datetime, pattern);
	}

	/**
	 * 格式化时间为字串 <br>
	 * 2009-2-23 上午09:04:45
	 * 
	 * @param datetime
	 * @param pattern
	 * @param locale
	 * @return
	 */
	public static String toString(Date datetime, String pattern, Locale locale) {
		return dateTimeService.toString(datetime, pattern, locale);
	}

	/**
	 * 取得年 <br>
	 * 2009-2-23 上午09:04:57
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getYear(Date datetime) {
		return dateTimeService.getYear(datetime);
	}

	/**
	 * 取得月，一月为1 <br>
	 * 2009-2-23 上午09:05:07
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getMonthOfYear(Date datetime) {
		return dateTimeService.getMonthOfYear(datetime);
	}

	/**
	 * 取得几号 <br>
	 * 2009-2-23 上午09:05:27
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getDayOfMonth(Date datetime) {
		return dateTimeService.getDayOfMonth(datetime);
	}

	/**
	 * 取得周,周日为7 <br>
	 * 2009-2-23 上午09:05:46
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getDayOfWeek(Date datetime) {
		return dateTimeService.getDayOfWeek(datetime);
	}

	/**
	 * 取得小时 <br>
	 * 2009-2-23 上午09:06:07
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getHourOfDay(Date datetime) {
		return dateTimeService.getHourOfDay(datetime);
	}

	/**
	 * 取得分钟 <br>
	 * 2009-2-23 上午09:06:21
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getMinuteOfHour(Date datetime) {
		return dateTimeService.getMinuteOfHour(datetime);
	}

	/**
	 * 取得秒 <br>
	 * 2009-2-23 上午09:06:36
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getSecondOfMinute(Date datetime) {
		return dateTimeService.getSecondOfMinute(datetime);
	}

	/**
	 * 取得毫秒 <br>
	 * 2009-2-23 上午09:07:43
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getMillisOfSecond(Date datetime) {
		return dateTimeService.getMillisOfSecond(datetime);
	}

	/**
	 * 取得一年中第几天 <br>
	 * 2009-2-23 上午09:07:52
	 * 
	 * @param datetime
	 * @return
	 */
	public static int getDayOfYear(Date datetime) {
		return dateTimeService.getDayOfYear(datetime);
	}

	/**
	 * 年的增减 <br>
	 * 2009-2-23 上午09:08:03
	 * 
	 * @param datetime
	 * @param years
	 * @return
	 */
	public static Date plusYears(Date datetime, int years) {
		return dateTimeService.plusYears(datetime, years);
	}

	/**
	 * 月份的增减 <br>
	 * 2009-2-23 上午09:08:10
	 * 
	 * @param datetime
	 * @param months
	 * @return
	 */
	public static Date plusMonths(Date datetime, int months) {
		return dateTimeService.plusMonths(datetime, months);
	}

	/**
	 * 周的增减 <br>
	 * 2009-2-23 上午09:08:52
	 * 
	 * @param datetime
	 * @param weeks
	 * @return
	 */
	public static Date plusWeeks(Date datetime, int weeks) {
		return dateTimeService.plusWeeks(datetime, weeks);
	}

	/**
	 * 天的增减 <br>
	 * 2009-2-23 上午09:08:59
	 * 
	 * @param datetime
	 * @param days
	 * @return
	 */
	public static Date plusDays(Date datetime, int days) {
		return dateTimeService.plusDays(datetime, days);
	}

	/**
	 * 小时的增减 <br>
	 * 2009-2-23 上午09:09:09
	 * 
	 * @param datetime
	 * @param hours
	 * @return
	 */
	public static Date plusHours(Date datetime, int hours) {
		return dateTimeService.plusHours(datetime, hours);
	}

	/**
	 * 分的增减 <br>
	 * 2009-2-23 上午09:09:21
	 * 
	 * @param datetime
	 * @param minutes
	 * @return
	 */
	public static Date plusMinutes(Date datetime, int minutes) {
		return dateTimeService.plusMinutes(datetime, minutes);
	}

	/**
	 * 秒的增减 <br>
	 * 2009-2-23 上午09:09:37
	 * 
	 * @param datetime
	 * @param seconds
	 * @return
	 */
	public static Date plusSeconds(Date datetime, int seconds) {
		return dateTimeService.plusSeconds(datetime, seconds);
	}

	/**
	 * 毫秒数的增减 <br>
	 * 2009-2-23 上午09:09:43
	 * 
	 * @param datetime
	 * @param millis
	 * @return
	 */
	public static Date plusMillis(Date datetime, int millis) {
		return dateTimeService.plusMillis(datetime, millis);
	}

	/**
	 * DateTime操作的接口
	 * 
	 * @author guoguo Email:jiaguo.tian#echineselearning.com 2009-2-23
	 *         上午09:09:59
	 */
	private static interface DateTimeService {
		public String toString(Date datetime, String pattern);

		public String toString(Date datetime, String pattern, Locale locale);

		public int getYear(Date datetime);

		public int getMonthOfYear(Date datetime);

		public int getDayOfMonth(Date datetime);

		public int getDayOfWeek(Date datetime);

		public int getHourOfDay(Date datetime);

		public int getMinuteOfHour(Date datetime);

		public int getSecondOfMinute(Date datetime);

		public int getMillisOfSecond(Date datetime);

		public int getDayOfYear(Date datetime);

		// public int getMinuteOfDay(Date datetime);
		//
		// public int getSecondOfDay(Date datetime) ;
		//
		// public int getMillisOfDay(Date datetime);

		public Date plusYears(Date datetime, int years);

		public Date plusMonths(Date datetime, int months);

		public Date plusWeeks(Date datetime, int weeks);

		public Date plusDays(Date datetime, int days);

		public Date plusHours(Date datetime, int hours);

		public Date plusMinutes(Date datetime, int minutes);

		public Date plusSeconds(Date datetime, int seconds);

		public Date plusMillis(Date datetime, int millis);
	}

	/**
	 * JDK的实现
	 * 
	 * @author guoguo Email:jiaguo.tian#echineselearning.com 2009-2-23
	 *         上午09:10:18
	 */
	private static class JdkDateTimeService implements DateTimeService {
		public String toString(Date datetime, String pattern) {
			if (datetime == null) {
				return "";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(datetime);
		}

		public String toString(Date datetime, String pattern, Locale locale) {
			if (datetime == null) {
				return "";
			}
			SimpleDateFormat format = new SimpleDateFormat(pattern, locale);
			return format.format(datetime);
		}

		public int getYear(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.YEAR);
		}

		public int getMonthOfYear(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.MONTH) + 1;
		}

		public int getDayOfMonth(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.DAY_OF_MONTH);
		}

		public int getDayOfWeek(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
			dayOfWeek -= 1;
			if (dayOfWeek == 0) {
				dayOfWeek = 7;
			}
			return dayOfWeek;
		}

		public int getHourOfDay(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.HOUR_OF_DAY);
		}

		public int getMinuteOfHour(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.MINUTE);
		}

		public int getSecondOfMinute(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.SECOND);
		}

		public int getMillisOfSecond(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.MILLISECOND);
		}

		public int getDayOfYear(Date datetime) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			return cal.get(Calendar.DAY_OF_YEAR);
		}

		// public int getMinuteOfDay(Date datetime) {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(datetime);
		// return 0;
		// }
		//
		// public int getSecondOfDay(Date datetime) {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(datetime);
		// return 0;
		// }
		//
		// public int getMillisOfDay(Date datetime) {
		// Calendar cal = Calendar.getInstance();
		// cal.setTime(datetime);
		// return 0;
		// }

		public Date plusYears(Date datetime, int years) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.YEAR, years);
			return cal.getTime();
		}

		public Date plusMonths(Date datetime, int months) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.MONTH, months);
			return cal.getTime();
		}

		public Date plusWeeks(Date datetime, int weeks) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.WEEK_OF_YEAR, weeks);
			return cal.getTime();
		}

		public Date plusDays(Date datetime, int days) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.DAY_OF_YEAR, days);
			return cal.getTime();
		}

		public Date plusHours(Date datetime, int hours) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.HOUR_OF_DAY, hours);
			return cal.getTime();
		}

		public Date plusMinutes(Date datetime, int minutes) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.MINUTE, minutes);
			return cal.getTime();
		}

		public Date plusSeconds(Date datetime, int seconds) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.SECOND, seconds);
			return cal.getTime();
		}

		public Date plusMillis(Date datetime, int millis) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(datetime);
			cal.add(Calendar.MILLISECOND, millis);
			return cal.getTime();
		}
	}

	/**
	 * JODA的实现
	 * 
	 * @author guoguo Email:jiaguo.tian#echineselearning.com 2009-2-23
	 *         上午09:10:31
	 */
	private static class JodaDateTimeService implements DateTimeService {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getDayOfMonth(java.util.Date)
		 */
		public int getDayOfMonth(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getDayOfMonth();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getDayOfWeek(java.util.Date)
		 */
		public int getDayOfWeek(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getDayOfWeek();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getDayOfYear(java.util.Date)
		 */
		public int getDayOfYear(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getDayOfYear();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getHourOfDay(java.util.Date)
		 */
		public int getHourOfDay(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getHourOfDay();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getMillisOfSecond(java.util.Date)
		 */
		public int getMillisOfSecond(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getMillisOfSecond();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getMinuteOfHour(java.util.Date)
		 */
		public int getMinuteOfHour(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getMinuteOfHour();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getMonthOfYear(java.util.Date)
		 */
		public int getMonthOfYear(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getMonthOfYear();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getSecondOfMinute(java.util.Date)
		 */
		public int getSecondOfMinute(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getSecondOfMinute();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#getYear(java.util.Date)
		 */
		public int getYear(Date date) {
			DateTime dateTime = new DateTime(date);
			return dateTime.getYear();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusDays(java.util.Date,
		 *      int)
		 */
		public Date plusDays(Date date, int days) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusDays(days).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusHours(java.util.Date,
		 *      int)
		 */
		public Date plusHours(Date date, int hours) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusHours(hours).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusMillis(java.util.Date,
		 *      int)
		 */
		public Date plusMillis(Date date, int millis) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusMillis(millis).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusMinutes(java.util.Date,
		 *      int)
		 */
		public Date plusMinutes(Date date, int minutes) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusMinutes(minutes).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusMonths(java.util.Date,
		 *      int)
		 */
		public Date plusMonths(Date date, int months) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusMonths(months).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusSeconds(java.util.Date,
		 *      int)
		 */
		public Date plusSeconds(Date date, int seconds) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusSeconds(seconds).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusWeeks(java.util.Date,
		 *      int)
		 */
		public Date plusWeeks(Date date, int weeks) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusWeeks(weeks).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#plusYears(java.util.Date,
		 *      int)
		 */
		public Date plusYears(Date date, int years) {
			DateTime dateTime = new DateTime(date);
			return dateTime.plusYears(years).toDate();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#toString(java.util.Date,
		 *      java.lang.String, java.util.Locale)
		 */
		public String toString(Date date, String pattern, Locale locale) {
			if (date == null) {
				return "";
			}
			DateTime dateTime = new DateTime(date);
			return dateTime.toString(pattern, locale);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.ecl.commons.DateTimeUtil.DateTimeService#toString(java.util.Date,
		 *      java.lang.String)
		 */
		public String toString(Date date, String pattern) {
			if (date == null) {
				return "";
			}
			DateTime dateTime = new DateTime(date);
			return dateTime.toString(pattern);
		}

	}

	/**
	 * <br>
	 * 2009-2-17 上午10:39:14
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		DateTimeService js = new JodaDateTimeService();
		DateTimeService od = new JdkDateTimeService();
		Date t = new Date();
		System.out.println("-----------start check-----------");
		for (int i = 0; i <= 8; i++) {
			Date dt = js.plusDays(t, i);
			if (js.getDayOfWeek(dt) != od.getDayOfWeek(dt)) {
				System.out.println("getDayOfWeek:false");
			}
			if (!js.toString(dt, "yyyy-MM-dd HH:mm:ss").equals(
					od.toString(dt, "yyyy-MM-dd HH:mm:ss"))) {
				System.out.println("toString:false");
			}
			if (!js.toString(dt, "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH).equals(
					od.toString(dt, "yyyy-MM-dd HH:mm:ss", Locale.ENGLISH))) {
				System.out.println("toString:false");
			}
			if (js.getYear(dt) != od.getYear(dt)) {
				System.out.println("getYear:false");
			}
			if (js.getMonthOfYear(dt) != od.getMonthOfYear(dt)) {
				System.out.println("getMonthOfYear:false");
			}
			if (js.getDayOfMonth(dt) != od.getDayOfMonth(dt)) {
				System.out.println("getDayOfMonth:false");
			}
			if (js.getDayOfWeek(dt) != od.getDayOfWeek(dt)) {
				System.out.println("getDayOfWeek:false");
			}
			if (js.getHourOfDay(dt) != od.getHourOfDay(dt)) {
				System.out.println("getHourOfDay:false");
			}
			if (js.getMinuteOfHour(dt) != od.getMinuteOfHour(dt)) {
				System.out.println("getMinuteOfHour:false");
			}
			if (js.getSecondOfMinute(dt) != od.getSecondOfMinute(dt)) {
				System.out.println("getSecondOfMinute:false");
			}
			if (js.getMillisOfSecond(dt) != od.getMillisOfSecond(dt)) {
				System.out.println("getMillisOfSecond:false");
			}
			if (js.getDayOfYear(dt) != od.getDayOfYear(dt)) {
				System.out.println("getDayOfYear:false");
			}
			//
			if (js.plusYears(dt, 2).getTime() != od.plusYears(dt, 2).getTime()) {
				System.out.println("plusYears:false");
			}
			if (js.plusMonths(dt, 2).getTime() != od.plusMonths(dt, 2)
					.getTime()) {
				System.out.println("plusMonths:false");
			}
			if (js.plusWeeks(dt, 2).getTime() != od.plusWeeks(dt, 2).getTime()) {
				System.out.println("plusWeeks:false");
			}
			if (js.plusDays(dt, 2).getTime() != od.plusDays(dt, 2).getTime()) {
				System.out.println("plusDays:false");
			}
			if (js.plusHours(dt, 2).getTime() != od.plusHours(dt, 2).getTime()) {
				System.out.println("plusHours:false");
			}
			if (js.plusMinutes(dt, 2).getTime() != od.plusMinutes(dt, 2)
					.getTime()) {
				System.out.println("plusMinutes:false");
			}
			if (js.plusSeconds(dt, 2).getTime() != od.plusSeconds(dt, 2)
					.getTime()) {
				System.out.println("plusMinutes:false");
			}
			if (js.plusMillis(dt, 2).getTime() != od.plusMillis(dt, 2)
					.getTime()) {
				System.out.println("plusMillis:false");
			}
		}
		System.out.println("-----------end check-----------");
	}

}
