package com.culabs.unicomportal.model.db;

public class DBSubnetsWithBLOBs extends DBSubnets {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subnets.description
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String description;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subnets.dns_nameservers
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String dns_nameservers;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subnets.allocation_pools
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String allocation_pools;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column subnets.host_routes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    private String host_routes;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subnets.description
     *
     * @return the value of subnets.description
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subnets.description
     *
     * @param description the value for subnets.description
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subnets.dns_nameservers
     *
     * @return the value of subnets.dns_nameservers
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getDns_nameservers() {
        return dns_nameservers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subnets.dns_nameservers
     *
     * @param dns_nameservers the value for subnets.dns_nameservers
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setDns_nameservers(String dns_nameservers) {
        this.dns_nameservers = dns_nameservers;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subnets.allocation_pools
     *
     * @return the value of subnets.allocation_pools
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getAllocation_pools() {
        return allocation_pools;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subnets.allocation_pools
     *
     * @param allocation_pools the value for subnets.allocation_pools
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setAllocation_pools(String allocation_pools) {
        this.allocation_pools = allocation_pools;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column subnets.host_routes
     *
     * @return the value of subnets.host_routes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public String getHost_routes() {
        return host_routes;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column subnets.host_routes
     *
     * @param host_routes the value for subnets.host_routes
     *
     * @mbggenerated Tue May 26 15:53:09 CST 2015
     */
    public void setHost_routes(String host_routes) {
        this.host_routes = host_routes;
    }
}