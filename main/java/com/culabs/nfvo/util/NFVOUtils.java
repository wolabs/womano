package com.culabs.nfvo.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.culabs.nfvo.config.UNFVOConfig;
import com.culabs.nfvo.model.FlexGridJSONData;
import com.culabs.nfvo.model.KVPair;
import com.culabs.nfvo.model.Page;
import com.culabs.nfvo.model.RestUrl;

/**
 * 
 * @ClassName: NFVOUtils
 * @Description: TODO
 * @author
 * @date 2015年4月23日 下午3:50:59
 * @version 1.0
 */
public class NFVOUtils
{
	private static Logger LOGGER = LoggerFactory.getLogger(NFVOUtils.class);

	public static final String ERRORS = "datatype 'Date' transform error: ";
	public static final String DATA_FORMAT_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

	public static boolean isEmpty(String str)
	{
		return null == str || "".equals(str.trim());
	}

	public static boolean isEmpty(Collection<?> con)
	{
		return null == con || con.isEmpty();
	}

	public static <T> boolean isEmpty(T[] arr)
	{
		return null == arr || arr.length == 0;
	}

	public static <T> boolean isEmpty(T t)
	{
		if (null != t && String.class == t.getClass())
		{
			return isEmpty((String) t);
		}
		return null == t;
	}

	public static boolean isEmpty(Number num)
	{
		return null == num;
	}

	public static Integer toIntger(String str)
	{
		return isEmpty(str) ? null : Integer.valueOf(str);
	}

	public static Integer toIntger(String str, int def)
	{
		int result = def;
		try
		{
			result = isEmpty(str) ? def : Integer.valueOf(str.trim());
		} catch (Exception e)
		{
			return def;
		}
		return result;
	}

	public static boolean toBoolean(String str, boolean def)
	{
		boolean result = def;
		try
		{
			result = isEmpty(str) ? def
					: (str.trim().equalsIgnoreCase("true") ? true : false);
		} catch (Exception e)
		{
			return def;
		}
		return result;
	}

