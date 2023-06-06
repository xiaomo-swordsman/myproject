/**
 * project: ecl
 * LessonCompleteStatus.java 2008-3-21 ����08:56:38
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
 * �γ�������/�Ʒ�״̬
 * 
 * @author pengfei
 */
public class LessonCompleteStatus {
	// �γ�������
	/*
	 * 1(3472)������� 1 2(21)��ǰ���� 1 -3ѧ���ٵ� 1 -4��ʦ�ٵ� 0 ʮ�����ڲ���ٵ� -5˫���ٵ� 0 ʮ�����ڲ���ٵ�
	 * -6��ǰȡ�� 0 ��Сʱǰ֪ͨ��ʦ�����ƿ�ʱ�� -7��ʱȡ�� 0.5 ��Сʱ��֪ͨ��ʦ����һ���ʱ�ѣ�����֪ͨʱ�� 3-8(337)ѧ��ȱ��
	 * 0.5 4-9(5)��ʦȱ�� -100 ����һ�ڷ�100Ԫ 5-10(9)˫��ȱ�� -100
	 * 
	 * ���ɿ���ȱ��
	 */
	/**
	 * ԤԼ
	 */
	public static int STATUS_BOOKING = 0;
	/**
	 * �������
	 */
	public static int STATUS_COMPLETE = 1;
	/**
	 * ��ǰ����
	 */
	public static int STATUS_AHEAD_OVER = 2;
	/**
	 * ѧ���ٵ�
	 */
	public static int STATUS_STUDENT_LATE = 3;
	/**
	 * ��ʦ�ٵ�
	 */
	public static int STATUS_TEACHER_LATE = 4;
	/**
	 * ˫���ٵ�
	 */
	public static int STATUS_BOTH_LATE = 5;
	/**
	 * ��ǰȡ��
	 */
	public static int STATUS_ADVANCE_CANCEL = 6;
	/**
	 * ��ʱȡ��
	 */
	public static int STATUS_TEMPORARY_CANCEL = 7;
	/**
	 * ѧ��ȱ�� 2009.8 �γ�����������ϸ��Ϊ��ѧ��ȱ��missing��ѧ��ȱ��absence
	 *		   2018.12  �γ��������� missing �� absence �ϲ�Ϊһ����
	 *			missing ����ʹ�ã�����absence��������ʷ����ȫ���޸�Ϊabsence
	 */
	/**
	 * ѧ��ȱ�� absence
	 */
	public static int STATUS_STUDENT_ABSENT = 8;
	/**
	 * ��ʦȱ��
	 */
	public static int STATUS_TEACHER_ABSENT = 9;
	/**
	 * ˫��ȱ��
	 */
	public static int STATUS_BOTH_ABSENT = 10;
	/**
	 * ���Ʒѿγ̣���ѧ����
	 */
	public static int STATUS_NO_FEE = 11;
	/**
	 * ���ɿ���ȱ��
	 */
	public static int STATUS_FORCE_ABSENT = 12;
	/**
	 * ѧ������ԭ��ȡ��
	 */
	public static int STATUS_STUDENT_TECHNICAL_REASON_CANCEL = 13;
	/**
	 * ��ʦ����ԭ��ȡ��
	 */
	public static int STATUS_TEACHER_TECHNICAL_REASON_CANCEL = 14;
	/**
	 * ѧ��ȱ�� missing ( missing ����ʹ��)
	 */
	public static int STATUS_STUDENT_ABSENT_MISSING = 15;
	/**
	 * ѧ��ȱ�� ѧ������ȱ�� Reported
	 */
	public static int STATUS_STUDENT_ABSENT_REPORTED = 16;
	/**
	 * �Ͽν�������ȡ��  Cancelled (Classroom Technical Problems)
	 */
	public static int STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL = 17;

	/**
	 * �γ���������ļ��������1,2,3,4,5,11
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
	 * �γ�δ�������8ѧ��ȱ�ڡ�9��ʦȱ�ڡ�10˫��ȱ�ڡ�12���ɿ���ȱ�ڡ�13ѧ������ԭ��ȡ����14��ʦ����ԭ��ȡ��
	 * 
	 * ��Щ״̬������д���ȱ�Ŀγ�ѧϰ����
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
	// * �γ�δ�������8ѧ��ȱ�ڡ�9��ʦȱ�ڡ�10˫��ȱ�ڡ�12���ɿ���ȱ�ڡ�13ѧ������ԭ��ȡ����14��ʦ����ԭ��ȡ��
	// */
	// public static final int[] STATUS_ARRAY_NOT_COMPLETE = {
	// LessonCompleteStatus.STATUS_STUDENT_ABSENT,
	// LessonCompleteStatus.STATUS_TEACHER_ABSENT,
	// LessonCompleteStatus.STATUS_BOTH_ABSENT,
	// LessonCompleteStatus.STATUS_FORCE_ABSENT,
	// LessonCompleteStatus.STATUS_STUDENT_TECHNICAL_REASON_CANCEL,
	// LessonCompleteStatus.STATUS_TEACHER_TECHNICAL_REASON_CANCEL };

