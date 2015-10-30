package com.culabs.nfvo.model;

public enum Role {
	ADMINISTRATOR("administrator", 0), OPERATIOR("operator", 1);
	private String name;
	private int level;

	private Role(String name, int level) {
		this.name = name;
		this.level = level;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public static Role getRole(String name) {
		Role[] roles = Role.values();
		for (Role r : roles) {
			if (r.getName().equals(name)) {
				return r;
			}
		}
		return null;
	}

	public boolean isMoreOrEqPower(Role r) {
		return this.level <= r.level;
	}

	public static boolean isMoreOrEqPower(String name1, String name2) {
		Role r1 = getRole(name1);
		Role r2 = getRole(name2);
		return null != r1 && null != r2 && r1.isMoreOrEqPower(r2);
	}
}
