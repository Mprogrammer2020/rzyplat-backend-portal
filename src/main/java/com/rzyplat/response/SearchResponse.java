package com.rzyplat.response;

import java.util.List;
import com.rzyplat.constant.Action;

public class SearchResponse<T> {

	private Action action;
	private Long totalCount;
	private List<T> list;

	public SearchResponse(Long totalCount, List<T> list) {
		super();
		this.action = Action.SEARCHED;
		this.totalCount = totalCount;
		this.list = list;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
