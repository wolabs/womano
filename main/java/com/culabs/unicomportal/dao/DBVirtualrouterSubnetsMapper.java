package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBVirtualrouterSubnets;

public interface DBVirtualrouterSubnetsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_subnets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_subnets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBVirtualrouterSubnets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_subnets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBVirtualrouterSubnets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_subnets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBVirtualrouterSubnets selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_subnets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBVirtualrouterSubnets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_subnets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBVirtualrouterSubnets record);
}