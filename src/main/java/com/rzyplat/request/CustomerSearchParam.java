package com.rzyplat.request;

public class CustomerSearchParam {
	private Integer page;
	private Integer size;
	private String sortBy;
	private String orderBy;

	public CustomerSearchParam(Integer page, Integer size, String sortBy, String orderBy) {
		super();
		this.page = page;
		this.size = size;
		this.sortBy = sortBy;
		this.orderBy = orderBy;
	}
	
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSize() {
		return size;
	}
	public void setSize(Integer size) {
		this.size = size;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}


}
