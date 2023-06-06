/**
 * project: ecl
 * LessonCompleteStatus.java 2008-3-21 上午08:56:38
 * Shanghua Times, All right reserved.
 */
package com.mytest.myservlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * 课程完成情况/计费状态
 * 
 * @author pengfei
 */
public class LessonCompleteStatus {
	// 课程完成情况
	/*
	 * 1(3472)正常完成 1 2(21)提前结束 1 -3学生迟到 1 -4老师迟到 0 十分钟内不算迟到 -5双方迟到 0 十分钟内不算迟到
	 * -6提前取消 0 两小时前通知老师，不计课时费 -7临时取消 0.5 两小时内通知老师，给一半课时费，设置通知时间 3-8(337)学生缺勤
	 * 0.5 4-9(5)老师缺勤 -100 旷课一节罚100元 5-10(9)双方缺勤 -100
	 * 
	 * 不可抗力缺勤
	 */
	/**
	 * 预约
	 */
	public static int STATUS_BOOKING = 0;
	/**
	 * 正常完成
	 */
	public static int STATUS_COMPLETE = 1;
	/**
	 * 提前结束
	 */
	public static int STATUS_AHEAD_OVER = 2;
	/**
	 * 学生迟到
	 */
	public static int STATUS_STUDENT_LATE = 3;
	/**
	 * 老师迟到
	 */
	public static int STATUS_TEACHER_LATE = 4;
	/**
	 * 双方迟到
	 */
	public static int STATUS_BOTH_LATE = 5;
	/**
	 * 提前取消
	 */
	public static int STATUS_ADVANCE_CANCEL = 6;
	/**
	 * 临时取消
	 */
	public static int STATUS_TEMPORARY_CANCEL = 7;
	/**
	 * 学生缺勤 2009.8 课程完成情况可以细分为：学生缺勤missing和学生缺勤absence
	 *		   2018.12  课程完成情况把 missing 和 absence 合并为一个，
	 *			missing 不再使用，保留absence，所有历史数据全部修改为absence
	 */
	/**
	 * 学生缺勤 absence
	 */
	public static int STATUS_STUDENT_ABSENT = 8;
	/**
	 * 老师缺勤
	 */
	public static int STATUS_TEACHER_ABSENT = 9;
	/**
	 * 双方缺勤
	 */
	public static int STATUS_BOTH_ABSENT = 10;
	/**
	 * 不计费课程（对学生）
	 */
	public static int STATUS_NO_FEE = 11;
	/**
	 * 不可抗力缺勤
	 */
	public static int STATUS_FORCE_ABSENT = 12;
	/**
	 * 学生技术原因取消
	 */
	public static int STATUS_STUDENT_TECHNICAL_REASON_CANCEL = 13;
	/**
	 * 老师技术原因取消
	 */
	public static int STATUS_TEACHER_TECHNICAL_REASON_CANCEL = 14;
	/**
	 * 学生缺勤 missing ( missing 不再使用)
	 */
	public static int STATUS_STUDENT_ABSENT_MISSING = 15;
	/**
	 * 学生缺勤 学生申请缺勤 Reported
	 */
	public static int STATUS_STUDENT_ABSENT_REPORTED = 16;
	/**
	 * 上课教室问题取消  Cancelled (Classroom Technical Problems)
	 */
	public static int STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL = 17;

