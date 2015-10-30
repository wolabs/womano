package com.culabs.nfvo.model;

/**
 *  uuid为底层平台返回的资源标识
 *  id为门户数据库返回的标识
 * 
 * */
public class ResourceIds {

	private String vpcUUID;
	private String vpcName;
	private String vpcNamePrefix;  //name the network element after this
	private String igwRouterUUID;
	private String routingTableUUID;
	private String routingRuleUUID;
	private String publicIpResourceUUID;
	private String publicIpUUID;
	private String publicIp;
	private String networkUUID;
	private String subnetUUID;
	private String subnetRoutingTableUUID;
	private String aclUUID;
	private String subnetAclTableUUID;
	private String natUUID;

	private int vpcId;
	private int igwRouterId;
	private int routingTableId;
	private int routingRuleId;
	private int publicIpResourceId;
	private int publicIpId;
	private int networkId;
	private int subnetId;
	private int subnetRoutingTableId;
	private int aclId;
	private int subnetAclTableId;
	private int natId;
	
	private String igwRouterName;

	public String getVpcUUID() {
		return vpcUUID;
	}

	public void setVpcUUID(String vpcUUID) {
		this.vpcUUID = vpcUUID;
	}

	public String getIgwRouterUUID() {
		return igwRouterUUID;
	}

	public void setIgwRouterUUID(String igwRouterUUID) {
		this.igwRouterUUID = igwRouterUUID;
	}

	public String getRoutingTableUUID() {
		return routingTableUUID;
	}

	public void setRoutingTableUUID(String routingTableUUID) {
		this.routingTableUUID = routingTableUUID;
	}

	public String getRoutingRuleUUID() {
		return routingRuleUUID;
	}

	public void setRoutingRuleUUID(String routingRuleUUID) {
		this.routingRuleUUID = routingRuleUUID;
	}

	public String getPublicIpResourceUUID() {
		return publicIpResourceUUID;
	}

	public void setPublicIpResourceUUID(String publicIpResourceUUID) {
		this.publicIpResourceUUID = publicIpResourceUUID;
	}

	public String getPublicIpUUID() {
		return publicIpUUID;
	}

	public void setPublicIpUUID(String publicIpUUID) {
		this.publicIpUUID = publicIpUUID;
	}

	public String getNetworkUUID() {
		return networkUUID;
	}

	public void setNetworkUUID(String networkUUID) {
		this.networkUUID = networkUUID;
	}

	public String getSubnetUUID() {
		return subnetUUID;
	}

	public void setSubnetUUID(String subnetUUID) {
		this.subnetUUID = subnetUUID;
	}

	public String getSubnetRoutingTableUUID() {
		return subnetRoutingTableUUID;
	}

	public void setSubnetRoutingTableUUID(String subnetRoutingTableUUID) {
		this.subnetRoutingTableUUID = subnetRoutingTableUUID;
	}

	public String getAclUUID() {
		return aclUUID;
	}

	public void setAclUUID(String aclUUID) {
		this.aclUUID = aclUUID;
	}


	public String getSubnetAclTableUUID() {
		return subnetAclTableUUID;
	}

	public void setSubnetAclTableUUID(String subnetAclTableUUID) {
		this.subnetAclTableUUID = subnetAclTableUUID;
	}

	public int getVpcId() {
		return vpcId;
	}

	public void setVpcId(int vpcId) {
		this.vpcId = vpcId;
	}

	public int getIgwRouterId() {
		return igwRouterId;
	}

	public void setIgwRouterId(int igwRouterId) {
		this.igwRouterId = igwRouterId;
	}

	public int getRoutingTableId() {
		return routingTableId;
	}

	public void setRoutingTableId(int routingTableId) {
		this.routingTableId = routingTableId;
	}

	public int getRoutingRuleId() {
		return routingRuleId;
	}

	public void setRoutingRuleId(int routingRuleId) {
		this.routingRuleId = routingRuleId;
	}

	public int getPublicIpResourceId() {
		return publicIpResourceId;
	}

	public void setPublicIpResourceId(int publicIpResourceId) {
		this.publicIpResourceId = publicIpResourceId;
	}

	public int getPublicIpId() {
		return publicIpId;
	}

	public void setPublicIpId(int publicIpId) {
		this.publicIpId = publicIpId;
	}

	public int getNetworkId() {
		return networkId;
	}

	public void setNetworkId(int networkId) {
		this.networkId = networkId;
	}

	public int getSubnetId() {
		return subnetId;
	}

	public void setSubnetId(int subnetId) {
		this.subnetId = subnetId;
	}

	public int getSubnetRoutingTableId() {
		return subnetRoutingTableId;
	}

	public void setSubnetRoutingTableId(int subnetRoutingTableId) {
		this.subnetRoutingTableId = subnetRoutingTableId;
	}

	public int getAclId() {
		return aclId;
	}

	public void setAclId(int aclId) {
		this.aclId = aclId;
	}


	public int getSubnetAclTableId() {
		return subnetAclTableId;
	}

	public void setSubnetAclTableId(int subnetAclTableId) {
		this.subnetAclTableId = subnetAclTableId;
	}

	public String getIgwRouterName() {
		return igwRouterName;
	}

	public void setIgwRouterName(String igwRouterName) {
		this.igwRouterName = igwRouterName;
	}

	public String getVpcName() {
		return vpcName;
	}

	public void setVpcName(String vpcName) {
		this.vpcName = vpcName;
	}

	public String getVpcNamePrefix() {
		return vpcNamePrefix;
	}

	public void setVpcNamePrefix(String vpcNamePrefix) {
		this.vpcNamePrefix = vpcNamePrefix;
	}

	public String getNatUUID() {
		return natUUID;
	}

	public void setNatUUID(String natUUID) {
		this.natUUID = natUUID;
	}

	public int getNatId() {
		return natId;
	}

	public void setNatId(int natId) {
		this.natId = natId;
	}

	public String getPublicIp() {
		return publicIp;
	}

	public void setPublicIp(String publicIp) {
		this.publicIp = publicIp;
	}
	

}
