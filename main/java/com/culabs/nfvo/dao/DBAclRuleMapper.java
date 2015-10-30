package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBAclRule;

public interface DBAclRuleMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rule
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int deleteByPrimaryKey(String acl_rule_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rule
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insert(DBAclRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rule
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int insertSelective(DBAclRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rule
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    DBAclRule selectByPrimaryKey(String acl_rule_id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rule
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKeySelective(DBAclRule record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table acl_rule
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    int updateByPrimaryKey(DBAclRule record);
    
    List<DBAclRule> selectList();
}