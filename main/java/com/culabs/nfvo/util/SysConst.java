package com.culabs.nfvo.util;

/**
 * 
 * @ClassName: SysConst
 * @Description: TODO
 * @author 
 * @date 2015年4月23日 下午3:50:42
 * @version 1.0
 */
public interface SysConst
{
	String LOG_CONFIG_FILE = "log4j.properties";

	String LOCAL_HOST_IP = "127.0.0.1";

	// WEB
	String MEDIA_JSON_UTF_8 = "application/json;charset=UTF-8";

	String MEDIA_TEXT_HTML_UTF_8 = "text/html;charset=utf-8";
	
	static enum MapQueryCond
	{
		LGEQT, LSEQT, EQT;
	}
}

