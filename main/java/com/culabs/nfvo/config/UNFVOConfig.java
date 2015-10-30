package com.culabs.nfvo.config;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.util.DirUtils;
import com.culabs.nfvo.util.NFVOUtils;

public class UNFVOConfig
{
	private static Logger LOGGER = LoggerFactory.getLogger(UNFVOConfig.class);
	public static UNFVOConfig SYSCONFIG = new UNFVOConfig();
	public final static Map<String, RestUrl> REST_URLS = new ConcurrentHashMap<String, RestUrl>();
	private static XMLConfiguration config = null;

	private UNFVOConfig()
	{
	}

	public static synchronized UNFVOConfig getInstance()
	{
		if (null == SYSCONFIG)
		{
			SYSCONFIG = new UNFVOConfig();
		}
		return SYSCONFIG;
	}

	public XMLConfiguration getXMLConfiguration()
	{
		return config;
	}

	@SuppressWarnings("unchecked")
	public void init(File configfile)
	{
		// String file = "conf/scmp-config.xml";
		if (NFVOUtils.isEmpty(configfile) && !configfile.isFile())
		{
			System.exit(-1);
		}
		try
		{
			// 初始化
			// ContrlModelConf.getInstance();
			config = new XMLConfiguration(configfile);
			List<String> handlers = (List<String>) config.getList("module[@handler]");
			if (null != handlers && !handlers.isEmpty())
			{
				for (String handler : handlers)
				{
					if (!NFVOUtils.isEmpty(handler))
					{
						try
						{
							Class<?> c = Class.forName(handler);
							Object handleObj = instanceByPrivConstr(c);
							if (null != handleObj && handleObj instanceof IModule)
							{
								((IModule) handleObj).cfg2Mdl(config);
								LOGGER.info("Config Handler: " + handleObj);
							}
						} catch (ClassNotFoundException e)
						{
							// TODO Auto-generated catch block
							LOGGER.error(e.toString());
						}
					} else
					{
						LOGGER.error("Config Handler is empty, drop it");
					}
				}
			}
			//
		} catch (ConfigurationException e)
		{
			// TODO Auto-generated catch block
			LOGGER.error(e.toString());
		}
	}

	private Object instanceByPrivConstr(Class<?> c)
	{
		Object handleObj = null;
		try
		{
			Constructor<?> con = c.getDeclaredConstructor(new Class<?>[] {});
			if (null != con)
			{
				con.setAccessible(true);
			}
			handleObj = con.newInstance();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			LOGGER.error(e.toString());
		}
		return handleObj;
	}

	public static void main(String[] args)
	{
		UNFVOConfig.getInstance().init(new File(DirUtils.getInstance().getConfigDir() + "/unfvo-config.xml"));
		//RestUrl rest = RestConfigHandler.getInstance().getUrl("getHypervisors");
		//System.out.println(rest);
		//System.out.println("json>>>==" + rest.getReqjson());
		//ConfigBlock block = SystemConfig.getInstance().getBlock("P-SCSF");
		//System.out.println(block);
		//System.out.println(block.getPairs().getPairVal("imageRef"));

	}
}
