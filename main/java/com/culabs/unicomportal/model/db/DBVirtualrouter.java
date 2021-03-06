package com.culabs.unicomportal.model.db;

import java.util.Date;

public class DBVirtualrouter {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date created_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date updated_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.virtualrouter_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String virtualrouter_uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.bandwidth_rx
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer bandwidth_rx;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.bandwidth_tx
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer bandwidth_tx;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.resource_type
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String resource_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.ha
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Boolean ha;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer owner_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer creator_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.job_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer job_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column virtualrouters.description
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String description;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.id
     *
     * @return the value of virtualrouters.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.id
     *
     * @param id the value for virtualrouters.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.created_at
     *
     * @return the value of virtualrouters.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.created_at
     *
     * @param created_at the value for virtualrouters.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.updated_at
     *
     * @return the value of virtualrouters.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.updated_at
     *
     * @param updated_at the value for virtualrouters.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.virtualrouter_uuid
     *
     * @return the value of virtualrouters.virtualrouter_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getVirtualrouter_uuid() {
        return virtualrouter_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.virtualrouter_uuid
     *
     * @param virtualrouter_uuid the value for virtualrouters.virtualrouter_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setVirtualrouter_uuid(String virtualrouter_uuid) {
        this.virtualrouter_uuid = virtualrouter_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.name
     *
     * @return the value of virtualrouters.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.name
     *
     * @param name the value for virtualrouters.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.bandwidth_rx
     *
     * @return the value of virtualrouters.bandwidth_rx
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getBandwidth_rx() {
        return bandwidth_rx;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.bandwidth_rx
     *
     * @param bandwidth_rx the value for virtualrouters.bandwidth_rx
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setBandwidth_rx(Integer bandwidth_rx) {
        this.bandwidth_rx = bandwidth_rx;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.bandwidth_tx
     *
     * @return the value of virtualrouters.bandwidth_tx
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getBandwidth_tx() {
        return bandwidth_tx;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.bandwidth_tx
     *
     * @param bandwidth_tx the value for virtualrouters.bandwidth_tx
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setBandwidth_tx(Integer bandwidth_tx) {
        this.bandwidth_tx = bandwidth_tx;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.resource_type
     *
     * @return the value of virtualrouters.resource_type
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getResource_type() {
        return resource_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.resource_type
     *
     * @param resource_type the value for virtualrouters.resource_type
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setResource_type(String resource_type) {
        this.resource_type = resource_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.ha
     *
     * @return the value of virtualrouters.ha
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Boolean getHa() {
        return ha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.ha
     *
     * @param ha the value for virtualrouters.ha
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setHa(Boolean ha) {
        this.ha = ha;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.owner_id
     *
     * @return the value of virtualrouters.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getOwner_id() {
        return owner_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.owner_id
     *
     * @param owner_id the value for virtualrouters.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.creator_id
     *
     * @return the value of virtualrouters.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getCreator_id() {
        return creator_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.creator_id
     *
     * @param creator_id the value for virtualrouters.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.job_id
     *
     * @return the value of virtualrouters.job_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getJob_id() {
        return job_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.job_id
     *
     * @param job_id the value for virtualrouters.job_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column virtualrouters.description
     *
     * @return the value of virtualrouters.description
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column virtualrouters.description
     *
     * @param description the value for virtualrouters.description
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setDescription(String description) {
        this.description = description;
    }
}