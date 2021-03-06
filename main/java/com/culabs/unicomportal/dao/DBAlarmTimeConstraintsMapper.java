package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBAlarmTimeConstraints;

public interface DBAlarmTimeConstraintsMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_time_constraints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_time_constraints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBAlarmTimeConstraints record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_time_constraints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBAlarmTimeConstraints record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_time_constraints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBAlarmTimeConstraints selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_time_constraints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBAlarmTimeConstraints record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table alarm_time_constraints
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBAlarmTimeConstraints record);
}