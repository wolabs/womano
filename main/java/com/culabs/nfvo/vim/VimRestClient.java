package com.culabs.nfvo.vim;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.WebResource.Builder;
import com.culabs.nfvo.config.RestConfigHandler;
import com.culabs.nfvo.config.UNFVOConfig;
import com.culabs.nfvo.model.KVPair;
import com.culabs.nfvo.model.RestUrl;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.util.DirUtils;
import com.culabs.nfvo.util.NFVOJsonUtils;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.SysConst;

/**
 * 
 * @ClassName: VimRestClient
 * @Description: TODO
 * @author
 * @date 2015年4月23日 下午3:50:34
 * @version 1.0
 */
public class VimRestClient
{
	private static Logger LOGGER = LoggerFactory.getLogger(VimRestClient.class);
	private static Client CLIENT = Client.create();
	private static String token_id = "";
	private static long token_update_time = System.currentTimeMillis();

	private static boolean isTokenIdExpired()
	{
		return System.currentTimeMillis() - token_update_time > 1800000;
	}

	private static String getTokenId()
	{
		if (NFVOUtils.isEmpty(token_id) || isTokenIdExpired())
		{
			token_id = (String) NFVOUtils.getMapValByKey(VimRestClient.post(
					RestConfigHandler.getInstance().getUrl("getTokenId"), HashMap.class, ArrayList.class), "id", 3,
					SysConst.MapQueryCond.LSEQT);
			token_update_time = System.currentTimeMillis();
			LOGGER.info("======================TokenId = {}, {}", token_id, "======================");
		}
		return token_id;
	}

	public static <T> T restCall(String id, Class<T> clz, Class<?>... clzs)
	{
		return restCall(RestConfigHandler.getInstance().getUrl(id), clz, clzs);
	}

	public static <T> T restCall(RestUrl ru, Class<T> clz, Class<?>... clzs)
	{
		T rst = null;
		if (null == ru)
		{
			return null;
		}
		try
		{
			rst = executeRest(ru, clz, rst, clzs);
		} catch (Exception e)
		{
			LOGGER.error("Rest call failed. The cause: {}", e.getMessage());
			LOGGER.warn("TokenId may be changed. ReCall rest api again");
			token_id = "";
			try
			{
				rst = executeRest(ru, clz, rst, clzs);
			} catch (Exception e2)
			{
				LOGGER.error("Recall failed. The cause: {}", e.getMessage());
			}
		}
		return rst;
	}

	private static <T> T executeRest(RestUrl ru, Class<T> clz, T rst, Class<?>... clzs)
	{
		switch (RestUrl.HttpType.valueof(ru.getType()))
		{
		case GET:
			rst = get(ru, clz, clzs);
			break;
		case PUT:
			rst = put(ru, clz, clzs);
			break;
		case POST:
			rst = post(ru, clz, clzs);
			break;
		case DELETE:
			rst = delete(ru, clz, clzs);
			break;
		default:
			break;
		}
		return rst;
	}

	public static <T> T get(RestUrl ru, Class<T> clz, Class<?>... clzs)
	{
		if (null == ru)
		{
			return null;
		}
		LOGGER.info("Rest url: " + NFVOUtils.getRealUrl(ru));
		WebResource webResource = CLIENT.resource(NFVOUtils.getRealUrl(ru));
		Builder builder = webResource.type(SysConst.MEDIA_JSON_UTF_8);
		if (ru.hasToken())
		{
			if (NFVOUtils.isEmpty(ru.getPairs().getPairVal(RestUrl.TOKEN_KEY)))
			{
				ru.addPair(RestUrl.TOKEN_KEY, getTokenId());
			}
			builder = builder.header(RestUrl.TOKEN_KEY, ru.getPairs().getPairVal(RestUrl.TOKEN_KEY));
		}
		String resp = builder.get(ClientResponse.class).getEntity(String.class);
		LOGGER.info("Rest response from unicom cloud: " + resp);
		return jsonToBean(clz, resp, clzs);
	}

	public static <T> T get(String id, KVPair<String, Object> pairs, boolean isNeedParam, Class<T> clz,
			Class<?>... clzs)
	{
		return get(RestConfigHandler.getInstance().getUrl(id), clz, clzs);
	}

	public static <T> T put(RestUrl ru, Class<T> clz, Class<?>... clzs)
	{
		if (null == ru)
		{
			return null;
		}
		LOGGER.info("Rest url: " + NFVOUtils.getRealUrl(ru));
		WebResource webResource = CLIENT.resource(NFVOUtils.getRealUrl(ru));
		Builder builder = webResource.type(SysConst.MEDIA_JSON_UTF_8);
		if (ru.hasToken())
		{
			if (NFVOUtils.isEmpty(ru.getPairs().getPairVal(RestUrl.TOKEN_KEY)))
			{
				ru.addPair(RestUrl.TOKEN_KEY, getTokenId());
			}
			builder = builder.header(RestUrl.TOKEN_KEY, ru.getPairs().getPairVal(RestUrl.TOKEN_KEY));
		}
		Object postjson = ru.getReqjson();
		String resp = builder.put(ClientResponse.class,
				postjson.getClass() == String.class ? postjson : JSONObject.fromObject(postjson).toString()).getEntity(
				String.class);
		LOGGER.info("Rest response from unicom cloud: " + resp);
		return jsonToBean(clz, resp, clzs);
	}

