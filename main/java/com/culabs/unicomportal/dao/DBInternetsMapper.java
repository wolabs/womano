package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBInternets;

public interface DBInternetsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table internets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table internets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBInternets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table internets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBInternets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table internets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBInternets selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table internets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBInternets record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table internets
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBInternets record);
}