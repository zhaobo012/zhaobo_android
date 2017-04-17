package com.zhaobo.utilslib;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则表达式工具类
 * 
 * @author
 */
public class RegularExpressionUtil {
	/**
	 * 是否是手机号
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isMobileNumber(String number) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		return number.matches("[1][345789]\\d{9}");
	}

	/**
	 * 是否是汉字
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isChinese(String content) {
		if (TextUtils.isEmpty(content)) {
			return false;
		}
		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
		Matcher m = p.matcher(content);
		return m.matches();
	}

	/**
	 * 是否是数字
	 * 
	 * @param number
	 * @return
	 */
	public static boolean isNumber(String number) {
		if (TextUtils.isEmpty(number)) {
			return false;
		}
		Pattern p = Pattern.compile("[0-9]*");
		Matcher m = p.matcher(number);
		return m.matches();
	}

	/**
	 * 账户、密码是否有效(只有字母、数字和下划线且不能以下划线开头和结尾)
	 * 
	 * @param content
	 * @return
	 */
	public static boolean isValidPassword(String content) {
		if (TextUtils.isEmpty(content)) {
			return false;
		}
		Pattern p = Pattern.compile("^(?!_)(?!.*?_$)[a-zA-Z0-9_]+$");
		Matcher m = p.matcher(content);
		return m.matches();
	}
	/**
	 * 是否是邮箱
	 *
	 * @param mailbox
	 * @return
	 */
	public static boolean isMailbox(String mailbox) {
		if (TextUtils.isEmpty(mailbox)) {
			return false;
		}
		Pattern p = Pattern.compile("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
		Matcher m = p.matcher(mailbox);
		return m.matches();
	}
}
