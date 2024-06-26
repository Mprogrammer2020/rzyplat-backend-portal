package com.rzyplat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import com.rzyplat.dto.MonitoringAlertHistoryDTO;

@Data
@AllArgsConstructor
public class MonitoringAlertHistoryResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<MonitoringAlertHistoryDTO> list;
}
