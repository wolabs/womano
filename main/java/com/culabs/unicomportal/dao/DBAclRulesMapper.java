package com.culabs.unicomportal.dao;

import com.culabs.unicomportal.model.db.DBAclRules;

public interface DBAclRulesMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insert(DBAclRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int insertSelective(DBAclRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    DBAclRules selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKeySelective(DBAclRules record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rules
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    int updateByPrimaryKey(DBAclRules record);
}