package com.culabs.nfvo.model.db;

public class DBTenant {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.tenant_id
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    private String tenant_id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.tenant_username
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    private String tenant_username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tenant.tenant_password
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    private String tenant_password;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.tenant_id
     *
     * @return the value of tenant.tenant_id
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    public String getTenant_id() {
        return tenant_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.tenant_id
     *
     * @param tenant_id the value for tenant.tenant_id
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    public void setTenant_id(String tenant_id) {
        this.tenant_id = tenant_id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.tenant_username
     *
     * @return the value of tenant.tenant_username
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    public String getTenant_username() {
        return tenant_username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.tenant_username
     *
     * @param tenant_username the value for tenant.tenant_username
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    public void setTenant_username(String tenant_username) {
        this.tenant_username = tenant_username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tenant.tenant_password
     *
     * @return the value of tenant.tenant_password
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    public String getTenant_password() {
        return tenant_password;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tenant.tenant_password
     *
     * @param tenant_password the value for tenant.tenant_password
     *
     * @mbggenerated Tue Apr 28 19:19:52 CST 2015
     */
    public void setTenant_password(String tenant_password) {
        this.tenant_password = tenant_password;
    }
}