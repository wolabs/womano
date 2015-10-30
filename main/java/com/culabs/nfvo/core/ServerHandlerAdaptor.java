package com.culabs.nfvo.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.config.RestConfigHandler;
import com.culabs.nfvo.config.SystemConfig;
import com.culabs.nfvo.log.LogService;
import com.culabs.nfvo.model.ConfigBlock;
import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.VNFServer;
import com.culabs.nfvo.model.db.DBAttribute;
import com.culabs.nfvo.model.db.DBInstanceServer;
import com.culabs.nfvo.monitor.TcpServer;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.SysConst;

/**
 * 
 * @ClassName: ServerHandlerAdaptor
 * @Description: TODO
 * @author 
 * @date 2015年5月30日 下午12:46:26
 * @version 1.0
 */
public abstract class ServerHandlerAdaptor implements IServer, IMonitorListener
{
	private static Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);
	protected final VNFServer vnfserver = new VNFServer();
	protected final BlockingQueue<Result> queue = new ArrayBlockingQueue<Result>(1);
	// 归属实例号
	protected String instanceId = "";
	// 服务启动时间间隔
	protected int server_period = 300;
	// 是否测试
	protected boolean isTest = false;

	public ServerHandlerAdaptor(String vnfId, String instanceId)
	{
		this.instanceId = instanceId;
		// TODO Auto-generated constructor stub
		TcpServer.register(this);
		ConfigBlock block = SystemConfig.getInstance().getBlock("work_environment");
		if (!NFVOUtils.isEmpty(block))
		{
			isTest = NFVOUtils.toBoolean(block.getPairs().getPairVal("isTest"), isTest);
		}
		block = SystemConfig.getInstance().getBlock(vnfId);
		if (!NFVOUtils.isEmpty(block))
		{
			server_period = NFVOUtils.toIntger(block.getPairs().getPairVal("server_period"), server_period);
		}
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

	protected boolean hasParent()
	{
		return !NFVOUtils.isEmpty(this.vnfserver.getParent());
	}

	protected void bindFirewall() throws NFVOException
	{
		String portId = InstanceOperator.getPort(this.vnfserver.getIp());
		if (NFVOUtils.isEmpty(portId))
		{
			throw new NFVOException("No port for ip ".concat(this.vnfserver.getIp()).concat(" found"));
		}
		InstanceOperator.bindFirewall(portId, "");
	}

	protected void storeInstance() throws NFVOException
	{
		String serverId = this.vnfserver.getServerId();
		if (NFVOUtils.isEmpty(serverId))
		{
			throw new RuntimeException("create server failed: serverId  empty!");
		}
		if (NFVOUtils.isEmpty(serverId) || NFVOUtils.isEmpty(instanceId))
		{
			throw new RuntimeException("create server failed: instanceID or serverId can not be empty!");
		}
		if (!hasParent())
		{
			DBInstanceServer dbInstanceServer = new DBInstanceServer();
			dbInstanceServer.setInstance_id(instanceId);
			dbInstanceServer.setServer_id(serverId);
			dbInstanceServer.setVnf_id(this.vnfserver.getVnfId());
			dbInstanceServer.setVnf_name(this.vnfserver.getServerName());
			dbInstanceServer.setInstancenum(this.vnfserver.getInstancesNum());
			dbInstanceServer.setSequence(this.vnfserver.getSequence());
			DBOperator.createInstanceServer(dbInstanceServer);
		} else
		{
			DBAttribute dbattr = new DBAttribute();
			dbattr.setName(this.vnfserver.getParent());
			dbattr.setType("lb_group");
			dbattr.setValue(this.vnfserver.getServerId());
			dbattr.setExt1(this.vnfserver.getMemberId());
		}

	}

	protected void fillVnfServer()
	{
		// query server detail for mac
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("getServerDetail");
		restUrl.getPairs().add("server_id", this.vnfserver.getServerId());
		HashMap<?, ?> serverDetail = InstanceOperator.getServerDetail(restUrl);
		fillMacAddrAndIP(serverDetail);
		if (this.vnfserver.getInstancesNum() > 1)
		{
			List<?> list = (List<?>) NFVOUtils.getMapValByKey(serverDetail, "os-extended-volumes:volumes_attached",
					null, SysConst.MapQueryCond.LSEQT);
			String volumeId = null;
			if (!NFVOUtils.isEmpty(list))
			{
				volumeId = (String) ((Map<?, ?>) list.get(0)).get("id");
				this.vnfserver.setVolumeId(volumeId);
			}
			restUrl = RestConfigHandler.getInstance().getUrl("getVolumeSize");
			restUrl.getPairs().add("volume_id", this.vnfserver.getVolumeId());
			HashMap<?, ?> volumeResp = InstanceOperator.getVolume(restUrl);
			Integer volumeSize = (Integer) NFVOUtils
					.getMapValByKey(volumeResp, "size", null, SysConst.MapQueryCond.LSEQT);
			this.vnfserver.setVolumeSize(NFVOUtils.isEmpty(volumeSize) ? 30 : volumeSize);
		}
	}

	protected Result fetchMonitorMsg(Result status)
	{
//		try
//		{
//			status = queue.poll(server_period, TimeUnit.SECONDS);
//
//		} catch (InterruptedException e)
//		{
//			LOGGER.error("Poll queue error: {}", e);
//		}
		if (isTest)
		{
			this.vnfserver.setStatus("0");
			status = Result.Factory.successResult();
		}
		return status;
	}

	protected void fillMacAddrAndIP(HashMap<?, ?> resp)
	{
		Map<?, ?> serverDetail = (Map<?, ?>) NFVOUtils
				.getMapValByKey(resp, "server", null, SysConst.MapQueryCond.LSEQT);
		Map<?, ?> addressMap = (Map<?, ?>) NFVOUtils.getMapValByKey(serverDetail, "addresses", null, null);
		for (Map.Entry<?, ?> entry : addressMap.entrySet())
		{
			List<?> addressList = (List<?>) entry.getValue();
			if (null != addressList)
			{
				Map<?, ?> targetInfo = (Map<?, ?>) addressList.get(0);
				this.vnfserver.setMac((String) targetInfo.get("OS-EXT-IPS-MAC:mac_addr"));
				this.vnfserver.setIp((String) targetInfo.get("addr"));
				return;
			}
		}
	}

	@Override
	public Result deleteServer() throws NFVOException
	{
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
		if (!NFVOUtils.isEmpty(mac) && mac.toLowerCase().contains(vnfserver.getMac().toLowerCase()))
		{
			if ("0".equals(status.toLowerCase()))
			{
				LOGGER.info("Server status: {}", status);
				queue.clear();
				queue.add(Result.Factory.successResult());
				this.vnfserver.setStatus(status);
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
