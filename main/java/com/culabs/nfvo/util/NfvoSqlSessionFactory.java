package com.culabs.nfvo.util;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @ClassName: NfvoSqlSessionFactory
 * @Description: TODO
 * @author 
 * @date 2015年4月23日 下午3:50:53
 * @version 1.0
 */
public class NfvoSqlSessionFactory
{
	private static SqlSessionFactory sqlSessionFactory = null;
	//联通门户 sz
	private static SqlSessionFactory sqlSessionFactoryForPortal = null;

	private NfvoSqlSessionFactory()
	{
		String rs = DirUtils.getInstance().getConfigDir() + "mybatis-config.xml";
		Reader reader = null;
		try
		{
			reader = new FileReader(rs);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		// sqlSessionFactory.getConfiguration().addMapper(UserService.class);
	}

	public static SqlSessionFactory getInstance()
	{
		if (sqlSessionFactory == null)
			synchronized (NfvoSqlSessionFactory.class)
			{
				if (sqlSessionFactory == null)
				{
					String rs = DirUtils.getInstance().getConfigDir() + "mybatis-config.xml";
					Reader reader = null;
					try
					{
						reader = new FileReader(rs);
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
				}
			}
		return sqlSessionFactory;
	}
	
	
	//sz
	private NfvoSqlSessionFactory(String eid)
	{
		String rs = DirUtils.getInstance().getConfigDir() + "mybatis-config.xml";
		Reader reader = null;
		try
		{
			reader = new FileReader(rs);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		sqlSessionFactoryForPortal = new SqlSessionFactoryBuilder().build(reader,eid);
		// sqlSessionFactory.getConfiguration().addMapper(UserService.class);
	}
	//联通门户db sz
	public static SqlSessionFactory getInstanceForPortal(String eid)
	{
		if (sqlSessionFactoryForPortal == null)
			synchronized (NfvoSqlSessionFactory.class)
			{
				if (sqlSessionFactoryForPortal == null)
				{
					String rs = DirUtils.getInstance().getConfigDir() + "mybatis-config.xml";
					Reader reader = null;
					try
					{
						reader = new FileReader(rs);
					} catch (IOException e)
					{
						e.printStackTrace();
					}
					sqlSessionFactoryForPortal = new SqlSessionFactoryBuilder().build(reader,eid);
				}
			}
		return sqlSessionFactoryForPortal;
	}

	public static void main(String[] args)
	{
		/*DBUsers dbUsers= new DBUsers();
		dbUsers.setActive(true);
		dbUsers.setCreated_at(new Date());
		dbUsers.setDeleted(false);
		dbUsers.setEmail("");
//		dbUsers.setId(id);
		dbUsers.setIs_super(true);
		dbUsers.setLast_login(new Date());
		dbUsers.setPassword("123456");
		dbUsers.setPhone("1234567890");
		dbUsers.setRegion("南京");
		dbUsers.setUpdated_at(new Date());
		dbUsers.setUsername("shizhan1");
		
		try {
			createUserUnicomPortal(dbUsers);
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		// importNSD();
		// importVNFD("VNFD_P-SCSF.xml");
		// importVNFD("VNFD_I&S.xml");
		// importVNFD("VNFD_HSS.xml");
		// importVNFD("VNFD_XDMS.xml");
		
		
//		DBAclRules dbAclRules = new DBAclRules();
//		dbAclRules.setAcl_rule_uuid("1212");
//		dbAclRules.setName("testname");
//		dbAclRules.setDirection("direction");
//		dbAclRules.setPort_range_min(123);
//		dbAclRules.setPort_range_max(456);
//		dbAclRules.setProtocol("tcp udp");
//		dbAclRules.setPrefix("sd");
//		dbAclRules.setStrategy("ddg");
//		dbAclRules.setCreated_at(new Date());
//		try {
//			UnicomPortalOperator.createAclRules(dbAclRules);
//		} catch (NFVOException e) {
//			e.printStackTrace();
//		}
		
	}

	@SuppressWarnings("unused")
	private static void importNSD()
	{
		// File tmpl = new File(DirUtils.getInstance()
		// .getTemplDir(DirUtils.TEMPLATE_NS).concat("/NSD-vIMS.xml"));
		// Nsd nsd = TemplateParser.xmlFileToBean(tmpl, Nsd.class);
		// System.out.println(nsd.getNsdId());
		// System.out.println(nsd.getVersion());
		// System.out.println(nsd.getNsFlavours().getNsFlavour().get(0));
		// String xml = TemplateParser.beanToXmlString(nsd);
		//
		// NfvoSqlSessionFactory.getInstance();
		// SqlSession sqlsession = NfvoSqlSessionFactory.getSqlSessionFactory()
		// .openSession(false);
		// DBNsd dbnsd = new DBNsd();
		// dbnsd.setTemplate_id(nsd.getNsdId());
		// dbnsd.setName(nsd.getName());
		// dbnsd.setProvider(nsd.getProvider());
		// dbnsd.setVersion(String.valueOf(nsd.getVersion()));
		// dbnsd.setTemplate_id(nsd.getType());
		// dbnsd.setDescription(nsd.getDescription());
		// dbnsd.setContent(xml);
		// try
		// {
		// sqlsession.getMapper(DBNsdMapper.class).insertSelective(dbnsd);
		// sqlsession.commit();
		// } catch (Exception e)
		// {
		// sqlsession.rollback();
		// System.out.println(e);
		// }
		// dbnsd = sqlsession.getMapper(DBNsdMapper.class).selectByPrimaryKey(
		// "vIMS");
		// System.out.println(">>>" + dbnsd);
		// List<DBNsd> dbnsds = sqlsession.getMapper(DBNsdMapper.class)
		// .selectList(null);
		// System.out.println(">>>" + dbnsds);
	}

	@SuppressWarnings("unused")
	private static void importVNFD(String file)
	{
		// File tmpl = new
		// File(DirUtils.getInstance().getTemplDir(DirUtils.TEMPLATE_VNF).concat("/"
		// + file));
		// Vnfd vnfd = TemplateParser.xmlFileToBean(tmpl, Vnfd.class);
		// String xml = TemplateParser.beanToXmlString(vnfd);
		// NfvoSqlSessionFactory.getInstance();
		// SqlSession sqlsession =
		// NfvoSqlSessionFactory.getSqlSessionFactory().openSession(false);
		// DBVnfd dbvnfd = new DBVnfd();
		// dbvnfd.setVnfd_id(vnfd.getVnfdId());
		// dbvnfd.setName(vnfd.getName());
		// dbvnfd.setProvider(vnfd.getProvider());
		// dbvnfd.setVersion(String.valueOf(vnfd.getVersion()));
		// dbvnfd.setDescription(vnfd.getDescription());
		// dbvnfd.setContent(xml);
		// try
		// {
		// sqlsession.getMapper(DBVnfdMapper.class).insertSelective(dbvnfd);
		// sqlsession.commit();
		// } catch (Exception e)
		// {
		// sqlsession.rollback();
		// System.out.println(e);
		// }
		// dbvnfd =
		// sqlsession.getMapper(DBVnfdMapper.class).selectByPrimaryKey("vIMS");
		// System.out.println(">>>" + dbvnfd);
		// List<DBVnfd> dbnsds =
		// sqlsession.getMapper(DBVnfdMapper.class).selectList(null);
		// System.out.println(">>>" + dbnsds);
	}
	
	//for test sz
//	public static int createUserUnicomPortal(DBUsers dbUser) throws Exception
//	{
//		SqlSession sqlsessionForPortal = null;
//		int count = 0;
//		try
//		{
//			sqlsessionForPortal = NfvoSqlSessionFactory.getInstanceForPortal("unicom").openSession(false);
//
//			count = sqlsessionForPortal.getMapper(DBUserMapper.class).insert(dbUser);
//			sqlsessionForPortal.commit();
//		} catch (Exception e)
//		{
//			sqlsessionForPortal.rollback();
//			throw new Exception(e);
//		} finally
//		{
//			sqlsessionForPortal.close();
//		}
//		return count;
//	}
}
