package com.rzyplat.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateDeviceRequest {
      	@NotBlank
	    private String categoryId;
      	@NotBlank
	    private String deviceTypeId;
      	@NotBlank
		private String serialNumber;
      	@NotBlank
		private String sku;

}
