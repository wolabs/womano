package com.culabs.unicomportal.model.db;

import java.util.Date;

public class DBUserRoles {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_roles.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_roles.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date created_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_roles.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date updated_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_roles.user_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer user_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user_roles.role_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer role_id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_roles.id
     *
     * @return the value of user_roles.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_roles.id
     *
     * @param id the value for user_roles.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_roles.created_at
     *
     * @return the value of user_roles.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_roles.created_at
     *
     * @param created_at the value for user_roles.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_roles.updated_at
     *
     * @return the value of user_roles.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_roles.updated_at
     *
     * @param updated_at the value for user_roles.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_roles.user_id
     *
     * @return the value of user_roles.user_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getUser_id() {
        return user_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_roles.user_id
     *
     * @param user_id the value for user_roles.user_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user_roles.role_id
     *
     * @return the value of user_roles.role_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getRole_id() {
        return role_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user_roles.role_id
     *
     * @param role_id the value for user_roles.role_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }
}