package com.sohlman.poc.finalvariable;

import java.util.HashMap;
import java.util.Map;

public class PropertyReaderUtil {
	private static Map<String, String> map = new HashMap<String, String>();
	
	static {
		map.put("ALPHABET", "ABC");
		map.put("MYBOOLEAN", "true");
	}

	

	public static String getValue(String key) {
		return map.get(key);
	}
	
	public static boolean getBooleanValue(String key) {
		return map.get(key).equals("true");
	}	
}
