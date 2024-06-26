package com.rzyplat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import com.rzyplat.dto.SecurityAlertHistoryDTO;

@Data
@AllArgsConstructor
public class SecurityAlertHistoryResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<SecurityAlertHistoryDTO> list;
}
