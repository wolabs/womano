package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBVolumes;

public interface DBVolumesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBVolumes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBVolumes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBVolumes selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBVolumes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(DBVolumes record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table volumes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBVolumes record);
}