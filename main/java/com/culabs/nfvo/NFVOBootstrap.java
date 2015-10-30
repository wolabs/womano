package com.culabs.nfvo;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.config.SystemConfig;
import com.culabs.nfvo.config.UNFVOConfig;
import com.culabs.nfvo.log.LogService;
import com.culabs.nfvo.model.ConfigBlock;
import com.culabs.nfvo.monitor.TcpServer;
import com.culabs.nfvo.util.DirUtils;
import com.culabs.nfvo.util.NFVOUtils;

/**
 * 
 * @ClassName: NFVOBootstrap
 * @Description: TODO
 * @author 
 * @date 2015年4月21日 下午3:48:55
 * @version 1.0
 */
public class NFVOBootstrap
{
	private static Logger LOGGER = LoggerFactory.getLogger(NFVOBootstrap.class);

	private static JettyServer jettyserver;
	static
	{
		LogService.prepare();
		UNFVOConfig.getInstance().init(new File(DirUtils.getInstance().getConfigDir() + "unfvo-config.xml"));
		TcpServer.init();
	}

	/**
	 * 
	 * @Title: main
	 * @Description: TODO
	 * @param @param args
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public static void main(String[] args) throws Exception
	{
		LOGGER.info("UNFVO start to run...");
		// 1. 创建jetty服务
		ConfigBlock block = SystemConfig.getInstance().getBlock("work_environment");
		int server_port = 9090;
		if (!NFVOUtils.isEmpty(block))
		{
			server_port = NFVOUtils.toIntger(block.getPairs().getPairVal("server_port"), server_port);
		}
		jettyserver = new JettyServer(server_port);
		Runtime.getRuntime().addShutdownHook(new ShutdownHook(jettyserver));
		jettyserver.startup();
		LOGGER.warn("**********************************************************");
		LOGGER.warn("**********************************************************");
		LOGGER.warn("*******                                            *******");
		LOGGER.warn("******                UNFVO STARTED                 ******");
		LOGGER.warn("*******                                            *******");
		LOGGER.warn("**********************************************************");
		LOGGER.warn("**********************************************************");
	}

}
