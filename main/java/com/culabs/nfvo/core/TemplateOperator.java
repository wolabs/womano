package com.culabs.nfvo.core;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.db.DBNsd;
import com.culabs.nfvo.model.db.DBVnfd;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.TemplObjectMapper;
import com.culabs.nfvo.util.TemplateParser;

/**
 * 
 * @ClassName: TemplateOperator
 * @Description: TODO
 * @author 
 * @date 2015年4月25日 下午7:05:04
 * @version 1.0
 */
public class TemplateOperator
{
	public static <U, V> Result importTemplate(InputStream ins, Class<U> src, Class<V> dst) throws NFVOException
	{
		U tmpobj = (U) TemplateParser.xmlFileInputStreamToBean(ins, src);
		Object obj = TemplObjectMapper.mapTo(tmpobj, src, dst);
		return DBOperator.storeTemplate(obj, src, dst);
	}

	public static <U, V> Result deleteTemplate(String templid, Class<U> src) throws NFVOException
	{
		return DBOperator.deleteTemplate(templid, src);
	}

	public static List<DBNsd> queryDBNsdList(String type)
	{
		List<DBNsd> list = DBOperator.queryDBNsdList(type);
		return NFVOUtils.isEmpty(list) ? new ArrayList<DBNsd>(0) : list;
	}

	public static List<DBVnfd> queryDBVnfdList(String templid)
	{
		List<DBVnfd> list = DBOperator.queryDBVnfdList(templid);
		return NFVOUtils.isEmpty(list) ? new ArrayList<DBVnfd>(0) : list;
	}

	public static DBNsd queryDBNsd(String templid)
	{
		DBNsd dnnsd = DBOperator.queryDBNsd(templid);
		return NFVOUtils.isEmpty(dnnsd) ? new DBNsd() : dnnsd;
	}

	public static DBVnfd queryDBVnfd(String templid)
	{
		DBVnfd dnvnfd = DBOperator.queryDBVnfd(templid);
		return NFVOUtils.isEmpty(dnvnfd) ? new DBVnfd() : dnvnfd;
	}
}
