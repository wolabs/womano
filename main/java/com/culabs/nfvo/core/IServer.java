package com.culabs.nfvo.core;

import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.ResourceIds;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.VNFServer;

/**
 * 
 * @ClassName: IServer
 * @Description: TODO
 * @author 
 * @date 2015年5月4日 下午2:03:09
 * @version 1.0
 */
public interface IServer
{
	public VNFServer getVNFServer();

	public Result createServer(ResourceIds resourceIds) throws NFVOException;

	public Result deleteServer() throws NFVOException;

	public Result queryServer() throws NFVOException;

	public Result startServer() throws NFVOException;

	public Result stopServer() throws NFVOException;
	
}
