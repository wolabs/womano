package com.culabs.nfvo.core;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.config.RestConfigHandler;
import com.culabs.nfvo.config.UNFVOConfig;
import com.culabs.nfvo.model.KVPair;
import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.ResourceIds;
import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.db.DBAcl;
import com.culabs.nfvo.model.db.DBAclRule;
import com.culabs.nfvo.model.db.DBAclSubnet;
import com.culabs.nfvo.model.db.DBGWRouter;
import com.culabs.nfvo.model.db.DBHypervisor;
import com.culabs.nfvo.model.db.DBMember;
import com.culabs.nfvo.model.db.DBNat;
import com.culabs.nfvo.model.db.DBNetwork;
import com.culabs.nfvo.model.db.DBPool;
import com.culabs.nfvo.model.db.DBPublicIp;
import com.culabs.nfvo.model.db.DBPublicIpResource;
import com.culabs.nfvo.model.db.DBRoutingRule;
import com.culabs.nfvo.model.db.DBRoutingTable;
import com.culabs.nfvo.model.db.DBServer;
import com.culabs.nfvo.model.db.DBSubnet;
import com.culabs.nfvo.model.db.DBSubnetRoutingTable;
import com.culabs.nfvo.model.db.DBVip;
import com.culabs.nfvo.model.db.DBVpc;
import com.culabs.nfvo.util.DirUtils;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.SysConst;
import com.culabs.nfvo.vim.VimRestClient;
import com.culabs.unicomportal.model.db.DBAclRules;
import com.culabs.unicomportal.model.db.DBAclTables;
import com.culabs.unicomportal.model.db.DBComputeNodes;
import com.culabs.unicomportal.model.db.DBFlavors;
import com.culabs.unicomportal.model.db.DBImages;
import com.culabs.unicomportal.model.db.DBInstances;
import com.culabs.unicomportal.model.db.DBInternets;
import com.culabs.unicomportal.model.db.DBJobs;
import com.culabs.unicomportal.model.db.DBNats;
import com.culabs.unicomportal.model.db.DBNetworks;
import com.culabs.unicomportal.model.db.DBPorts;
import com.culabs.unicomportal.model.db.DBPublicResources;
import com.culabs.unicomportal.model.db.DBPublicips;
import com.culabs.unicomportal.model.db.DBRouteTables;
import com.culabs.unicomportal.model.db.DBRouterNetworks;
import com.culabs.unicomportal.model.db.DBRouterRules;
import com.culabs.unicomportal.model.db.DBRouters;
import com.culabs.unicomportal.model.db.DBSubnetAclTables;
import com.culabs.unicomportal.model.db.DBSubnetRouteTables;
import com.culabs.unicomportal.model.db.DBSubnets;
import com.culabs.unicomportal.model.db.DBSubnetsWithBLOBs;

/**
 * 
 * @ClassName: InstanceOperator
 * @Description: complete all action which vpc-instance need
 * @date 2015年4月29日 下午2:20:34
 * @version 1.0
 */
public class InstanceOperator
{

	private static final Logger LOGGER = LoggerFactory.getLogger(InstanceOperator.class);
	private static final int OWNER_ID = 3;
	private static final int CREATOR_ID = 3;
	private static final int PORTMAX = 65535;
	private static final int PORTMIN = 0;
	private static final String PREFIX = "0.0.0.0/0";
	private static final String STRATEGY = "allow";
	private static final String PRIORITY = "1";
	
