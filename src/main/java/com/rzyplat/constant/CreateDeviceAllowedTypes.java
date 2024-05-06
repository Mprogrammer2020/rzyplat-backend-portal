package com.rzyplat.constant;

public enum CreateDeviceAllowedTypes {

	CSV("text/csv"), XLS("application/vnd.ms-excel"),
	XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

	private final String contentType;

	CreateDeviceAllowedTypes(String contentType) {
	   this.contentType = contentType;
	}

	public String getContentType() {
		return contentType;
	}
}
