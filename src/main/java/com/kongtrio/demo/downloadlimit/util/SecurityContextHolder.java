package com.kongtrio.demo.downloadlimit.util;


public class SecurityContextHolder {

	private static final ThreadLocal<String> tenantIdHolder = new ThreadLocal<String>();
	private static final ThreadLocal<String> userIdHolder = new ThreadLocal<String>();
	private static final ThreadLocal<String> languageHolder = new ThreadLocal<String>();
	private static final ThreadLocal<Boolean> apikeyFlagHolder = new ThreadLocal<Boolean>();
	private static final ThreadLocal<String> ipHolder = new ThreadLocal<String>();
	private static final ThreadLocal<Boolean> defaultViewRoleHolder = new ThreadLocal<Boolean>();
	
	
	private static final ThreadLocal<HttpInfo> httpInfoHolder = new ThreadLocal<HttpInfo>();
	
	public static void add(String tenantId, String userId, String language, boolean apikeyFlag, String ip, boolean defaultViewRole) {
		tenantIdHolder.set(tenantId);
		userIdHolder.set(userId);
		languageHolder.set(language);
		apikeyFlagHolder.set(apikeyFlag);
		ipHolder.set(ip);
		defaultViewRoleHolder.set(defaultViewRole);
	}
	
	public static void add(String tenantId, String userId, boolean apikeyFlag) {
		add(tenantId, userId, null, apikeyFlag, null, false);
	}
	
	public static void clean() {
		tenantIdHolder.remove();
		userIdHolder.remove();
		languageHolder.remove();
		apikeyFlagHolder.remove();
		ipHolder.remove();
		defaultViewRoleHolder.remove();
	}

	public static void add(String language) {
		languageHolder.set(language);
	}
	
	public static String getUserId() {
		return userIdHolder.get();
	}
	
	public static String getTenantId() {
		return tenantIdHolder.get();
	}
	
	public static String getLanguage() {
		return languageHolder.get();
	}
	
	public static void setLanguage(String language) {
		languageHolder.set(language);
	}
	
	public static boolean isChinaLanguage() {
		return LanguageUtil.isChina(languageHolder.get());
	}
	
	public static boolean isUsLanguage() {
		return LanguageUtil.isUs(languageHolder.get());
	}
	
	public static boolean getApikeyFlag() {
		Boolean b = apikeyFlagHolder.get();
		if(b == null){
			return false;
		}
		return b;
	}
	
	public static boolean getDefaultViewRole() {
		Boolean b = defaultViewRoleHolder.get();
		if(b == null){
			return false;
		}
		return b;
	}
	
	public static String getIp() {
		return ipHolder.get();
	}
	
	public static void addHttpInfo(String url, String method, String body){
		httpInfoHolder.set(new HttpInfo(url, method, body));
	}
	
	public static HttpInfo getHttpInfo(){
		return httpInfoHolder.get();
	}
	
	public static class HttpInfo{
		private String url;
		private String method;
		private String body;
		
		
		public HttpInfo(String url, String method, String body) {
			super();
			this.url = url;
			this.method = method;
			this.body = body;
		}

		@Override
		public String toString() {
			return "HttpInfo [url=" + url + ", method=" + method + ", body="
					+ body + "]";
		}
	}
}
