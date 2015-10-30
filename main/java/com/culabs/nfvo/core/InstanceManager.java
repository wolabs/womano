package com.culabs.nfvo.core;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.ResourceIds;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.VNFServer;
import com.culabs.nfvo.model.db.DBAttribute;
import com.culabs.nfvo.model.db.DBAttributeKey;
import com.culabs.nfvo.model.db.DBInstance;
import com.culabs.nfvo.model.db.DBInstanceServer;
import com.culabs.nfvo.model.db.DBVnfd;
import com.culabs.nfvo.model.nsd.MemberVnf;
import com.culabs.nfvo.model.nsd.Nsd;
import com.culabs.nfvo.model.vnfd.Vdu;
import com.culabs.nfvo.model.vnfd.Vnfd;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.SysConst;
import com.culabs.nfvo.util.TemplateParser;

public class InstanceManager
{
	private static Logger LOGGER = LoggerFactory.getLogger(InstanceManager.class);
	private static final Map<String, List<IServer>> INSTANCE = new ConcurrentHashMap<String, List<IServer>>();
	private static final ExecutorService exs = Executors.newCachedThreadPool();

	public static void createInstance(final String templateId, final String instanceId, String type)
	{
		exs.submit(new Callable<Result>() {

			@Override
			public Result call() throws Exception
			{
				return newInstance(templateId, instanceId, type);
			}
		});
	}

	private static Result newInstance(String templateId, String instanceId, String type) throws Exception
	{
		// 1. 创建ns实例， 实例名，实例id， 创建时间，运行状态， 模板快照,入库
		storeInstance(templateId, instanceId, type);
		// 2. 解析依赖关系
		parseDependency(templateId, instanceId);
		LOGGER.info("Instance: {}", INSTANCE.get(instanceId));
		// 3. 创建vpc相关
		ResourceIds resourceIds = createVPC();
        // 4. 创建虚拟机
		Result resp = null;
		for (IServer server : INSTANCE.get(instanceId))
		{
			try
			{
				resp = server.createServer(resourceIds);
				if (!resp.isSuccess() || NFVOUtils.isEmpty(instanceId)
						|| NFVOUtils.isEmpty(server.getVNFServer().getServerId()))
				{
					throw new IllegalArgumentException("argument(s) is(are) empty");
				}
				LOGGER.info("create server: {} successfully", server.getVNFServer().getVnfId());
			} catch (Exception e)
			{
				LOGGER.error("create server {} failed. The cause: {}", server.getVNFServer().getVnfId(), e);
				updateInstanceStatus(instanceId, "-1");
				throw new NFVOException("create server failed", e);
			}
		}
		updateInstanceStatus(instanceId, "0");
		return Result.Factory.successResult();
	}

	private static void updateInstanceStatus(String instanceId, String status) throws NFVOException
	{
		// status: 0-实例化成功， 1-实例化构建中， -1-实例化失败（不完整）
		DBInstance initialInstance = DBOperator.queryInstanceById(instanceId);
		initialInstance.setStatus(status);
		DBOperator.updateInstance(initialInstance);
	}

	private static String storeInstance(String templateId, String instanceID, String type, String... instanceName)
			throws NFVOException
	{
		DBInstance dbInstance = new DBInstance();
		dbInstance.setInstance_id(instanceID);
		dbInstance.setInstance_name(NFVOUtils.isEmpty(instanceName) ? templateId.concat("_").concat(instanceID)
				: instanceName[0]);
		dbInstance.setType(type);
		dbInstance.setStatus("1");
		dbInstance.setDescription(templateId.concat(" Instance"));
		dbInstance.setCreate_time(new Date());
		dbInstance.setTemplate_id(templateId);
		DBOperator.createInstance(dbInstance);
		return instanceID;
	}

