package com.rzyplat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import com.rzyplat.dto.MoldAlertHistoryDTO;

@Data
@AllArgsConstructor
public class MoldAlertHistoryResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<MoldAlertHistoryDTO> list;
}
