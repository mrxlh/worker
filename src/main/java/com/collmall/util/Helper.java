package com.collmall.util;

import com.collmall.exception.BusinessException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Helper {

	/**
	 * 正整数，负整数，0
	 */
	public static final String SRE_NEGATIVE_OR_DIGIT = "^(-?)\\d+$";

	public static final Pattern NEGATIVE_OR_DIGIT = Pattern.compile(SRE_NEGATIVE_OR_DIGIT);

	public static int getCurrentTime() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	public static int getCurrentDate() {
		return getDate(getCurrentTime());
	}

	public static int getCurrentMonth() {
		return getMonth((int) (System.currentTimeMillis() / 1000));
	}

	public static int getMonth(int time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((long) time * 1000);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), 1, 0, 0, 0);
		return (int) (c.getTimeInMillis() / 1000);
	}

	public static int addMonth(int time, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((long) time * 1000);
		c.add(Calendar.MONTH, amount);
		return (int) (c.getTimeInMillis() / 1000);
	}

	public static int addDay(int time, int amount) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((long) time * 1000);
		c.add(Calendar.DATE, amount);
		return (int) (c.getTimeInMillis() / 1000);
	}

	public static int getDate(int time) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((long) time * 1000);
		c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), 0, 0, 0);

		return (int) (c.getTimeInMillis() / 1000);
	}

	public static String getFormatDate(Integer time, String format) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis((long) time * 1000);
		//c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE), c.get(Calendar.HOUR_OF_DAY),c.get(Calendar.MINUTE), c.get(Calendar.SECOND));
		if (isEmpty(format))
			format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(c.getTime());
	}



	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()))
			return true;
		return false;
	}

	public static  <T extends  Object>  String listToString(List<T> list){
		StringBuilder sb=new StringBuilder();
		for(T item : list){
			if(sb.length()==0) {
				sb.append(item.toString());
			}
			else{
				sb.append(","+item.toString());
			}
		}
		return sb.toString();
	}

	public static String listToString(String[] list){
		StringBuilder sb=new StringBuilder();
		for(String item : list){
			if(sb.length()==0) {
				sb.append(item);
			}
			else{
				sb.append(","+item);
			}
		}
		return sb.toString();
	}

	public static int toUnixTime(Date date){
		return (int)(date.getTime()/1000);
	}

	public static int toUnixTime(String stringDate){
		Date date = new Date();
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return toUnixTime(date);
	}
	
	public static int toUnixTime(String stringDate,String format){
		Date date = new Date();
		if(isEmpty(format))
			format = "yyyy-MM-dd";
		DateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(stringDate);
		} catch (ParseException e) {
			throw new BusinessException("日期解析失败");
		}
		return toUnixTime(date);
	}

	public static String getExceptionStack(Exception e){
		StringWriter writer = null;
		PrintWriter printWriter = null;
		try {
			writer = new StringWriter();
			printWriter = new PrintWriter(writer);
			e.printStackTrace(printWriter);
			printWriter.flush();
			writer.flush();
		}finally{
			if(writer != null){
				try {
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if(null != printWriter){
				printWriter.close();
			}
		}
		return writer.toString();
	}

	public static Map<Long, Long> convertListToMap(
			List<Map<String, Object>> mapList) {
		Map<Long, Long> resultMap = new HashMap<Long, Long>();
		if (null == mapList) {
			return resultMap;
		}
		for (Map<String, Object> map : mapList) {

			if (map == null || map.get("customer_id") == null) {
				continue;
			}
			String supplierIdString = String.valueOf(map.get("customer_id"));
			Long supplierId = Long.valueOf(supplierIdString);
			if (0 == supplierId) {
				continue;
			}
			String moneyString = String.valueOf(map.get("sum_money"));
			Long sumMoney = Long.valueOf(moneyString).longValue();
			if (sumMoney == null) {
				sumMoney = 0L;
			}
			resultMap.put(supplierId, sumMoney);
		}
		return resultMap;
	}

	/**
	 * 检查字符串是否为整数
	 * @author xulihui
	 * @param number 数字形式的字符串
	 * @return
	 */
	public static final boolean isDigit(String number) {
		Matcher m = NEGATIVE_OR_DIGIT.matcher(number);
		return m.matches();
	}
}
