package com.rzyplat.request;

import com.alibaba.excel.annotation.ExcelProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDeviceFromFileRequest {
	
	@ExcelProperty("Device Category")
    private String deviceCategory;
	@ExcelProperty("Device Type")
    private String type;
	@ExcelProperty("Serial Number")
	private String serialNumber;
	@ExcelProperty("SKU")
	private String sku;

}
