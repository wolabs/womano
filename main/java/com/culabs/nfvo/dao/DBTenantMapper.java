package com.culabs.nfvo.dao;

import com.culabs.nfvo.model.db.DBTenant;

public interface DBTenantMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String tenant_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBTenant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBTenant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBTenant selectByPrimaryKey(String tenant_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBTenant record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tenant
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBTenant record);
}