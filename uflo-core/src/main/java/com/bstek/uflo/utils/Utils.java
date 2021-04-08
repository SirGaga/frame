/*******************************************************************************
 * Copyright 2017 Bstek
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
package com.bstek.uflo.utils;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jacky.gao
 * @since 2018年7月2日
 */
public class Utils {
	public static BigDecimal toBigDecimal(Object obj){
		if(obj==null){
			return null;
		}
		if(obj instanceof BigDecimal){
			return (BigDecimal)obj;
		}else if(obj instanceof String){
			if(obj.toString().trim().equals("")){
				return new BigDecimal(0);
			}
			try{
				String str=obj.toString().trim();
				return new BigDecimal(str);				
			}catch(Exception ex){
				throw new RuntimeException("Can not convert "+obj+" to BigDecimal.");
			}
		}else if(obj instanceof Number){
			Number n=(Number)obj;
			return new BigDecimal(n.doubleValue());
		}
		throw new RuntimeException("Can not convert "+obj+" to BigDecimal.");
	}

	public static String getContentInfo(String content) {
		Pattern regex = Pattern.compile("\\$\\{([^}]*)\\}");
		Matcher matcher = regex.matcher(content);
		StringBuilder sql = new StringBuilder();
		while(matcher.find()) {
			sql.append(matcher.group(1)+",");
		}
		if (sql.length() > 0) {
			sql.deleteCharAt(sql.length() - 1);
		}
		return sql.toString();
	}

	public static String parseContentInfo(String oldstr,String content) {
		Pattern p = Pattern.compile("(\\$\\{)([\\w]+)(\\})");
		Matcher m = p.matcher(oldstr);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			String group = m.group(2);//规则中${值}中的 值 一样 的数据不
			//下一步是替换并且把替换好的值放到sb中
			m.appendReplacement(sb, content);
		}
		//把符合的数据追加到sb尾
		m.appendTail(sb);
		return sb.toString();
	}
}
