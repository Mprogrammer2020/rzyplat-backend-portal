package com.rzyplat.response;

import java.util.List;

import com.rzyplat.dto.DeviceDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DeviceResponse {	
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalElements;
	private List<DeviceDTO> list;

}
