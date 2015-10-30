package com.culabs.nfvo.util;

public enum ResourceType {

		AGGREGATE("Aggregate"),
		COMPUTENODE("ComputeNode"),
		ROUTER("Router"),
		NETWORK("Network"),
		INSTANCE("Instance"),
		VOLUME("Volume"),
		SECURITYGROUP("SecurityGroup"),
		PORT("Port"),
		ACLTABLE("ACLTable"),
		INTERNET("Internet"),
		IMAGE("Image"),
		PUBLICRESOURCE("PublicResource"),
		PUBLICIP("PublicIP"),
		NAT("NAT");

		private String name;
		
		private ResourceType(String name){
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
}
