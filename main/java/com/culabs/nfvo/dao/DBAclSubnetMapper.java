package com.culabs.nfvo.dao;

import com.culabs.nfvo.model.db.DBAclSubnet;

public interface DBAclSubnetMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_subnet
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String acl_subnet_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_subnet
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBAclSubnet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_subnet
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBAclSubnet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_subnet
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBAclSubnet selectByPrimaryKey(String acl_subnet_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_subnet
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBAclSubnet record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_subnet
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBAclSubnet record);
}