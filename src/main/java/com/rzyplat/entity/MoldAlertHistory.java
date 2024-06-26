package com.rzyplat.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Data;

@Data
@Document(collection="mold_alert_history")
public class MoldAlertHistory {
	@Id
	private String id;
	private String property;
	private String unitStatus;
	private LocalDateTime closedDate;
	
	@CreatedDate
	private LocalDateTime createdDate;
	private String createdBy;
	
	@LastModifiedDate
	private LocalDateTime updatedDate;
	private String updatedBy;

}
