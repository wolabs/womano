package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBVirtualrouter;

public interface DBVirtualrouterMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBVirtualrouter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBVirtualrouter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBVirtualrouter selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBVirtualrouter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(DBVirtualrouter record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table virtualrouters
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBVirtualrouter record);
}