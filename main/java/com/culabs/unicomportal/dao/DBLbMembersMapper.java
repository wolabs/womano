package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBLbMembers;

public interface DBLbMembersMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lb_members
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lb_members
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBLbMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lb_members
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBLbMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lb_members
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBLbMembers selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lb_members
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBLbMembers record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table lb_members
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBLbMembers record);
}