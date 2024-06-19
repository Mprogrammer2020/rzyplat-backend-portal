package com.rzyplat.controller;

import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;

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

import com.rzyplat.dto.DeviceTypeDTO;
import com.rzyplat.impl.DeviceTypeServiceImpl;
import com.rzyplat.response.DeviceTypeResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceTypeControllerTest {

	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceTypeServiceImpl deviceTypeService;
    
    @Test
    public void testCreateDeviceType() throws Exception {
        String categoryId = "123";
        String type = "Smartphone";
        MockMultipartFile image = new MockMultipartFile("image", "test.jpg", "image/jpeg", "dummy image content".getBytes());
        String expectedResponse = "Device Type Created Successfully";

        when(deviceTypeService.createDeviceType(categoryId, type, image)).thenReturn(expectedResponse);

        mockMvc.perform(multipart("/device-type/{categoryId}", categoryId)
                .file(image)
                .param("type", type)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
            .andExpect(status().isCreated())
            .andExpect(content().string(expectedResponse));
    }
    
    @Test
    public void testBasicGetDeviceType() throws Exception {
        String categoryId = "123";
        List<DeviceTypeDTO> deviceTypes = new ArrayList<>();

        when(deviceTypeService.getDeviceTypes(categoryId)).thenReturn(deviceTypes);

        mockMvc.perform(get("/device-type/basic/{categoryId}", categoryId))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void testGetDeviceType() throws Exception {
        String categoryId = "123";
        DeviceTypeResponse deviceTypeResponse = new DeviceTypeResponse(0, 10, 1, 20L, new ArrayList<>());

        when(deviceTypeService.getDeviceTypes(0, 10, categoryId)).thenReturn(deviceTypeResponse);

        mockMvc.perform(get("/device-type/{categoryId}", categoryId)
                .param("page", "0")
                .param("size", "10"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.totalElements").value(20))
		        .andExpect(jsonPath("$.totalPages").value(1))
		        .andExpect(jsonPath("$.pageNumber").value(0))
		        .andExpect(jsonPath("$.pageSize").value(10));
    }


}