	/**
	 * �γ���������ļƷѵļ��������1,2,3,4,5���ڽ�ʦ��ʱͳ��ʱ�ã�
	 */
	public static int[] STATUS_ARRAY_COMPLETE_FEE = {
			LessonCompleteStatus.STATUS_COMPLETE,
			LessonCompleteStatus.STATUS_AHEAD_OVER,
			LessonCompleteStatus.STATUS_STUDENT_LATE,
			LessonCompleteStatus.STATUS_TEACHER_LATE,
			LessonCompleteStatus.STATUS_BOTH_LATE
	};
	
	
	/**
	 * ������õĿγ̣����ð���ͳ�Ƶĵط�ʹ�á���Ҫ���Ƿ񳬳�ѧ�������ܵ���
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
	 * ȡֵ��Χ��ȷ��
	 */
	private static Map<Integer, Map<Locale, String>> lessonStatus = new HashMap<Integer, Map<Locale, String>>();
	static {
		Map<Locale, String> local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "�γ�ԤԼ");
		local.put(Locale.US, "Booked");
		lessonStatus.put(STATUS_BOOKING, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "�������");
		local.put(Locale.US, "Completed");
		lessonStatus.put(STATUS_COMPLETE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "��ǰ����");
		local.put(Locale.US, "Completed");
		lessonStatus.put(STATUS_AHEAD_OVER, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "ѧ���ٵ�");
		local.put(Locale.US, "Student Late");
		lessonStatus.put(STATUS_STUDENT_LATE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "��ʦ�ٵ�");
		local.put(Locale.US, "Teacher Late");
		lessonStatus.put(STATUS_TEACHER_LATE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "˫���ٵ�");
		local.put(Locale.US, "Student / Teacher Late");
		lessonStatus.put(STATUS_BOTH_LATE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "��ǰȡ��");
		local.put(Locale.US, "Cancelled");
		lessonStatus.put(STATUS_ADVANCE_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "��ʱȡ��");
		local.put(Locale.US, "Cancelled (within 12 hours)");
		lessonStatus.put(STATUS_TEMPORARY_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "ѧ��ȱ��(absence)");
		local.put(Locale.US, "Student Absence");
		lessonStatus.put(STATUS_STUDENT_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "��ʦȱ��");
		local.put(Locale.US, "Teacher Absence");
		lessonStatus.put(STATUS_TEACHER_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "˫��ȱ��");
		local.put(Locale.US, "Student / Teacher Absence");
		lessonStatus.put(STATUS_BOTH_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "���Ʒѿγ�");
		local.put(Locale.US, "Completed");
		lessonStatus.put(STATUS_NO_FEE, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "���ɿ���ȱ��");
		local.put(Locale.US, "Cancelled (force majeure)");
		lessonStatus.put(STATUS_FORCE_ABSENT, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "ѧ������ԭ��ȡ��");
		local.put(Locale.US, "Cancelled (Student Technical Reasons)");
		lessonStatus.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "��ʦ����ԭ��ȡ��");
		local.put(Locale.US, "Cancelled (Teacher Technical Reasons)");
		lessonStatus.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "ѧ������ȱ��");
		local.put(Locale.US, "Student Absence (Reported)");
		lessonStatus.put(STATUS_STUDENT_ABSENT_REPORTED, local);
		local = new HashMap<Locale, String>();
		local.put(Locale.PRC, "�Ͽν�������ȡ��");
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
	 * status map "name - key" ����input:select
	 */
	private static LinkedHashMap<String, Integer> name_map_cn = new LinkedHashMap<String, Integer>();

	static {
		init_map();
	}

	private static void init_map() {
		key_map_cn.put(STATUS_BOOKING, "�γ�ԤԼ");
		key_map_en.put(STATUS_BOOKING, "Booked");
		key_map_cn.put(STATUS_COMPLETE, "�������");
		key_map_en.put(STATUS_COMPLETE, "Completed");
		key_map_cn.put(STATUS_AHEAD_OVER, "��ǰ����");
		key_map_en.put(STATUS_AHEAD_OVER, "Completed");
		key_map_cn.put(STATUS_STUDENT_LATE, "ѧ���ٵ�");
		key_map_en.put(STATUS_STUDENT_LATE, "Student Late");
		key_map_cn.put(STATUS_TEACHER_LATE, "��ʦ�ٵ�");
		key_map_en.put(STATUS_TEACHER_LATE, "Teacher Late");
		key_map_cn.put(STATUS_BOTH_LATE, "˫���ٵ�");
		key_map_en.put(STATUS_BOTH_LATE, "Student/Teacher Late");
		key_map_cn.put(STATUS_ADVANCE_CANCEL, "��ǰȡ��");
		key_map_en.put(STATUS_ADVANCE_CANCEL, "Cancelled");
		key_map_cn.put(STATUS_TEMPORARY_CANCEL, "��ʱȡ��");
		key_map_en.put(STATUS_TEMPORARY_CANCEL, "Cancelled(within 12 hours)");
		key_map_cn.put(STATUS_STUDENT_ABSENT, "ѧ��ȱ��(absence)");
		key_map_en.put(STATUS_STUDENT_ABSENT, "Student Absence");
		key_map_cn.put(STATUS_STUDENT_ABSENT_REPORTED, "ѧ������ȱ��");
		key_map_en.put(STATUS_STUDENT_ABSENT_REPORTED, "Student Absence (Reported)");
		
		// ѧ��ȱ��  missing �ϲ�Ϊ absence
		// key_map_cn.put(STATUS_STUDENT_ABSENT_MISSING, "ѧ��ȱ��(missing)");
		// key_map_en.put(STATUS_STUDENT_ABSENT_MISSING,"Student Absence(missing)");
		 
		key_map_cn.put(STATUS_TEACHER_ABSENT, "��ʦȱ��");
		key_map_en.put(STATUS_TEACHER_ABSENT, "Teacher Absence");
		key_map_cn.put(STATUS_BOTH_ABSENT, "˫��ȱ��");
		key_map_en.put(STATUS_BOTH_ABSENT, "Student/Teacher Absence");
		key_map_cn.put(STATUS_NO_FEE, "���Ʒѿγ�");
		key_map_en.put(STATUS_NO_FEE, "Completed");
		key_map_cn.put(STATUS_FORCE_ABSENT, "���ɿ���ȱ��");
		key_map_en.put(STATUS_FORCE_ABSENT, "Cancelled(force majeure)");
		key_map_cn.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, "ѧ������ԭ��ȡ��");
		key_map_en.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, "Cancelled (Student Technical Reasons)");
		key_map_cn.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, "��ʦ����ԭ��ȡ��");
		key_map_en.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, "Cancelled (Teacher Technical Reasons)");

		key_map_cn.put(STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL, "�Ͽν�������ȡ��");
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
		map.put(STATUS_COMPLETE, "�������");
		map.put(STATUS_AHEAD_OVER, "��ǰ����");
		map.put(STATUS_STUDENT_LATE, "ѧ���ٵ�");
		map.put(STATUS_TEACHER_LATE, "��ʦ�ٵ�");
		map.put(STATUS_BOTH_LATE, "˫���ٵ�");
		// map.put(STATUS_ADVANCE_CANCEL, "��ǰȡ��");
		// map.put(STATUS_TEMPORARY_CANCEL, "��ʱȡ��");
		map.put(STATUS_STUDENT_ABSENT, "ѧ��ȱ��(absent)");
		// ѧ��ȱ�� missing �ϲ�Ϊ absent 
		// map.put(STATUS_STUDENT_ABSENT_MISSING, "ѧ��ȱ��(missing)");
		
		map.put(STATUS_TEACHER_ABSENT, "��ʦȱ��");
		map.put(STATUS_BOTH_ABSENT, "˫��ȱ��");
		// map.put(STATUS_NO_FEE, "���Ʒѿγ�");
		map.put(STATUS_FORCE_ABSENT, "���ɿ���ȱ��");
		map.put(STATUS_STUDENT_TECHNICAL_REASON_CANCEL, "ѧ������ԭ��ȡ��");
		map.put(STATUS_TEACHER_TECHNICAL_REASON_CANCEL, "��ʦ����ԭ��ȡ��");
		map.put(STATUS_CLASSROOM_TECHNICAL_PROBLEMS_CANCEL, "�Ͽν�������ȡ��");
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