	/**
	 * 课程真正上完的几种情况。1,2,3,4,5,11
	 */
	public static final int[] STATUS_ARRAY_COMPLETE = {
			LessonCompleteStatus.STATUS_COMPLETE,
			LessonCompleteStatus.STATUS_AHEAD_OVER,
			LessonCompleteStatus.STATUS_STUDENT_LATE,
			LessonCompleteStatus.STATUS_TEACHER_LATE,
			LessonCompleteStatus.STATUS_BOTH_LATE,
			LessonCompleteStatus.STATUS_NO_FEE
	};
	 /**
	 * 课程未上完情况8学生缺勤、9老师缺勤、10双方缺勤、12不可抗力缺勤、13学生技术原因取消、14老师技术原因取消
	 * 
	 * 这些状态不用填写进度表的课程学习内容
	 */
	public static List<String> notWriteStatusList = new ArrayList<String>();
	static {
		notWriteStatusList.add(LessonCompleteStatus.STATUS_STUDENT_ABSENT + "");
		notWriteStatusList.add(LessonCompleteStatus.STATUS_TEACHER_ABSENT + "");
		notWriteStatusList.add(LessonCompleteStatus.STATUS_BOTH_ABSENT + "");
		notWriteStatusList.add(LessonCompleteStatus.STATUS_FORCE_ABSENT + "");
		notWriteStatusList.add(LessonCompleteStatus.STATUS_STUDENT_TECHNICAL_REASON_CANCEL + "");
		notWriteStatusList.add(LessonCompleteStatus.STATUS_TEACHER_TECHNICAL_REASON_CANCEL + "");
	}
	// /**
	// * 课程未上完情况8学生缺勤、9老师缺勤、10双方缺勤、12不可抗力缺勤、13学生技术原因取消、14老师技术原因取消
	// */
	// public static final int[] STATUS_ARRAY_NOT_COMPLETE = {
	// LessonCompleteStatus.STATUS_STUDENT_ABSENT,
	// LessonCompleteStatus.STATUS_TEACHER_ABSENT,
	// LessonCompleteStatus.STATUS_BOTH_ABSENT,
	// LessonCompleteStatus.STATUS_FORCE_ABSENT,
	// LessonCompleteStatus.STATUS_STUDENT_TECHNICAL_REASON_CANCEL,
	// LessonCompleteStatus.STATUS_TEACHER_TECHNICAL_REASON_CANCEL };

