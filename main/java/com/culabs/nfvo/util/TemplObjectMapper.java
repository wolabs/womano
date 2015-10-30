package com.culabs.nfvo.util;

import com.culabs.nfvo.model.db.DBNsd;
import com.culabs.nfvo.model.db.DBVnfd;
import com.culabs.nfvo.model.nsd.Nsd;
import com.culabs.nfvo.model.vnfd.Vnfd;

public class TemplObjectMapper
{
	@SuppressWarnings("unchecked")
	public static <K, V> V mapTo(Object src, Class<K> srck, Class<V> dstv)
	{
		Nsd nsd = null;
		Vnfd vnfd = null;
		DBNsd dbnsd = null;
		DBVnfd dbvnfd = null;
		if (srck == Nsd.class && dstv == DBNsd.class)
		{
			nsd = (Nsd) src;
			dbnsd = new DBNsd();
			dbnsd.setTemplate_id(nsd.getNsdId());
			dbnsd.setName(nsd.getName());
			dbnsd.setProvider(nsd.getProvider());
			dbnsd.setVersion(String.valueOf(nsd.getVersion()));
			dbnsd.setType(nsd.getType());
			dbnsd.setDescription(nsd.getDescription());
			dbnsd.setContent(TemplateParser.beanToXmlString(nsd));
			return (V) dbnsd;
		}
		if (srck == Vnfd.class && dstv == DBVnfd.class)
		{
			vnfd = (Vnfd) src;
			dbvnfd = new DBVnfd();
			dbvnfd.setVnfd_id(vnfd.getVnfdId());
			dbvnfd.setName(vnfd.getName());
			dbvnfd.setProvider(vnfd.getProvider());
			dbvnfd.setDescription(vnfd.getDescription());
			dbvnfd.setVersion(vnfd.getDescription());
			dbvnfd.setContent(TemplateParser.beanToXmlString(vnfd));
			return (V) dbvnfd;
		}
		return (V) dstv;
	}
}
