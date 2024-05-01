package com.rzyplat.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DeviceSearchParam {
	private Integer page;
	private Integer size;
	private String categoryId;
	private String deviceTypeId;

}
