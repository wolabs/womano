package com.culabs.nfvo.model;

public class Page {
	private Integer rp;
	private Integer page;
	private int total;
	private String sortname;
	private String sortorder;
	private String query;
	private String qtype;

	public Page() {
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
	
	public int getEnd() {
		int end = (this.getPage() - 1) * this.getRp() + this.getRp();
		return end > total ? total : end;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getEnd(int total) {
		int end = (this.getPage() - 1) * this.getRp() + this.getRp();
		return end > total ? total : end;
	}

	@Override
	public String toString() {
		return "PageInfo [rp=" + rp + ", page=" + page + ", sortname="
				+ sortname + ", sortorder=" + sortorder + ", query=" + query
				+ ", qtype=" + qtype + "]";
	}

}
