package com.kongtrio.demo.downloadlimit.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

public class LanguageUtil {

	public static boolean isChina(String language) {
		if(StringUtils.equals(Locale.CHINA.toString(), language)){
			return true;
		}
		return false;
	}
	
	public static boolean isUs(String language) {
		if(StringUtils.equals(Locale.US.toString(), language)){
			return true;
		}
		return false;
	}
}
