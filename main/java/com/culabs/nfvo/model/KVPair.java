package com.culabs.nfvo.model;

import java.util.LinkedList;
import java.util.List;

import com.culabs.nfvo.util.NFVOUtils;

public class KVPair<K, V>
{
	private List<Pair<K, V>> LIST = new LinkedList<Pair<K, V>>();
	private int index = -1;

	public static class Factory
	{
		public static <K, V> KVPair<K, V> createKVPair(Class<K> k, Class<V> v)
		{
			return new KVPair<K, V>();
		}
	}

	public KVPair()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	public KVPair<K, V> add(K k, V v)
	{
		if (NFVOUtils.isEmpty(k))
		{
			throw new IllegalArgumentException("key not allow to be empty");
		}
		Pair<K, V> pair = new Pair<K, V>(k, v);
		if (!LIST.contains(pair))
		{
			LIST.add(pair);
		} else
		{
			LIST.remove(pair);
			LIST.add(pair);
		}
		return this;
	}

	public Pair<K, V> current()
	{
		if (!LIST.isEmpty())
		{
			if (index < 0)
			{
				return LIST.get(0);
			} else if (index > LIST.size() - 1)
			{
				return LIST.get(LIST.size() - 1);
			}
			return LIST.get(index);
		}
		return new Pair<K, V>(null, null);
	}

	public Pair<K, V> next()
	{
		if (!LIST.isEmpty())
		{
			if (index < LIST.size() - 1)
			{
				return LIST.get(++index);
			} else
			{
				return LIST.get(LIST.size() - 1);
			}
		}
		return new Pair<K, V>(null, null);
	}

	public Pair<K, V> pre()
	{
		if (!LIST.isEmpty())
			if (index > 0)
			{
				return LIST.get(--index);
			} else
			{
				return LIST.get(0);
			}
		return new Pair<K, V>(null, null);
	}

	public Pair<K, V> getPair(K k)
	{
		if (!LIST.isEmpty())
		{
			for (Pair<K, V> pair : LIST)
			{
				if (null != k && k.equals(pair.getKey()))
				{
					return pair;
				}
			}
		}
		return null;
	}

	public V getPairVal(K k)
	{
		if (!LIST.isEmpty())
		{
			for (Pair<K, V> pair : LIST)
			{
				if (null != k && k.equals(pair.getKey()))
				{
					return pair.getValue();
				}
			}
		}
		return null;
	}

	public boolean hasNext()
	{
		return !LIST.isEmpty() && index < LIST.size() - 1;
	}

	public boolean isEmpty()
	{
		return LIST.isEmpty();
	}

	public void reset()
	{
		index = -1;
	}

	public void clear()
	{
		LIST.clear();
		this.index = -1;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((LIST == null) ? 0 : LIST.hashCode());
		result = prime * result + index;
		return result;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KVPair other = (KVPair) obj;
		if (LIST == null)
		{
			if (other.LIST != null)
				return false;
		} else if (!LIST.equals(other.LIST))
			return false;
		if (index != other.index)
			return false;
		return true;
	}

	@Override
	public String toString()
	{
		return "KVPair [LIST=" + LIST + "]";
	}

	public static void main(String[] args)
	{
		KVPair<String, String> pair = new KVPair<String, String>();
		// pair.addParam("dpid", "0000000000000004");
		// pair.
		pair.add("a", "a").add("b", "b").add("c", "c");
		// System.out.println(pair.next().getKey());
		// System.out.println(pair.next().getKey());
		// System.out.println(pair.next().getKey());
		// System.out.println(pair.next().getKey());
		//
		// System.out.println(pair.pre().getKey());
		// System.out.println(pair.pre().getKey());
		// System.out.println(pair.pre().getKey());
		// System.out.println(pair.pre().getKey());
		// System.out.println(pair.pre().getKey());
		// System.out.println(pair.current().getKey());
		// System.out.println(pair.getPair("c").getValue());
		// pair.reset();
		// System.out.println(pair.current().getKey());
		// pair.clear();
		// System.out.println(pair.current().getKey());
		// Object obj = null;
		// System.out.println(pair);
		// pair.add("a", "b").add("b", "c").add("c", "a");
		// System.out.println(pair);
		// pair.add("a", "a").add("b", "b").add("c", "c");
		// System.out.println(pair);
		pair.add("", null);
		// System.out.println(pair);
	}
}
