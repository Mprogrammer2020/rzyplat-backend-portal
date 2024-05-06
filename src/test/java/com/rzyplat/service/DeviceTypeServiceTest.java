package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.DeviceTypeServiceImpl;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.repository.DeviceTypeRepository;
import com.rzyplat.response.DeviceTypeResponse;

@SpringBootTest
public class DeviceTypeServiceTest {

    @Mock
    private DeviceTypeRepository repository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private DeviceTypeServiceImpl service;

    @Test
    public void testCreateDeviceType() throws IOException, EntityNotFoundException {
        String categoryId = "1";
        String type = "Smartphone";
        MockMultipartFile file = new MockMultipartFile("image", "device.jpg", "image/jpeg", "test image".getBytes());

        Category category = new Category();
        category.setId(categoryId);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        DeviceType deviceType = new DeviceType();
        deviceType.setCategory(category);
        deviceType.setType(type);
        deviceType.setCreatedDate(LocalDateTime.now());
        

        when(repository.save(any(DeviceType.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = service.createDeviceType(categoryId, type, file);

        assertEquals(Constants.DEVICE_TYPE_CREATED, result);
        verify(categoryRepository).findById(categoryId);
        verify(repository).save(any(DeviceType.class));
    }

    @Test
    public void testGetDevices() {
        Pageable pageable = PageRequest.of(0, 10);
        List<DeviceType> deviceTypes = Arrays.asList(new DeviceType());
        Page<DeviceType> paged = new PageImpl<>(deviceTypes, pageable, 1);
        String categoryId = "1";

        when(repository.findByCategoryId(anyString(), any(Pageable.class))).thenReturn(paged);

        DeviceTypeResponse response = service.getDevices(0, 10, categoryId);

        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        verify(repository).findByCategoryId(anyString(), any(Pageable.class));
    }

    @Test
    public void testFindById() throws EntityNotFoundException {
        String deviceId = "123";
        DeviceType deviceType = new DeviceType();
        deviceType.setId(deviceId);

        when(repository.findById(deviceId)).thenReturn(Optional.of(deviceType));

        DeviceType found = service.findById(deviceId);

        assertEquals(deviceId, found.getId());
        verify(repository).findById(deviceId);
    }

    @Test
    public void testFindByTypeAndCategoryId() throws EntityNotFoundException {
        String type = "Smartphone";
        String categoryId = "1";
        DeviceType deviceType = new DeviceType();
        deviceType.setType(type);
        deviceType.setCategory(new Category());

        when(repository.findByTypeAndCategoryId(type, categoryId)).thenReturn(Optional.of(deviceType));

        DeviceType result = service.findByTypeAndCategoryId(type, categoryId);

        assertEquals(type, result.getType());
        verify(repository).findByTypeAndCategoryId(type, categoryId);
    }

    @Test
    public void testGetByCategoryId() {
        String categoryId = "1";
        List<DeviceType> deviceTypes = Arrays.asList(new DeviceType());

        when(repository.getByCategoryId(categoryId)).thenReturn(deviceTypes);

        List<DeviceType> results = service.getByCategoryId(categoryId);

        assertFalse(results.isEmpty());
        verify(repository).getByCategoryId(categoryId);
    }

    @Test
    public void testSave() {
        DeviceType deviceType = new DeviceType();
        service.save(deviceType);
        verify(repository).save(deviceType);
    }

    @Test
    public void testSaveAll() {
        List<DeviceType> deviceTypes = Arrays.asList(new DeviceType(), new DeviceType());
        service.saveAll(deviceTypes);
        verify(repository).saveAll(deviceTypes);
    }
}
