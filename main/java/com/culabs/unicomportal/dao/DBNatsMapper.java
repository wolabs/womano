package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBNats;

public interface DBNatsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBNats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBNats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBNats selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBNats record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table nats
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBNats record);
}