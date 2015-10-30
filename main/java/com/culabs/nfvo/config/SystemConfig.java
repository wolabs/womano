package com.culabs.nfvo.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.SubnodeConfiguration;
import org.apache.commons.configuration.XMLConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.model.ConfigBlock;
import com.culabs.nfvo.util.NFVOUtils;

/**
 * 
 * @ClassName: SystemConfig
 * @Description: TODO
 * @author 
 * @date 2015年5月7日 下午1:32:50
 * @version 1.0
 */
public class SystemConfig implements IModule
{
	private static Logger LOGGER = LoggerFactory.getLogger(SystemConfig.class);
	public final static Map<String, ConfigBlock> CONFIG_BLOCKS = new ConcurrentHashMap<String, ConfigBlock>();
	private static final SystemConfig SYSTEM_CONFIG = new SystemConfig();

	private SystemConfig()
	{
		super();
	}

	public static SystemConfig getInstance()
	{
		return SYSTEM_CONFIG;
	}

	@SuppressWarnings("unchecked")
	public void cfg2Mdl(XMLConfiguration config)
	{
		// TODO Auto-generated method stub
		if (null == config)
		{
			return;
		}
		List<SubnodeConfiguration> items = config.configurationsAt("module.block");
		ConfigBlock block = null;
		for (SubnodeConfiguration subnode : items)
		{
			if (null != subnode && !NFVOUtils.isEmpty(subnode.getString("[@id]")))
			{

				block = new ConfigBlock();
				block.setId(subnode.getString("[@id]"));

				List<SubnodeConfiguration> items2 = subnode.configurationsAt("property");
				for (SubnodeConfiguration subnode2 : items2)
				{
					block.getPairs().add(subnode2.getString("[@key]"), subnode2.getString("[@value]"));
				}
				CONFIG_BLOCKS.put(block.getId(), block);
			}
		}
		LOGGER.info("CONFIG_BLOCKS = {}", CONFIG_BLOCKS);
	}

	public void mdl2Cfg(XMLConfiguration config, Object obj)
	{
	}

	public ConfigBlock getBlock(String key)
	{
		return CONFIG_BLOCKS.get(key);
	}
}
