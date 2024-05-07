package com.rzyplat.controller;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.impl.DeviceServiceImpl;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.response.DeviceResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private DeviceServiceImpl deviceService;

	@Test
	public void testCreateDevice() throws Exception {
		CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
		createDeviceRequest.setCategoryId("categoryId");
		createDeviceRequest.setDeviceTypeId("type1");
		createDeviceRequest.setSerialNumber("KJDHS6778BHB");
		createDeviceRequest.setSku("KHHK8676854GGFGF");
		
		String expectedMessage = "Device created successfully";

		when(deviceService.createDevice(any(CreateDeviceRequest.class))).thenReturn(expectedMessage);

		mockMvc.perform(post("/devices").contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(createDeviceRequest))).andExpect(status().isCreated())
				.andExpect(content().string(expectedMessage));
	}

	@Test
	public void testCreateBulkDevices() throws Exception {
		MockMultipartFile file = new MockMultipartFile("file", "filename.csv", "text/csv", "data".getBytes());
		String expectedMessage = "Bulk devices created";

		when(deviceService.createBulkDevices(any(MultipartFile.class))).thenReturn(expectedMessage);

		mockMvc.perform(multipart("/devices/bulk-upload").file("file", file.getBytes())
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE)).andExpect(status().isCreated())
				.andExpect(content().string(expectedMessage));
	}

	@Test
	    public void testGetDevices() throws Exception {
	        DeviceResponse deviceResponse = new DeviceResponse(0, 10, 1, 20L, new ArrayList<>());

	        when(deviceService.searchDevice(0, 10, null, null)).thenReturn(deviceResponse);

	        mockMvc.perform(get("/devices")
	                .param("page", "0")
	                .param("size", "10"))
	        		.andExpect(status().isOk())
			        .andExpect(jsonPath("$.pageNumber").value(0))
			        .andExpect(jsonPath("$.pageSize").value(10))
			        .andExpect(jsonPath("$.totalElements").value(20))
			        .andExpect(jsonPath("$.totalPages").value(1));
	}
	
	@Test
	public void testDeleteDevice() throws Exception {
	    String deviceId = "1";
	    String expectedMessage = "Device deleted";

	    when(deviceService.deleteDeviceById(deviceId)).thenReturn(expectedMessage);

	    mockMvc.perform(delete("/devices/{deviceId}", deviceId))
	        .andExpect(status().isOk())
	        .andExpect(content().string(expectedMessage));
	}


}
