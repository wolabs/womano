package com.culabs.nfvo.core;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.culabs.nfvo.model.NFVOException;
import com.culabs.nfvo.util.NfvoSqlSessionFactory;
import com.culabs.unicomportal.dao.DBAclRulesMapper;
import com.culabs.unicomportal.dao.DBAclTablesMapper;
import com.culabs.unicomportal.dao.DBComputeNodesMapper;
import com.culabs.unicomportal.dao.DBFlavorsMapper;
import com.culabs.unicomportal.dao.DBImagesMapper;
import com.culabs.unicomportal.dao.DBInstancesMapper;
import com.culabs.unicomportal.dao.DBInternetsMapper;
import com.culabs.unicomportal.dao.DBJobsMapper;
import com.culabs.unicomportal.dao.DBLbMembersMapper;
import com.culabs.unicomportal.dao.DBLbPoolsMapper;
import com.culabs.unicomportal.dao.DBLbVipsMapper;
import com.culabs.unicomportal.dao.DBNatsMapper;
import com.culabs.unicomportal.dao.DBNetworksMapper;
import com.culabs.unicomportal.dao.DBPortsMapper;
import com.culabs.unicomportal.dao.DBPublicResourcesMapper;
import com.culabs.unicomportal.dao.DBPublicipsMapper;
import com.culabs.unicomportal.dao.DBRouteTablesMapper;
import com.culabs.unicomportal.dao.DBRouterNetworksMapper;
import com.culabs.unicomportal.dao.DBRouterRulesMapper;
import com.culabs.unicomportal.dao.DBRoutersMapper;
import com.culabs.unicomportal.dao.DBSubnetAclTablesMapper;
import com.culabs.unicomportal.dao.DBSubnetRouteTablesMapper;
import com.culabs.unicomportal.dao.DBSubnetsMapper;
import com.culabs.unicomportal.dao.DBVirtualrouterMapper;
import com.culabs.unicomportal.model.db.DBAclRules;
import com.culabs.unicomportal.model.db.DBAclTables;
import com.culabs.unicomportal.model.db.DBComputeNodes;
import com.culabs.unicomportal.model.db.DBFlavors;
import com.culabs.unicomportal.model.db.DBImages;
import com.culabs.unicomportal.model.db.DBInstances;
import com.culabs.unicomportal.model.db.DBInternets;
import com.culabs.unicomportal.model.db.DBJobs;
import com.culabs.unicomportal.model.db.DBLbMembers;
import com.culabs.unicomportal.model.db.DBLbPools;
import com.culabs.unicomportal.model.db.DBLbVips;
import com.culabs.unicomportal.model.db.DBNats;
import com.culabs.unicomportal.model.db.DBNetworks;
import com.culabs.unicomportal.model.db.DBPorts;
import com.culabs.unicomportal.model.db.DBPublicResources;
import com.culabs.unicomportal.model.db.DBPublicips;
import com.culabs.unicomportal.model.db.DBRouteTables;
import com.culabs.unicomportal.model.db.DBRouterNetworks;
import com.culabs.unicomportal.model.db.DBRouterRules;
import com.culabs.unicomportal.model.db.DBRouters;
import com.culabs.unicomportal.model.db.DBSubnetAclTables;
import com.culabs.unicomportal.model.db.DBSubnetRouteTables;
import com.culabs.unicomportal.model.db.DBSubnets;
import com.culabs.unicomportal.model.db.DBSubnetsWithBLOBs;
import com.culabs.unicomportal.model.db.DBVirtualrouter;

/**
 * 
 * @ClassName: UnicomPortalOperator
 * @version 1.0
 */
public class UnicomPortalOperator {

    private static Logger LOGGER = LoggerFactory.getLogger(UnicomPortalOperator.class);

    /**
     * 创建VPC
     * 
     * @param DBRouters
     * @return int
     * @throws Exception
     */
    public static int createRouter(DBRouters dbRouters) throws NFVOException {
        SqlSession sqlsession = null;
        int id = 0;
        try {
            sqlsession = getSqlSessionForUnicomPortal();
            
            sqlsession.getMapper(DBRoutersMapper.class).insert(dbRouters);

            sqlsession.commit();
            
            id = dbRouters.getId();
        } catch (Exception e) {
            sqlsession.rollback();
            LOGGER.error("insert Vpc: {} failed", dbRouters.getName());
            throw new NFVOException(e);
        } finally {
            sqlsession.close();
        }
        return id;
    }
    
