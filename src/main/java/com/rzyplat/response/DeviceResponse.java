package com.rzyplat.response;

import java.util.List;
import com.rzyplat.entity.Device;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DeviceResponse {	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<Device> list;

}
