package com.rzyplat.dto;

import java.time.LocalDate;
import com.rzyplat.request.CreateContactRequest;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ContactDTO extends CreateContactRequest {

	private String id;
	private LocalDate joiningDate;
	
}
