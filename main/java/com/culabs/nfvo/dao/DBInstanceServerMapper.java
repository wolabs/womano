package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBInstanceServer;

public interface DBInstanceServerMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance_server
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance_server
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int insert(DBInstanceServer record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance_server
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int insertSelective(DBInstanceServer record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance_server
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    DBInstanceServer selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance_server
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int updateByPrimaryKeySelective(DBInstanceServer record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance_server
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int updateByPrimaryKey(DBInstanceServer record);

    List<DBInstanceServer> selectListByInstanceId(String instanceId);

    int deleteByInstanceId(String instanceId);
}