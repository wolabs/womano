package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBVpns;

public interface DBVpnsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpns
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpns
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBVpns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpns
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBVpns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpns
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBVpns selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpns
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBVpns record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vpns
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBVpns record);
}