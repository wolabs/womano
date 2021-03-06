package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBRoutingTable;

public interface DBRoutingTableMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String routing_table_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBRoutingTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBRoutingTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBRoutingTable selectByPrimaryKey(String routing_table_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBRoutingTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBRoutingTable record);

    List<DBRoutingTable> selectList();
}