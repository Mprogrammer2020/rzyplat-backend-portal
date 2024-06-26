package com.rzyplat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import com.rzyplat.dto.InsuranceAlertHistoryDTO;

@Data
@AllArgsConstructor
public class InsuranceAlertHistoryResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<InsuranceAlertHistoryDTO> list;

}