	public static <T> T put(String id, Class<T> clz, Class<?>... clzs)
	{
		return put(RestConfigHandler.getInstance().getUrl(id), clz, clzs);
	}

	public static <T> T post(RestUrl ru, Class<T> clz, Class<?>... clzs)
	{
		if (null == ru)
		{
			return null;
		}
		LOGGER.info("Rest url: " + NFVOUtils.getRealUrl(ru));
		WebResource webResource = CLIENT.resource(NFVOUtils.getRealUrl(ru));
		Builder builder = webResource.type(SysConst.MEDIA_JSON_UTF_8);
		if (ru.hasToken())
		{
			if (NFVOUtils.isEmpty(ru.getPairs().getPairVal(RestUrl.TOKEN_KEY)))
			{
				ru.addPair(RestUrl.TOKEN_KEY, getTokenId());
			}
			builder = builder.header(RestUrl.TOKEN_KEY, ru.getPairs().getPairVal(RestUrl.TOKEN_KEY));
		}
		Object postjson = ru.getReqjson();
		String resp = builder.post(ClientResponse.class,
				postjson.getClass() == String.class ? postjson : JSONObject.fromObject(postjson).toString()).getEntity(
				String.class);
		LOGGER.info("Rest response from unicom cloud: " + resp);
		return jsonToBean(clz, resp, clzs);
	}

	public static <T> T post(String id, KVPair<String, Object> pairs, boolean isNeedParam, Class<T> clz,
			Class<?>... clzs)
	{
		return post(RestConfigHandler.getInstance().getUrl(id), clz, clzs);
	}

	public static <T> T delete(RestUrl ru, Class<T> clz, Class<?>... clzs)
	{
		if (null == ru)
		{
			return null;
		}
		LOGGER.info("Rest url: " + NFVOUtils.getRealUrl(ru));
		WebResource webResource = CLIENT.resource(NFVOUtils.getRealUrl(ru));
		Builder builder = webResource.type(SysConst.MEDIA_JSON_UTF_8);
		if (ru.hasToken())
		{
			if (NFVOUtils.isEmpty(ru.getPairs().getPairVal(RestUrl.TOKEN_KEY)))
			{
				ru.addPair(RestUrl.TOKEN_KEY, getTokenId());
			}
			builder = builder.header(RestUrl.TOKEN_KEY, ru.getPairs().getPairVal(RestUrl.TOKEN_KEY));
		}
		Object postjson = ru.getReqjson();
		String resp = builder.delete(ClientResponse.class,
				postjson.getClass() == String.class ? postjson : JSONObject.fromObject(postjson).toString()).getEntity(
				String.class);
		LOGGER.info("Rest response from unicom cloud: " + resp);
		return jsonToBean(clz, resp, clzs);
	}

	public static <T> T delete(String id, Class<T> clz, Class<?>... clzs)
	{
		return delete(RestConfigHandler.getInstance().getUrl(id), clz, clzs);
	}

	public static Result deleteForNoResp(RestUrl ru)
	{
		if (null == ru)
		{
			return new Result(400, "RestUrl can not be empty!");
		}
		LOGGER.info("Rest url: " + NFVOUtils.getRealUrl(ru));
		WebResource webResource = CLIENT.resource(NFVOUtils.getRealUrl(ru));
		Builder builder = webResource.type(SysConst.MEDIA_JSON_UTF_8);
		if (ru.hasToken())
		{
			if (NFVOUtils.isEmpty(ru.getPairs().getPairVal(RestUrl.TOKEN_KEY)))
			{
				ru.addPair(RestUrl.TOKEN_KEY, getTokenId());
			}
			builder = builder.header(RestUrl.TOKEN_KEY, ru.getPairs().getPairVal(RestUrl.TOKEN_KEY));
		}
		Object postjson = ru.getReqjson();
		ClientResponse clientResponse = builder.delete(ClientResponse.class,
				postjson.getClass() == String.class ? postjson : JSONObject.fromObject(postjson).toString());
		return new Result(clientResponse.getStatus(), clientResponse.getStatusInfo().getReasonPhrase());
	}

	@SuppressWarnings("unchecked")
	private static <T> T jsonToBean(Class<T> clz, String resp, Class<?>... clzs)
	{
		T t = null;
		if (clz == String.class)
		{
			t = (T) resp;
		} else if (clz == HashMap.class)
		{
			t = (T) NFVOJsonUtils.parseJSON2Map(resp);
		} else
		{
			t = NFVOUtils.json2Bean(resp, clz, clzs);
		}
		return t;
	}

	public static void main(String[] args)
	{
		UNFVOConfig.getInstance().init(new File(DirUtils.getInstance().getConfigDir() + "/unfvo-config.xml"));
		System.out.println("-- " + VimRestClient.getTokenId());
		
	}
}
