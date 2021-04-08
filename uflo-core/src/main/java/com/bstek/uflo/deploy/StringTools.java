package com.bstek.uflo.deploy;

import org.apache.commons.lang.StringUtils;

/**
 * @author zhangjie
 * @date 2021-04-08
 */
public class StringTools {
	public static String escape(String str) {
		if (StringUtils.isEmpty(str)) {
			return str;
		}
		str = replaceString(str, "&", "&amp;");
		str = replaceString(str, "<", "&lt;");
		str = replaceString(str, ">", "&gt;");
		str = replaceString(str, "&apos;", "&apos;");
		str = replaceString(str, "\"", "&quot;");
		return str;
	}

	public static String unescape(String str) {
		str = replaceString(str, "&lt;", "<");
		str = replaceString(str, "&gt;", ">");
		str = replaceString(str, "&apos;", "&apos;");
		str = replaceString(str, "&quot;", "\"");
		str = replaceString(str, "&amp;", "&");
		return str;
	}

	private static String replaceString(String strData, String regex,
			String replacement) {
		if (strData == null) {
			return null;
		}
		int index;
		index = strData.indexOf(regex);
		String strNew = "";
		if (index >= 0) {
			while (index >= 0) {
				strNew += strData.substring(0, index) + replacement;
				strData = strData.substring(index + regex.length());
				index = strData.indexOf(regex);
			}
			strNew += strData;
			return strNew;
		}
		return strData;
	}
}
