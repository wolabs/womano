package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBUserRoles;

public interface DBUserRolesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_roles
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_roles
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBUserRoles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_roles
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBUserRoles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_roles
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBUserRoles selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_roles
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBUserRoles record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_roles
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBUserRoles record);
}