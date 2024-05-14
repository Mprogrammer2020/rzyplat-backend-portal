package com.rzyplat.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CreateDeviceRequest extends UpdateDeviceRequest {
    
	@NotBlank
	private String sku;

}