    /**
     * 根据uuid查询Router信息
     * 
     * @param DBRouters
     * @return DBRouters
     * @throws NFVOException
     */
    public static DBRouters queryRouterByUUID(String uuid) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBRouters router = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            router = sqlsession.getMapper(DBRoutersMapper.class).selectByUUID(uuid);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("query vpc: {} failed", uuid);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return router;
    }
    
    /**
     * 查询DBPublicIpResource信息列表
     * 
     * @param
     * @return List<DBPublicResources>
     * @throws NFVOException
     */
    public static List<DBPublicResources> queryPublicResourcesList() throws NFVOException
    {
        SqlSession sqlsession = null;
        List<DBPublicResources> dbPublicResourceList = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            dbPublicResourceList = sqlsession.getMapper(DBPublicResourcesMapper.class).selectList();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("queryPublicIpResourceList: {} failed");
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return dbPublicResourceList;
    }

    /**
     * 根据public_resource_uuid查询DBPublicIpResource信息
     * 
     * @param
     * @return List<DBPublicResources>
     * @throws NFVOException
     */
    public static DBPublicResources queryPublicResourcesByUUID(String uuid) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBPublicResources dbPublicResource = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            dbPublicResource = sqlsession.getMapper(DBPublicResourcesMapper.class).selectPublicResourceByUUID(uuid);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("queryPublicResourcesByUUID: {} failed");
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return dbPublicResource;
    }
    
    /**
     * 创建PublicIp
     * 
     * @param DBPublicIp
     * @return int
     * @throws NFVOException
     */
    public static int createPublicIp(DBPublicips dbPublicips) throws NFVOException
    {
        SqlSession sqlsession = null;
        int id = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBPublicipsMapper.class).insert(dbPublicips);
            sqlsession.commit();
            id = dbPublicips.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("insert PublicIp: {} failed", dbPublicips.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    

    /**
     * 创建DBVirtualrouter
     * 
     * @param DBVirtualrouter
     * @return int
     * @throws NFVOException
     */
    public static int createVirtualrouter(DBVirtualrouter portalVirtualRouter) throws NFVOException{
        SqlSession sqlsession = null;
        int id = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBVirtualrouterMapper.class).insert(portalVirtualRouter);
            sqlsession.commit();
            id = portalVirtualRouter.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createVirtualrouter {} failed", portalVirtualRouter.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;        
    }
    
    /**
     * 创建DBInternets
     * 
     * @param DBInternets
     * @return int
     * @throws NFVOException
     */
    public static int createInternet(DBInternets portalInternets) throws NFVOException{
        SqlSession sqlsession = null;
        int id = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBInternetsMapper.class).insert(portalInternets);
            sqlsession.commit();
            id = portalInternets.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createInternet {} failed", portalInternets.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;        
    }

    /**
     * 创建acl
     * 
     * @param DBAclTables
     * @return int
     * @throws NFVOException
     */
    public static int createAclTable(DBAclTables dbAclTable) throws NFVOException
    {

        SqlSession sqlsession = null;
        int id = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBAclTablesMapper.class).insert(dbAclTable);
            sqlsession.commit();
            id = dbAclTable.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("insert AclTable: {} failed", dbAclTable.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
    /**
     * 创建DBSubnetAclTables, acltable与subnet关联表
     * 
     * @param DBSubnetAclTables
     * @return int
     * @throws NFVOException
     */
    public static int createSubnetAclTable(DBSubnetAclTables subnetAclTables) throws NFVOException
    {
        SqlSession sqlsession = null;
        int id = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBSubnetAclTablesMapper.class).insert(subnetAclTables);
            sqlsession.commit();
            id = subnetAclTables.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createSubnetAclTable: {} failed", subnetAclTables.getSubnet_acl_table_uuid());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
    
    /**
     * 根据acltableId查询DBAcl信息
     * 
     * @param acltableId
     * @return DBAclTables
     * @throws NFVOException
     */
    public static DBAclTables queryAclTableByUUID(String acltableId) throws NFVOException
    {

        SqlSession sqlsession = null;
        DBAclTables dbAcl = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            dbAcl = sqlsession.getMapper(DBAclTablesMapper.class).selectByUUID(acltableId);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("query AclTables: {} failed", acltableId);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return dbAcl;
    }
    
    /**
     * 根据subnetId查询DBSubnet信息
     * 
     * @param subnetId
     * @return DBSubnet
     * @throws NFVOException
     */
    public static DBSubnets querySubnetByUUID(String uuid) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBSubnets dbSubnet = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            dbSubnet = sqlsession.getMapper(DBSubnetsMapper.class).selectByUUID(uuid);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("query querySubnetByUUID: {} failed", uuid);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return dbSubnet;
    }
    
    /**
     * 根据name查询DBSubnet信息
     * 
     * @param name
     * @return DBSubnet
     * @throws NFVOException
     */
    public static DBSubnets querySubnetByName(String name) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBSubnets dbSubnet = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            dbSubnet = sqlsession.getMapper(DBSubnetsMapper.class).selectByName(name);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("query querySubnetByName: {} failed", name);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return dbSubnet;
    }
    
    /**
     * 根据name查询DBFlavors信息
     * 
     * @param name
     * @return DBFlavors
     * @throws NFVOException
     */
    public static DBFlavors queryFlavorByName(String name) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBFlavors flavor = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            flavor = sqlsession.getMapper(DBFlavorsMapper.class).selectByName(name);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("queryFlavorByName: {} failed", name);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return flavor;
    }
    
    /**
     * 根据uuid查询DBImages信息
     * 
     * @param uuid
     * @return DBImages
     * @throws NFVOException
     */
    public static DBImages queryImagesByUUID(String uuid) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBImages image = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            image = sqlsession.getMapper(DBImagesMapper.class).selectByUUID(uuid);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("queryImagesByUUID: {} failed", uuid);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return image;
    }
    
    /**
     * @param hostName
     * @return DBComputeNodes
     * @throws NFVOException
     */
    public static DBComputeNodes queryComputeNodeByHostName(String hostName) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBComputeNodes computeNode = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            computeNode = sqlsession.getMapper(DBComputeNodesMapper.class).selectByHostName(hostName);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("queryComputeNodeByHostName: {} failed", hostName);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return computeNode;
    }
    
    /**
     * 创建云主机
     * 
     * @param DBInstances
     * @return int
     * @throws NFVOException
     */
    public static int createInstance(DBInstances dbInstance) throws NFVOException
    {
        SqlSession sqlsession = null;
        int count = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            count = sqlsession.getMapper(DBInstancesMapper.class).insert(dbInstance);
            sqlsession.commit();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("insert Server: {} failed", dbInstance.getDisplay_name());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return count;
    }
    
    
    
    
    /**
	 * 创建 AclRules (unicom portalDB)
	 * @author sz
	 * @param DBAclRule
	 * @return int
	 * @throws NFVOException
	 */
	public static int createAclRules(DBAclRules dbAclRules) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();

			count = sqlsession.getMapper(DBAclRulesMapper.class).insert(dbAclRules);
			sqlsession.commit();
		} catch (Exception e)
		{
			sqlsession.rollback();
//			LOGGER.error("insert acl: {} failed", dbAclRules.getAcl_rule_id());
			throw new NFVOException(e);
		} finally
		{
			sqlsession.close();
		}
		return count;
	}
	
	
	/**
	 * 创建 networks (unicom portalDB)
	 * @author sz
	 * @param  dbNetworks
	 * @return int
	 * @throws NFVOException
	 */
	public static int createNetworks(DBNetworks dbNetworks) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBNetworksMapper.class).insert(dbNetworks);
			count = dbNetworks.getId();
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
	
	/**
	 * 创建 RouterNetwork关联 (unicom portalDB)
	 * @author sz
	 * @param  DBRouterNetworks
	 * @return int
	 * @throws NFVOException
	 */
	public static int createRouterNetwork(DBRouterNetworks dbRouterNetworks) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBRouterNetworksMapper.class).insert(dbRouterNetworks);
			count = dbRouterNetworks.getId();
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
	
    /**
     * 根据network name查询DBNetworks信息
     * 
     * @param network name
     * @return DBNetworks
     * @throws NFVOException
     */
    public static DBNetworks queryNetworkByName(String name) throws NFVOException
    {
        SqlSession sqlsession = null;
        DBNetworks dbNetwork = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            dbNetwork = sqlsession.getMapper(DBNetworksMapper.class).selectByName(name);
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("query queryNetworkByName: {} failed", name);
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return dbNetwork;
    }
    
	
	/**
	 * 创建 public_resources (unicom portalDB)
	 * @author sz
	 * @param  dbPublicResources
	 * @return int
	 * @throws NFVOException
	 */
	public static int createPublicResources(DBPublicResources dbPublicResources) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBPublicResourcesMapper.class).insert(dbPublicResources);
			count = dbPublicResources.getId();
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
	
	/**
	 * 创建 route_tables (unicom portalDB)
	 * @author sz
	 * @param  dbRouteTables
	 * @return int
	 * @throws NFVOException
	 */
	public static int createRouteTables(DBRouteTables dbRouteTables) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBRouteTablesMapper.class).insert(dbRouteTables);
			count = dbRouteTables.getId();
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
	
	
	/**
	 * 创建 routing_rules (unicom portalDB)
	 * @author sz
	 * @param  dbRouterRules
	 * @return int
	 * @throws NFVOException
	 */
	public static int createRouterRules(DBRouterRules dbRouterRules) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBRouterRulesMapper.class).insert(dbRouterRules);
			count = dbRouterRules.getId();
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
	
	
	/**
	 * 创建 subnet_route_tables (unicom portalDB)
	 * @author sz
	 * @param  dbRouterRules
	 * @return int
	 * @throws NFVOException
	 */
	public static int createSubnetRouteTables(DBSubnetRouteTables dbSubnetRouteTables) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBSubnetRouteTablesMapper.class).insert(dbSubnetRouteTables);
			count = dbSubnetRouteTables.getId();
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
	
	
	
	/**
	 * 创建 subnets (unicom portalDB)
	 * @author sz
	 * @param  dbSubnets
	 * @return int
	 * @throws NFVOException
	 */
	public static int createSubnets(DBSubnetsWithBLOBs dbSubnets) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBSubnetsMapper.class).insert(dbSubnets);
			count = dbSubnets.getId();
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
	
    /**
     * 创建LbPools
     * 
     * @param DBLbPools
     * @return int
     * @throws NFVOException
     */
    public static int createLbPools(DBLbPools lbPools) throws NFVOException
    {
    	int id = 0;
        SqlSession sqlsession = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBLbPoolsMapper.class).insert(lbPools);
            sqlsession.commit();
            id = lbPools.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createLbPools: {} failed", lbPools.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
    /**
     * 创建LbVips
     * 
     * @param DBLbVips
     * @return int
     * @throws NFVOException
     */
    public static int createLbVip(DBLbVips lbVips) throws NFVOException
    {
    	int id = 0;
        SqlSession sqlsession = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBLbVipsMapper.class).insert(lbVips);
            sqlsession.commit();
            id = lbVips.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createLbVip: {} failed", lbVips.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
    /**
     * 创建LbMembers
     * 
     * @param DBLbMembers
     * @return int
     * @throws NFVOException
     */
    public static int createLbMember(DBLbMembers lbMembers) throws NFVOException
    {
    	int id = 0;
        SqlSession sqlsession = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBLbMembersMapper.class).insert(lbMembers);
            sqlsession.commit();
            id = lbMembers.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createLbMember: {} failed", lbMembers.getMember_uuid());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
    /**
     * 创建ports
     * 
     * @param DBports
     * @return int
     * @throws NFVOException
     */
    public static int createPort(DBPorts port) throws NFVOException
    {
    	int id = 0;
        SqlSession sqlsession = null;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBPortsMapper.class).insert(port);
            sqlsession.commit();
            id = port.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("createPort: {} failed", port.getPort_uuid());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
    /**
     * 创建nat
     * 
     * @param DBNats
     * @return int
     * @throws NFVOException
     */
    public static int createNat(DBNats dbNats) throws NFVOException
    {

        SqlSession sqlsession = null;
        int id = 0;
        try
        {
            sqlsession = getSqlSessionForUnicomPortal();
            sqlsession.getMapper(DBNatsMapper.class).insert(dbNats);
            sqlsession.commit();
            id = dbNats.getId();
        } catch (Exception e)
        {
            sqlsession.rollback();
            LOGGER.error("insert unicom nat: {} failed", dbNats.getName());
            throw new NFVOException(e);
        } finally
        {
            sqlsession.close();
        }
        return id;
    }
    
	/**
	 * 创建 操作记录jobs (unicom portalDB)
	 * @param  DBjobs
	 * @return int
	 * @throws NFVOException
	 */
	public static int createJobs(DBJobs job) throws NFVOException
	{
		SqlSession sqlsession = null;
		int count = 0;
		try
		{
			sqlsession = getSqlSessionForUnicomPortal();
			sqlsession.getMapper(DBJobsMapper.class).insert(job);
			count = job.getId();
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
	
	
	private static SqlSession getSqlSessionForUnicomPortal() {
		SqlSession sqlsessionForPortal = NfvoSqlSessionFactory.getInstanceForPortal("unicom").openSession(false);
        return sqlsessionForPortal;
    }
}
