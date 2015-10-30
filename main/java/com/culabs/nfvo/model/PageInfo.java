package com.culabs.nfvo.model;

public class PageInfo {
	private Integer rp;
	private Integer page;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;
	
	public PageInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Integer getRp() {
		return rp;
	}
	public void setRp(Integer rp) {
		this.rp = rp;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	@Override
	public String toString() {
		return "PageInfo [rp=" + rp + ", page=" + page + ", sortname="
				+ sortname + ", sortorder=" + sortorder + ", query=" + query
				+ ", qtype=" + qtype + "]";
	}
	
	
}
