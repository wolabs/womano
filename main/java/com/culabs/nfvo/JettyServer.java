package com.culabs.nfvo;

import java.io.File;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.DirUtils;

/**
 * 
 * @ClassName: JettyServer
 * @Description: TODO
 * @author 
 * @date 2015年4月21日 下午3:49:31
 * @version 1.0
 */
public class JettyServer
{
	private static Logger LOGGER = LoggerFactory.getLogger(JettyServer.class);

	private int port;

	private Server server;

	public int getPort()
	{
		return port;
	}

	public void setPort(int port)
	{
		this.port = port;
	}

	public JettyServer()
	{
		super();
	}

	public JettyServer(int port)
	{
		this.port = port;
	}

	/**
	 * 
	 * @Title: startup
	 * @Description: TODO
	 * @param @throws Exception
	 * @return void
	 * @throws
	 */
	public void startup() throws Exception
	{
		String webappDirLocation = DirUtils.getInstance().getWebAppDir();
		if (NFVOUtils.isEmpty(this.port))
		{
			LOGGER.info("Set default port: {}", this.port);
			this.port = 8080;
		}
		server = new Server(this.port);
		server.setAttribute("CharacterEncoding", "UTF-8");
		WebAppContext context = new WebAppContext();
		context.setContextPath("/nfvo");
		context.setResourceBase(webappDirLocation);
		context.setDescriptor(new StringBuffer(webappDirLocation).append("WEB-INF").append(File.separator)
				.append("web.xml").toString());
		context.setParentLoaderPriority(true);
		server.setHandler(context);
		new Thread(new Runnable() {
			public void run()
			{
				try
				{
					server.start();
					server.join();
					LOGGER.warn("JettyServer running");
				} catch (Exception e)
				{
					LOGGER.error("JettyServer Exception:", e);
				}
			}
		}).start();

	}

	public void shutdown()
	{
		if (null != server)
		{
			server.destroy();
			LOGGER.warn("JettyServer closed");
		}
	}

}
