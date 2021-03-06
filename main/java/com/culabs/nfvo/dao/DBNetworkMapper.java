package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBNetwork;

public interface DBNetworkMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String network_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBNetwork record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBNetwork record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBNetwork selectByPrimaryKey(String network_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBNetwork record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table network
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBNetwork record);

    List<DBNetwork> selectList();
}