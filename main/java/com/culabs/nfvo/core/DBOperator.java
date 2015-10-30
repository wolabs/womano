package com.culabs.nfvo.core;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.dao.DBAclMapper;
import com.culabs.nfvo.dao.DBAclRuleMapper;
import com.culabs.nfvo.dao.DBAclSubnetMapper;
import com.culabs.nfvo.dao.DBAttributeMapper;
import com.culabs.nfvo.dao.DBGWRouterMapper;
import com.culabs.nfvo.dao.DBHypervisorMapper;
import com.culabs.nfvo.dao.DBInstanceMapper;
import com.culabs.nfvo.dao.DBInstanceServerMapper;
import com.culabs.nfvo.dao.DBMemberMapper;
import com.culabs.nfvo.dao.DBNatMapper;
import com.culabs.nfvo.dao.DBNetworkMapper;
import com.culabs.nfvo.dao.DBNsdMapper;
import com.culabs.nfvo.dao.DBPoolMapper;
import com.culabs.nfvo.dao.DBPortMapper;
import com.culabs.nfvo.dao.DBPublicIpMapper;
import com.culabs.nfvo.dao.DBPublicIpResourceMapper;
import com.culabs.nfvo.dao.DBRoutingRuleMapper;
import com.culabs.nfvo.dao.DBRoutingTableMapper;
import com.culabs.nfvo.dao.DBServerMapper;
import com.culabs.nfvo.dao.DBSubnetMapper;
import com.culabs.nfvo.dao.DBSubnetRoutingTableMapper;
import com.culabs.nfvo.dao.DBTenantMapper;
import com.culabs.nfvo.dao.DBVipMapper;
import com.culabs.nfvo.dao.DBVnfdMapper;
import com.culabs.nfvo.dao.DBVpcMapper;
import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.model.Result;
import com.culabs.nfvo.model.db.DBAcl;
import com.culabs.nfvo.model.db.DBAclRule;
import com.culabs.nfvo.model.db.DBAclSubnet;
import com.culabs.nfvo.model.db.DBAttribute;
import com.culabs.nfvo.model.db.DBAttributeKey;
import com.culabs.nfvo.model.db.DBGWRouter;
import com.culabs.nfvo.model.db.DBHypervisor;
import com.culabs.nfvo.model.db.DBInstance;
import com.culabs.nfvo.model.db.DBInstanceServer;
import com.culabs.nfvo.model.db.DBMember;
import com.culabs.nfvo.model.db.DBNat;
import com.culabs.nfvo.model.db.DBNetwork;
import com.culabs.nfvo.model.db.DBNsd;
import com.culabs.nfvo.model.db.DBPool;
import com.culabs.nfvo.model.db.DBPort;
import com.culabs.nfvo.model.db.DBPublicIp;
import com.culabs.nfvo.model.db.DBPublicIpResource;
import com.culabs.nfvo.model.db.DBRoutingRule;
import com.culabs.nfvo.model.db.DBRoutingTable;
import com.culabs.nfvo.model.db.DBServer;
import com.culabs.nfvo.model.db.DBSubnet;
import com.culabs.nfvo.model.db.DBSubnetRoutingTable;
import com.culabs.nfvo.model.db.DBTenant;
import com.culabs.nfvo.model.db.DBVip;
import com.culabs.nfvo.model.db.DBVnfd;
import com.culabs.nfvo.model.db.DBVpc;
import com.culabs.nfvo.model.nsd.Nsd;
import com.culabs.nfvo.model.vnfd.Vnfd;
import com.culabs.nfvo.util.NFVOUtils;
import com.culabs.nfvo.util.NfvoSqlSessionFactory;

/**
 * 
 * @ClassName: VPCOperator
 * @version 1.0
 */
public class DBOperator
{

	private static Logger LOGGER = LoggerFactory.getLogger(DBOperator.class);

	public static <U, V> Result storeTemplate(Object obj, Class<U> src, Class<V> dst) throws NFVOException
	{
		SqlSession sqlsession = null;
		try
		{
			sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
			if (src == Nsd.class)
			{
				sqlsession.getMapper(DBNsdMapper.class).insertSelective((DBNsd) obj);
			} else if (src == Vnfd.class)
			{
				sqlsession.getMapper(DBVnfdMapper.class).insertSelective((DBVnfd) obj);
			}
			sqlsession.commit();
		} catch (Exception e)
		{
			if (null != sqlsession)
			{
				sqlsession.rollback();
			}
			LOGGER.error("Import template failed. {}", e);
			throw new NFVOException(e);
		} finally
		{
			if (null != sqlsession)
			{
				sqlsession.close();
			}
		}
		return Result.SUCCESS;
	}

