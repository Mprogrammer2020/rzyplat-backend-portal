package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.Device;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.exception.InvalidDataFormatException;
import com.rzyplat.impl.DeviceServiceImpl;
import com.rzyplat.repository.DeviceRepository;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.response.DeviceResponse;

@SpringBootTest
public class DeviceServiceTest {

	@Mock
    private DeviceRepository repository;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private CategoryService categoryService;
    @Mock
    private DeviceTypeService deviceTypeService;

    @InjectMocks
    private DeviceServiceImpl deviceService;
	
    
    @Test
    public void testCreateDevice() throws Exception {
        // Given
        String categoryId = "cat123";
        String deviceTypeId = "devType123";
        CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest();
        createDeviceRequest.setCategoryId(categoryId);
        createDeviceRequest.setDeviceTypeId(deviceTypeId);
        
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Electronics");
        category.setCount(0);
        
        DeviceType deviceType = new DeviceType();
        deviceType.setId("devType1");
        deviceType.setType("Smartphone");
        deviceType.setCount(0);
        
        Device device = new Device();
        device.setCategory(category);
        device.setDeviceType(deviceType);
        device.setSerialNumber("GHG556GHG");

        when(categoryService.findById(categoryId)).thenReturn(category);
        when(deviceTypeService.findById(deviceTypeId)).thenReturn(deviceType);
        when(objectMapper.convertValue(any(CreateDeviceRequest.class), eq(Device.class))).thenReturn(device);
        when(repository.save(any(Device.class))).thenReturn(device);

        // When
        String result = deviceService.createDevice(createDeviceRequest);

        // Then
        assertEquals(Constants.DEVICE_CREATED, result);
        assertEquals(category.getCount(), 1);
        assertEquals(deviceType.getCount(), 1);
        verify(categoryService).findById(categoryId);
        verify(deviceTypeService).findById(deviceTypeId);
        verify(repository).save(device);
        
        verify(objectMapper).convertValue(any(CreateDeviceRequest.class), eq(Device.class));
    }
    
    @Test
    public void testCreateBulkDevices() throws Exception {
    	MockMultipartFile bulkUploadunsupported = new MockMultipartFile("image", "device.jpg", "image/jpeg", "test unsupported file".getBytes());
    	String message=deviceService.createBulkDevices(bulkUploadunsupported);
    	assertEquals(message, Constants.UNSUPPORTED_FILE);
    }
    
    @Test
    public void testCreateBulkDevicesFormat() throws Exception {
    	MockMultipartFile bulkUploadunsupported = new MockMultipartFile("csv", "device.csv", "text/csv", "".getBytes());
    	assertThrows(InvalidDataFormatException.class, () -> deviceService.createBulkDevices(bulkUploadunsupported));
    }
    
    @Test
    public void testGetDevices() throws EntityNotFoundException {
        int page = 0;
        int size = 5;
        String categoryId = "cat123";
        String deviceTypeId = null;  // Test with category ID only first

        List<Device> deviceList = Arrays.asList(new Device(), new Device(), new Device());  // Mock some devices
        Page<Device> devicePage = new PageImpl<>(deviceList, PageRequest.of(page, size), deviceList.size());
        Example<Device> example=Example.of(new Device());
        
        when(repository.findAll(any(example.getClass()), any(Pageable.class))).thenReturn(devicePage);

        DeviceResponse response = deviceService.searchDevice(page,size,categoryId,deviceTypeId);
        
        assertNotNull(response, "Response should not be null");
        assertEquals(devicePage.getNumber(), response.getPageNumber());
        assertEquals(devicePage.getSize(), response.getPageSize());
        assertEquals(devicePage.getTotalPages(), response.getTotalPages());
        assertEquals(devicePage.getTotalElements(), response.getTotalElements());
        assertEquals(deviceList.size(), response.getList().size());

        verify(repository).findAll(example, any(Pageable.class));
    }
    
    @Test
    public void testDeleteDeviceById() throws Exception {
        String deviceId = "dev123";
        Device device = new Device();
        device.setId(deviceId);
        
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));
        doNothing().when(repository).deleteById(deviceId);

        String result = deviceService.deleteDeviceById(deviceId);

        assertEquals(Constants.DEVICE_DELETED, result);
        verify(repository).findById(deviceId);
        verify(repository).deleteById(deviceId);
    }


}