	public static String getProperty(Object obj, String field)
	{
		String fieldval = "";
		try
		{
			fieldval = BeanUtils.getProperty(obj, field);
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fieldval;
	}

	public static <T> FlexGridJSONData obj2Grid(List<T> list,
			List<String> fields, Integer persize, Integer curpage, Integer total)
	{
		persize = null == persize ? 5 : persize;
		FlexGridJSONData grid = FlexGridJSONData.Factory.newInstance();
		grid.setPage(curpage);
		grid.setTotal(total);
		T t = null;
		for (int i = 0; i < list.size(); ++i)
		{
			t = list.get(i);
			grid.setRowId(i);
			for (String field : fields)
			{
				grid.addColdata(getProperty(t, field));
			}
			grid.commitData();
		}
		return grid;
	}

	public static <T> T cloneBean(T src, T dest)
	{
		try
		{
			BeanUtils.copyProperties(dest, src);
		} catch (IllegalAccessException e)
		{
			e.printStackTrace();
			LOGGER.warn(e.toString());
			return null;
		} catch (InvocationTargetException e)
		{
			LOGGER.warn(e.toString());
			return null;
		}
		return dest;
	}

	public static Page wrapPage(HttpServletRequest request)
	{
		Page p = new Page();
		p.setRp(Integer.valueOf(request.getParameter("rp")));
		p.setPage(Integer.valueOf(request.getParameter("page")));
		p.setQtype(request.getParameter("qtype"));
		p.setQuery(request.getParameter("query"));
		p.setSortname(request.getParameter("sortname"));
		p.setSortorder(request.getParameter("sortorder"));
		return p;
	}

	// public static UserContext createUserContext(User user) {
	// UserContext userContext = new UserContext();
	// userContext.setUser(user);
	// userContext.setConfiglist(SysModelConf.getInstance().getConfigItemList(
	// ""));
	// return userContext;
	// }
	//
	// public static UserContext createUserContext(User user, String...
	// groupids) {
	// UserContext userContext = new UserContext();
	// userContext.setUser(user);
	// userContext.setConfiglist(SysModelConf.getInstance().getConfigItemList(
	// user.getRole(), groupids));
	// userContext.getConfiglist().addAll(
	// SysModelConf.getInstance().getConfigItemList("",
	// SysModelConf.GROUPID_MENU));
	// return userContext;
	// }

	@SuppressWarnings({ "unchecked" })
	public static <T> T reqParams2Model(T obj, HttpServletRequest req)
	{
		if (null != req && null != obj)
		{
			for (Map.Entry<String, ?> entry : (Set<Map.Entry<String, ?>>) req
					.getParameterMap().entrySet())
			{
				try
				{
					BeanUtils
							.setProperty(obj, entry.getKey(), entry.getValue());
				} catch (IllegalAccessException e)
				{
					LOGGER.warn(e.toString());
				} catch (InvocationTargetException e)
				{
					LOGGER.warn(e.toString());
				}
			}
		}
		LOGGER.info(obj.toString());
		return obj;
	}

	public static <T> List<T> json2BeanList(String jsonstr, Class<T> clz,
			Class<?>... clzs)
	{
		List<T> list = new ArrayList<T>();
		T object = null;
		try
		{
			JSONArray array = new JSONArray(jsonstr);
			for (int i = 0; i < array.length(); i++)
			{
				object = json2Bean(array.getString(i), clz, clzs);
				list.add(object);
			}
		} catch (JSONException e)
		{
			LOGGER.warn(e.toString());
		}
		return list;
	}

	public static <T> T json2Bean(String jsonstr, Class<T> clz,
			Class<?>... clzs)
	{
		ObjectMapper mapper = new ObjectMapper();
		T obj = null;
		try
		{
			obj = mapper.readValue(jsonstr,
					getCollectionType(mapper, clz, clzs));
		} catch (JsonParseException e)
		{
			LOGGER.warn(e.toString());
		} catch (JsonMappingException e)
		{
			LOGGER.warn(e.toString());
		} catch (IOException e)
		{
			LOGGER.warn(e.toString());
		}
		return obj;
	}

	public static JavaType getCollectionType(ObjectMapper mapper,
			Class<?> collectionClass, Class<?>... elementClasses)
	{
		return mapper.getTypeFactory().constructParametricType(collectionClass,
				elementClasses);
	}

	public static String getRealUrl(RestUrl ru)
	{
		if (null == ru)
		{
			return "";
		}
		String urltempl = ru.getUrl();
		KVPair<String, Object> pairs = ru.getPairs();
		if (null == pairs)
		{
			return urltempl;
		}
		pairs.reset();
		boolean isString = (ru.getReqjson().getClass() == String.class);
		while (pairs.hasNext())
		{
			urltempl = urltempl.replace(
					"{" + pairs.next().getKey() + "}",
					null == pairs.current().getValue() ? "" : String
							.valueOf(pairs.current().getValue()));
			if (isString)
			{
				ru.setReqjson(((String) ru.getReqjson()).replace(
						"{" + pairs.current().getKey() + "}",
						null == pairs.current().getValue() ? "" : String
								.valueOf(pairs.current().getValue())));
			}
		}
		return urltempl;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object getMapValByKey(Map map, String key, Integer loop,
			SysConst.MapQueryCond cond)
	{
		if (null == map || NFVOUtils.isEmpty(key))
		{
			return null;
		}
		cond = isEmpty(cond) ? SysConst.MapQueryCond.LSEQT : cond;
		Integer temp = (null == loop) ? null : --loop;
		if (map.containsKey(key))
		{
			if (SysConst.MapQueryCond.EQT.equals(cond)
					&& (null == loop || temp == 0))
			{
				return map.get(key);
			} else if (SysConst.MapQueryCond.LSEQT.equals(cond)
					&& (null == loop || temp >= 0))
			{
				return map.get(key);
			} else if (SysConst.MapQueryCond.LGEQT.equals(cond)
					&& (null == loop || temp <= 0))
			{
				return map.get(key);
			}
		}

		Object obj = null;
		for (Map.Entry entry : (Set<Map.Entry>) map.entrySet())
		{
			if (entry.getValue() instanceof Map)
			{
				obj = getMapValByKey((Map) entry.getValue(), key, temp, cond);
				if (!NFVOUtils.isEmpty(obj))
				{
					return obj;
				}
			} else if (entry.getValue() instanceof List)
			{
				obj = getListMapValByKey((List) entry.getValue(), key, temp,
						cond);
				if (!NFVOUtils.isEmpty(obj))
				{
					return obj;
				}
			} else
			{
				continue;
			}
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Object getListMapValByKey(List list, String key,
			Integer loop, SysConst.MapQueryCond cond)
	{
		if (null == list || NFVOUtils.isEmpty(key))
		{
			return null;
		}
		Integer temp = (null == loop) ? null : --loop;
		Object obj = null;
		for (Object subobj : list)
		{
			if (subobj instanceof Map)
			{
				obj = getMapValByKey((Map) subobj, key, temp, cond);
				if (!NFVOUtils.isEmpty(obj))
				{
					return obj;
				}
			} else if (subobj instanceof List)
			{
				obj = getListMapValByKey((List) subobj, key, loop, cond);
				if (!NFVOUtils.isEmpty(obj))
				{
					return obj;
				}
			}
		}
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void resettMapValNotNull(Map map)
	{
		if (null == map)
		{
			return;
		}
		for (Map.Entry entry : (Set<Map.Entry>) map.entrySet())
		{
			if (entry.getValue() instanceof Map)
			{
				resettMapValNotNull((Map) entry.getValue());
			} else if (entry.getValue() instanceof List)
			{
				restListNotNull((List) entry.getValue());
			} else
			{
				if (NFVOUtils.isEmpty(entry.getValue())
						|| entry.getValue().toString().equals("null"))
				{
					map.put(entry.getKey(), "");
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	public static void restListNotNull(List list)
	{
		for (Object subobj : list)
		{
			if (subobj instanceof Map)
			{
				resettMapValNotNull((Map) subobj);
			} else if (subobj instanceof List)
			{
				restListNotNull((List) subobj);
			}
		}
	}

	public static String getAsDateFormat(String dataformat, Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		if (obj instanceof java.util.Date)
		{
			SimpleDateFormat sdf = new SimpleDateFormat(dataformat);
			return sdf.format(obj);
		} else
		{
			String msg = ERRORS + obj.toString();
			throw new RuntimeException(msg);
		}
	}

	public static String getAsDateSpecificFormat(Object obj)
	{
		return getAsDateFormat(DATA_FORMAT_YYYYMMDDHHMMSS, obj);
	}

	public static long ipToLong(String strIp){ 
		if(isEmpty(strIp)) {
			return 0;
		}
		String[] ipArray = strIp.split("\\.");
        return (Long.parseLong(ipArray[0]) << 24) + (Long.parseLong(ipArray[1])<< 16) + (Long.parseLong(ipArray[2]) << 8) + Long.parseLong(ipArray[3]);  
    }   
	
	public static void main(String[] args)
	{
		System.out.println(ipToLong("210.6.2.222"));  
	}
}
