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

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.core.TemplateOperator;
import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.UITableResp;
import com.culabs.nfvo.model.db.DBNsd;
import com.culabs.nfvo.model.db.DBVnfd;
import com.culabs.nfvo.model.nsd.Nsd;
import com.culabs.nfvo.model.vnfd.Vnfd;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.SysConst;

/**
 * 
 * @ClassName: DevicesWebResource
 * @Description: TODO(描述类作用)
 * @author 
 * @date 2015年4月21日 上午9:58:02
 * @version 1.0
 */
@Path("rest/template")
public class NFVTemplateResource
{
	private static Logger LOGGER = LoggerFactory.getLogger(NFVTemplateResource.class);
	@Context
	HttpServletRequest request;

	@GET
	@Path("ns")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public UITableResp<HashMap<String, Object>> getNSTempls(@QueryParam("type") String type)
	{
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		UITableResp<HashMap<String, Object>> resp = new UITableResp<HashMap<String, Object>>();
		List<DBNsd> dbnsds = TemplateOperator.queryDBNsdList(type);
		HashMap<String, Object> map = null;
		DBNsd dbnsd = null;
		for (int i = 0; i < dbnsds.size(); ++i)
		{
			map = new HashMap<String, Object>();
			dbnsd = dbnsds.get(i);
			map.put("id", dbnsd.getTemplate_id());
			map.put("name", dbnsd.getName());
			map.put("provider", dbnsd.getProvider());
			map.put("type", dbnsd.getType());
			map.put("version", dbnsd.getVersion());
			map.put("desc", dbnsd.getDescription());
			list.add(map);
		}
		resp.setRows(list);
		resp.setTotal(list.size());
		return resp;
	}

	@GET
	@Path("vnf")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public UITableResp<HashMap<String, Object>> getVNFTempls()
	{
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();

		UITableResp<HashMap<String, Object>> resp = new UITableResp<HashMap<String, Object>>();
		List<DBVnfd> dbvnfds = TemplateOperator.queryDBVnfdList(null);
		HashMap<String, Object> map = null;
		DBVnfd dbvnfd = null;
		for (int i = 0; i < dbvnfds.size(); ++i)
		{
			map = new HashMap<String, Object>();
			dbvnfd = dbvnfds.get(i);
			map.put("id", dbvnfd.getVnfd_id());
			map.put("name", dbvnfd.getName());
			map.put("provider", dbvnfd.getProvider());
			map.put("version", dbvnfd.getVersion());
			map.put("desc", dbvnfd.getDescription());
			list.add(map);
		}
		resp.setRows(list);
		resp.setTotal(list.size());
		return resp;
	}

	@DELETE
	@Path("vnf/{vnfid}")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public Result deleteVNFTemplateById(@PathParam("vnfid") String vnfid) throws NFVOException
	{
		return TemplateOperator.deleteTemplate(vnfid, Vnfd.class);
	}

	@GET
	@Path("vnf/{vnfid}/xml")
	@Produces("text/plain")
	public String getVNFTemplateXmlById(@PathParam("vnfid") String vnfid)
	{
		DBVnfd dbvnfd = TemplateOperator.queryDBVnfd(vnfid);
		LOGGER.info("dbnsds= " + dbvnfd);
		if (!NFVOUtils.isEmpty(dbvnfd))
		{
			LOGGER.info("Vnf template content: {} " + dbvnfd.getContent());
			return dbvnfd.getContent();
		}
		return "";
	}

	@DELETE
	@Path("ns/{nsid}")
	@Produces(SysConst.MEDIA_JSON_UTF_8)
	public Result deleteNSTemplateById(@PathParam("nsid") String nsid) throws NFVOException
	{
		return TemplateOperator.deleteTemplate(nsid, Nsd.class);
	}

	@GET
	@Path("ns/{nsid}/xml")
	@Produces("text/plain")
	public String getNSTemplateXmlById(@PathParam("nsid") String nsid)
	{
		DBNsd dbnsd = TemplateOperator.queryDBNsd(nsid);
		LOGGER.info("dbnsds= " + dbnsd);
		if (!NFVOUtils.isEmpty(dbnsd))
		{
			LOGGER.debug("NS template content: {} ", dbnsd.getContent());
			return dbnsd.getContent();
		}
		return "";
	}
}
