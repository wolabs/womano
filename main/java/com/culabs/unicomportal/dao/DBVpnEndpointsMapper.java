package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBVpnEndpoints;

public interface DBVpnEndpointsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpn_endpoints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpn_endpoints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBVpnEndpoints record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpn_endpoints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBVpnEndpoints record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpn_endpoints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBVpnEndpoints selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpn_endpoints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBVpnEndpoints record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpn_endpoints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBVpnEndpoints record);
}