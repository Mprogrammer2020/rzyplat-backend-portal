package com.rzyplat.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class SecurityAlertHistoryDTO {
	
	private String property;
	private String unitStatus;
	private LocalDateTime closedDate;
	private LocalDateTime createdDate;
}
