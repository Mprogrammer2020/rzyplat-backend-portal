package com.rzyplat.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class SearchCustomerParam {
	
	private Integer page;
	private Integer size;
	private String orderBy;
	private String direction;
	
}
