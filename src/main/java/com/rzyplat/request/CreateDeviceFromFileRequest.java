package com.rzyplat.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateDeviceFromFileRequest {
	
    private String category;
    private String type;
	private String serialNumber;
	private String sku;

}
