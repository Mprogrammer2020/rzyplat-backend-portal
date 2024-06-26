package com.rzyplat.entity;

import lombok.Data;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "insurance_alert_summary")
public class InsuranceAlertSummary {

	@Id
	private String id;
	private Integer totalAlerts;
	private Integer activeAlerts;
	private Integer totalClaims;
	private Integer severeAlerts;

	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;
}
