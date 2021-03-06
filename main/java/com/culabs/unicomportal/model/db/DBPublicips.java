package com.culabs.unicomportal.model.db;

import java.util.Date;

public class DBPublicips {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date created_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Date updated_at;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.ip
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String name;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.mask
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer mask;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.gateway_ip
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String gateway_ip;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.public_resource_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer public_resource_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.used
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Boolean used;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.bind_resource_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer bind_resource_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.bind_resource_type
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String bind_resource_type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.public_ip_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String public_ip_uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.default_snat_source
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Boolean default_snat_source;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.floating_ip_address
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String floating_ip_address;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.publicip_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String publicip_uuid;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.job_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer job_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer owner_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column publicips.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private Integer creator_id;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.id
     *
     * @return the value of publicips.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.id
     *
     * @param id the value for publicips.id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.created_at
     *
     * @return the value of publicips.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getCreated_at() {
        return created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.created_at
     *
     * @param created_at the value for publicips.created_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.updated_at
     *
     * @return the value of publicips.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Date getUpdated_at() {
        return updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.updated_at
     *
     * @param updated_at the value for publicips.updated_at
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.ip
     *
     * @return the value of publicips.ip
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getIp() {
        return ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.ip
     *
     * @param ip the value for publicips.ip
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.name
     *
     * @return the value of publicips.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getName() {
        return name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.name
     *
     * @param name the value for publicips.name
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.mask
     *
     * @return the value of publicips.mask
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getMask() {
        return mask;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.mask
     *
     * @param mask the value for publicips.mask
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setMask(Integer mask) {
        this.mask = mask;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.gateway_ip
     *
     * @return the value of publicips.gateway_ip
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getGateway_ip() {
        return gateway_ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.gateway_ip
     *
     * @param gateway_ip the value for publicips.gateway_ip
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setGateway_ip(String gateway_ip) {
        this.gateway_ip = gateway_ip;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.public_resource_id
     *
     * @return the value of publicips.public_resource_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getPublic_resource_id() {
        return public_resource_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.public_resource_id
     *
     * @param public_resource_id the value for publicips.public_resource_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setPublic_resource_id(Integer public_resource_id) {
        this.public_resource_id = public_resource_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.used
     *
     * @return the value of publicips.used
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Boolean getUsed() {
        return used;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.used
     *
     * @param used the value for publicips.used
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setUsed(Boolean used) {
        this.used = used;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.bind_resource_id
     *
     * @return the value of publicips.bind_resource_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getBind_resource_id() {
        return bind_resource_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.bind_resource_id
     *
     * @param bind_resource_id the value for publicips.bind_resource_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setBind_resource_id(Integer bind_resource_id) {
        this.bind_resource_id = bind_resource_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.bind_resource_type
     *
     * @return the value of publicips.bind_resource_type
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getBind_resource_type() {
        return bind_resource_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.bind_resource_type
     *
     * @param bind_resource_type the value for publicips.bind_resource_type
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setBind_resource_type(String bind_resource_type) {
        this.bind_resource_type = bind_resource_type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.public_ip_uuid
     *
     * @return the value of publicips.public_ip_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getPublic_ip_uuid() {
        return public_ip_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.public_ip_uuid
     *
     * @param public_ip_uuid the value for publicips.public_ip_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setPublic_ip_uuid(String public_ip_uuid) {
        this.public_ip_uuid = public_ip_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.default_snat_source
     *
     * @return the value of publicips.default_snat_source
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Boolean getDefault_snat_source() {
        return default_snat_source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.default_snat_source
     *
     * @param default_snat_source the value for publicips.default_snat_source
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setDefault_snat_source(Boolean default_snat_source) {
        this.default_snat_source = default_snat_source;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.floating_ip_address
     *
     * @return the value of publicips.floating_ip_address
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getFloating_ip_address() {
        return floating_ip_address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.floating_ip_address
     *
     * @param floating_ip_address the value for publicips.floating_ip_address
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setFloating_ip_address(String floating_ip_address) {
        this.floating_ip_address = floating_ip_address;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.publicip_uuid
     *
     * @return the value of publicips.publicip_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getPublicip_uuid() {
        return publicip_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.publicip_uuid
     *
     * @param publicip_uuid the value for publicips.publicip_uuid
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setPublicip_uuid(String publicip_uuid) {
        this.publicip_uuid = publicip_uuid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.job_id
     *
     * @return the value of publicips.job_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getJob_id() {
        return job_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.job_id
     *
     * @param job_id the value for publicips.job_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setJob_id(Integer job_id) {
        this.job_id = job_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.owner_id
     *
     * @return the value of publicips.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getOwner_id() {
        return owner_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.owner_id
     *
     * @param owner_id the value for publicips.owner_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setOwner_id(Integer owner_id) {
        this.owner_id = owner_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column publicips.creator_id
     *
     * @return the value of publicips.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public Integer getCreator_id() {
        return creator_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column publicips.creator_id
     *
     * @param creator_id the value for publicips.creator_id
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setCreator_id(Integer creator_id) {
        this.creator_id = creator_id;
    }
}