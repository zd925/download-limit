package com.kongtrio.demo.downloadlimit.exception;


import java.util.List;

/**
 * @author DH
 * @date 2020-03-17 09:36
 */
public class DHException  extends RuntimeException  {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String key;
	private List<?> data;

	public String getErrorCode() {
		return errorCode;
	}

	public List<?> getData(){
		return data;
	}

	/**
	 * 支持占位符
	 * @param errorCode
	 * @param key
	 * @param msgParam
	 */
	public DHException(String errorCode, String key, Object... msgParam) {
		super(replaceMsg(key, msgParam));
		this.key = key;
		this.errorCode = errorCode;
	}

	/**
	 * 带有data数据的异常类
	 * @param errorCode
	 * @param data
	 * @param key
	 * @param msgParam
	 */
	public DHException(String errorCode, List<?> data, String key, Object... msgParam) {
		super(replaceMsg(key, msgParam));
		this.errorCode = errorCode;
		this.key = key;
		this.data = data;
	}


	/**
	 * 支持占位符
	 * @param errorCode
	 * @param key
	 * @param msgParam
	 */
	public DHException(String errorCode, Throwable cause, String key, Object... msgParam) {
		super(replaceMsg(key, msgParam), cause);
		this.key = key;
		this.errorCode = errorCode;
	}

	/**
	 * 带有data数据的异常类
	 * @param errorCode
	 * @param data
	 * @param cause
	 * @param key
	 * @param msgParam
	 */
	public DHException(String errorCode, List<?> data, Throwable cause, String key, Object... msgParam) {
		super(replaceMsg(key, msgParam), cause);
		this.key = key;
		this.errorCode = errorCode;
		this.data = data;
	}

	/**
	 * 占位符替换处理
	 * @param key
	 * @param msgParam
	 * @return
	 */
	public static String replaceMsg(String key, Object... msgParam) {
		return I18N.getMessage(key, msgParam);
	}

	public void setData(List<?> data) {
		this.data = data;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
