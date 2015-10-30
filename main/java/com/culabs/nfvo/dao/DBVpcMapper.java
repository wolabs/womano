package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBVpc;

public interface DBVpcMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpc
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String vpc_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpc
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBVpc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpc
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBVpc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpc
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBVpc selectByPrimaryKey(String vpc_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpc
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBVpc record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpc
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBVpc record);

    List<DBVpc> selectList();
}