	/**
	 * 课程真正上完的计费的几种情况。1,2,3,4,5（在教师课时统计时用）
	 */
	public static int[] STATUS_ARRAY_COMPLETE_FEE = {
			LessonCompleteStatus.STATUS_COMPLETE,
			LessonCompleteStatus.STATUS_AHEAD_OVER,
			LessonCompleteStatus.STATUS_STUDENT_LATE,
			LessonCompleteStatus.STATUS_TEACHER_LATE,
			LessonCompleteStatus.STATUS_BOTH_LATE
	};
	
	
	/**
	 * 分配费用的课程，费用按月统计的地方使用。还要看是否超出学籍付费总点数
	 */
	public static int[] STATUS_ARRAY_COMPLETE_PAID = {
			LessonCompleteStatus.STATUS_BOOKING,
			LessonCompleteStatus.STATUS_COMPLETE,
			LessonCompleteStatus.STATUS_AHEAD_OVER,
			LessonCompleteStatus.STATUS_STUDENT_LATE,
			LessonCompleteStatus.STATUS_TEACHER_LATE,
			LessonCompleteStatus.STATUS_BOTH_LATE,
			LessonCompleteStatus.STATUS_STUDENT_ABSENT,
			LessonCompleteStatus.STATUS_STUDENT_ABSENT_MISSING,
			LessonCompleteStatus.STATUS_NO_FEE,
			LessonCompleteStatus.STATUS_FORCE_ABSENT,
			LessonCompleteStatus.STATUS_STUDENT_TECHNICAL_REASON_CANCEL,
			LessonCompleteStatus.STATUS_TEACHER_TECHNICAL_REASON_CANCEL
	};
	

	
	/**
	 * 取值范围待确定
	 */
	private static Map<Integer, Map<Locale, String>> lessonStatus = new HashMap<Integer, Map<Locale, String>>();
	static {
		Map<Locale, String> local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "课程预约");
		local.put(Locale.US, "Booked");
		lessonStatus.put(STATUS_BOOKING, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "正常完成");
		local.put(Locale.US, "Completed");
		lessonStatus.put(STATUS_COMPLETE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "提前结束");
		local.put(Locale.US, "Completed");
		lessonStatus.put(STATUS_AHEAD_OVER, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "学生迟到");
		local.put(Locale.US, "Student Late");
		lessonStatus.put(STATUS_STUDENT_LATE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "老师迟到");
		local.put(Locale.US, "Teacher Late");
		lessonStatus.put(STATUS_TEACHER_LATE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "双方迟到");
		local.put(Locale.US, "Student / Teacher Late");
		lessonStatus.put(STATUS_BOTH_LATE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "提前取消");
		local.put(Locale.US, "Cancelled");
		lessonStatus.put(STATUS_ADVANCE_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "临时取消");
		local.put(Locale.US, "Cancelled (within 12 hours)");
		lessonStatus.put(STATUS_TEMPORARY_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "学生缺勤(absence)");
		local.put(Locale.US, "Student Absence");
		lessonStatus.put(STATUS_STUDENT_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "老师缺勤");
		local.put(Locale.US, "Teacher Absence");
		lessonStatus.put(STATUS_TEACHER_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "双方缺勤");
		local.put(Locale.US, "Student / Teacher Absence");
		lessonStatus.put(STATUS_BOTH_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "不计费课程");
		local.put(Locale.US, "Completed");
		lessonStatus.put(STATUS_NO_FEE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "不可抗力缺勤");
		local.put(Locale.US, "Cancelled (force majeure)");
		lessonStatus.put(STATUS_FORCE_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "学生技术原因取消");
		local.put(Locale.US, "Cancelled (Student Technical Reasons)");
		lessonStatus.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "老师技术原因取消");
		local.put(Locale.US, "Cancelled (Teacher Technical Reasons)");
		lessonStatus.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "学生申请缺勤");
		local.put(Locale.US, "Student Absence (Reported)");
		lessonStatus.put(STATUS_STUDENT_ABSENT_REPORTED, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "上课教室问题取消");
		local.put(Locale.US, "Cancelled (Classroom Technical Problems)");
		lessonStatus.put(STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL, local);
	}

	public static String getLessonStatus(int i, Locale locale) {
		Map<Locale, String> local = (Map<Locale, String>) lessonStatus.get(i);
		return (String) local.get(locale);
	}

	private static LinkedHashMap<Integer, String> key_map_cn = new LinkedHashMap<Integer, String>();
	private static LinkedHashMap<Integer, String> key_map_en = new LinkedHashMap<Integer, String>();
	/**
	 * status map "name - key" 用于input:select
	 */
	private static LinkedHashMap<String, Integer> name_map_cn = new LinkedHashMap<String, Integer>();

	static {
		init_map();
	}

	private static void init_map() {
		key_map_cn.put(STATUS_BOOKING, "课程预约");
		key_map_en.put(STATUS_BOOKING, "Booked");
		key_map_cn.put(STATUS_COMPLETE, "正常完成");
		key_map_en.put(STATUS_COMPLETE, "Completed");
		key_map_cn.put(STATUS_AHEAD_OVER, "提前结束");
		key_map_en.put(STATUS_AHEAD_OVER, "Completed");
		key_map_cn.put(STATUS_STUDENT_LATE, "学生迟到");
		key_map_en.put(STATUS_STUDENT_LATE, "Student Late");
		key_map_cn.put(STATUS_TEACHER_LATE, "老师迟到");
		key_map_en.put(STATUS_TEACHER_LATE, "Teacher Late");
		key_map_cn.put(STATUS_BOTH_LATE, "双方迟到");
		key_map_en.put(STATUS_BOTH_LATE, "Student/Teacher Late");
		key_map_cn.put(STATUS_ADVANCE_CANCEL, "提前取消");
		key_map_en.put(STATUS_ADVANCE_CANCEL, "Cancelled");
		key_map_cn.put(STATUS_TEMPORARY_CANCEL, "临时取消");
		key_map_en.put(STATUS_TEMPORARY_CANCEL, "Cancelled(within 12 hours)");
		key_map_cn.put(STATUS_STUDENT_ABSENT, "学生缺勤(absence)");
		key_map_en.put(STATUS_STUDENT_ABSENT, "Student Absence");
		key_map_cn.put(STATUS_STUDENT_ABSENT_REPORTED, "学生申请缺勤");
		key_map_en.put(STATUS_STUDENT_ABSENT_REPORTED, "Student Absence (Reported)");
		
		// 学生缺勤  missing 合并为 absence
		// key_map_cn.put(STATUS_STUDENT_ABSENT_MISSING, "学生缺勤(missing)");
		// key_map_en.put(STATUS_STUDENT_ABSENT_MISSING,"Student Absence(missing)");
		 
		key_map_cn.put(STATUS_TEACHER_ABSENT, "老师缺勤");
		key_map_en.put(STATUS_TEACHER_ABSENT, "Teacher Absence");
		key_map_cn.put(STATUS_BOTH_ABSENT, "双方缺勤");
		key_map_en.put(STATUS_BOTH_ABSENT, "Student/Teacher Absence");
		key_map_cn.put(STATUS_NO_FEE, "不计费课程");
		key_map_en.put(STATUS_NO_FEE, "Completed");
		key_map_cn.put(STATUS_FORCE_ABSENT, "不可抗力缺勤");
		key_map_en.put(STATUS_FORCE_ABSENT, "Cancelled(force majeure)");
		key_map_cn.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, "学生技术原因取消");
		key_map_en.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, "Cancelled (Student Technical Reasons)");
		key_map_cn.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, "老师技术原因取消");
		key_map_en.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, "Cancelled (Teacher Technical Reasons)");

		key_map_cn.put(STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL, "上课教室问题取消");
		key_map_en.put(STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL, "Cancelled (Classroom Technical Problems)");

		// init name_map_cn
		Iterator<Integer> it = key_map_cn.keySet().iterator();
		while (it.hasNext()) {
			Integer key = it.next();
			name_map_cn.put(key_map_cn.get(key), (Integer) key);
		}
	}

	public static String getNameCn(int status) {
		return key_map_cn.get(status);
	}

	public static String getNameEn(int status) {
		return key_map_en.get(status);
	}

	public static LinkedHashMap<Integer, String> getKeyMapCn() {
		return key_map_cn;
	}

	public static LinkedHashMap<Integer, String> getKeyMapEn() {
		return key_map_en;
	}

	public static Map<String, String> getLessonCompleteStatusCnSelectMap(String defaultKey, String defaultValue) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (defaultKey != null) {
			map.put(defaultKey, defaultValue);
		}
		Map<String, Integer> statusMap = name_map_cn;
		Iterator<String> lessonCompleteStatusIter = statusMap.keySet().iterator();
		while (lessonCompleteStatusIter.hasNext()) {
			String key = lessonCompleteStatusIter.next();
			String value = statusMap.get(key) + "";
			map.put(key, value);
		}

		return map;
	}

	public static Map<Integer, String> getNomalCompleteStatusMap() {
		Map<Integer, String> map = new LinkedHashMap<Integer, String>();
		map.put(STATUS_COMPLETE, "正常完成");
		map.put(STATUS_AHEAD_OVER, "提前结束");
		map.put(STATUS_STUDENT_LATE, "学生迟到");
		map.put(STATUS_TEACHER_LATE, "老师迟到");
		map.put(STATUS_BOTH_LATE, "双方迟到");
		// map.put(STATUS_ADVANCE_CANCEL, "提前取消");
		// map.put(STATUS_TEMPORARY_CANCEL, "临时取消");
		map.put(STATUS_STUDENT_ABSENT, "学生缺勤(absent)");
		// 学生缺勤 missing 合并为 absent 
		// map.put(STATUS_STUDENT_ABSENT_MISSING, "学生缺勤(missing)");
		
		map.put(STATUS_TEACHER_ABSENT, "老师缺勤");
		map.put(STATUS_BOTH_ABSENT, "双方缺勤");
		// map.put(STATUS_NO_FEE, "不计费课程");
		map.put(STATUS_FORCE_ABSENT, "不可抗力缺勤");
		map.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, "学生技术原因取消");
		map.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, "老师技术原因取消");
		map.put(STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL, "上课教室问题取消");
		return map;
	}

	public static Map<String, String> getNomalCompleteStatusSelectMap(
			String defaultKey, String defaultValue) {
		Map<String, String> map = new LinkedHashMap<String, String>();
		if (defaultKey != null) {
			map.put(defaultKey, defaultValue);
		}
		Map<Integer, String> temp = getNomalCompleteStatusMap();
		for (Integer key : temp.keySet()) {
			String value = temp.get(key);
			map.put(value, key.toString());
		}
		return map;
	}

}
