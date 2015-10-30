package com.culabs.nfvo.core;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

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
import com.culabs.nfvo.model.db.DBAttribute;
import com.culabs.nfvo.model.db.DBMember;
import com.culabs.nfvo.model.db.DBPool;
import com.culabs.nfvo.model.db.DBSubnet;
import com.culabs.nfvo.model.db.DBVip;
import com.culabs.nfvo.monitor.TcpServer;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.unicomportal.model.db.DBLbMembers;
import com.culabs.unicomportal.model.db.DBLbPools;
import com.culabs.unicomportal.model.db.DBLbVips;
import com.culabs.unicomportal.model.db.DBSubnets;

/**
 * 
 * @ClassName: VNFInstance
 * @Description: TODO
 * @author 
 * @date 2015年5月5日 上午11:05:23
 * @version 1.0
 */
public class LBServerHandler extends ServerHandlerAdaptor
{
	private static Logger LOGGER = LoggerFactory.getLogger(LBServerHandler.class);
	private final List<IServer> LB_MEMBERS = new CopyOnWriteArrayList<IServer>();

	public LBServerHandler(String vnfId, String instanceId)
	{
		super(vnfId, instanceId);
	}

	public String getInstanceId()
	{
		return instanceId;
	}

	public VNFServer getVNFServer()
	{
		return vnfserver;
	}

	@Override
	public Result createServer(ResourceIds resourceIds) throws NFVOException
	{
		String volume_id = this.vnfserver.getVolumeId();
		Integer volumeSize = 0;
		if (NFVOUtils.isEmpty(volume_id))
		{
			ConfigBlock block = SystemConfig.getInstance().getBlock(vnfserver.getVnfId());
			if (!NFVOUtils.isEmpty(block))
			{
				volume_id = block.getPairs().getPairVal("volume_id");
				volumeSize = NFVOUtils.toIntger(block.getPairs().getPairVal("volume_id"), 30);
			}
		}
		String newVolumeId = InstanceOperator.cloneVolume(volume_id, volumeSize, this.vnfserver.getServerName());
		//HashMap<?, ?> resp = InstanceOperator.cloneServer(newVolumeId, this.vnfserver.getServerName());
		// volumeId创建出错,改为直接创建
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createServer");
		restUrl.getPairs().add("name",resourceIds.getVpcNamePrefix() + this.vnfserver.getServerName());
		restUrl.getPairs().add("network_uuid", resourceIds.getNetworkUUID());
		ConfigBlock block = SystemConfig.getInstance().getBlock(vnfserver.getVnfId());
		if (!NFVOUtils.isEmpty(block))
		{
			restUrl.getPairs().add("imageRef", block.getPairs().getPairVal("imageRef"))
					.add("flavorRef", block.getPairs().getPairVal("flavorRef"));
		}
		HashMap<?, ?> resp = InstanceOperator.createServer(restUrl, resourceIds);
		// 代替创建
		this.vnfserver.setServerId((String) NFVOUtils.getMapValByKey(resp, "id", null, null));
		this.vnfserver.setVolumeId(newVolumeId);
		fillVnfServer();
		storeInstance();
		Result status = Result.ERROR;
		status = fetchMonitorMsg(status);
		if (NFVOUtils.isEmpty(status) || status.isSameCode(Result.ERROR))
		{
			LOGGER.error("Monitor status is {}", status);
			throw new NFVOException("The expected service of ".concat("server ".concat(this.vnfserver.getServerId())
					.concat("start failed.")));
		}
		// 绑定防火墙
		bindFirewall();
		// 创建负载均衡
		createLB();
		// 创建多实例
		multiInstances(resourceIds);
		// 记录实例
		status.setData(resp);
		vnfserver.setStatus("0");
		return status;
	}

