package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBNetworks;

public interface DBNetworksMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBNetworks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBNetworks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBNetworks selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBNetworks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(DBNetworks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table networks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBNetworks record);

	DBNetworks selectByName(String name);
}