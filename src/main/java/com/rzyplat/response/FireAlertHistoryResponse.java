package com.rzyplat.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;
import com.rzyplat.dto.FireAlertHistoryDTO;

@Data
@AllArgsConstructor
public class FireAlertHistoryResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<FireAlertHistoryDTO> list;

}
