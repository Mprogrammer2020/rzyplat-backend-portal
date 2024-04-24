package com.rzyplat.request;

public class SearchCustomerParam {
	private Integer page;
	private Integer size;
	private String orderBy;
	private String direction;

	public SearchCustomerParam(Integer page, Integer size, String orderBy, String direction) {
		super();
		this.page = page;
		this.size = size;
		this.orderBy = orderBy;
		this.direction = direction;
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

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

}
