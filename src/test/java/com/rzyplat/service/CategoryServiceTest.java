package com.rzyplat.service;

import static org.mockito.Mockito.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.CategoryDTO;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.CategoryServiceImpl;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.response.CategoryResponse;

@SpringBootTest
public class CategoryServiceTest {
	
	@Mock
	private ObjectMapper objectMapper;
	@Mock
    private CategoryRepository repository;
	@Mock
    private DeviceTypeService deviceTypeService;
    @InjectMocks
    private CategoryServiceImpl service; // Assuming the service class is named CategoryServiceImpl

    @Test
    public void testCreateCategory() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
            "image", "test.jpg", "image/jpeg", "test image content".getBytes());
        
        when(repository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = service.createCategory("Electronics", file);

        assertEquals(Constants.CATEGORY_CREATED, result);
        verify(repository).save(any(Category.class));
    }

    @Test
    public void testSearchCategories() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Category> mockPage = new PageImpl<>(Arrays.asList(new Category()), pageable, 1);
        
        when(objectMapper.convertValue(any(Category.class), eq(CategoryDTO.class))).thenReturn(new CategoryDTO());
        when(repository.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(deviceTypeService.getByCategoryId(anyString())).thenReturn(Arrays.asList(new DeviceType()));

        CategoryResponse response = service.searchCategory(0, 10, null, null);

        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        verify(repository).findAll(pageable);
    }

    @Test
    public void testFindById() throws EntityNotFoundException {
        Category category = new Category();
        category.setName("Electronics");
        
        when(repository.findById("1")).thenReturn(Optional.of(category));

        Category found = service.findById("1");

        assertEquals("Electronics", found.getName());
        verify(repository).findById("1");
    }

    @Test
    public void testFindByName() throws EntityNotFoundException {
        Category category = new Category();
        category.setName("Electronics");
        
        when(repository.findByName("Electronics")).thenReturn(Optional.of(category));

        Category found = service.findByName("Electronics");
        
        assertEquals("Electronics", found.getName());
        verify(repository).findByName("Electronics");
    }

    @Test
    public void testSave() {
        Category category = new Category();
        service.save(category);
        verify(repository).save(category);
    }

    @Test
    public void testSaveAll() {
        List<Category> categories = Arrays.asList(new Category(), new Category());
        service.saveAll(categories);
        verify(repository).saveAll(categories);
    }
}
