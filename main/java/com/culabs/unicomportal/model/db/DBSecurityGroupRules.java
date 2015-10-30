package com.culabs.unicomportal.model.db;

import java.util.Date;

public class DBSecurityGroupRules {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date created_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date updated_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.security_group_rule_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String security_group_rule_uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.direction
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String direction;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.ethertype
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String ethertype;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.protocol
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String protocol;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.port_range_min
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer port_range_min;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.port_range_max
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer port_range_max;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.remote_ip_prefix
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String remote_ip_prefix;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.security_group_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer security_group_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer owner_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column security_group_rules.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer creator_id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.id
     *
     * @return the value of security_group_rules.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.id
     *
     * @param id the value for security_group_rules.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.created_at
     *
     * @return the value of security_group_rules.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.created_at
     *
     * @param created_at the value for security_group_rules.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.updated_at
     *
     * @return the value of security_group_rules.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.updated_at
     *
     * @param updated_at the value for security_group_rules.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.name
     *
     * @return the value of security_group_rules.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.name
     *
     * @param name the value for security_group_rules.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.security_group_rule_uuid
     *
     * @return the value of security_group_rules.security_group_rule_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getSecurity_group_rule_uuid() {
        return security_group_rule_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.security_group_rule_uuid
     *
     * @param security_group_rule_uuid the value for security_group_rules.security_group_rule_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setSecurity_group_rule_uuid(String security_group_rule_uuid) {
        this.security_group_rule_uuid = security_group_rule_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.direction
     *
     * @return the value of security_group_rules.direction
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getDirection() {
        return direction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.direction
     *
     * @param direction the value for security_group_rules.direction
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setDirection(String direction) {
        this.direction = direction;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.ethertype
     *
     * @return the value of security_group_rules.ethertype
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getEthertype() {
        return ethertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.ethertype
     *
     * @param ethertype the value for security_group_rules.ethertype
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setEthertype(String ethertype) {
        this.ethertype = ethertype;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.protocol
     *
     * @return the value of security_group_rules.protocol
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.protocol
     *
     * @param protocol the value for security_group_rules.protocol
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.port_range_min
     *
     * @return the value of security_group_rules.port_range_min
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getPort_range_min() {
        return port_range_min;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.port_range_min
     *
     * @param port_range_min the value for security_group_rules.port_range_min
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setPort_range_min(Integer port_range_min) {
        this.port_range_min = port_range_min;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.port_range_max
     *
     * @return the value of security_group_rules.port_range_max
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getPort_range_max() {
        return port_range_max;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.port_range_max
     *
     * @param port_range_max the value for security_group_rules.port_range_max
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setPort_range_max(Integer port_range_max) {
        this.port_range_max = port_range_max;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.remote_ip_prefix
     *
     * @return the value of security_group_rules.remote_ip_prefix
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getRemote_ip_prefix() {
        return remote_ip_prefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.remote_ip_prefix
     *
     * @param remote_ip_prefix the value for security_group_rules.remote_ip_prefix
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setRemote_ip_prefix(String remote_ip_prefix) {
        this.remote_ip_prefix = remote_ip_prefix;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.security_group_id
     *
     * @return the value of security_group_rules.security_group_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getSecurity_group_id() {
        return security_group_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.security_group_id
     *
     * @param security_group_id the value for security_group_rules.security_group_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setSecurity_group_id(Integer security_group_id) {
        this.security_group_id = security_group_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.owner_id
     *
     * @return the value of security_group_rules.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getOwner_id() {
        return owner_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.owner_id
     *
     * @param owner_id the value for security_group_rules.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column security_group_rules.creator_id
     *
     * @return the value of security_group_rules.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getCreator_id() {
        return creator_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column security_group_rules.creator_id
     *
     * @param creator_id the value for security_group_rules.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }
}