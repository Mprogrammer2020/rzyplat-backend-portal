package com.rzyplat.response;

import java.util.List;

import com.rzyplat.dto.DeviceTypeDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DeviceTypeResponse {
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalElements;
	private List<DeviceTypeDTO> list;

}
