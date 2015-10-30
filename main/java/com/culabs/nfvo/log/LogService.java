package com.culabs.nfvo.log;

import java.io.File;

import org.apache.log4j.PropertyConfigurator;

import com.culabs.nfvo.util.SysConst;
import com.culabs.nfvo.util.DirUtils;

/**
 * 
 * @ClassName: LogService 
 * @Description: TODO(描述类作用) 
 * @author  
 * @date 2015年4月20日 下午12:18:50 
 * @version 1.0
 */
public class LogService {

	public static void prepare() {
		File logdir = new File(DirUtils.getInstance().getLogDir());
		if (!logdir.exists()) {
			logdir.mkdirs();
		}
		System.setProperty("logdir", DirUtils.getInstance().getLogDir());
		PropertyConfigurator.configureAndWatch(DirUtils.getInstance()
				.getConfigDir() + File.separator + SysConst.LOG_CONFIG_FILE);
	}
}
