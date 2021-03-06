package com.culabs.nfvo.dao;

import com.culabs.nfvo.model.db.DBSubnetRoutingTable;

public interface DBSubnetRoutingTableMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subnet_routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String subnet_routing_table_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subnet_routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBSubnetRoutingTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subnet_routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBSubnetRoutingTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subnet_routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBSubnetRoutingTable selectByPrimaryKey(String subnet_routing_table_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subnet_routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBSubnetRoutingTable record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table subnet_routing_table
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBSubnetRoutingTable record);
}