	public static List<VNFServer> queryInstance(String instanceId) throws NFVOException
	{
		List<IServer> serhandlers = INSTANCE.get(instanceId);
		if (NFVOUtils.isEmpty(serhandlers))
		{
			serhandlers = new CopyOnWriteArrayList<IServer>();
			synchronized (serhandlers)
			{
				INSTANCE.put(instanceId, serhandlers);
				List<DBInstanceServer> servers = DBOperator.queryServerListByInstanceId(instanceId);
				VNFServer vnfi = null;
				IServer handler = null;
				HashMap<?, ?> serverDetailMap = InstanceOperator.getServersDetail();
				for (DBInstanceServer dbsr : servers)
				{
					DBAttributeKey key = new DBAttributeKey();
					key.setName(dbsr.getServer_id());
					key.setType("lb_info");
					key.setValue(dbsr.getServer_id());
					DBAttribute dbattr = DBOperator.queryAttribute(key);
					if (NFVOUtils.isEmpty(dbattr) || NFVOUtils.isEmpty(dbattr.getName()))
					{
						handler = new ServerHandler(dbsr.getVnf_id(), instanceId);
					} else
					{
						handler = new LBServerHandler(dbsr.getVnf_id(), instanceId);
					}
					serhandlers.add(handler);
					vnfi = handler.getVNFServer();
					vnfi.setServerId(dbsr.getServer_id());
					vnfi.setServerName(dbsr.getVnf_name());

					// distinct vm by instanceid and vnfname for real-timely show vm's status in your browser
					vnfi.setVnfName(dbsr.getVnf_name());
					vnfi.setVnfId(dbsr.getVnf_id());
					vnfi.setNsinstanceId(dbsr.getInstance_id());
					vnfi.setInstancesNum(dbsr.getInstancenum());
					vnfi.setSequence(dbsr.getSequence());
					setMacAndName(serverDetailMap, dbsr.getServer_id(), vnfi);
					// set poolId, vipId
					if (!NFVOUtils.isEmpty(dbattr))
					{
						vnfi.setPoolId(dbattr.getExt1());
						vnfi.setVipId(dbattr.getExt2());
					}
					// vnfi.setStatus("1");
					handler.queryServer();
				}
			}
		}
		List<VNFServer> list = new ArrayList<VNFServer>();
		for (IServer server : serhandlers)
		{
			VNFServer vnfServer = server.getVNFServer();
			list.add(vnfServer);
		}
		return list;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void setMacAndName(HashMap<?, ?> resp, String server_id, VNFServer vnfi)
	{

		List<Map<?, ?>> serviceDetials = (List<Map<?, ?>>) NFVOUtils.getMapValByKey(resp, "servers", null,
				SysConst.MapQueryCond.LSEQT);
		for (Map<?, ?> serverDetail : serviceDetials)
		{
			String id = (String) NFVOUtils.getMapValByKey(serverDetail, "id", null, null);
			if (id.equalsIgnoreCase(server_id))
			{
				// 获取serverName
				vnfi.setServerName((String) NFVOUtils.getMapValByKey(serverDetail, "name", null, null));
				Map<?, ?> addressMap = (Map<?, ?>) NFVOUtils.getMapValByKey(serverDetail, "addresses", null, null);
				for (Map.Entry<?, ?> entry : addressMap.entrySet())
				{
					List addressList = (List) entry.getValue();
					if (null != addressList)
					{
						Map<?, ?> targetInfo = (Map<?, ?>) addressList.get(0);
						// 获取mac
						vnfi.setMac((String) targetInfo.get("OS-EXT-IPS-MAC:mac_addr"));
						return;
					}
				}
				break;
			}
		}
		return;
	}

	public static Result deleteInstance(String instanceId) throws NFVOException
	{
		Result result = Result.Factory.successResult();
		List<IServer> serhandlers = INSTANCE.get(instanceId);
		try
		{
			if (NFVOUtils.isEmpty(serhandlers))
			{
				queryInstance(instanceId);
				serhandlers = INSTANCE.get(instanceId);
			}
			if (!NFVOUtils.isEmpty(serhandlers))
			{
				synchronized (serhandlers)
				{
					for (IServer server : serhandlers)
					{
						server.deleteServer();
						serhandlers.remove(server);
					}
				}
			}
			// 删除instance_server
			DBOperator.deleteByInstanceId(instanceId);
			DBOperator.deleteInstanceById(instanceId);
			INSTANCE.remove(instanceId);
			serhandlers.clear();
		} catch (Exception e)
		{
			LOGGER.error("Delete instance {} failed", instanceId);
			if (result.isSuccess())
			{
				result = Result.Factory.failResult("-1", "Delete instance failed. The cause: ".concat(e.toString()));
			}
		}
		return result;
	}

	public static ResourceIds createVPC() throws NFVOException
	{
		
		ResourceIds resourceIds = new ResourceIds();
		
		// 1. 创建vpc
		InstanceOperator.createVpc(resourceIds);

		// 2. 创建网关路由
		InstanceOperator.createGWRouter(resourceIds);
		// 3. 创建路由表
		InstanceOperator.createRoutingTable(resourceIds);

		// 4. 创建路由规则
		InstanceOperator.createRoutingRule(resourceIds);

		// 5. 创建公网IP资源
//		InstanceOperator.createPublicIpResource(resourceIds);

		// 6. 创建公网IP
		InstanceOperator.createPublicIp(resourceIds);

		// 7. 创建网络
		InstanceOperator.createNetwork(resourceIds);
		
		// 8， 创建子网
		InstanceOperator.createSubnet(resourceIds);
		
		// 9. 子网关联vpc
		InstanceOperator.createVpcSubnet(resourceIds);

		// 10. 创建子网路由表
		InstanceOperator.createSubnetRoutingTable(resourceIds);

		// 11. 创建acl
		InstanceOperator.createAcl(resourceIds);

		// 12. 创建acl规则
		InstanceOperator.addVpcAclRules(resourceIds);

		// 13. 创建acl子网规则
		InstanceOperator.createSubnetAclTable(resourceIds);
		
		// 14. 创建nat
		InstanceOperator.createNat(resourceIds);

		return resourceIds;
	}
	

	private static void parseDependency(String templateId, String instanceId)
	{
		Nsd nsd = TemplateParser.xmlToBean(TemplateOperator.queryDBNsd(templateId).getContent(), Nsd.class);
		List<MemberVnf> memvnfs = nsd.getNsFlavours().getNsFlavour().get(0).getMemberVnfs().getMemberVnf();
		List<IServer> serhandlers = INSTANCE.get(instanceId);
		if (NFVOUtils.isEmpty(serhandlers))
		{
			serhandlers = new CopyOnWriteArrayList<IServer>();
			INSTANCE.put(instanceId, serhandlers);
			synchronized (serhandlers)
			{
				VNFServer vnfi = null;
				IServer handler = null;
				for (MemberVnf vnf : memvnfs)
				{
					DBVnfd dbvnfd = DBOperator.queryDBVnfd(vnf.getMemberVnfId());
					Vnfd vnfd = TemplateParser.xmlToBean(dbvnfd.getContent(), Vnfd.class);
					Vdu vdu = vnfd.getVnfFlavours().getVnfFlavour().get(0).getVdus().getVdu().get(0);
					if (NFVOUtils.isEmpty(vdu.getNumInstances()) || vdu.getNumInstances().intValue() < 2)
					{
						handler = new ServerHandler(vnf.getMemberVnfId(), instanceId);
					} else
					{
						// 暂不考虑多实例负载均衡
						// handler = new LBServerHandler(vnf.getMemberVnfId(),
						// instanceId);
						handler = new ServerHandler(vnf.getMemberVnfId(), instanceId);
					}

					vnfi = handler.getVNFServer();
					// 设置vnf模板信息
					vnfi.setVnfId(vnf.getMemberVnfId());
					vnfi.setServerName(vnfd.getName());
					vnfi.setInstancesNum(NFVOUtils.isEmpty(vdu.getNumInstances()) ? 1 : vdu.getNumInstances()
							.intValue());
					vnfi.setImageRef(vdu.getVduFlavour().getVmImage());
					BigInteger bgi = vdu.getVduFlavour().getCpu();
					vnfi.getFlavor().setCpu(NFVOUtils.isEmpty(bgi) ? null : String.valueOf(bgi));
					bgi = vdu.getVduFlavour().getMemory();
					vnfi.getFlavor().setMemory(NFVOUtils.isEmpty(bgi) ? null : String.valueOf(bgi));
					vnfi.setDependency(vnf.getDependency());
					// vnfi.setMac("00:50:56:98:49:12");
					vnfi.setCompletions(vnf.getCompletions());
					serhandlers.add(handler);
				}
				INSTANCE.put(instanceId, depencySort(serhandlers));
				int idx = 0;
				for (IServer server : INSTANCE.get(instanceId))
				{
					// 设置顺序
					server.getVNFServer().setSequence(++idx);
				}
			}
		}
		serhandlers.clear();
	}

	private static List<IServer> depencySort(List<IServer> serhandlers)
	{
		List<IServer> temp = new CopyOnWriteArrayList<IServer>();
		IServer servh1 = null;
		IServer servh2 = null;
		int maxloop = serhandlers.size() * serhandlers.size();
		int idx = 0;
		while (idx < maxloop && temp.size() < serhandlers.size())
		{
			++idx;
			for (int i = 0; i < serhandlers.size(); ++i)
			{
				servh1 = serhandlers.get(i);
				if (temp.isEmpty())
				{
					temp.add(servh1);
					continue;
				}
				if (temp.contains(servh1))
				{
					continue;
				}
				for (int j = 0; j < temp.size(); ++j)
				{
					servh2 = temp.get(j);
					if (servh1.equals(servh2))
					{
						break;
					}
					if ("".equals(servh1.getVNFServer().getDependency().getSource().trim()))
					{
						temp.add(0, servh1);
						break;
					} else if ("".equals(servh1.getVNFServer().getDependency().getTarget().trim()))
					{
						temp.add(servh1);
						break;
					} else if (servh1.getVNFServer().getDependency().getTarget()
							.equals(servh2.getVNFServer().getVnfId()))
					{
						temp.add(j, servh1);
						break;
					}

				}
			}
		}
		return temp;
	}

}