	public String getTokenId()
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("getTokenId");
		HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
		Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("access");
		if (NFVOUtils.isEmpty(secondLevelMap))
		{
			LOGGER.error("getTokenId error :" + respMap.get("NeutronError"));
			return null;
		}
		Map<?, ?> thirdLevelMap = (Map<?, ?>) secondLevelMap.get("token");
		String access_token_id = (String) thirdLevelMap.get("id");
		return access_token_id;
	}

	public static ResourceIds createVpc(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createVpc");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		kvPair.add("vpcid", "...");
		HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
		try
		{
			DBVpc dbVpc = new DBVpc();
			Map<?, ?> vpcMap = (Map<?, ?>) respMap.get("router");
			if (NFVOUtils.isEmpty(vpcMap))
			{
				LOGGER.error("createVpc error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			String vpcName = (String) kvPair.getPairVal("vpcname");
			if (NFVOUtils.isEmpty(vpcName))
			{
				LOGGER.error("createVpc error : vpc name can not be empty!");
				return resourceIds;
			}
			dbVpc.setVpc_id((String) vpcMap.get("id"));
			dbVpc.setStatus((String) vpcMap.get("status"));
			dbVpc.setAdmin_state_up((Boolean) vpcMap.get("admin_state_up"));
			dbVpc.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbVpc.setName(vpcName);
			DBOperator.createVPC(dbVpc);
			// insert unicom portal db
			DBRouters portalRouter = new DBRouters();
			portalRouter.setRouter_uuid(dbVpc.getVpc_id());
			portalRouter.setName(dbVpc.getName());
			portalRouter.setStatus(dbVpc.getStatus());
			portalRouter.setAdmin_state_up(dbVpc.getAdmin_state_up());
			portalRouter.setCidr("0.0.0.0/0");
			portalRouter.setOwner_id(OWNER_ID);
			portalRouter.setCreator_id(CREATOR_ID);
			portalRouter.setCreated_at(new Date());
			portalRouter.setUpdated_at(new Date());
			result = UnicomPortalOperator.createRouter(portalRouter);
			
			resourceIds.setVpcUUID(dbVpc.getVpc_id());
			resourceIds.setVpcId(result);
			resourceIds.setVpcName(dbVpc.getName());
			String[] preArray = vpcName.split("_", 2);
			resourceIds.setVpcNamePrefix(preArray[0] + "_");
//			InstanceOperator.createJobs(result, dbVpc.getName(), "Router");
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createGWRouter(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createGWRouter");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String igwName = resourceIds.getVpcNamePrefix() + (String) kvPair.getPairVal("name");
			jsonObject.put("name", igwName);
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			jsonObject.put("bandwidth_tx", kvPair.getPairVal("bandwidth_tx"));
			jsonObject.put("bandwidth_rx", kvPair.getPairVal("bandwidth_rx"));
			String vpcId = (String) kvPair.getPairVal("router_id");
			if (NFVOUtils.isEmpty(vpcId))
			{
				jsonObject.put("router_id", resourceIds.getVpcUUID());
			}
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("igwrouter", jsonObject);
			restUrl.setReqjson(outJsonObject);

			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("igwrouter");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createGWRouter error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBGWRouter dbGWRouter = new DBGWRouter();
			String gw_router_id = (String) secondLevelMap.get("id");
			dbGWRouter.setGw_router_id(gw_router_id);
			dbGWRouter.setVpc_id(resourceIds.getVpcUUID());
			dbGWRouter.setStatus((String) secondLevelMap.get("status"));
			dbGWRouter.setAdmin_state_up((Boolean) secondLevelMap.get("admin_state_up"));
			dbGWRouter.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbGWRouter.setName(igwName);
			dbGWRouter.setBandwidth_tx(Integer.valueOf((String) kvPair.getPairVal("bandwidth_tx")));
			dbGWRouter.setBandwidth_rx(Integer.valueOf((String) kvPair.getPairVal("bandwidth_rx")));
			dbGWRouter.setRouter_type((String) kvPair.getPairVal("router_type"));
			DBOperator.createGWRouter(dbGWRouter);

			// insert unicom portal db
			DBInternets portalDBInternets = new DBInternets();
			portalDBInternets.setBandwidth_rx(dbGWRouter.getBandwidth_rx());
			portalDBInternets.setBandwidth_tx(dbGWRouter.getBandwidth_tx());
			portalDBInternets.setCreated_at(new Date());
			portalDBInternets.setUpdated_at(new Date());
			portalDBInternets.setStatus("active");
			portalDBInternets.setName(dbGWRouter.getName());
			portalDBInternets.setInternet_uuid(dbGWRouter.getGw_router_id());
			portalDBInternets.setRouter_id(resourceIds.getVpcId());
			portalDBInternets.setAdmin_state_up(true);
			portalDBInternets.setCreator_id(CREATOR_ID);
			portalDBInternets.setOwner_id(OWNER_ID);
			result = UnicomPortalOperator.createInternet(portalDBInternets);
			
			resourceIds.setIgwRouterUUID(dbGWRouter.getGw_router_id());
			resourceIds.setIgwRouterId(result);
			resourceIds.setIgwRouterName(dbGWRouter.getName());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createPublicIpResource(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createPublicIpResource");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
		jsonObject.put("begin", kvPair.getPairVal("begin"));
		jsonObject.put("end", kvPair.getPairVal("end"));
		jsonObject.put("mask", kvPair.getPairVal("mask"));
		jsonObject.put("gateway_ip", kvPair.getPairVal("gateway_ip"));
		JSONObject outJsonObject = new JSONObject();
		outJsonObject.put("publicipresource", jsonObject);
		restUrl.setReqjson(outJsonObject);

		try
		{
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("publicipresource");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createPublicIpResource error :" + respMap.get("NeutronError"));
				return resourceIds;
			}
			JSONArray thiredJSONArray = (JSONArray) secondLevelMap.get("publicipresources");
			Map<?, ?> thiredLevelMap = (Map<?, ?>) thiredJSONArray.get(0);
			DBPublicIpResource dbPublicIpResource = new DBPublicIpResource();
			String public_ip_resource_id = (String) thiredLevelMap.get("id");
			dbPublicIpResource.setPublic_ip_resource_id(public_ip_resource_id);
			dbPublicIpResource.setIp((String) thiredLevelMap.get("ip"));
			dbPublicIpResource.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbPublicIpResource.setBegin((String) kvPair.getPairVal("begin"));
			dbPublicIpResource.setEnd((String) kvPair.getPairVal("end"));
			dbPublicIpResource.setMask((String) kvPair.getPairVal("mask"));
			dbPublicIpResource.setGateway_ip((String) kvPair.getPairVal("gateway_ip"));
			DBOperator.createPublicIpResource(dbPublicIpResource);

			// unicom portal DB start sz
			DBPublicResources dbPublicResources = new DBPublicResources();
			dbPublicResources.setPublic_resource_uuid((String) thiredLevelMap.get("id"));
			dbPublicResources.setBegin_ip((String) kvPair.getPairVal("begin"));
			dbPublicResources.setEnd_ip((String) kvPair.getPairVal("end"));
			dbPublicResources.setMask(Integer.parseInt((String) kvPair.getPairVal("mask")));
			dbPublicResources.setGateway_ip((String) kvPair.getPairVal("gateway_ip"));
			dbPublicResources.setCreated_at(new Date());
			dbPublicResources.setUpdated_at(new Date());
			result = UnicomPortalOperator.createPublicResources(dbPublicResources);
			// unicom portal DB end sz
			
			resourceIds.setPublicIpResourceId(result);
			resourceIds.setPublicIpResourceUUID(dbPublicIpResource.getPublic_ip_resource_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createPublicIp(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createPublicIp");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String publicIpName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			jsonObject.put("name", publicIpName);
			jsonObject.put("default_snat_source", kvPair.getPairVal("default_snat_source"));
			jsonObject.put("type", kvPair.getPairVal("type"));
			String floatingIpAddr = (String) kvPair.getPairVal("floating_ip_address");
			jsonObject.put("floating_ip_address", floatingIpAddr);

			String gw_router_id = (String) kvPair.getPairVal("gwrouter_id");
			if (NFVOUtils.isEmpty(gw_router_id))
			{
				jsonObject.put("gwrouter_id", resourceIds.getIgwRouterUUID());
			}

			String public_ip_resource_id = (String) kvPair.getPairVal("publicipresource_id");
			if (NFVOUtils.isEmpty(public_ip_resource_id))
			{
				public_ip_resource_id = queryPublicIpResourceList(floatingIpAddr);
				jsonObject.put("publicipresource_id", public_ip_resource_id);
			}else{
				jsonObject.put("publicipresource_id", public_ip_resource_id);
			}
		
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("publicip", jsonObject);
			restUrl.setReqjson(outJsonObject);
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("publicip");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createPublicIp error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBPublicIp dbPublicIp = new DBPublicIp();
			dbPublicIp.setPublic_ip_id((String) secondLevelMap.get("id"));
			dbPublicIp.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbPublicIp.setName(publicIpName);
			dbPublicIp.setType((String) kvPair.getPairVal("type"));
			dbPublicIp.setDefault_snat_source(Boolean.valueOf((String) kvPair.getPairVal("default_snat_source")));
			dbPublicIp.setPublic_ip_resource_id(public_ip_resource_id);
			dbPublicIp.setAdmin_state_up((Boolean) secondLevelMap.get("admin_state_up"));
			dbPublicIp.setGw_router_id(resourceIds.getIgwRouterUUID());
			dbPublicIp.setFloating_ip_address((String) secondLevelMap.get("floating_ip_address"));
			dbPublicIp.setStatus((String) secondLevelMap.get("status"));
			DBOperator.createPublicIp(dbPublicIp);

			// insert unicom portal db
			DBPublicips portalPublicIp = new DBPublicips();
			portalPublicIp.setCreated_at(new Date());
			portalPublicIp.setDefault_snat_source(dbPublicIp.getDefault_snat_source());
			portalPublicIp.setFloating_ip_address(dbPublicIp.getFloating_ip_address());
			portalPublicIp.setGateway_ip((String) kvPair.getPairVal("gateway_ip"));
			portalPublicIp.setIp(dbPublicIp.getFloating_ip_address());
			portalPublicIp.setMask(Integer.valueOf((String) kvPair.getPairVal("mask")));
			portalPublicIp.setName(dbPublicIp.getName());
			portalPublicIp.setPublic_ip_uuid(public_ip_resource_id);
			portalPublicIp.setPublicip_uuid(dbPublicIp.getPublic_ip_id());
			portalPublicIp.setBind_resource_id(resourceIds.getIgwRouterId());
			portalPublicIp.setBind_resource_type("igw");
			List<DBPublicResources> publicIpResList = UnicomPortalOperator.queryPublicResourcesList();
			for (DBPublicResources publicResource : publicIpResList) {
				long beginIp = NFVOUtils.ipToLong(publicResource.getBegin_ip());
				long endIp = NFVOUtils.ipToLong(publicResource.getEnd_ip());
				long floatingIp = NFVOUtils.ipToLong(dbPublicIp.getFloating_ip_address());
				if (beginIp <= floatingIp && floatingIp <= endIp) {
					portalPublicIp.setPublic_resource_id(publicResource.getId());
					break;
				}
			}
			
			portalPublicIp.setUpdated_at(new Date());
			portalPublicIp.setCreator_id(CREATOR_ID);
			portalPublicIp.setOwner_id(OWNER_ID);
			result = UnicomPortalOperator.createPublicIp(portalPublicIp);
			resourceIds.setPublicIpId(result);
			resourceIds.setPublicIpUUID(dbPublicIp.getPublic_ip_id());
			resourceIds.setPublicIp(dbPublicIp.getFloating_ip_address());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}
	
	public static String queryPublicIpResourceList(String ip)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("queryPublicIpResourceList");

		try
		{
			HashMap<?, ?> respMap = VimRestClient.get(restUrl, HashMap.class, ArrayList.class);
			if (!NFVOUtils.isEmpty(respMap.get("NeutronError")))
			{
				LOGGER.error("createNetwork error :" + respMap.get("NeutronError"));
				return null;
			}
			Collection<Map<?, ?>> thiredJSONArray = (Collection<Map<?, ?>>) respMap.get("publicipresources");
			Iterator<Map<?, ?>> iterator = thiredJSONArray.iterator();
			while (iterator.hasNext())
			{
				Map<?,?> map = iterator.next();
				String ipResource = (String) map.get("ip");
				if(ip.equals(ipResource)){
					return (String) map.get("id");
				}
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static ResourceIds createRoutingTable(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createRoutingTable");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			String routingTableName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			jsonObject.put("name", routingTableName);
			String router_id = (String) kvPair.getPairVal("router_id");
			if(NFVOUtils.isEmpty(router_id))
			{
				jsonObject.put("router_id", resourceIds.getVpcUUID());
			}

			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("routingtable", jsonObject);
			restUrl.setReqjson(outJsonObject);
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("routingtable");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createRoutingTable error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBRoutingTable dbRoutingTable = new DBRoutingTable();
			dbRoutingTable.setName(routingTableName);
			dbRoutingTable.setRouting_table_id((String) secondLevelMap.get("id"));
			dbRoutingTable.setRouting_table_index((Integer) secondLevelMap.get("index"));
			dbRoutingTable.setStatus((String) secondLevelMap.get("status"));
			dbRoutingTable.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbRoutingTable.setVpc_id((String) secondLevelMap.get("router_id"));
			DBOperator.createRoutingTable(dbRoutingTable);

			// unicom portal DB start sz
			DBRouteTables dbRouteTables = new DBRouteTables();
			dbRouteTables.setRoute_table_uuid((String) secondLevelMap.get("id"));
			dbRouteTables.setName(routingTableName);
			dbRouteTables.setRouter_id((Integer) secondLevelMap.get("index"));
			dbRouteTables.setCreated_at(new Date());
			dbRouteTables.setUpdated_at(new Date());
			dbRouteTables.setOwner_id(OWNER_ID);
			dbRouteTables.setCreator_id(CREATOR_ID);
			dbRouteTables.setRouter_id(resourceIds.getVpcId());
			result = UnicomPortalOperator.createRouteTables(dbRouteTables);
			// unicom portal DB end sz
			
			resourceIds.setRoutingTableUUID(dbRoutingTable.getRouting_table_id());
			resourceIds.setRoutingTableId(result);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createRoutingRule(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createRoutingRule");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String routingRuleName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			jsonObject.put("name", routingRuleName);
			String gwrouter_id = (String) kvPair.getPairVal("gwrouter_id");
			String routingtable_id = (String) kvPair.getPairVal("routingtable_id");
			if(NFVOUtils.isEmpty(gwrouter_id))
			{
			   jsonObject.put("gwrouter_id", resourceIds.getIgwRouterUUID());
			}
			
			if(NFVOUtils.isEmpty(routingtable_id))
			{
				jsonObject.put("routingtable_id", resourceIds.getRoutingTableUUID());
			}

			jsonObject.put("prefix", kvPair.getPairVal("prefix"));
			jsonObject.put("nexthop", kvPair.getPairVal("nexthop"));
			jsonObject.put("type", kvPair.getPairVal("type"));

			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("routingrule", jsonObject);
			restUrl.setReqjson(outJsonObject);
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("routingrule");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createRoutingRule error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBRoutingRule dbRoutingRule = new DBRoutingRule();
			dbRoutingRule.setName(routingRuleName);
			dbRoutingRule.setRouting_table_id((String) secondLevelMap.get("routingtable_id"));
			dbRoutingRule.setRouting_rule_id((String) secondLevelMap.get("id"));
			dbRoutingRule.setType(((String) kvPair.getPairVal("type")));
			dbRoutingRule.setStatus((String) secondLevelMap.get("status"));
			dbRoutingRule.setNexthop(((String) kvPair.getPairVal("nexthop")));
			dbRoutingRule.setPrefix(((String) kvPair.getPairVal("prefix")));
			dbRoutingRule.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			DBOperator.createRoutingRule(dbRoutingRule);
			// unicom portal DB start sz routing_rules
			DBRouterRules dbRouterRules = new DBRouterRules();
			dbRouterRules.setName(routingRuleName);
			dbRouterRules.setPrefix(((String) kvPair.getPairVal("prefix")));
			dbRouterRules.setNext_hop(((String) kvPair.getPairVal("nexthop")));
			dbRouterRules.setRoute_table_id(resourceIds.getRoutingTableId());
			dbRouterRules.setTarget_type("igw");
			dbRouterRules.setTarget(1);// target 入什么数据？
			dbRouterRules.setTarget_name(resourceIds.getIgwRouterName());
			dbRouterRules.setCreated_at(new Date());
			dbRouterRules.setUpdated_at(new Date());
			dbRouterRules.setRouting_rule_uuid(dbRoutingRule.getRouting_rule_id());
			dbRouterRules.setRouter_id(resourceIds.getVpcId());
			result = UnicomPortalOperator.createRouterRules(dbRouterRules);
			// unicom portal DB end sz
			
			resourceIds.setRoutingRuleId(result);
			resourceIds.setRoutingRuleUUID(dbRoutingRule.getRouting_rule_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createNetwork(ResourceIds resourceIds)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createNetwork");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		JSONObject jsonObject = new JSONObject();
		String networkName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
		jsonObject.put("name", networkName);
		jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
		jsonObject.put("shared", kvPair.getPairVal("shared"));
		JSONObject outJsonObject = new JSONObject();
		outJsonObject.put("network", jsonObject);
		restUrl.setReqjson(outJsonObject);
		try
		{
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("network");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createNetwork error :" + respMap.get("NeutronError"));
				return resourceIds;
			}
			DBNetwork dbNetwork = new DBNetwork();
			dbNetwork.setNetwork_id((String) secondLevelMap.get("id"));
			dbNetwork.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbNetwork.setStatus(((String) secondLevelMap.get("status")));
			dbNetwork.setName(networkName);
			dbNetwork.setAdmin_state_up(((Boolean) secondLevelMap.get("admin_state_up")));
			dbNetwork.setShared((Boolean) secondLevelMap.get("shared"));
			dbNetwork.setSubnets((String) kvPair.getPairVal("subnets"));
			dbNetwork.setProvider_network_type((String) secondLevelMap.get("provider:network_type"));
			dbNetwork.setProvider_physical_network("default");
			dbNetwork.setProvider_segmentation_id(String.valueOf(secondLevelMap.get("provider:segmentation_id")));
			dbNetwork.setRouter_external((Boolean) secondLevelMap.get("router:external"));
			DBOperator.createNetwork(dbNetwork);

			// unicom portal DB start sz
			DBNetworks dbNetworks = new DBNetworks();
			dbNetworks.setNetwork_uuid(dbNetwork.getNetwork_id());
			dbNetworks.setName(dbNetwork.getName());
			dbNetworks.setShared(dbNetwork.getShared());
			dbNetworks.setStatus(dbNetwork.getStatus());
			dbNetworks.setSegmentation_id((Integer) secondLevelMap.get("provider:segmentation_id"));
			dbNetworks.setExternal_net(dbNetwork.getRouter_external());
			dbNetworks.setNetwork_type(dbNetwork.getProvider_network_type());
			dbNetworks.setCreator_id(CREATOR_ID);
			dbNetworks.setOwner_id(OWNER_ID);
			dbNetworks.setPhysical_network(dbNetwork.getProvider_physical_network());
			dbNetworks.setCreated_at(new Date());
			dbNetworks.setUpdated_at(new Date());
			int id = UnicomPortalOperator.createNetworks(dbNetworks);
			// unicom portal DB end sz
			
			DBRouterNetworks dbRouterNetworks = new DBRouterNetworks();
			dbRouterNetworks.setCreated_at(new Date());
			dbRouterNetworks.setCreator_id(CREATOR_ID);
			dbRouterNetworks.setInterface_uuid(resourceIds.getVpcUUID());
			dbRouterNetworks.setNetwork_id(id);
			dbRouterNetworks.setOwner_id(OWNER_ID);
			dbRouterNetworks.setRouter_id(resourceIds.getVpcId());
			dbRouterNetworks.setUpdated_at(new Date());
			UnicomPortalOperator.createRouterNetwork(dbRouterNetworks);
			
			resourceIds.setNetworkId(id);
			resourceIds.setNetworkUUID(dbNetwork.getNetwork_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}
	
	

	public static ResourceIds createSubnet(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createSubnet");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String subnetName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
			jsonObject.put("name", subnetName);
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			String network_id = (String) kvPair.getPairVal("network_id");
			if(NFVOUtils.isEmpty(network_id))
			{
				jsonObject.put("network_id", resourceIds.getNetworkUUID());
			}

			jsonObject.put("gateway_ip", kvPair.getPairVal("gateway_ip"));
			jsonObject.put("ip_version", kvPair.getPairVal("ip_version"));
			jsonObject.put("cidr", kvPair.getPairVal("cidr"));
			jsonObject.put("enable_dhcp", Boolean.valueOf((String) kvPair.getPairVal("enable_dhcp")));
			// jsonObject.put("dns_nameservers",
			// kvPair.getPairVal("dns_nameservers"));

			// JSONObject poolJsonObject = new JSONObject();
			// poolJsonObject.put("start",
			// kvPair.getPairVal("allocation_pools_start"));
			// poolJsonObject.put("end",
			// kvPair.getPairVal("allocation_pools_end"));
			// jsonObject.put("allocation_pools", poolJsonObject);

			// JSONObject hostRouteJsonObject = new JSONObject();
			// hostRouteJsonObject.put("destination",
			// kvPair.getPairVal("host_routes_destination"));
			// hostRouteJsonObject.put("nexthop",
			// kvPair.getPairVal("host_routes_nexthop"));
			// jsonObject.put("host_routes", hostRouteJsonObject);

			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("subnet", jsonObject);
			restUrl.setReqjson(outJsonObject);

			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("subnet");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createSubnet error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBSubnet dbSubnet = new DBSubnet();
			dbSubnet.setAllocation_pools_end((String) kvPair.getPairVal("allocation_pools_end"));
			dbSubnet.setAllocation_pools_start((String) kvPair.getPairVal("allocation_pools_start"));
			dbSubnet.setCidr((String) kvPair.getPairVal("cidr"));
			dbSubnet.setDns_nameservers((String) kvPair.getPairVal("dns_nameservers"));
			dbSubnet.setEnable_dhcp((Boolean) secondLevelMap.get("enable_dhcp"));
			dbSubnet.setGateway_ip((String) kvPair.getPairVal("gateway_ip"));
			dbSubnet.setHost_routes_destination((String) kvPair.getPairVal("host_routes_destination"));
			dbSubnet.setHost_routes_nexthop((String) kvPair.getPairVal("host_routes_nexthop"));
			dbSubnet.setIp_version((String) kvPair.getPairVal("ip_version"));
			dbSubnet.setIpv6_address_mode((String) kvPair.getPairVal("ipv6_address_mode"));
			dbSubnet.setIpv6_ra_mode((String) kvPair.getPairVal("ipv6_ra_mode"));
			dbSubnet.setName(subnetName);
			dbSubnet.setNetwork_id((String) secondLevelMap.get("network_id"));
			dbSubnet.setSubnet_id((String) secondLevelMap.get("id"));
			dbSubnet.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			DBOperator.createSubnet(dbSubnet);

			// unicom portal DB start sz
			DBSubnetsWithBLOBs dbSubnets = new DBSubnetsWithBLOBs();
			dbSubnets.setSubnet_uuid((String) secondLevelMap.get("id"));
			dbSubnets.setName(subnetName);
			dbSubnets.setGateway_ip((String) kvPair.getPairVal("gateway_ip"));
			dbSubnets.setCidr((String) kvPair.getPairVal("cidr"));
			dbSubnets.setIp_version(dbSubnet.getIp_version());
			dbSubnets.setNetwork_id(resourceIds.getNetworkId());
			dbSubnets.setEnable_dhcp((Boolean) secondLevelMap.get("enable_dhcp"));
			String start = (String) kvPair.getPairVal("allocation_pools_start");
			String end = (String) kvPair.getPairVal("allocation_pools_end");
			String allocationPools = "[{\"start\": \"" + start + "\", \"end\": \"" + end + "\"}]";
			dbSubnets.setAllocation_pools(allocationPools);
			dbSubnets.setCreated_at(new Date());
			dbSubnets.setCreator_id(CREATOR_ID);
			dbSubnets.setOwner_id(OWNER_ID);
			result = UnicomPortalOperator.createSubnets(dbSubnets);
			// unicom portal DB end
			
			resourceIds.setSubnetId(result);
			resourceIds.setSubnetUUID(dbSubnet.getSubnet_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createVpcSubnet(ResourceIds resourceIds)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createVpcSubnet");
		try
		{
			restUrl.getPairs().add("vpc_id", resourceIds.getVpcUUID());
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("subnet_id", resourceIds.getSubnetUUID());
			restUrl.setReqjson(jsonObject);

			HashMap<?, ?> respMap = VimRestClient.put(restUrl, HashMap.class, ArrayList.class);
			if (!NFVOUtils.isEmpty(respMap.get("NeutronError")))
			{
				LOGGER.error("createSubnet error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}
	
	public static ResourceIds createSubnetRoutingTable(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createSubnetRoutingTable");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String subnetRoutingTableName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			jsonObject.put("name", subnetRoutingTableName);
			String subnetId = (String) kvPair.getPairVal("subnet_id");
			if (NFVOUtils.isEmpty(subnetId))
			{
				jsonObject.put("subnet_id", resourceIds.getSubnetUUID());
			}

			String routingtable_id = (String) kvPair.getPairVal("routingtable_id");
			if (NFVOUtils.isEmpty(routingtable_id))
			{
				jsonObject.put("routingtable_id", resourceIds.getRoutingTableUUID());
			}

			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("subnettoroutingtable", jsonObject);
			restUrl.setReqjson(outJsonObject);
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("subnettoroutingtable");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createSubnetRoutingTable error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBSubnetRoutingTable dbSubnetRoutingTable = new DBSubnetRoutingTable();
			dbSubnetRoutingTable.setTenant_id((String) secondLevelMap.get("tenant_id"));
			dbSubnetRoutingTable.setName(subnetRoutingTableName);
			dbSubnetRoutingTable.setRouting_table_id((String) secondLevelMap.get("routingtable_id"));
			dbSubnetRoutingTable.setAdmin_state_up((Boolean) secondLevelMap.get("admin_state_up"));
			dbSubnetRoutingTable.setSubnet_id((String) secondLevelMap.get("subnet_id"));
			dbSubnetRoutingTable.setStatus((String) secondLevelMap.get("status"));
			dbSubnetRoutingTable.setSubnet_routing_table_id((String) secondLevelMap.get("id"));
			DBOperator.createSubnetRoutingTable(dbSubnetRoutingTable);

			// unicom portal DB start sz
			DBSubnetRouteTables dbSubnetRouteTables = new DBSubnetRouteTables();
			dbSubnetRouteTables.setSubnet_route_table_uuid((String) secondLevelMap.get("routingtable_id"));
			dbSubnetRouteTables.setName(subnetRoutingTableName);
			dbSubnetRouteTables.setCreated_at(new Date());
			dbSubnetRouteTables.setSubnet_id(resourceIds.getSubnetId());
			dbSubnetRouteTables.setRoute_table_id(resourceIds.getRoutingTableId());
			dbSubnetRouteTables.setUpdated_at(new Date());
			result = UnicomPortalOperator.createSubnetRouteTables(dbSubnetRouteTables);
			// unicom portal DB end
			
			resourceIds.setSubnetRoutingTableId(result);
			resourceIds.setSubnetRoutingTableUUID(dbSubnetRoutingTable.getSubnet_routing_table_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static void createServer(ResourceIds resourceIds)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createServer");
		KVPair<String, Object> kvPair = restUrl.getPairs();

		try
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			jsonObject.put("imageRef", kvPair.getPairVal("imageRef"));
			jsonObject.put("flavorRef", kvPair.getPairVal("flavorRef"));
			jsonObject.put("name", kvPair.getPairVal("name"));
			jsonObject.put("max_count", kvPair.getPairVal("max_count"));
			jsonObject.put("min_count", kvPair.getPairVal("min_count"));
			Map<String, Object> networkMap = new HashMap<String, Object>();
			String networkUUID = (String) kvPair.getPairVal("network_uuid");
			if(NFVOUtils.isEmpty(networkUUID))
			{
				List<DBNetwork> networkList = DBOperator.queryNetworkList();
				networkUUID = networkList.get(0).getNetwork_id();
			}
			networkMap.put("uuid", networkUUID);
			JSONArray networkArray = new JSONArray();
			networkArray.add(networkMap);
			jsonObject.put("networks", networkArray);
			Map<String, Object> securityGroupMap = new HashMap<String, Object>();
			securityGroupMap.put("name", kvPair.getPairVal("security_groups_name"));
			JSONArray securityGroupArray = new JSONArray();
			securityGroupArray.add(securityGroupMap);
			jsonObject.put("security_groups", securityGroupArray);
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("server", jsonObject);
			restUrl.setReqjson(outJsonObject.toString());
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("server");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createServer error :" + respMap.get("NeutronError"));
				return;
			}

			DBServer dbServer = new DBServer();
			dbServer.setName((String) kvPair.getPairVal("name"));
			dbServer.setNetworks_uuid(networkUUID);
			dbServer.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbServer.setSecurity_groups_name((String) kvPair.getPairVal("security_groups_name"));
			dbServer.setFlavor_id((String) kvPair.getPairVal("flavorRef"));
			dbServer.setDelete_on_termination(Boolean.valueOf((String) kvPair.getPairVal("delete_on_termination")));
			dbServer.setGuest_format((String) kvPair.getPairVal("guest_format"));
			dbServer.setSource_type((String) kvPair.getPairVal("source_type"));
			dbServer.setServer_id((String) secondLevelMap.get("id"));
			dbServer.setDisk_config((String) secondLevelMap.get("OS-DCF:diskConfig"));
			DBOperator.createServer(dbServer);
			
			insertUnicomPortalInstance(respMap, kvPair, resourceIds);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static HashMap<?, ?> createServer(RestUrl restUrl, ResourceIds resourceIds)
	{
		KVPair<String, Object> kvPair = restUrl.getPairs();
		HashMap<?, ?> respMap = null;
		try
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
			String imageRef = (String) kvPair.getPairVal("imageRef");
			jsonObject.put("imageRef", imageRef);
			String flavorRef = (String) kvPair.getPairVal("flavorRef");
			jsonObject.put("flavorRef", flavorRef);
			jsonObject.put("name", kvPair.getPairVal("name"));
			jsonObject.put("max_count", kvPair.getPairVal("max_count"));
			jsonObject.put("min_count", kvPair.getPairVal("min_count"));
			Map<String, Object> networkMap = new HashMap<String, Object>();
			String networkUUID = (String) kvPair.getPairVal("network_uuid");
			if(NFVOUtils.isEmpty(networkUUID))
			{
				List<DBNetwork> networkList = DBOperator.queryNetworkList();
				networkUUID = networkList.get(0).getNetwork_id();
			}
			networkMap.put("uuid", networkUUID);
			networkMap.put("fixed_ip", (String) kvPair.getPairVal("fixed_ip"));
			JSONArray networkArray = new JSONArray();
			networkArray.add(networkMap);
			jsonObject.put("networks", networkArray);
			
			Map<String, Object> blockDeviceMap = new HashMap<String, Object>();
			blockDeviceMap.put("source_type", kvPair.getPairVal("source_type"));
			blockDeviceMap.put("delete_on_termination", Boolean.valueOf((String) kvPair.getPairVal("delete_on_termination")));
			blockDeviceMap.put("boot_index", Integer.valueOf((String) kvPair.getPairVal("boot_index")));
			blockDeviceMap.put("destination_type", kvPair.getPairVal("destination_type"));
			blockDeviceMap.put("uuid", kvPair.getPairVal("volume_uuid"));
			JSONArray blockDeviceArray = new JSONArray();
			blockDeviceArray.add(blockDeviceMap);
			jsonObject.put("block_device_mapping_v2", blockDeviceArray);
			
			Map<String, Object> securityGroupMap = new HashMap<String, Object>();
			securityGroupMap.put("name", kvPair.getPairVal("security_groups_name"));
			JSONArray securityGroupArray = new JSONArray();
			securityGroupArray.add(securityGroupMap);
			jsonObject.put("security_groups", securityGroupArray);
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("server", jsonObject);
			restUrl.setReqjson(outJsonObject.toString());
			respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("server");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createServer error :" + respMap.get("NeutronError"));
				System.out.println(respMap.get("NeutronError"));
				return null;
			}

			DBServer dbServer = new DBServer();
			dbServer.setName((String) kvPair.getPairVal("name"));
			dbServer.setNetworks_uuid(networkUUID);
			dbServer.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbServer.setSecurity_groups_name((String) kvPair.getPairVal("security_groups_name"));
			dbServer.setFlavor_id((String) kvPair.getPairVal("flavorRef"));
			dbServer.setDelete_on_termination(Boolean.valueOf((String) kvPair.getPairVal("delete_on_termination")));
			dbServer.setGuest_format((String) kvPair.getPairVal("guest_format"));
			dbServer.setSource_type((String) kvPair.getPairVal("source_type"));
			dbServer.setServer_id((String) secondLevelMap.get("id"));
			dbServer.setDisk_config((String) secondLevelMap.get("OS-DCF:diskConfig"));
			DBOperator.createServer(dbServer);
			
			insertUnicomPortalInstance(respMap, kvPair, resourceIds);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return respMap;
	}

	private static void insertUnicomPortalInstance(HashMap<?, ?> resp, KVPair<String, Object> initRequestMap, ResourceIds resourceIds)
	{
		try
		{
			Map<?, ?> serverDetail = (Map<?, ?>) NFVOUtils.getMapValByKey(resp, "server", null,
					SysConst.MapQueryCond.LSEQT);
			DBInstances dbInstance = new DBInstances();
			dbInstance.setCreated_at(new Date());
			dbInstance.setUpdated_at(new Date());
			dbInstance.setInstance_uuid((String) serverDetail.get("id"));
			dbInstance.setDisplay_name((String) initRequestMap.getPairVal("name"));
			dbInstance.setCreator_id(CREATOR_ID);
			dbInstance.setOwner_id(OWNER_ID);
			dbInstance.setIops(0);
			
			//for lack of info of vm, should query vm's detail after creating vm by invoking query api . 
			Thread.sleep(1000);
			Map responseMap = getServerAfterCreating(dbInstance.getInstance_uuid());
			Map serverMap = (Map) responseMap.get("server");
//			dbInstance.setVm_state((String) serverMap.get("OS-EXT-SRV-ATTR:vm_state"));
			dbInstance.setAvailability_zone((String) serverMap.get("OS-EXT-AZ:availability_zone"));
			String computeNodeName = String.valueOf(serverMap.get("OS-EXT-SRV-ATTR:host"));
			System.out.println(serverMap.toString());
			DBComputeNodes node = UnicomPortalOperator.queryComputeNodeByHostName(computeNodeName);
			dbInstance.setCompute_node_id(null==node?null:node.getId());
			
			String imageRef = (String) initRequestMap.getPairVal("imageRef");
			String flavorRef = (String) initRequestMap.getPairVal("flavorRef");
			DBImages image = UnicomPortalOperator.queryImagesByUUID(imageRef);
			if(null!=image)
			{
				dbInstance.setImage_id(image.getId());
			}
			
			DBFlavors flavor = UnicomPortalOperator.queryFlavorByName(flavorRef);
			if(null!=flavor)
			{
				dbInstance.setFlavor_id(flavor.getId());
				dbInstance.setMemory_mb(flavor.getMemory_mb());
				dbInstance.setVcpus(flavor.getVcpus());
				dbInstance.setRoot_gb(flavor.getRoot_gb());
			}
			
			UnicomPortalOperator.createInstance(dbInstance);
			
			//insert subnet and ip address into tables ports to show vm ip in unicom web portal  
			insertUnicomPortalPort(serverMap, dbInstance.getId(), resourceIds);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
	
	private static void insertUnicomPortalPort(Map serverMap, int instanceId, ResourceIds resourceIds) {
		DBPorts port = new DBPorts();
		try {
			List<Map<String, String>> fixIpMapList = new ArrayList<Map<String, String>>();
			Map<?, ?> addressMap = (Map<?, ?>) serverMap.get("addresses");
			for (Map.Entry<?, ?> entry : addressMap.entrySet()) {
				List<?> addressList = (List<?>) entry.getValue();
				if (null != addressList) {
					Map<?, ?> targetInfo = (Map<?, ?>) addressList.get(0);
					String subnetName = resourceIds.getVpcNamePrefix() + "subnet";
					DBSubnets subnet = UnicomPortalOperator.querySubnetByName(subnetName);
					System.out.println("*********************************networkID**************8888" +subnetName + " " +subnet.getNetwork_id());
					Map<String, String> fixIpMap = new HashMap<String,String>();
					fixIpMap.put("subnet_id", null == subnet ? "" : subnet.getSubnet_uuid());
					fixIpMap.put("ip_address", (String) targetInfo.get("addr"));
					fixIpMapList.add(fixIpMap);
					JSONArray fixIpJson = JSONArray.fromObject(fixIpMapList);
					port.setFixed_ips(fixIpJson.toString());
					port.setMac_address((String) targetInfo.get("OS-EXT-IPS-MAC:mac_addr"));
					port.setSubnet_id(subnet.getId());
					port.setNetwork_id(subnet.getNetwork_id());
				}
			}
			port.setCreated_at(new Date());
			port.setCreator_id(CREATOR_ID);
			port.setOwner_id(OWNER_ID);
			port.setBandwidth_rx(-1);
			port.setBandwidth_tx(-1);
			port.setMtu(-1);
			port.setInstance_id(instanceId);
			UnicomPortalOperator.createPort(port);
		} catch (NFVOException e) {
			e.printStackTrace();
		}
	}
	
	private static Map getServerAfterCreating(String serverUUID){
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("getServerDetail");
		restUrl.getPairs().add("server_id", serverUUID);
		HashMap<?, ?> serverDetail = InstanceOperator.getServerDetail(restUrl);
		return serverDetail;
	}
	
	public static Result deleteServer(String serverId)
	{
		Result httpStatus = null;
		try
		{
			RestUrl restUrl = RestConfigHandler.getInstance().getUrl("deleteServer");
			KVPair<String, Object> kvPair = restUrl.getPairs();
			kvPair.add("server_id", serverId);
			restUrl.setPairs(kvPair);
			httpStatus = VimRestClient.deleteForNoResp(restUrl);
			if (!httpStatus.getCode().equals("204"))
			{
				LOGGER.error("deleteServer error : " + httpStatus.getMessage());
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return httpStatus;
	}

	public static HashMap<?, ?> getServerDetail(RestUrl restUrl)
	{
		HashMap<?, ?> respMap = null;
		try
		{
			respMap = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return respMap;
	}

	public static void createNat(ResourceIds resourceIds)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createNat");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
		String natName = resourceIds.getVpcNamePrefix() + (String) kvPair.getPairVal("nat_name");
		jsonObject.put("name", natName);
		jsonObject.put("publicip_id", resourceIds.getPublicIpUUID());
		jsonObject.put("publicip", resourceIds.getPublicIp());
		jsonObject.put("type", kvPair.getPairVal("type"));
		jsonObject.put("igwgateway_id", resourceIds.getIgwRouterUUID());
		jsonObject.put("protocol", kvPair.getPairVal("protocol"));
		jsonObject.put("fixed_ip", kvPair.getPairVal("fixed_ip"));
		jsonObject.put("source_port", kvPair.getPairVal("source_port"));
		jsonObject.put("destination_port", kvPair.getPairVal("destination_port"));
		JSONObject outJsonObject = new JSONObject();
		outJsonObject.put("nat", jsonObject);
		restUrl.setReqjson(outJsonObject);

		try
		{
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("nat");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createNat error :" + respMap.get("NeutronError"));
				return;
			}
			DBNat dbNat = new DBNat();
			dbNat.setDestination_port((String) kvPair.getPairVal("destination_port"));
			dbNat.setFixed_ip((String) kvPair.getPairVal("fixed_ip"));
			dbNat.setIgw_router_id(resourceIds.getIgwRouterUUID());
			dbNat.setNat_id((String) secondLevelMap.get("id"));
			dbNat.setProtocol((String) kvPair.getPairVal("protocol"));
			dbNat.setPublic_ip(resourceIds.getPublicIp());
			dbNat.setPublic_ip_id(resourceIds.getPublicIpUUID());
			dbNat.setSource_port((String) kvPair.getPairVal("source_port"));
			dbNat.setStatus("ACTIVE");
			dbNat.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbNat.setType((String) kvPair.getPairVal("type"));
			DBOperator.createNat(dbNat);
			
			DBNats unicomNat = new DBNats();
			unicomNat.setName(natName);
			unicomNat.setAdmin_state_up(true);
			unicomNat.setCreated_at(new Date());
			unicomNat.setCreator_id(CREATOR_ID);
			unicomNat.setDestination_port(Integer.valueOf(dbNat.getDestination_port()));
			unicomNat.setFixed_ip(dbNat.getFixed_ip());
			unicomNat.setNat_uuid(dbNat.getNat_id());
			unicomNat.setInternet_id(String.valueOf(resourceIds.getIgwRouterId()));
			unicomNat.setOwner_id(OWNER_ID);
			unicomNat.setProtocol(dbNat.getProtocol());
			unicomNat.setPublicip_id(resourceIds.getPublicIpId());
			unicomNat.setSource_port(Integer.valueOf(dbNat.getSource_port()));
			unicomNat.setStatus("ACTIVE");
			unicomNat.setType(dbNat.getType());
			unicomNat.setUpdated_at(new Date());
			UnicomPortalOperator.createNat(unicomNat);
			
			resourceIds.setNatId(unicomNat.getId());
			resourceIds.setNatUUID(dbNat.getNat_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static ResourceIds createAcl(ResourceIds resourceIds)
	{
		int result = 0;
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createAcl");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String aclTableName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name");
			jsonObject.put("name", aclTableName);
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
		    
			jsonObject.put("router_id", resourceIds.getVpcUUID());
			
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("acltable", jsonObject);
			restUrl.setReqjson(outJsonObject);

			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("acltable");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createAcl error :" + respMap.get("NeutronError"));
				return resourceIds;
			}
			DBAcl dbAcl = new DBAcl();
			String acl_id = (String) secondLevelMap.get("id");
			dbAcl.setAcl_id(acl_id);
			dbAcl.setStatus((String) secondLevelMap.get("status"));
			dbAcl.setTenant_id((String) kvPair.getPairVal("tenant_id"));
			dbAcl.setName(aclTableName);
			dbAcl.setVpc_id(resourceIds.getVpcUUID());
			DBOperator.createAcl(dbAcl);

			// insert unicom portal db
			DBAclTables portalAclTable = new DBAclTables();
			portalAclTable.setAcl_table_uuid(dbAcl.getAcl_id());
			portalAclTable.setCreated_at(new Date());
			portalAclTable.setName(dbAcl.getName());
			portalAclTable.setRouter_id(resourceIds.getVpcId());
			portalAclTable.setStatus(dbAcl.getStatus());
			portalAclTable.setUpdated_at(new Date());
			portalAclTable.setOwner_id(OWNER_ID);
			portalAclTable.setCreator_id(CREATOR_ID);
			result = UnicomPortalOperator.createAclTable(portalAclTable);
			
			resourceIds.setAclId(result);
			resourceIds.setAclUUID(dbAcl.getAcl_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	public static ResourceIds createAclRule(ResourceIds resourceIds) 
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createAclRule");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("name", kvPair.getPairVal("name"));
		jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
		String acl_id = (String) kvPair.getPairVal("acltable_id");
		if (NFVOUtils.isEmpty(acl_id))
		{
			jsonObject.put("acltable_id", resourceIds.getAclUUID());
		}
		jsonObject.put("direction", kvPair.getPairVal("direction"));
		jsonObject.put("prot_range_min", kvPair.getPairVal("port_range_min"));
		jsonObject.put("port_range_max", kvPair.getPairVal("port_range_max"));
		jsonObject.put("priority", kvPair.getPairVal("priority"));
		jsonObject.put("prefix", kvPair.getPairVal("prefix"));
		jsonObject.put("strategy", kvPair.getPairVal("strategy"));
		jsonObject.put("protocol", kvPair.getPairVal("protocol"));
		JSONObject outJsonObject = new JSONObject();
		outJsonObject.put("aclrule", jsonObject);
		restUrl.setReqjson(outJsonObject);
		try
		{
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("aclrule");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createAclRule error :" + respMap.get("NeutronError"));
				return resourceIds;
			}
			DBAclRule dbAclRule = new DBAclRule();
			String aclrule_id = (String) secondLevelMap.get("id");
			dbAclRule.setAcl_rule_id(aclrule_id);

			dbAclRule.setName(((String) kvPair.getPairVal("name")));
			dbAclRule.setTenant_id(((String) kvPair.getPairVal("tenant_id")));
			dbAclRule.setAcl_id(resourceIds.getAclUUID());
			dbAclRule.setDirection(((String) kvPair.getPairVal("direction")));
			dbAclRule.setPort_range_min(((String) kvPair.getPairVal("port_range_min")));
			dbAclRule.setPort_range_max(((String) kvPair.getPairVal("port_range_max")));
			dbAclRule.setProtocol(((String) kvPair.getPairVal("protocol")));
			dbAclRule.setPrefix(((String) kvPair.getPairVal("prefix")));
			dbAclRule.setStrategy(((String) kvPair.getPairVal("strategy")));
			DBOperator.createAclRule(dbAclRule);

			// unicom portal DB start sz
			DBAclRules dbAclRules = new DBAclRules();
			dbAclRules.setAcl_rule_uuid(aclrule_id);
			dbAclRules.setName(((String) kvPair.getPairVal("name")) == null ? "" : ((String) kvPair.getPairVal("name")));
			dbAclRules.setDirection(((String) kvPair.getPairVal("direction")));
			dbAclRules.setPort_range_min(Integer.valueOf((String) kvPair.getPairVal("port_range_min")));
			dbAclRules.setPort_range_max(Integer.valueOf((String) kvPair.getPairVal("port_range_max")));
			dbAclRules.setProtocol(((String) kvPair.getPairVal("protocol")));
			dbAclRules.setPrefix(((String) kvPair.getPairVal("prefix")));
			dbAclRules.setStrategy(((String) kvPair.getPairVal("strategy")));
			dbAclRules.setCreated_at(new Date());
			dbAclRules.setAcl_table_id(resourceIds.getAclId());
			dbAclRules.setCreator_id(CREATOR_ID);
			dbAclRules.setOwner_id(OWNER_ID);
			UnicomPortalOperator.createAclRules(dbAclRules);
			// unicom portal DB end

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}
	
	public static void addVpcAclRules(ResourceIds resourceIds) {
		addAclRule("1", "inbound", "tcp", resourceIds);
		addAclRule("2", "outbound", "tcp", resourceIds);
		addAclRule("3", "inbound", "udp", resourceIds);
		addAclRule("4", "outbound", "udp", resourceIds);
		addAclRule("5", "inbound", "icmp", resourceIds);
		addAclRule("6", "outbound", "icmp", resourceIds);
	}
	
	private static ResourceIds addAclRule(String num, String direction, String protocol, ResourceIds resourceIds) 
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createAclRule");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		JSONObject jsonObject = new JSONObject();
		String aclName = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name") + num; 
		jsonObject.put("name", aclName);
		jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));
		String acl_id = (String) kvPair.getPairVal("acltable_id");
		if (NFVOUtils.isEmpty(acl_id))
		{
			jsonObject.put("acltable_id", resourceIds.getAclUUID());
		}
		jsonObject.put("direction", direction);
		jsonObject.put("prot_range_min", PORTMIN);
		jsonObject.put("port_range_max", PORTMAX);
		jsonObject.put("priority", PRIORITY);
		jsonObject.put("prefix", PREFIX);
		jsonObject.put("strategy",STRATEGY);
		jsonObject.put("protocol", protocol);
		JSONObject outJsonObject = new JSONObject();
		outJsonObject.put("aclrule", jsonObject);
		restUrl.setReqjson(outJsonObject);
		try
		{
			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("aclrule");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createAclRule error :" + respMap.get("NeutronError"));
				return resourceIds;
			}
			DBAclRule dbAclRule = new DBAclRule();
			String aclrule_id = (String) secondLevelMap.get("id");
			dbAclRule.setAcl_rule_id(aclrule_id);
			dbAclRule.setName(aclName);
			dbAclRule.setTenant_id(((String) kvPair.getPairVal("tenant_id")));
			dbAclRule.setAcl_id(resourceIds.getAclUUID());
			dbAclRule.setDirection(direction);
			dbAclRule.setPort_range_min(String.valueOf(PORTMIN));
			dbAclRule.setPort_range_max(String.valueOf(PORTMAX));
			dbAclRule.setProtocol(protocol);
			dbAclRule.setPrefix(PREFIX);
			dbAclRule.setPriority(PRIORITY);
			dbAclRule.setStrategy(STRATEGY);
			DBOperator.createAclRule(dbAclRule);

			// unicom portal DB start sz
			DBAclRules dbAclRules = new DBAclRules();
			dbAclRules.setAcl_rule_uuid(aclrule_id);
			dbAclRules.setName(aclName);
			dbAclRules.setDirection(direction);
			dbAclRules.setPort_range_min(PORTMIN);
			dbAclRules.setPort_range_max(PORTMAX);
			dbAclRules.setProtocol(protocol);
			dbAclRules.setPrefix(PREFIX);
			dbAclRules.setStrategy(STRATEGY);
			dbAclRules.setCreated_at(new Date());
			dbAclRules.setAcl_table_id(resourceIds.getAclId());
			dbAclRules.setCreator_id(CREATOR_ID);
			dbAclRules.setOwner_id(OWNER_ID);
			UnicomPortalOperator.createAclRules(dbAclRules);
			// unicom portal DB end

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}
	

	public static ResourceIds createSubnetAclTable(ResourceIds resourceIds)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createSubnetAclTable");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		try
		{
			JSONObject jsonObject = new JSONObject();
			String subnetAclTable = resourceIds.getVpcNamePrefix() + (String)kvPair.getPairVal("name"); 
			jsonObject.put("name", subnetAclTable);
			jsonObject.put("tenant_id", kvPair.getPairVal("tenant_id"));

			String subnet_id = (String) kvPair.getPairVal("subnet_id");
			if (NFVOUtils.isEmpty(subnet_id))
			{
				jsonObject.put("subnet_id", resourceIds.getSubnetUUID());
			}
			
			String aclId = (String) kvPair.getPairVal("acltable_id");;
			if (NFVOUtils.isEmpty(aclId))
			{
				jsonObject.put("acltable_id", resourceIds.getAclUUID());

			}
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("subnetacltable", jsonObject);
			restUrl.setReqjson(outJsonObject);

			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			Map<?, ?> secondLevelMap = (Map<?, ?>) respMap.get("subnetacltable");
			if (NFVOUtils.isEmpty(secondLevelMap))
			{
				LOGGER.error("createAclSubnet error :" + respMap.get("NeutronError"));
				return resourceIds;
			}

			DBAclSubnet dbAclSubnet = new DBAclSubnet();
			String acl_subnet_id = (String) secondLevelMap.get("id");
			dbAclSubnet.setAcl_subnet_id(acl_subnet_id);
			dbAclSubnet.setStatus((String) secondLevelMap.get("status"));
			dbAclSubnet.setName(subnetAclTable);
			dbAclSubnet.setTenant_id(((String) kvPair.getPairVal("tenant_id")));
			dbAclSubnet.setSubnet_id(resourceIds.getSubnetUUID());
			dbAclSubnet.setAcl_id(resourceIds.getAclUUID());
			DBOperator.createAclSubnet(dbAclSubnet);
			// insert unicom portal db
			DBSubnetAclTables portalSubnetAclTables = new DBSubnetAclTables();
			portalSubnetAclTables.setAcl_table_id(resourceIds.getAclId());
			portalSubnetAclTables.setCreated_at(new Date());
			portalSubnetAclTables.setName(dbAclSubnet.getName());
			portalSubnetAclTables.setSubnet_acl_table_uuid(dbAclSubnet.getAcl_subnet_id());
			portalSubnetAclTables.setSubnet_id(resourceIds.getSubnetId());
			portalSubnetAclTables.setUpdated_at(new Date());
			int id = UnicomPortalOperator.createSubnetAclTable(portalSubnetAclTables);
			
			resourceIds.setSubnetAclTableId(id);
			resourceIds.setSubnetAclTableUUID(dbAclSubnet.getAcl_subnet_id());
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resourceIds;
	}

	@SuppressWarnings("unchecked")
	public static void getHypervisors()
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("getHypervisors");

		try
		{
			String tenantId = (String) restUrl.getPairs().getPairVal("tenant_id");
			HashMap<?, ?> respMap = VimRestClient.get(restUrl, HashMap.class, ArrayList.class);
			if (!NFVOUtils.isEmpty(respMap.get("NeutronError")))
			{
				LOGGER.error("createNetwork error :" + respMap.get("NeutronError"));
				return;
			}
			Collection<Map<?, ?>> thiredJSONArray = (Collection<Map<?, ?>>) respMap.get("hypervisors");
			Iterator<Map<?, ?>> iterator = thiredJSONArray.iterator();
			while (iterator.hasNext())
			{
				Map<?, ?> hypervisorMap = iterator.next();
				DBHypervisor dbHypervisor = new DBHypervisor();
				dbHypervisor.setHypervisor_id(String.valueOf(hypervisorMap.get("id")));
				dbHypervisor.setTenant_id(tenantId);
				dbHypervisor.setHypervisor_hostname(String.valueOf(hypervisorMap.get("hypervisor_hostname")));
				DBOperator.createHypervisor(dbHypervisor);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void updateHypervisorById()
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("getHypervisorDetail");
		KVPair<String, Object> kvPair = restUrl.getPairs();
		String hypervisorId = (String) kvPair.getPairVal("hypervisor_id");
		try
		{
			HashMap<?, ?> respMap = VimRestClient.get(restUrl, HashMap.class, ArrayList.class);
			if (!NFVOUtils.isEmpty(respMap.get("NeutronError")))
			{
				LOGGER.error("createNetwork error :" + respMap.get("NeutronError"));
				return;
			}
			Map<?, ?> secondMap = (Map<?, ?>) respMap.get("hypervisor");

			DBHypervisor dbHypervisor = DBOperator.queryHypervisorById(hypervisorId);
			boolean createFlag = false;
			if (null == dbHypervisor)
			{
				dbHypervisor = new DBHypervisor();
				dbHypervisor.setHypervisor_id((String) secondMap.get("hypervisor_id"));
				dbHypervisor.setHypervisor_hostname((String) secondMap.get("hypervisor_hostname"));
				createFlag = true;
			}
			dbHypervisor.setCount((Integer) secondMap.get("count"));
			dbHypervisor.setCpu_info(((JSONObject) secondMap.get("cpu_info")).toString());
			dbHypervisor.setCurrent_workload((Integer) secondMap.get("current_workload"));
			dbHypervisor.setDisk_available_least((Integer) secondMap.get("disk_available_least"));
			dbHypervisor.setFree_disk_gb((Integer) secondMap.get("free_disk_gb"));
			dbHypervisor.setFree_ram_mb((Integer) secondMap.get("free_ram_mb"));
			dbHypervisor.setHost_ip((String) secondMap.get("host_ip"));
			dbHypervisor.setHypervisor_type((String) secondMap.get("hypervisor_type"));
			dbHypervisor.setHypervisor_version(String.valueOf(secondMap.get("hypervisor_version")));
			dbHypervisor.setLocal_gb((Integer) secondMap.get("local_gb"));
			dbHypervisor.setLocal_gb_used((Integer) secondMap.get("local_gb_used"));
			dbHypervisor.setMemory_mb((Integer) secondMap.get("memory_mb"));
			dbHypervisor.setMemory_mb_used((Integer) secondMap.get("memory_mb_used"));
			dbHypervisor.setRunning_vms((Integer) secondMap.get("running_vms"));
			dbHypervisor.setTenant_id((String) secondMap.get("tenant_id"));
			dbHypervisor.setVcpus((Integer) secondMap.get("vcpus"));
			dbHypervisor.setVcpus_used((Integer) secondMap.get("vcpus_used"));

			Map<?, ?> thirdMap = (Map<?, ?>) secondMap.get("service");
			dbHypervisor.setService_host((String) thirdMap.get("host"));
			dbHypervisor.setService_id(String.valueOf(thirdMap.get("id")));
			if (createFlag)
			{
				DBOperator.createHypervisor(dbHypervisor);
			} else
			{
				DBOperator.updateHypervisor(dbHypervisor);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void backUpInstance()
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("backUpInstance");
		try
		{
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("name", "my_first_backup");
			jsonObject.put("backup_type", "daily");
			jsonObject.put("rotation", 1);
			JSONObject outJsonObject = new JSONObject();
			outJsonObject.put("createBackup", jsonObject);
			restUrl.setReqjson(outJsonObject);

			HashMap<?, ?> respMap = VimRestClient.post(restUrl, HashMap.class, ArrayList.class);
			if (!NFVOUtils.isEmpty(respMap.get("computeFault")))
			{
				LOGGER.error("createNetwork error :" + respMap.get("computeFault"));
				return;
			}

		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
	public static List<Map> getHypervisorServers()
	{
		Map<?, ?> serverMap = new HashMap();
		Map<?, ?> serversDetail = getServersDetail();
		List<Map> serverDetailList = (List<Map>) NFVOUtils.getMapValByKey(serversDetail, "servers", null, null);

		Map<?, ?> hyperVisors = getHypervisorList();
		List<Map> hyperList = (List<Map>) NFVOUtils.getMapValByKey(hyperVisors, "hypervisors", null, null);
		List<Map> temp = null;
		for (Map hyperVisor : hyperList)
		{
			for (Map server : serverDetailList)
			{
				if (hyperVisor.get("hypervisor_hostname").equals(server.get("OS-EXT-SRV-ATTR:host")))
				{
					if (!hyperVisor.containsKey("servers"))
					{
						hyperVisor.put("servers", new ArrayList<Map>());
					}
					((List<Map>) hyperVisor.get("servers")).add(server);
				}
			}
		}
		LOGGER.debug("getHypervisorServers(): {}", hyperList);
		return hyperList;
	}

	public static HashMap<?, ?> getServersDetail()
	{
		HashMap<?, ?> resp = VimRestClient.restCall("getServersDetail", HashMap.class, ArrayList.class);
		LOGGER.debug("getServersDetail resp=" + resp);
		return resp;
	}

	public static HashMap<?, ?> getHypervisorList()
	{
		HashMap<?, ?> resp = VimRestClient.restCall("getHypervisors", HashMap.class, ArrayList.class);
		LOGGER.debug("getHypervisors resp={}", resp);
		return resp;
	}

	public static String cloneVolume(String volumeId, int size, String display_name)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("cloneVolume");
		restUrl.getPairs().add("source_volid", volumeId).add("volume_size", size).add("display_name", display_name);
		HashMap<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		return (String) NFVOUtils.getMapValByKey(resp, "id", null, null);
	}

	public static HashMap<?, ?> cloneServer(String volumeId, String name)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("cloneServer");
		restUrl.getPairs().add("volume_uuid", volumeId).add("name", name);
		HashMap<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		return resp;
	}

	public static void attacheBalance(String string, String string2)
	{

	}

	public static void createLoadBalance()
	{
		// 4. 创建pool
		// 5. 创建vips
	}

	public static HashMap<?, ?> getVolume(RestUrl restUrl)
	{
		HashMap<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		LOGGER.debug("getVolume resp={}", resp);
		return resp;
	}

	@SuppressWarnings("unchecked")
	public static String getPort(String ip)
	{
		HashMap<?, ?> resp = VimRestClient.restCall("getPorts", HashMap.class, ArrayList.class);
		List<Map<?, ?>> list = (List<Map<?, ?>>) NFVOUtils.getMapValByKey(resp, "ports", null,
				SysConst.MapQueryCond.LSEQT);
		if (!NFVOUtils.isEmpty(list))
		{
			for (Map<?, ?> map : list)
			{
				List<Map<?, ?>> ips = (List<Map<?, ?>>) NFVOUtils.getMapValByKey(map, "fixed_ips", null,
						SysConst.MapQueryCond.LSEQT);
				for (Map<?, ?> map2 : ips)
				{
					if (map2.get("ip_address").equals(ip))
					{
						return (String) NFVOUtils.getMapValByKey(map, "id", null, SysConst.MapQueryCond.LSEQT);
					}
				}
			}
		}
		return "";
	}

	public static void bindFirewall(String portId, String security_groups_uuid)
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("updatePorts");
		restUrl.getPairs().add("port_id", portId);
		if (NFVOUtils.isEmpty(security_groups_uuid))
		{
			restUrl.getPairs().add("security_groups_uuid", security_groups_uuid);
		}
		HashMap<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		LOGGER.debug("Bind firewall: {}", resp);
	}

	public static DBPool createPool(String name, String protocal, String subnetId) throws NFVOException
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createPool");
		restUrl.getPairs().add("pool_name", name).add("subnet_id", subnetId);
		Map<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);

		String poolId = (String) NFVOUtils.getMapValByKey(resp, "id", null, SysConst.MapQueryCond.LSEQT);

		DBPool dbpool = new DBPool();
		dbpool.setId(poolId);
		dbpool.setName(name);
		dbpool.setSubnet_id(subnetId);
		dbpool.setTenant_id((String) restUrl.getPairs().getPairVal("tenant_id"));
		dbpool.setStatus((String) NFVOUtils.getMapValByKey(resp, "status", null, SysConst.MapQueryCond.LSEQT));
		dbpool.setLb_method((String) restUrl.getPairs().getPairVal("lb_method"));
		DBOperator.insertPool(dbpool);
		return dbpool;
	}

	public static DBVip createVip(String name, String poolId, String subnetId, String protocal, String protocalport)
			throws NFVOException
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createVIP");
		restUrl.getPairs().add("vip_name", name).add("subnet_id", subnetId).add("pool_id", poolId);// .add("protocol_port",
																									// protocalport);
		Map<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		String vipId = (String) NFVOUtils.getMapValByKey(resp, "id", null, SysConst.MapQueryCond.LSEQT);
		DBVip dbvip = new DBVip();
		dbvip.setId(vipId);
		dbvip.setName(name);
		dbvip.setPool_id(poolId);
		dbvip.setProtocal((String) restUrl.getPairs().getPairVal("protocol"));
		dbvip.setProtocal_port((String) restUrl.getPairs().getPairVal("protocol_port"));
		dbvip.setSubnet_id(subnetId);
		dbvip.setTenant_id((String) restUrl.getPairs().getPairVal("tenant_id"));
		dbvip.setAdress((String) NFVOUtils.getMapValByKey(resp, "address", null, SysConst.MapQueryCond.LSEQT));
		dbvip.setStatus((String) NFVOUtils.getMapValByKey(resp, "status", null, SysConst.MapQueryCond.LSEQT));
		DBOperator.insertVip(dbvip);
		return dbvip;
	}

	public static void deleteVip(String vipId) throws NFVOException
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("deleteVIP");
		restUrl.getPairs().add("vip_id", vipId);
		VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		DBVip dbvip = new DBVip();
		dbvip.setId(vipId);
		DBOperator.deleteVip(dbvip);
	}

	public static DBMember createMember(String ip, String poolId, String string) throws NFVOException
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createMember");
		restUrl.getPairs().add("member_ip", ip).add("pool_id", poolId);
		Map<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		String memberId = (String) NFVOUtils.getMapValByKey(resp, "id", null, SysConst.MapQueryCond.LSEQT);

		DBMember dbmember = new DBMember();
		dbmember.setAddress((String) restUrl.getPairs().getPairVal("member_ip"));
		dbmember.setId(memberId);
		dbmember.setPool_id(poolId);
		dbmember.setProtocol_port((String) restUrl.getPairs().getPairVal("protocol_port"));
		dbmember.setStatus((String) NFVOUtils.getMapValByKey(resp, "status", null, SysConst.MapQueryCond.LSEQT));
		dbmember.setTenant_id((String) restUrl.getPairs().getPairVal("tenant_id"));
		DBOperator.insertMember(dbmember);
		return dbmember;
	}

	public static void deleteMember(String memberId) throws NFVOException
	{
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("deleteMember");
		restUrl.getPairs().add("member_id", memberId);
		VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);

		DBMember dbm = new DBMember();
		dbm.setId(memberId);
		DBOperator.deleteMember(dbm);
	}
	
	public static String createVolume(RestUrl restUrl)
	{
		KVPair<String, Object> kvPair = restUrl.getPairs();
		JSONObject innerJsonObject = new JSONObject();
		innerJsonObject.put("display_name", kvPair.getPairVal("display_name"));
		innerJsonObject.put("imageRef", kvPair.getPairVal("imageRef"));
		innerJsonObject.put("bootable", Boolean.valueOf((String) kvPair.getPairVal("bootable")));
		innerJsonObject.put("size", kvPair.getPairVal("size"));
		JSONObject outJsonObject = new JSONObject();
		outJsonObject.put("volume", innerJsonObject);
		restUrl.setReqjson(outJsonObject.toString());
		HashMap<?, ?> resp = VimRestClient.restCall(restUrl, HashMap.class, ArrayList.class);
		return (String) NFVOUtils.getMapValByKey(resp, "id", null, null);
	}
	
	public static DBJobs createJobs(Integer resource_id, String resource_name, String type){
		DBJobs job = new DBJobs();
		try 
		{
			job.setCreated_at(new Date());
			job.setUpdated_at(new Date());
			job.setState("SUCCESS");
			job.setCreator_id(CREATOR_ID);
			job.setOwner_id(OWNER_ID);
			job.setResource_id(resource_id);
			job.setResource_name(resource_name);
			job.setResource_type(type);
			UnicomPortalOperator.createJobs(job);
		}
		catch (NFVOException e) 
		{
			e.printStackTrace();
		}
		return job;
	}
	
	
	
	public static void main(String[] args) {
		UNFVOConfig.getInstance().init(new File(DirUtils.getInstance().getConfigDir() + "/unfvo-config.xml"));
		ResourceIds resourceIds = new ResourceIds();
// 1. 创建vpc
//		resourceIds = InstanceOperator.createVpc(resourceIds);  //  ok,  cidr? 
		
//		InstanceOperator.createJobs(id, "tan_vpc", "Router");
//		resourceIds.setVpcId(5);
//		resourceIds.setVpcUUID("e73edd8a-63ca-4134-9e14-0d2b98c9ee10");
//		resourceIds.setVpcNamePrefix("02last_");
// 2. 创建网关路由
//		InstanceOperator.createGWRouter(resourceIds);          //  ok,  needed? 
//		resourceIds.setIgwRouterId(11);
//		resourceIds.setIgwRouterUUID("954f8334-5b5d-4f86-9b1d-1db0b1434547");
//		resourceIds.setIgwRouterName("integrated_igw");
// 3. 创建路由表
//		resourceIds = InstanceOperator.createRoutingTable(resourceIds);  //  ok,  index return 0, but get the routingtable is not 0 (id=52 is  portal db vpc id)
//		resourceIds.setRoutingTableId(6);
//		resourceIds.setRoutingTableUUID("74edf753-84da-4361-ad1b-b9e0391017da");
// 4. 创建路由规则
//		 InstanceOperator.createRoutingRule(resourceIds);     //  ok, (id 16 is unicom portal db routing table id)
		
//		resourceIds.setRoutingRuleId(3);
//		resourceIds.setRoutingRuleUUID("6acefaa1-305b-4f54-baac-6732a08da225");
// 5. 创建公网IP资源
//        InstanceOperator.createPublicIpResource(resourceIds);   //  ok
		
//		resourceIds.setPublicIpResourceUUID("ca5de1b6-ba95-4723-9130-9f58de54231f");
		
// 6. 创建公网IP
//		InstanceOperator.createPublicIp(resourceIds);  //  ok
//		System.out.println(resourceIds.getPublicIpId() + "  "+resourceIds.getPublicIpUUID());
//		resourceIds.setPublicIpId(29);
//		resourceIds.setPublicIpUUID("ca5de1b6-ba95-4723-9130-9f58de54231f");
		
//		更新publicips表的bind_resource_id bind_resource_type也就是internets的id
// 7. 创建网络
//		InstanceOperator.createNetwork(resourceIds);  //  ok
//		resourceIds.setNetworkId(4);
//		resourceIds.setNetworkUUID("6be3e921-66d4-4359-8501-0a5914881cc6");

// 8， 创建子网
//		InstanceOperator.createSubnet(resourceIds);   //  ok
//		resourceIds.setSubnetId(4);
//		resourceIds.setSubnetUUID("8cf99f2e-3cf7-4689-9641-39c377c86fbb");
		
//		InstanceOperator.createVpcSubnet(resourceIds);
		
// 10. 创建子网路由表
//		InstanceOperator.createSubnetRoutingTable(resourceIds);  //  ok
//		resourceIds.setSubnetRoutingTableId(5);
//		resourceIds.setSubnetRoutingTableUUID("74edf753-84da-4361-ad1b-b9e0391017da");
		
// 11. 创建acl
//		InstanceOperator.createAcl(resourceIds);         //  500
//		resourceIds.setAclId(11);
//		resourceIds.setAclUUID("1701aea8-075e-4f9f-ba34-4210f28d0899");
		
// 12. 创建acl规则
//		InstanceOperator.createAclRule(resourceIds);  
		
//		System.out.println(resourceIds.getAclId() + "---" + resourceIds.getAclUUID());
		
// 13. 创建acl子网规则
//		InstanceOperator.createSubnetAclTable(resourceIds);
		
//		System.out.println(resourceIds.getSubnetAclTableId() + "---" + resourceIds.getSubnetAclTableUUID());
//		String volume_uuid = createServerVolume("scscf_exceed" + "_volume", "a217e0e1-eded-4bf4-bb38-843783785918", 64);
//		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createServer");
//		KVPair<String, Object> kvPair = restUrl.getPairs();
//		kvPair.add("volume_uuid", volume_uuid);
//		InstanceOperator.createServer(restUrl, resourceIds);
		
//		System.out.println(getPort("172.24.0.38"));
//		System.out.println(queryPublicIpResourceList("10.2.167.218"));
//		resourceIds.setIgwRouterId(12);
//		resourceIds.setIgwRouterUUID("2843521c-5846-49e8-97e2-e7b488484ca0");
//		resourceIds.setPublicIpId(284);
//		resourceIds.setPublicIpUUID("a5daa5d8-d70d-4548-9c8b-878ffbaa541a");
//		resourceIds.setPublicIp("172.16.64.33");
//		resourceIds.setVpcNamePrefix("tan_");
//		InstanceOperator.createNat(resourceIds);
		
	}
	
	public static String createServerVolume(String volumeName, String imageRef, int size) {
		RestUrl restUrl = RestConfigHandler.getInstance().getUrl("createVolume");
		restUrl.getPairs().add("display_name", volumeName);
		restUrl.getPairs().add("imageRef", imageRef);
		restUrl.getPairs().add("size", size);
		
		String volumeId = InstanceOperator.createVolume(restUrl);
		return volumeId;
	}
}
