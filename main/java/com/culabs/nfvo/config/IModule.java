package com.culabs.nfvo.config;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

/**
 * 
 * @ClassName: IModule
 * @Description: TODO(描述类作用)
 * @author DELL
 * @date 2015年4月21日 下午2:32:55
 * @version 1.0
 */
public interface IModule
{

	void cfg2Mdl(XMLConfiguration config);

	void mdl2Cfg(XMLConfiguration config, Object obj) throws ConfigurationException;
}
