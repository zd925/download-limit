package com.kongtrio.demo.downloadlimit.exception;

/**
 * @author DH
 * @date 2020-03-17 09:41
 */
public class ExceptionCode {
	/**参数错误，不包含验证数据*/
	public static final String ERROR_CODE_PARAM_REQUIRE = "E01";
	/**业务处理异常*/
	public static final String ERROR_CODE_SERVICE_EXP = "E02";
	/**业务不符合要求错误*/
	public static final String ERROR_CODE_SERVICE_REQUIRE = "E03";
	/**无访问权限*/
	public static final String ERROR_CODE_NOT_AUTH = "E04";
	/**参数错误，包含验证数据*/
	public static final String ERROR_CODE_PARAM_REQUIRE_DATA = "E05";
	/**系统异常*/
	public static final String ERROR_CODE_SYSTEM_ERROR = "E09";
}