	/**
	 * insert lbvip into unicom database.
	 * 
	 * @param dbVip
	 * @return
	 * @throws NFVOException
	 */
	private int createUnicomPortalLbVip(DBVip dbVip) throws NFVOException
	{
		DBLbVips vips = new DBLbVips();
		vips.setName(dbVip.getName());
		vips.setProtocol(dbVip.getProtocal());
		vips.setProtocol_port(Integer.valueOf(dbVip.getProtocal_port()));
		vips.setCreated_at(new Date());
		vips.setUpdated_at(new Date());
		vips.setLb_vip_uuid(dbVip.getId());
		int unicomPortalPoolId = UnicomPortalOperator.createLbVip(vips);
		return unicomPortalPoolId;
	}

	private int createUnicomPortalLbPool(DBPool dbPool, int vipId) throws NFVOException
	{
		DBLbPools lbPools = new DBLbPools();
		lbPools.setCreated_at(new Date());
		lbPools.setLb_method(dbPool.getLb_method());
		lbPools.setName(dbPool.getName());
		lbPools.setProtocol(dbPool.getProtocol());
		lbPools.setLb_pool_uuid(dbPool.getId());
		DBSubnets unicomPortalSubnet = UnicomPortalOperator.querySubnetByUUID(dbPool.getSubnet_id());
		lbPools.setSubnet_id(unicomPortalSubnet.getId());
		lbPools.setUpdated_at(new Date());
		lbPools.setVip_id(vipId);
		int unicomPortalPoolId = UnicomPortalOperator.createLbPools(lbPools);
		return unicomPortalPoolId;
	}

	private int createUnicomPortalLbMember(DBMember dbMember, int unicomPortalPoolId) throws NFVOException
	{
		DBLbMembers lbMembers = new DBLbMembers();
		lbMembers.setAddress(dbMember.getAddress());
		lbMembers.setLb_pool_id(unicomPortalPoolId);
		lbMembers.setProtocol_port(Integer.valueOf(dbMember.getProtocol_port()));
		lbMembers.setMember_uuid(dbMember.getId());
		lbMembers.setWeight(1);
		lbMembers.setCreated_at(new Date());
		lbMembers.setUpdated_at(new Date());
		int unicomPortalMemberId = UnicomPortalOperator.createLbMember(lbMembers);
		return unicomPortalMemberId;
	}

	private void createLB() throws NFVOException
	{
		List<DBSubnet> subnetList = DBOperator.querySubnetList();
		if (NFVOUtils.isEmpty(subnetList))
		{
			throw new NFVOException("No subnet found");
		}
		int unicomPortalPoolId = 0;
		if (!hasParent())
		{
			String subnetId = subnetList.get(0).getSubnet_id();
			DBPool dbpool = InstanceOperator.createPool("pool_".concat(vnfserver.getServerName()), "", subnetId);
			DBVip dbvip = InstanceOperator.createVip("vip_".concat(vnfserver.getServerName()), dbpool.getId(),
					subnetId, "", "");
			
			this.vnfserver.setPoolId(dbpool.getId());
			this.vnfserver.setVipId(dbvip.getId());
			DBAttribute dbattr = new DBAttribute();
			dbattr.setName(this.vnfserver.getServerId());
			dbattr.setType("lb_info");
			dbattr.setValue(this.vnfserver.getServerId());
			dbattr.setExt1(dbpool.getId());
			dbattr.setExt2(dbvip.getId());
			DBOperator.insertAttribute(dbattr);
			// 联通数据库
			int unicomPortalVipId = createUnicomPortalLbVip(dbvip);
			unicomPortalPoolId = createUnicomPortalLbPool(dbpool, unicomPortalVipId);
		}
		DBMember member = InstanceOperator.createMember(this.vnfserver.getIp(), this.vnfserver.getPoolId(), "");
		// 联通数据库
		createUnicomPortalLbMember(member, unicomPortalPoolId);
		this.vnfserver.setMemberId(member.getId());
	}