	public static <U, V> Result deleteTemplate(String templid, Class<U> src) throws NFVOException
	{
		SqlSession sqlsession = null;
		try
		{
			sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
			if (src == Nsd.class)
			{
				sqlsession.getMapper(DBNsdMapper.class).deleteByPrimaryKey(templid);
			} else if (src == Vnfd.class)
			{
				sqlsession.getMapper(DBVnfdMapper.class).deleteByPrimaryKey(templid);
			}
			sqlsession.commit();
		} catch (Exception e)
		{
			if (null != sqlsession)
			{
				sqlsession.rollback();
			}
			LOGGER.error("delete template: {} failed", templid);
			throw new NFVOException(e);
		} finally
		{
			if (null != sqlsession)
			{
				sqlsession.close();
			}
		}
		return Result.SUCCESS;
	}

	public static List<DBNsd> queryDBNsdList(String type)
	{
		List<DBNsd> list = null;
		SqlSession sqlsession = null;
		try
		{
			sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
			list = sqlsession.getMapper(DBNsdMapper.class).selectList(type);
		} catch (Exception e)
		{
			e.printStackTrace();
		} finally
		{
			if (null != sqlsession)
			{
				sqlsession.close();
			}
		}
		return NFVOUtils.isEmpty(list) ? new ArrayList<DBNsd>(0) : list;
	}

	public static List<DBVnfd> queryDBVnfdList(String templid)
	{
		List<DBVnfd> list = null;
		SqlSession sqlsession = null;
		try
		{
			sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
			list = sqlsession.getMapper(DBVnfdMapper.class).selectList(templid);
		} catch (Exception e)
		{
			LOGGER.error("query template: {} failed", templid);
		} finally
		{
			if (null != sqlsession)
			{
				sqlsession.close();
			}
		}
		return NFVOUtils.isEmpty(list) ? new ArrayList<DBVnfd>(0) : list;
	}

	public static DBNsd queryDBNsd(String templid)
	{
		DBNsd dnnsd = null;
		SqlSession sqlsession = null;
		try
		{
			sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
			dnnsd = sqlsession.getMapper(DBNsdMapper.class).selectByPrimaryKey(templid);
		} catch (Exception e)
		{
			LOGGER.error("delete template: {} failed", templid);
		} finally
		{
			if (null != sqlsession)
			{
				sqlsession.close();
			}
		}
		return NFVOUtils.isEmpty(dnnsd) ? new DBNsd() : dnnsd;
	}

	public static DBVnfd queryDBVnfd(String templid)
	{
		DBVnfd dnvnfd = null;
		SqlSession sqlsession = null;
		try
		{
			sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
			dnvnfd = sqlsession.getMapper(DBVnfdMapper.class).selectByPrimaryKey(templid);
		} catch (Exception e)
		{
			LOGGER.error("delete template: {} failed", templid);
		} finally
		{
			if (null != sqlsession)
			{
				sqlsession.close();
			}
		}
		return NFVOUtils.isEmpty(dnvnfd) ? new DBVnfd() : dnvnfd;
	}

