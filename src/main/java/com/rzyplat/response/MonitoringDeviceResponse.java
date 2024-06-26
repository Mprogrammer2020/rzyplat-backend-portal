package com.rzyplat.response;

import lombok.Data;
import java.util.List;
import lombok.AllArgsConstructor;
import com.rzyplat.dto.MonitoringDeviceDTO;

@Data
@AllArgsConstructor
public class MonitoringDeviceResponse {
	
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalElements;
	private Integer onlineCount;
	private Integer offlineCount;
	private Integer lowbatteryCount;
	private List<MonitoringDeviceDTO> list;
}