	private void multiInstances(ResourceIds resourceIds) throws NFVOException
	{
		if (!hasParent())
		{
			return;
		}
		int num = vnfserver.getInstancesNum();
		if (num <= 0)
		{
			return;
		}
		synchronized (LB_MEMBERS)
		{
			VNFServer vnfi = null;
			for (int i = 0; i < num; ++i)
			{
				// 1. clone instance
				IServer handler = null;
				handler = new LBServerHandler(this.vnfserver.getVnfId(), instanceId);
				vnfi = handler.getVNFServer();
				vnfi.setParent(this.vnfserver.getServerId());
				vnfi.setPoolId(this.vnfserver.getPoolId());
				// 设置vnf模板信息
				vnfi.setVnfId(this.vnfserver.getVnfId());
				vnfi.setServerName(this.vnfserver.getServerName().concat("_").concat(String.valueOf((i + 1))));
				vnfi.setInstancesNum(-1);
				vnfi.setVolumeId(this.vnfserver.getVolumeId());
				vnfi.setVolumeSize(this.vnfserver.getVolumeSize());
				vnfi.setImageRef(this.vnfserver.getImageRef());
				// vnfi.setDependency();
				// vnfi.setMac("00:50:56:98:49:12");
				vnfi.setCompletions(this.vnfserver.getCompletions());
				handler.createServer(resourceIds);
				LB_MEMBERS.add(handler);
			}
		}
	}

	@Override
	public Result deleteServer() throws NFVOException
	{
		// TODO Auto-generated method stub
		// delete servers on wocloud
		// delete LB instances
		DBAttribute dbattr = new DBAttribute();
		dbattr.setName(this.vnfserver.getServerId());
		dbattr.setType("lb_group");
		for (IServer s : LB_MEMBERS)
		{
			InstanceOperator.deleteMember(s.getVNFServer().getMemberId());
			InstanceOperator.deleteServer(s.getVNFServer().getServerId());
			dbattr.setValue(s.getVNFServer().getServerId());
			DBOperator.deleteAttribute(dbattr);
			TcpServer.unregister(s);
		}
		InstanceOperator.deleteServer(this.vnfserver.getServerId());
		// clear poolId,vipId record
		dbattr = new DBAttribute();
		dbattr.setName(this.vnfserver.getServerId());
		dbattr.setType("lb_info");
		dbattr.setValue(this.vnfserver.getServerId());
		DBOperator.deleteAttribute(dbattr);
		// delete servers
		DBOperator.deleteServerById(this.vnfserver.getServerId());
		// unregister monitor
		TcpServer.unregister(this);
		return null;
	}

	@Override
	public Result queryServer()
	{
		// TODO Auto-generated method stub
		if (LB_MEMBERS.isEmpty())
		{
			DBAttribute dbattr = new DBAttribute();
			dbattr.setName(this.vnfserver.getServerId());
			synchronized (LB_MEMBERS)
			{
				try
				{
					List<DBAttribute> dbattrs = DBOperator.queryAttributes(dbattr);
					VNFServer vnfi = null;
					for (int i = 0; i < dbattrs.size(); ++i)
					{
						// 1. clone instance
						IServer handler = null;
						handler = new LBServerHandler(this.vnfserver.getVnfId(), instanceId);
						vnfi = handler.getVNFServer();
						vnfi.setParent(this.vnfserver.getServerId());
						vnfi.setPoolId(this.vnfserver.getPoolId());
						// 设置vnf模板信息
						vnfi.setVnfId(this.vnfserver.getVnfId());
						vnfi.setServerName(this.vnfserver.getServerName().concat("_").concat(String.valueOf((i + 1))));
						vnfi.setInstancesNum(-1);
						vnfi.setVolumeId(this.vnfserver.getVolumeId());
						vnfi.setVolumeSize(this.vnfserver.getVolumeSize());
						vnfi.setImageRef(this.vnfserver.getImageRef());
						// vnfi.setDependency();
						// vnfi.setMac("00:50:56:98:49:12");
						vnfi.setCompletions(this.vnfserver.getCompletions());
						//modified by tanch 
						handler.createServer(null);
						LB_MEMBERS.add(handler);
					}
				} catch (NFVOException e)
				{
					LOGGER.error(e.getMessage());
				}
			}
		}

		return null;
	}

	@Override
	public Result startServer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result stopServer()
	{
		// TODO Auto-generated method stub
		return null;
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
