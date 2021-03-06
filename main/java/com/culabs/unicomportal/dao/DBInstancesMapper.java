package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBInstances;

public interface DBInstancesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBInstances record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBInstances record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBInstances selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBInstances record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(DBInstances record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table instances
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBInstances record);
}