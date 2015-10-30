package com.culabs.nfvo.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.util.NFVOUtils;

public class RestConfigHandler implements IModule
{
	private static Logger LOGGER = LoggerFactory.getLogger(RestConfigHandler.class);
	public final static Map<String, RestUrl> REST_URLS = new ConcurrentHashMap<String, RestUrl>();
	private static final RestConfigHandler REST_URL_MODLE_CONF = new RestConfigHandler();

	private RestConfigHandler()
	{
		super();
	}

	public static RestConfigHandler getInstance()
	{
		return REST_URL_MODLE_CONF;
	}

	@SuppressWarnings("unchecked")
	public void cfg2Mdl(XMLConfiguration config)
	{
		// TODO Auto-generated method stub
		if (null == config)
		{
			return;
		}
		List<SubnodeConfiguration> items = config.configurationsAt("module.rest");
		RestUrl resturl = null;
		for (SubnodeConfiguration subnode : items)
		{
			if (null != subnode && !NFVOUtils.isEmpty(subnode.getString("[@id]")))
			{
				REST_URLS.put(
						subnode.getString("[@id]"),
						resturl = new RestUrl(subnode.getString("[@id]"), subnode.getString("url"), subnode
								.getString("[@type]"), subnode.getString("[@desc]")));
				resturl.setReqjson(subnode.getString("reqjson"));
				List<SubnodeConfiguration> items2 = subnode.configurationsAt("properties.property");
				for (SubnodeConfiguration subnode2 : items2)
				{
					resturl.getPairs().add(subnode2.getString("[@key]"), subnode2.getString("[@value]"));
				}
			}
		}
		LOGGER.info("REST_URLS = {}", REST_URLS);
	}

	public void mdl2Cfg(XMLConfiguration config, Object obj)
	{
	}

	public RestUrl getUrl(String key)
	{
		return NFVOUtils.cloneBean(REST_URLS.get(key), new RestUrl());
	}
}
