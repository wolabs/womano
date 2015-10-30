package com.culabs.nfvo.model;

public class ConfigBlock
{
	private String id;
	private KVPair<String, String> pairs = KVPair.Factory.createKVPair(
			String.class, String.class);

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public KVPair<String, String> getPairs()
	{
		return pairs;
	}

	public void setPairs(KVPair<String, String> pairs)
	{
		this.pairs = pairs;
	}

	public void addPair(String key, String value)
	{
		this.pairs.add(key, value);
	}

	@Override
	public String toString()
	{
		return "ConfigBlock [id=" + id + ", pairs=" + pairs + "]";
	}

}
