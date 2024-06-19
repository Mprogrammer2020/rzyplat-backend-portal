package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import com.rzyplat.dto.DeviceDTO;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.Device;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.exception.InvalidDataFormatException;
import com.rzyplat.impl.DeviceServiceImpl;
import com.rzyplat.repository.DeviceRepository;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.request.UpdateDeviceRequest;
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
    	MockMultipartFile bulkUploadUnsupported = new MockMultipartFile("image", "device.jpg", "image/jpeg", "test unsupported file".getBytes());
    	String message=deviceService.createBulkDevices(bulkUploadUnsupported);
    	assertEquals(message, Constants.UNSUPPORTED_FILE);
    }
    
    @Test
    public void testCreateBulkDevicesFormat() throws Exception {
    	MockMultipartFile bulkUploadunsupported = new MockMultipartFile("csv", "device.csv", "text/csv", "Device Category,Device Type,SKU".getBytes());
    	assertThrows(InvalidDataFormatException.class, () -> deviceService.createBulkDevices(bulkUploadunsupported));
    }
    
    @Test
    public void testUpdateDevice() throws EntityNotFoundException {
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
        when(repository.findById(categoryId)).thenReturn(Optional.<Device>of(device));
        when(objectMapper.convertValue(any(UpdateDeviceRequest.class), eq(Device.class))).thenReturn(device);
        when(repository.save(any(Device.class))).thenReturn(device);

        String result = deviceService.updateDevice(categoryId,createDeviceRequest);

        assertEquals(Constants.DEVICE_UPDATED, result);
        verify(repository).save(device);
    }
    
    @Test
    public void testSearchDevices() throws EntityNotFoundException {
        int page = 0;
        int size = 5;
        String categoryId = "cat123";
        String deviceTypeId = null; 
        
        Device device=new Device();
        device.setCategory(new Category());
        device.setDeviceType(new DeviceType());
        device.setManufacturer("test manufacturer");
        device.setSku("SKU94934");
        
        List<Device> deviceList = Arrays.asList(device);  
        Page<Device> devicePage = new PageImpl<>(deviceList, PageRequest.of(page, size), deviceList.size());
        Example<Device> example=Example.of(new Device());
        
        when(repository.findAll(any(example.getClass()), any(Pageable.class))).thenReturn(devicePage);
        when(objectMapper.convertValue(any(Device.class), eq(DeviceDTO.class))).thenReturn(new DeviceDTO());

        DeviceResponse response = deviceService.searchDevice(page,size,categoryId,deviceTypeId,null,null);
        
        assertNotNull(response, "Response should not be null");
        assertEquals(devicePage.getNumber(), response.getPageNumber());
        assertEquals(devicePage.getSize(), response.getPageSize());
        assertEquals(devicePage.getTotalPages(), response.getTotalPages());
        assertEquals(devicePage.getTotalElements(), response.getTotalElements());
        assertEquals(deviceList.size(), response.getList().size());

        verify(repository).findAll(any(example.getClass()), any(Pageable.class));
    }
    
    @Test
    public void testDeleteDeviceById() throws Exception {
    	Category category = new Category();
        category.setId("category123");
        category.setName("Electronics");
        category.setCount(0);
        
        DeviceType deviceType = new DeviceType();
        deviceType.setId("devType1");
        deviceType.setType("Smartphone");
        deviceType.setCount(0);
    	
        String deviceId = "dev123";
        Device device = new Device();
        device.setId(deviceId);
        device.setCategory(category);
        device.setDeviceType(deviceType);
        
        when(repository.findById(deviceId)).thenReturn(Optional.of(device));
        doNothing().when(repository).deleteById(deviceId);

        String result = deviceService.deleteDeviceById(deviceId);

        assertEquals(Constants.DEVICE_DELETED, result);
        verify(repository).findById(deviceId);
        verify(repository).deleteById(deviceId);
    }


}
