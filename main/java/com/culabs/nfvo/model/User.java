package com.culabs.nfvo.model;

public class User {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.id
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    private Integer id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userName
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    private String username;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userAge
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    private Integer userage;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column user.userAddress
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    private String useraddress;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.id
     *
     * @return the value of user.id
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.id
     *
     * @param id the value for user.id
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userName
     *
     * @return the value of user.userName
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userName
     *
     * @param username the value for user.userName
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userAge
     *
     * @return the value of user.userAge
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public Integer getUserage() {
        return userage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userAge
     *
     * @param userage the value for user.userAge
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public void setUserage(Integer userage) {
        this.userage = userage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column user.userAddress
     *
     * @return the value of user.userAddress
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public String getUseraddress() {
        return useraddress;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column user.userAddress
     *
     * @param useraddress the value for user.userAddress
     *
     * @mbggenerated Wed Apr 22 01:01:24 CST 2015
     */
    public void setUseraddress(String useraddress) {
        this.useraddress = useraddress;
    }

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", userage="
				+ userage + ", useraddress=" + useraddress + "]";
	}
    
}