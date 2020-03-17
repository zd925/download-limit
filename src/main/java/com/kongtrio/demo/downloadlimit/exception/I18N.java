package com.kongtrio.demo.downloadlimit.exception;

import java.util.Locale;
import java.util.Map;

import com.kongtrio.demo.downloadlimit.util.BeanContext;
import com.kongtrio.demo.downloadlimit.util.SecurityContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class I18N {

	
	private static ApplicationContext ctx = BeanContext.ctx;

	private static ReloadableResourceBundleMessageSource messageSource;
	
	public static String getMessage(String key, Object... msgParam) {
		return getMessageIncludeDef(key, key, msgParam);
	}
	
	public static String getMessageIncludeDef(String key, String defaultMessage, Object... msgParam) {
		if(ctx == null){
			return key;
		}
		Locale local = Locale.CHINA;
		String language = SecurityContextHolder.getLanguage();
		if(StringUtils.isNotBlank(language)){
			local = org.springframework.util.StringUtils.parseLocaleString(language);
		}
		if(messageSource == null){
			Map<String, ReloadableResourceBundleMessageSource> map = ctx.getBeansOfType(ReloadableResourceBundleMessageSource.class);
			if(map.size() ==  0){
				return key;
			}
			String beanKey = map.keySet().toArray(new String[]{})[0];
			messageSource = map.get(beanKey);
		}
		return messageSource.getMessage(key, msgParam, defaultMessage, local);
	}
	
	public static String getMessagByLocle(String key, Locale locale, Object... msgParam) {
		return getMessagByLocleIncludeDef(key, key, locale, msgParam);
	}
	
	public static String getMessagByLocleIncludeDef(String key, String defaultMessage, Locale locale, Object... msgParam) {
		if (ctx == null) {
			return key;
		}
		if (messageSource == null) {
			Map<String, ReloadableResourceBundleMessageSource> map = ctx
					.getBeansOfType(ReloadableResourceBundleMessageSource.class);
			if (map.size() == 0) {
				return key;
			}
			String beanKey = map.keySet().toArray(new String[] {})[0];
			messageSource = map.get(beanKey);
		}
		return messageSource.getMessage(key, msgParam, defaultMessage, locale);
	}
}
