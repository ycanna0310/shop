package com.cn.utils;

import java.util.Collection;
import java.util.Map;

public class EmptyUtils {
	public static boolean isEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			String str = (String) obj;
			if ("".equals(str.trim())) {
				return true;
			}
		}
		if (obj instanceof Collection) {
			Collection coll = (Collection) obj;
			if (coll.size() == 0) {
				return true;
			}
		}
		if (obj instanceof Map) {
			Map map = (Map) obj;
			if (map.isEmpty()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotEmpty(Object obj) {
		return !isEmpty(obj);
	}

}
