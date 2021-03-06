package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBLogBooks;

public interface DBLogBooksMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBLogBooks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBLogBooks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBLogBooks selectByPrimaryKey(String uuid);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBLogBooks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(DBLogBooks record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table logbooks
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBLogBooks record);
}