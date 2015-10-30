package com.culabs.nfvo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.monitor.TcpServer;

/**
 * 
 * <code>ShutdownHook.java</code>
 * <p>
 * 功能:
 * 
 * <p>
 * 
 * @author 
 * @version 1.0 </br> 最后修改人 无 Copyright-culabs 2014 All right
 *          reserved.
 */
public class ShutdownHook extends Thread
{
	private static Logger LOGGER = LoggerFactory.getLogger(ShutdownHook.class);
	private JettyServer jettyserver = null;

	public ShutdownHook(JettyServer jettyserver)
	{
		super();
		this.jettyserver = jettyserver;
	}

	public void run()
	{
		if (null != jettyserver)
		{
			LOGGER.warn("Shutdown jettyServer...");
			jettyserver.shutdown();
		}
		TcpServer.shutdown();
		LOGGER.warn("**********************************************************");
		LOGGER.warn("******                                              ******");
		LOGGER.warn("******                UNFVO shutdown                ******");
		LOGGER.warn("******                                              ******");
		LOGGER.warn("**********************************************************");
	}
}
