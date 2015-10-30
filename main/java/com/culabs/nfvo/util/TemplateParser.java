package com.culabs.nfvo.util;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @ClassName: TemplateParser
 * @Description: TODO(描述类作用)
 * @author 
 * @date 2015年4月22日 下午7:30:33
 * @version 1.0
 */
public class TemplateParser
{
	private static Logger LOGGER = LoggerFactory
			.getLogger(TemplateParser.class);

	public static String beanToXmlString(Object obj)
	{
		String xml = "";
		try
		{
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller marshaller = context.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			Writer w = new StringWriter();
			marshaller.marshal(obj, w);
			xml = w.toString();
		} catch (JAXBException e)
		{
			LOGGER.error("Convert Bean to Xml failed", e);
		}
		return xml;
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlFileInputStreamToBean(InputStream ins, Class<T> clazz)
	{
		if (null == ins)
		{
			return null;
		}
		T bean = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			bean = (T) unmarshaller.unmarshal(ins);
		} catch (JAXBException e)
		{
			LOGGER.error("Parse xml File to Bean failed", e);
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlFileToBean(File tmpl, Class<T> clazz)
	{
		if (null == tmpl || !tmpl.isFile())
		{
			return null;
		}
		T bean = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			bean = (T) unmarshaller.unmarshal(tmpl);
		} catch (JAXBException e)
		{
			LOGGER.error("Parse xml File to Bean failed", e);
		}
		return bean;
	}

	@SuppressWarnings("unchecked")
	public static <T> T xmlToBean(String xml, Class<T> clazz)
	{
		T bean = null;
		try
		{
			JAXBContext context = JAXBContext.newInstance(clazz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Reader reader = new StringReader(xml);
			bean = (T) unmarshaller.unmarshal(reader);
		} catch (JAXBException e)
		{
			LOGGER.error("Parse xml to Bean failed", e);
		}
		return bean;
	}

	public static void main(String[] args)
	{

		// File tmpl = new File(DirUtils.getInstance()
		// .getTemplDir(DirUtils.TEMPLATE_NS).concat("/NSD-vIMS.xml"));
		// System.out.println("tmpl=" + tmpl);
		// Nsd nsd = xmlFileToBean(tmpl, Nsd.class);
		// System.out.println(nsd.getNsdId());
		// System.out.println(nsd.getVersion());
		// System.out.println(nsd.getNsFlavours().getNsFlavour().getFlavourId());
		// String xml = beanToXmlString(nsd);
		// System.out.println("NSD Xml:\n" + xml);

		// File tmpl2 = new File(DirUtils.getInstance()
		// .getTemplDir(DirUtils.TEMPLATE_VNF).concat("/VNFD_P-SCSF.xml"));
		// System.out.println("tmpl2=" + tmpl2);
		// Vnfd nfvd = xmlFileToBean(tmpl2, Vnfd.class);
		// System.out.println(nfvd.getConnectionPoints().getConnectionPoint()
		// .get(0).getDescription());
		// System.out.println(nfvd.getWorkflows().getInit());
		// System.out.println("nfvd=" + nfvd);
		// System.out.println(nfvd.getVnfdId());
		// System.out.println(nfvd.getName());
		// System.out.println(nfvd.getVnfFlavours().getVnfFlavour().get(0)
		// .getVdus().getVdu().get(0).getVduFlavour().getVduFlavorurId());
		// String xml2 = beanToXmlString(nfvd);
		// System.out.println("\n==========================\nNSD Xml:\n" + xml2);

	}
}
