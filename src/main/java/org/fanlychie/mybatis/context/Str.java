package org.fanlychie.mybatis.context;

public class Str {

	public boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	public boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}
	
	public String capitalize(String str) {
		if (isNotEmpty(str)) {
			char first = str.charAt(0);
			if (!Character.isTitleCase(first)) {
				return Character.toUpperCase(first) + str.substring(1);
			}
		}
		return str;
	}
	
	public String getOrIs(String type) {
		return type.equalsIgnoreCase("boolean") ? "is" : "get";
	}
	
}