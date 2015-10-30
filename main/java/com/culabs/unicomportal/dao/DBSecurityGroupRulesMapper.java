package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBSecurityGroupRules;

public interface DBSecurityGroupRulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_group_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_group_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBSecurityGroupRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_group_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBSecurityGroupRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_group_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBSecurityGroupRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_group_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBSecurityGroupRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table security_group_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBSecurityGroupRules record);
}