package com.rzyplat.file;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Arrays;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.util.ConverterUtils;
import com.rzyplat.request.CreateDeviceFromFileRequest;

public class DeviceDataListener extends AnalysisEventListener<CreateDeviceFromFileRequest> {

	private boolean headersValid=false;
	private final List<CreateDeviceFromFileRequest> devices = new ArrayList<>();
	private final List<String> headers = Arrays.asList("Device Category", "Device Type", "Serial Number", "SKU");
	
	@Override
	public void invoke(CreateDeviceFromFileRequest data, AnalysisContext context) {
		devices.add(data);
	}

	@Override
	public void doAfterAllAnalysed(AnalysisContext context) { }
	
	@Override
	public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
		if(headMap.size()==4) {
			List<String> columns=ConverterUtils.convertToStringMap(headMap, context)
					.entrySet().stream()
					.map(entry -> entry.getValue())
					.collect(Collectors.toList());
			
			headersValid=headers.equals(columns);
			
			super.invokeHead(headMap, context);
		}
	}
	
	public List<CreateDeviceFromFileRequest> getDevices() {
        return devices;
    }
	
	public boolean isHeadersValid() {
		return headersValid;
	}
}
