/*
 * Copyright 2014 Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.culabs.nfvo.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.core.InstanceManager;
import com.culabs.nfvo.core.TemplateOperator;
import com.culabs.nfvo.core.DBOperator;
import com.culabs.nfvo.core.InstanceOperator;
import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.VNFServer;
import com.culabs.nfvo.model.db.DBInstance;
import com.culabs.nfvo.model.db.DBNsd;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.SysConst;

/**
 * 
 * @ClassName: DevicesWebResource
 * @Description: TODO(描述类作用)
 * @date 2015年4月21日 上午9:58:02
 * @version 1.0
 */
@Path("rest/instance")
public class NFVInstanceResource
{
	private static Logger LOGGER = LoggerFactory.getLogger(NFVInstanceResource.class);

	private JSONObject resultObj;

	@GET
	@Path("instances")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public JSONObject getInstances(@QueryParam("type") String type)
	{
		List<DBInstance> instancelist;
		try
		{
			List<HashMap<String, Object>> returnlist = new ArrayList<HashMap<String, Object>>();
			Map<String, Object> jsonMap = new HashMap<String, Object>();
			instancelist = DBOperator.queryInstanceListByType(type);
			HashMap<String, Object> map = null;
			for (DBInstance instance : instancelist)
			{
				map = new HashMap<String, Object>();
				map.put("instance_id", instance.getInstance_id());
				map.put("instance_name", instance.getInstance_name());
				map.put("type", instance.getType());
				map.put("status", instance.getStatus());
				map.put("description", instance.getDescription());
				map.put("create_time", NFVOUtils.getAsDateSpecificFormat(instance.getCreate_time()));
				returnlist.add(map);
			}

			jsonMap.put("total", returnlist.size());
			jsonMap.put("rows", returnlist);
			resultObj = JSONObject.fromObject(jsonMap);
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return resultObj;
	}

	@SuppressWarnings("rawtypes")
	@GET
	@Path("fullview")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public List<Map> getFullView()
	{
		List<Map> list = InstanceOperator.getHypervisorServers();
		NFVOUtils.restListNotNull(list);
		LOGGER.info("Get full views: {}", list);
		return list;
	}

	@POST
	@Path("template/{templateId}")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public JSONObject instantiation(@PathParam("templateId") String templateId)
	{
		String instanceId = null;
		DBNsd dbnsd = TemplateOperator.queryDBNsd(templateId);
		if (!NFVOUtils.isEmpty(dbnsd) && !NFVOUtils.isEmpty(dbnsd.getType()))
		{
			instanceId = UUID.randomUUID().toString();
			InstanceManager.createInstance(templateId, instanceId, dbnsd.getType());
		}
		JSONObject result = new JSONObject();
		result.put("instanceId", instanceId);
		return result;
	}

	@DELETE
	@Path("{instanceId}")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public Result deleteInstance(@PathParam("instanceId") String instanceId) throws NFVOException
	{
		return InstanceManager.deleteInstance(instanceId);
	}

	@GET
	@Path("{instanceId}/status")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public List<VNFServer> queryInstancesStatus(@PathParam("instanceId") String instanceId)
	{
		List<VNFServer> list = null;
		try
		{
			DBInstance dbinstance = DBOperator.queryInstanceById(instanceId);
			if (NFVOUtils.isEmpty(dbinstance) || "-1".equals(dbinstance.getStatus()))
			{
				return null;
			}
			list = InstanceManager.queryInstance(instanceId);

		} catch (Exception e)
		{
			LOGGER.error("Query server status failed. The cause:{}", e);
		}
		return list;
	}
}
