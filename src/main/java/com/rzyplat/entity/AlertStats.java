package com.rzyplat.entity;



import lombok.Data;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "alert_stats")
public class AlertStats {

	@Id
	private String id;
	private Integer completedAlerts;
	private Integer averageAlertsPerWeek;
	private Integer alertCloseTime;
	private Integer alertOpenTime;
	
	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;
}