	/**
	 * 根据租户编号查询租户信息
	 * 
	 * @param tenantId
	 * @return DBTenant
	 * @throws NFVOException
	 */
	public static DBTenant queryTenantByPrimaryKey(String tenantId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBTenant tenant = null;
		try
		{
			sqlsession = getSqlSession();
			tenant = sqlsession.getMapper(DBTenantMapper.class).selectByPrimaryKey(tenantId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryTenantByPrimaryKey failed. {}", e);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return tenant;
	}

	/**
	 * 创建VPC
	 * 
	 * @param DBVpc
	 * @return int
	 * @throws NFVOException
	 */
	public static int createVPC(DBVpc dbVpc) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbVpc.getVpc_id())
			{
				LOGGER.error("check your Vpc data you want to create, " + "it must contain a unique routerid!");
				return count;
			}
			count = sqlsession.getMapper(DBVpcMapper.class).insert(dbVpc);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert Vpc: {} failed", dbVpc.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据vpcId查询VPC信息
	 * 
	 * @param DBVpc
	 * @return DBVpc
	 * @throws NFVOException
	 */
	public static DBVpc queryVPC(String vpcId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBVpc dbVpc = null;
		try
		{
			sqlsession = getSqlSession();
			dbVpc = sqlsession.getMapper(DBVpcMapper.class).selectByPrimaryKey(vpcId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("query vpc: {} failed", vpcId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbVpc;
	}

	/**
	 * 查询vpc信息列表
	 * 
	 * @param
	 * @return List<DBGWRouter>
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static List<DBVpc> queryVPCList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBVpc> dbVpcList = null;
		try
		{
			sqlsession = getSqlSession();
			dbVpcList = sqlsession.getMapper(DBVpcMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryVPCList failed" + e);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbVpcList;
	}

	/**
	 * 删除指定vpc
	 * 
	 * @param
	 * @return List<DBGWRouter>
	 * @throws NFVOException
	 */
	public static int deleteVPCById(String vpcId) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBVpcMapper.class).deleteByPrimaryKey(vpcId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteVpcById {} failed" + vpcId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 创建GWRouter
	 * 
	 * @param DBGWRouter
	 * @return int
	 * @throws NFVOException
	 */
	public static int createGWRouter(DBGWRouter dbGWRouter) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbGWRouter.getVpc_id())
			{
				LOGGER.error("check your router data you want to create, " + "it must contain a unique routerid!");
				return count;
			}
			count = sqlsession.getMapper(DBGWRouterMapper.class).insert(dbGWRouter);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert router: {} failed", dbGWRouter.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据routerId查询GWRouter信息
	 * 
	 * @param routerId
	 * @return DBGWRouter
	 * @throws NFVOException
	 */
	public static DBGWRouter queryDBGWRouter(String routerId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBGWRouter dbGWRouter = null;
		try
		{
			sqlsession = getSqlSession();
			dbGWRouter = sqlsession.getMapper(DBGWRouterMapper.class).selectByPrimaryKey(routerId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("query GWRouter: {} failed", routerId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbGWRouter;
	}

	/**
	 * 如果为空则查询所有router信息
	 * 
	 * @param routerType
	 * @return List<DBGWRouter>
	 * @throws NFVOException
	 */
	public static List<DBGWRouter> queryGWRouterList(String routerType) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBGWRouter> DBGWRouterList = null;
		try
		{
			sqlsession = getSqlSession();
			DBGWRouterList = sqlsession.getMapper(DBGWRouterMapper.class).selectList(routerType);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryRouterList failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return DBGWRouterList;
	}

	/**
	 * 删除指定GWRouter
	 * 
	 * @param
	 * @return List<DBGWRouter>
	 * @throws NFVOException
	 */
	public static int deleteGWRouterById(String routerId) throws NFVOException
	{

		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBGWRouterMapper.class).deleteByPrimaryKey(routerId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteGWRouterById {} failed" + routerId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 创建acl
	 * 
	 * @param DBAcl
	 * @return int
	 * @throws NFVOException
	 */
	public static int createAcl(DBAcl dbAcl) throws NFVOException
	{

		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbAcl.getVpc_id())
			{
				LOGGER.error("check your acl data you want to create, " + "it must contain a unique aclId!");
				return count;
			}
			count = sqlsession.getMapper(DBAclMapper.class).insert(dbAcl);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert acl: {} failed", dbAcl.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据aclId查询DBAcl信息
	 * 
	 * @param aclId
	 * @return DBAcl
	 * @throws NFVOException
	 */
	public static DBAcl queryAcl(String aclId) throws NFVOException
	{

		SqlSession sqlsession = null;
		DBAcl dbAcl = null;
		try
		{
			sqlsession = getSqlSession();
			dbAcl = sqlsession.getMapper(DBAclMapper.class).selectByPrimaryKey(aclId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("query dbAcl: {} failed", aclId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbAcl;
	}

	/**
	 * 查询DBAcl信息列表
	 * 
	 * @param
	 * @return List<DBAcl>
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static List<DBAcl> queryAclList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBAcl> dbAclList = null;
		try
		{
			sqlsession = getSqlSession();
			dbAclList = sqlsession.getMapper(DBAclMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("querySubnetList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbAclList;
	}

	/**
	 * 删除指定dbAcl
	 * 
	 * @param aclId
	 * @return
	 * @throws NFVOException
	 */
	public static int deleteAclById(String aclId) throws NFVOException
	{

		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBAclMapper.class).deleteByPrimaryKey(aclId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteAclById {} failed" + aclId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 创建network
	 * 
	 * @param DBNetwork
	 * @return int
	 * @throws NFVOException
	 */
	public static int createNetwork(DBNetwork dbNetwork) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbNetwork.getNetwork_id())
			{
				LOGGER.error("check your network data you want to create, " + "it must contain a unique networkId!");
				return count;
			}
			count = sqlsession.getMapper(DBNetworkMapper.class).insert(dbNetwork);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert acl: {} failed", dbNetwork.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据networkId查询DBNetwork信息
	 * 
	 * @param networkId
	 * @return DBNetwork
	 * @throws NFVOException
	 */
	public static DBNetwork queryNetworkById(String networkId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBNetwork dbNetwork = null;
		try
		{
			sqlsession = getSqlSession();
			dbNetwork = sqlsession.getMapper(DBNetworkMapper.class).selectByPrimaryKey(networkId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("query network by id: {} failed", networkId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbNetwork;
	}

	/**
	 * 查询DBNetwork信息列表
	 * 
	 * @param
	 * @return List<DBNetwork>
	 * @throws NFVOException
	 */
	public static List<DBNetwork> queryNetworkList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBNetwork> dbNetworkList = null;
		try
		{
			sqlsession = getSqlSession();
			dbNetworkList = sqlsession.getMapper(DBNetworkMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryNetworkList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbNetworkList;
	}

	/**
	 * 删除指定DBNetwork
	 * 
	 * @param networkId
	 * @return
	 * @throws NFVOException
	 */
	public static int deleteNetworkById(String networkId) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBNetworkMapper.class).deleteByPrimaryKey(networkId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteAclById {} failed" + networkId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 创建subnet
	 * 
	 * @param DBSubnet
	 * @return int
	 * @throws NFVOException
	 */
	public static int createSubnet(DBSubnet dbSubnet) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbSubnet.getSubnet_id())
			{
				LOGGER.error("check your Subnet data you want to create, " + "it must contain a unique subnetId!");
				return count;
			}
			count = sqlsession.getMapper(DBSubnetMapper.class).insert(dbSubnet);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert subnet: {} failed", dbSubnet.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据subnetId查询DBSubnet信息
	 * 
	 * @param subnetId
	 * @return DBSubnet
	 * @throws NFVOException
	 */
	public static DBSubnet querySubnetById(String subnetId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBSubnet dbSubnet = null;
		try
		{
			sqlsession = getSqlSession();
			dbSubnet = sqlsession.getMapper(DBSubnetMapper.class).selectByPrimaryKey(subnetId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("query querySubnetById: {} failed", subnetId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbSubnet;
	}

	/**
	 * 查询DBSubnet信息列表
	 * 
	 * @param
	 * @return List<DBSubnet>
	 * @throws NFVOException
	 */
	public static List<DBSubnet> querySubnetList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBSubnet> dbSubnetList = null;
		try
		{
			sqlsession = getSqlSession();
			dbSubnetList = sqlsession.getMapper(DBSubnetMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("querySubnetList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbSubnetList;
	}

	/**
	 * 创建Port
	 * 
	 * @param DBPort
	 * @return int
	 * @throws NFVOException
	 */
	public static int createPort(DBPort dbPort) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbPort.getPort_id())
			{
				LOGGER.error("check your Port data you want to create, " + "it must contain a unique portId!");
				return count;
			}
			count = sqlsession.getMapper(DBPortMapper.class).insert(dbPort);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert acl: {} failed", dbPort.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据portId查询DBPort信息
	 * 
	 * @param portId
	 * @return DBPort
	 * @throws NFVOException
	 */
	public static DBPort queryPortById(String portId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBPort dbPort = null;
		try
		{
			sqlsession = getSqlSession();
			dbPort = sqlsession.getMapper(DBPortMapper.class).selectByPrimaryKey(portId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryPortById: {} failed", portId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbPort;
	}

	/**
	 * 创建AclRule
	 * 
	 * @param DBAclRule
	 * @return int
	 * @throws NFVOException
	 */
	public static int createAclRule(DBAclRule dbAclRule) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbAclRule.getAcl_rule_id())
			{
				LOGGER.error("check your AclRule data you want to create, " + "it must contain a unique aclRuleId!");
				return count;
			}
			count = sqlsession.getMapper(DBAclRuleMapper.class).insert(dbAclRule);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert acl: {} failed", dbAclRule.getAcl_rule_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据aclRuleId查询DBAclRule信息
	 * 
	 * @param aclRuleId
	 * @return DBAclRule
	 * @throws NFVOException
	 */
	public static DBAclRule queryAclRuleById(String aclRuleId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBAclRule dbAclRule = null;
		try
		{
			sqlsession = getSqlSession();
			dbAclRule = sqlsession.getMapper(DBAclRuleMapper.class).selectByPrimaryKey(aclRuleId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryAclRuleById: {} failed", aclRuleId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbAclRule;
	}

	/**
	 * 创建AclSubnet, acl与subnet关联表
	 * 
	 * @param DBAclSubnet
	 * @return int
	 * @throws NFVOException
	 */
	public static int createAclSubnet(DBAclSubnet dbAclSubnet) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbAclSubnet.getAcl_subnet_id())
			{
				LOGGER.error("check your AclSubnet data you want to create, " + "it must contain a unique aclSubnetId!");
				return count;
			}
			count = sqlsession.getMapper(DBAclSubnetMapper.class).insert(dbAclSubnet);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert aclsubnet: {} failed", dbAclSubnet.getAcl_subnet_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据aclSubnetId查询DBAclSubnet信息
	 * 
	 * @param aclSubnetId
	 * @return DBAclSubnet
	 * @throws NFVOException
	 */
	public static DBAclSubnet queryAclSubnetById(String aclSubnetId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBAclSubnet dbAclSubnet = null;
		try
		{
			sqlsession = getSqlSession();
			dbAclSubnet = sqlsession.getMapper(DBAclSubnetMapper.class).selectByPrimaryKey(aclSubnetId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryAclSubnetById: {} failed", aclSubnetId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbAclSubnet;
	}

	/**
	 * 创建RoutingTable
	 * 
	 * @param DBRoutingTable
	 * @return int
	 * @throws NFVOException
	 */
	public static int createRoutingTable(DBRoutingTable dbRoutingTable) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbRoutingTable.getRouting_table_id())
			{
				LOGGER.error("check your RoutingTable data you want to create, "
						+ "it must contain a unique routingTableId!");
				return count;
			}
			count = sqlsession.getMapper(DBRoutingTableMapper.class).insert(dbRoutingTable);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert routingTable: {} failed", dbRoutingTable.getRouting_table_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 查询DBRoutingTable信息列表
	 * 
	 * @param
	 * @return List<DBRoutingTable>
	 * @throws NFVOException
	 */
	public static List<DBRoutingTable> queryRoutingTableList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBRoutingTable> dbRoutingTableList = null;
		try
		{
			sqlsession = getSqlSession();
			dbRoutingTableList = sqlsession.getMapper(DBRoutingTableMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryRoutingTableList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbRoutingTableList;
	}

	/**
	 * 根据dbRoutingTableId查询DBRoutingTable信息
	 * 
	 * @param dbRoutingTableId
	 * @return DBRoutingTable
	 * @throws NFVOException
	 */
	public static DBRoutingTable queryRoutingTableById(String dbRoutingTableId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBRoutingTable dbRoutingTable = null;
		try
		{
			sqlsession = getSqlSession();
			dbRoutingTable = sqlsession.getMapper(DBRoutingTableMapper.class).selectByPrimaryKey(dbRoutingTableId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryDBRoutingTableById: {} failed", dbRoutingTableId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbRoutingTable;
	}

	/**
	 * 创建NAT
	 * 
	 * @param DBNat
	 * @return int
	 * @throws NFVOException
	 */
	public static int createNat(DBNat dbNat) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbNat.getNat_id())
			{
				LOGGER.error("check your dbNat data you want to create, " + "it must contain a unique natId!");
				return count;
			}
			count = sqlsession.getMapper(DBNatMapper.class).insert(dbNat);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert NAT: {} failed", dbNat.getNat_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据dbNatId查询DBNat信息
	 * 
	 * @param dbNat
	 * @return DBNat
	 * @throws NFVOException
	 */
	public static DBNat queryNatById(String natId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBNat dbNat = null;
		try
		{
			sqlsession = getSqlSession();
			dbNat = sqlsession.getMapper(DBNatMapper.class).selectByPrimaryKey(natId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryNatById: {} failed", natId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbNat;
	}

	/**
	 * 创建PublicIpResource
	 * 
	 * @param DBPublicIpResource
	 * @return int
	 * @throws NFVOException
	 */
	public static int createPublicIpResource(DBPublicIpResource dbPublicIpResource) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbPublicIpResource.getPublic_ip_resource_id())
			{
				LOGGER.error("check your dbPublicIpResource data you want to create, "
						+ "it must contain a unique publicIpResourceId!");
				return count;
			}
			count = sqlsession.getMapper(DBPublicIpResourceMapper.class).insert(dbPublicIpResource);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert PublicIpResource: {} failed", dbPublicIpResource.getPublic_ip_resource_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据dbPublicIpResourceId查询DBPublicIpResource信息
	 * 
	 * @param dbPublicIpResourceId
	 * @return DBPublicIpResource
	 * @throws NFVOException
	 */
	public static DBPublicIpResource queryPublicIpResourceById(String dbPublicIpResourceId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBPublicIpResource dbPublicIpResource = null;
		try
		{
			sqlsession = getSqlSession();
			dbPublicIpResource = sqlsession.getMapper(DBPublicIpResourceMapper.class).selectByPrimaryKey(
					dbPublicIpResourceId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryPublicIpResourceById: {} failed", dbPublicIpResourceId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbPublicIpResource;
	}

	/**
	 * 查询DBPublicIpResource信息列表
	 * 
	 * @param
	 * @return List<DBPublicIpResource>
	 * @throws NFVOException
	 */
	public static List<DBPublicIpResource> queryPublicIpResourceList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBPublicIpResource> dbPublicIpResourceList = null;
		try
		{
			sqlsession = getSqlSession();
			dbPublicIpResourceList = sqlsession.getMapper(DBPublicIpResourceMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryPublicIpResourceList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbPublicIpResourceList;
	}

	/**
	 * 创建PublicIp
	 * 
	 * @param DBPublicIp
	 * @return int
	 * @throws NFVOException
	 */
	public static int createPublicIp(DBPublicIp dbPublicIp) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbPublicIp.getPublic_ip_id())
			{
				LOGGER.error("check your dbPublicIp data you want to create, " + "it must contain a unique publicIpId!");
				return count;
			}
			count = sqlsession.getMapper(DBPublicIpMapper.class).insert(dbPublicIp);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert PublicIp: {} failed", dbPublicIp.getPublic_ip_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据dbPublicIpId查询DBPublicIp信息
	 * 
	 * @param dbPublicIpId
	 * @return DBPublicIp
	 * @throws NFVOException
	 */
	public static DBPublicIp queryPublicIpById(String dbPublicIpId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBPublicIp dbPublicIp = null;
		try
		{
			sqlsession = getSqlSession();
			dbPublicIp = sqlsession.getMapper(DBPublicIpMapper.class).selectByPrimaryKey(dbPublicIpId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryPublicIpById: {} failed", dbPublicIpId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbPublicIp;
	}

	/**
	 * 查询DBPublicIp信息列表
	 * 
	 * @param
	 * @return List<DBPublicIp>
	 * @throws NFVOException
	 */
	public static List<DBPublicIp> queryPublicIpList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBPublicIp> dbPublicIpList = null;
		try
		{
			sqlsession = getSqlSession();
			dbPublicIpList = sqlsession.getMapper(DBPublicIpMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryPublicIpList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbPublicIpList;
	}

	/**
	 * 创建Server
	 * 
	 * @param DBServer
	 * @return int
	 * @throws NFVOException
	 */
	public static int createServer(DBServer dbServer) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbServer.getServer_id())
			{
				LOGGER.error("check your dbServer data you want to create, " + "it must contain a unique serverId!");
				return count;
			}
			count = sqlsession.getMapper(DBServerMapper.class).insert(dbServer);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert Server: {} failed", dbServer.getServer_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * @throws NFVOException
	 * 
	 * @Title: deleteServerById
	 * @Description: TODO
	 * @param @param serverId
	 * @param @return
	 * @return int
	 * @throws
	 */
	public static int deleteServerById(String serverId) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBServerMapper.class).deleteByPrimaryKey(serverId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("delete Server: {} failed", serverId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 查询DBServer信息列表
	 * 
	 * @param
	 * @return List<DBServer>
	 * @throws NFVOException
	 */
	public static List<DBServer> queryServerList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBServer> dbServerList = null;
		try
		{
			sqlsession = getSqlSession();
			dbServerList = sqlsession.getMapper(DBServerMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryServerList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbServerList;
	}

	/**
	 * 根据dbServerId查询DBServer信息
	 * 
	 * @param dbServerId
	 * @return DBServer
	 * @throws NFVOException
	 */
	public static DBServer queryServerById(String dbServerId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBServer dbServer = null;
		try
		{
			sqlsession = getSqlSession();
			dbServer = sqlsession.getMapper(DBServerMapper.class).selectByPrimaryKey(dbServerId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryServerById: {} failed", dbServerId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbServer;
	}

	/**
	 * 创建SubnetRoutingTable
	 * 
	 * @param DBSubnetRoutingTable
	 * @return int
	 * @throws NFVOException
	 */
	public static int createSubnetRoutingTable(DBSubnetRoutingTable dbSubnetRoutingTable) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbSubnetRoutingTable.getSubnet_routing_table_id())
			{
				LOGGER.error("check your SubnetRoutingTable data you want to create, "
						+ "it must contain a unique subnetRoutingTableId!");
				return count;
			}
			count = sqlsession.getMapper(DBSubnetRoutingTableMapper.class).insert(dbSubnetRoutingTable);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert subnetRoutingTable: {} failed", dbSubnetRoutingTable.getName());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据subnetId查询DBSubnetRoutingTable信息
	 * 
	 * @param subnetId
	 * @return DBSubnetRoutingTable
	 * @throws NFVOException
	 */
	public static DBSubnetRoutingTable querySubnetRoutingTableById(String subnetId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBSubnetRoutingTable dbSubnetRoutingTable = null;
		try
		{
			sqlsession = getSqlSession();
			dbSubnetRoutingTable = sqlsession.getMapper(DBSubnetRoutingTableMapper.class).selectByPrimaryKey(subnetId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("querySubnetRoutingTableById: {} failed", subnetId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbSubnetRoutingTable;
	}

	/**
	 * 创建Hypervisor
	 * 
	 * @param DBHypervisor
	 * @return int
	 * @throws NFVOException
	 */
	public static int createHypervisor(DBHypervisor dbHypervisor) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbHypervisor.getHypervisor_id())
			{
				LOGGER.error("check your createHypervisor data you want to create, "
						+ "it must contain a unique hypervisor id!");
				return count;
			}
			count = sqlsession.getMapper(DBHypervisorMapper.class).insert(dbHypervisor);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert hypervisor: {} failed", dbHypervisor.getHypervisor_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 更新Hypervisor
	 * 
	 * @param DBHypervisor
	 * @return int
	 * @throws NFVOException
	 */
	public static int updateHypervisor(DBHypervisor dbHypervisor) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBHypervisorMapper.class).updateByPrimaryKeySelective(dbHypervisor);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("updateHypervisor: {} failed", dbHypervisor.getHypervisor_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据hypervisorId查询Hypervisor信息
	 * 
	 * @param hypervisorId
	 * @return DBHypervisor
	 * @throws NFVOException
	 */
	public static DBHypervisor queryHypervisorById(String hypervisorId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBHypervisor dbHypervisor = null;
		try
		{
			sqlsession = getSqlSession();
			dbHypervisor = sqlsession.getMapper(DBHypervisorMapper.class).selectByPrimaryKey(hypervisorId);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryHypervisorIdById: {} failed", hypervisorId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbHypervisor;
	}

	/**
	 * 创建服务实例信息
	 * 
	 * @return DBInstance
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static int createInstance(DBInstance dbInstance) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBInstanceMapper.class).insert(dbInstance);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert Instance: {} failed", dbInstance.getInstance_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据instanceId查询服务实例信息
	 * 
	 * @param instanceId
	 * @return DBInstance
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static DBInstance queryInstanceById(String instanceId) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBInstance dbInstance = null;
		try
		{
			sqlsession = getSqlSession();
			dbInstance = sqlsession.getMapper(DBInstanceMapper.class).selectByPrimaryKey(instanceId);
		} catch (Exception e)
		{
			LOGGER.error("queryInstanceById: {} failed", instanceId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbInstance;
	}

	/**
	 * 根据查询服务实例信息列表
	 * 
	 * @return List<DBInstance>
	 * @throws NFVOException
	 */
	public static List<DBInstance> queryInstanceList() throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBInstance> dbInstanceList = null;
		try
		{
			sqlsession = getSqlSession();
			dbInstanceList = sqlsession.getMapper(DBInstanceMapper.class).selectList();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryInstanceList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbInstanceList;
	}

	/**
	 * 根据查询服务实例信息列表
	 * 
	 * @return List<DBInstance>
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static List<DBInstance> queryInstanceListByType(String type) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBInstance> dbInstanceList = null;
		try
		{
			sqlsession = getSqlSession();
			dbInstanceList = sqlsession.getMapper(DBInstanceMapper.class).selectListByType(type);
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("queryInstanceList: {} failed");
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbInstanceList;
	}

	/**
	 * 更新DBInstance
	 * 
	 * @param DBInstance
	 * @return int
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static int updateInstance(DBInstance dbInstance) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBInstanceMapper.class).updateByPrimaryKeySelective(dbInstance);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("updateInstance: {} failed", dbInstance.getInstance_name());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 删除指定DBInstance
	 * 
	 * @param instanceId
	 * @return
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static int deleteInstanceById(String instanceId) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBInstanceMapper.class).deleteByPrimaryKey(instanceId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteInstanceById {} failed" + instanceId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 创建服务实例信息
	 * 
	 * @return DBInstanceServer
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static int createInstanceServer(DBInstanceServer dbInstanceServer) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBInstanceServerMapper.class).insert(dbInstanceServer);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert middle table instanceServer: {} failed", dbInstanceServer.getId());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 根据instanceId查询关联的
	 * 
	 * @param instanceId
	 * @return List<DBInstanceServer>
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static List<DBInstanceServer> queryServerListByInstanceId(String instanceId) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBInstanceServer> dbInstanceServer = null;
		try
		{
			sqlsession = getSqlSession();
			dbInstanceServer = sqlsession.getMapper(DBInstanceServerMapper.class).selectListByInstanceId(instanceId);
		} catch (Exception e)
		{
			LOGGER.error("queryServerListByInstanceId: {} failed", instanceId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbInstanceServer;
	}

	/**
	 * 删除指定DBInstanceServer
	 * 
	 * @param Id
	 * @return
	 * @throws NFVOException
	 * @throws NFVOException
	 */
	public static int deleteInstanceServerById(int id) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBInstanceServerMapper.class).deleteByPrimaryKey(id);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteInstanceById {} failed" + id);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	/**
	 * 删除指定DBInstanceServer
	 * 
	 * @param Id
	 * @return
	 * @throws NFVOException
	 */
	public static int deleteByInstanceId(String instanceId) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBInstanceServerMapper.class).deleteByInstanceId(instanceId);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("deleteByInstanceId {} failed" + instanceId);
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	private static SqlSession getSqlSession()
	{
		SqlSession sqlsession = NfvoSqlSessionFactory.getInstance().openSession(false);
		return sqlsession;
	}

	public static int createRoutingRule(DBRoutingRule dbRoutingRule) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();

			if (null == dbRoutingRule.getRouting_table_id())
			{
				LOGGER.error("check your RoutingTable data you want to create, "
						+ "it must contain a unique routingTableId!");
				return count;
			}
			count = sqlsession.getMapper(DBRoutingRuleMapper.class).insert(dbRoutingRule);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			LOGGER.error("insert routingTable: {} failed", dbRoutingRule.getRouting_table_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static int insertPool(DBPool dbpool) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBPoolMapper.class).insert(dbpool);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static DBPool queryPool(String id) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBPool dbpool = null;
		try
		{
			sqlsession = getSqlSession();
			dbpool = sqlsession.getMapper(DBPoolMapper.class).selectByPrimaryKey(id);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbpool;
	}

	public static List<DBPool> queryPools(DBPool pool) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBPool> dbpools = null;
		try
		{
			sqlsession = getSqlSession();
			dbpools = sqlsession.getMapper(DBPoolMapper.class).selectList(pool);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbpools;
	}

	public static int deletePool(DBPool pool) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBPoolMapper.class).deleteByPrimaryKey(pool.getId());
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static int insertVip(DBVip dbvip) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBVipMapper.class).insert(dbvip);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static DBVip queryVip(String id) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBVip dbvip = null;
		try
		{
			sqlsession = getSqlSession();
			dbvip = sqlsession.getMapper(DBVipMapper.class).selectByPrimaryKey(id);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbvip;
	}

	public static List<DBVip> queryVips(DBVip vip) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBVip> dbvips = null;
		try
		{
			sqlsession = getSqlSession();
			dbvips = sqlsession.getMapper(DBVipMapper.class).selectList(vip);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbvips;
	}

	public static int deleteVip(DBVip vip) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBVipMapper.class).deleteByPrimaryKey(vip.getId());
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static int insertMember(DBMember dbmember) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBMemberMapper.class).insert(dbmember);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static DBMember queryMember(String id) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBMember dbmember = null;
		try
		{
			sqlsession = getSqlSession();
			dbmember = sqlsession.getMapper(DBMemberMapper.class).selectByPrimaryKey(id);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbmember;
	}

	public static List<DBMember> queryMembers(DBMember dbm) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBMember> dbmembers = null;
		try
		{
			sqlsession = getSqlSession();
			dbmembers = sqlsession.getMapper(DBMemberMapper.class).selectList(dbm);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbmembers;
	}

	public static int deleteMember(DBMember dbm) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBMemberMapper.class).deleteByPrimaryKey(dbm.getId());
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static int insertAttribute(DBAttribute dbattribute) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			count = sqlsession.getMapper(DBAttributeMapper.class).insert(dbattribute);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

	public static DBAttribute queryAttribute(DBAttributeKey key) throws NFVOException
	{
		SqlSession sqlsession = null;
		DBAttribute dbattribute = null;
		try
		{
			sqlsession = getSqlSession();
			dbattribute = sqlsession.getMapper(DBAttributeMapper.class).selectByPrimaryKey(key);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbattribute;
	}

	public static List<DBAttribute> queryAttributes(DBAttribute dbattr) throws NFVOException
	{
		SqlSession sqlsession = null;
		List<DBAttribute> dbattributes = null;
		try
		{
			sqlsession = getSqlSession();
			dbattributes = sqlsession.getMapper(DBAttributeMapper.class).selectList(dbattr);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return dbattributes;
	}

	public static int deleteAttribute(DBAttribute dbattr) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSession();
			DBAttributeKey key = new DBAttributeKey();
			key.setName(dbattr.getName());
			key.setType(dbattr.getType());
			key.setValue(dbattr.getValue());
			count = sqlsession.getMapper(DBAttributeMapper.class).deleteByPrimaryKey(key);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}

}
