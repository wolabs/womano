package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBInstance;

public interface DBInstanceMapper {
    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int deleteByPrimaryKey(String instance_id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int insert(DBInstance record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int insertSelective(DBInstance record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    DBInstance selectByPrimaryKey(String instance_id);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int updateByPrimaryKeySelective(DBInstance record);

    /**
     * This method was generated by MyBatis Generator. This method corresponds to the database table instance
     * @mbggenerated  Tue May 05 16:36:51 CST 2015
     */
    int updateByPrimaryKey(DBInstance record);
    
    /**
     * query all instance
     * @return
     */
    List<DBInstance> selectList();
    
    /**
     * query all instance by type
     * @return
     */
    List<DBInstance> selectListByType(String type);
}