package com.culabs.nfvo.dao;

import java.util.List;

import com.culabs.nfvo.model.db.DBMember;

public interface DBMemberMapper
{
	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table member
	 *
	 * @mbggenerated Mon May 25 14:47:06 CST 2015
	 */
	int deleteByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table member
	 *
	 * @mbggenerated Mon May 25 14:47:06 CST 2015
	 */
	int insert(DBMember record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table member
	 *
	 * @mbggenerated Mon May 25 14:47:06 CST 2015
	 */
	int insertSelective(DBMember record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table member
	 *
	 * @mbggenerated Mon May 25 14:47:06 CST 2015
	 */
	DBMember selectByPrimaryKey(String id);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table member
	 *
	 * @mbggenerated Mon May 25 14:47:06 CST 2015
	 */
	int updateByPrimaryKeySelective(DBMember record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds
	 * to the database table member
	 *
	 * @mbggenerated Mon May 25 14:47:06 CST 2015
	 */
	int updateByPrimaryKey(DBMember record);

	List<DBMember> selectList(DBMember record);
}