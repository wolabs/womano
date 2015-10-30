package com.culabs.nfvo.core;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.config.RestConfigHandler;
import com.culabs.nfvo.config.SystemConfig;
import com.culabs.nfvo.log.LogService;
import com.culabs.nfvo.model.ConfigBlock;
import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.ResourceIds;
import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.VNFServer;
import com.culabs.nfvo.monitor.TcpServer;
import com.culabs.nfvo.util.NFVOUtils;

/**
 * 
 * @ClassName: VNFInstance
 * @Description: TODO
 * @author 
 * @date 2015年5月5日 上午11:05:23
 * @version 1.0
 */
public class ServerHandler extends ServerHandlerAdaptor
{
	private static Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);
	private static final int IMAGE_SIZE = 64;

	public ServerHandler(String vnfId, String instanceId)
	{
		super(instanceId, instanceId);
	}

	public String getInstanceId()
	{
		return instanceId;
	}

	@Override
	public VNFServer getVNFServer()
	{
		return vnfserver;
	}

	@Override
	public Result createServer(ResourceIds resourceIds) throws NFVOException
	{
		/**
		 *  create volume binding to server start
		 */
		ConfigBlock block = SystemConfig.getInstance().getBlock(vnfserver.getVnfId());
		String serverName = this.vnfserver.getServerName();
		String imageRef = "";
		if (!NFVOUtils.isEmpty(block))
		{
			imageRef = block.getPairs().getPairVal("imageRef");
		}
		String volume_uuid = createServerVolume(serverName + "_volume", imageRef, IMAGE_SIZE);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		/**
		 *  create volume binding to server end
		 */
		
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createServer");
		restUrl.getPairs().add("name", resourceIds.getVpcNamePrefix() + serverName);
		restUrl.getPairs().add("network_uuid", resourceIds.getNetworkUUID());
		restUrl.getPairs().add("volume_uuid", volume_uuid);
		if (!NFVOUtils.isEmpty(block))
		{
			restUrl.getPairs().add("imageRef", block.getPairs().getPairVal("imageRef"))
					.add("flavorRef", block.getPairs().getPairVal("flavorRef"))
					.add("fixed_ip", block.getPairs().getPairVal("fixed_ip"));
		}
		HashMap<?, ?> resp = InstanceOperator.createServer(restUrl, resourceIds);
		this.vnfserver.setServerId((String) NFVOUtils.getMapValByKey(resp, "id", null, null));
		fillVnfServer();
		Result status = Result.ERROR;
		status = fetchMonitorMsg(status);
		if (NFVOUtils.isEmpty(status) || status.isSameCode(Result.ERROR))
		{
			throw new NFVOException("The expected service of ".concat("server ".concat(this.vnfserver.getServerId())
					.concat("start failed.")));
		}
		// 绑定防火墙
//		bindFirewall();
		status.setData(resp);
		vnfserver.setStatus("0");
		storeInstance();
		return status;
	}

	public String createServerVolume(String volumeName, String imageRef, int size) throws NFVOException{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createVolume");
		restUrl.getPairs().add("display_name", volumeName);
		restUrl.getPairs().add("imageRef", imageRef);
		restUrl.getPairs().add("size", size);
		
		String volumeId = InstanceOperator.createVolume(restUrl);
		return volumeId;
	}
	

	@Override
	public Result deleteServer() throws NFVOException
	{
		// delete servers on wocloud
		InstanceOperator.deleteServer(this.vnfserver.getServerId());
		// delete servers
		DBOperator.deleteServerById(this.vnfserver.getServerId());
		// unregister monitor
		TcpServer.unregister(this);

		return null;
	}

	@Override
	public Result queryServer()
	{
		return null;
	}

	@Override
	public Result startServer()
	{
		return null;
	}

	@Override
	public Result stopServer()
	{
		return null;
	}

	@Override
	public void monitor(String mac, String status)
	{
		if (!NFVOUtils.isEmpty(mac) && !NFVOUtils.isEmpty(vnfserver.getMac())
				&& mac.toLowerCase().contains(vnfserver.getMac().toLowerCase()))
		{
			// Completion com = vnfserver.getCompletions().getCompletion();
			String newStatus = status.replace("\n", "");
			if ("0".equals(newStatus))
			{
				LOGGER.info("Server status: {}", status);
				queue.clear();
				queue.add(Result.Factory.successResult());
				this.vnfserver.setStatus("0");
			} else
			{
				this.vnfserver.setStatus("-1");
			}
		}
	}

	@Override
	public String toString()
	{
		return "ServerHandler [vnfserver=" + vnfserver + "]";
	}

	public static void main(String[] args)
	{
		LogService.prepare();
		TcpServer.init();
	}
}
