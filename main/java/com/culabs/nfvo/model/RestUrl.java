package com.culabs.nfvo.model;

import com.culabs.nfvo.util.NFVOUtils;

public class RestUrl
{
	public final static String TOKEN_KEY = "X-Auth-Token";
	public final static String IP = "rest_ip";
	public final static String PORT = "rest_port";
	private String id;
	private String url;
	private String type;
	private String desc;
	private Object reqjson = "";
	private KVPair<String, Object> pairs = KVPair.Factory.createKVPair(
			String.class, Object.class);
	public enum HttpType
	{
		GET("get"), PUT("put"), POST("post"), DELETE("delete");
		private String type = "";

		private HttpType(String type)
		{
			this.type = type;
		}

		public static HttpType valueof(String value) {
	        switch (value) {
	        case "get":
	            return GET;
	        case "put":
	            return PUT;
	        case "post":
	            return POST;
	        case "delete":
	            return DELETE;
	        default:
				return null;
	        }
	    }
		
		public String value()
		{
			return this.type;
		}
	}

	public RestUrl()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public RestUrl(String id, String url, String type, String desc)
	{
		super();
		this.id = id;
		this.url = url;
		this.type = type;
		this.desc = desc;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public Object getReqjson()
	{
		return reqjson;
	}

	public void setReqjson(Object reqjson)
	{
		this.reqjson = reqjson;
	}

	public KVPair<String, Object> getPairs()
	{
		return pairs;
	}

	public void setPairs(KVPair<String, Object> pairs)
	{
		this.pairs = pairs;
	}

	public void addPair(String key, String value)
	{
		this.pairs.add(key, value);
	}

	public boolean hasToken()
	{
		return !NFVOUtils.isEmpty(pairs.getPair(TOKEN_KEY));
	}

	@Override
	public String toString()
	{
		return "RestUrl [id=" + id + ", url=" + url + ", type=" + type
				+ ", desc=" + desc + ", reqjson=" + reqjson + ", pairs="
				+ pairs + "]";
	}

}
