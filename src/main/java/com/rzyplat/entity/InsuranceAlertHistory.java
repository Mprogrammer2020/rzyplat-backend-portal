package com.rzyplat.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection="insurance_alert_history")
public class InsuranceAlertHistory {
	@Id
	private String id;
	private String title;
	private String property;
	private String unitStatus;
	private String alertStatus;
	private LocalDateTime closedDate;
	
	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;
}
