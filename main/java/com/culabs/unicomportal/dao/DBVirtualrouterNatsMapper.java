package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBVirtualrouterNats;

public interface DBVirtualrouterNatsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBVirtualrouterNats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBVirtualrouterNats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBVirtualrouterNats selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBVirtualrouterNats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouter_nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBVirtualrouterNats record);
}