package com.rzyplat.service;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.CategoryServiceImpl;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.request.SearchParam;
import com.rzyplat.response.CategoryResponse;

@SpringBootTest
public class CategoryServiceTest {
	
	@Mock
    private FileService fileService;

	@Mock
    private CategoryRepository repository;

	@Mock
    private DeviceTypeService deviceTypeService;

    @InjectMocks
    private CategoryServiceImpl service; // Assuming the service class is named CategoryServiceImpl

    @Test
    public void testCreateCategory() throws Exception {
        String expectedPath = "path/to/image";
        MockMultipartFile file = new MockMultipartFile(
            "image", "test.jpg", "image/jpeg", "test image content".getBytes());
        when(fileService.save(any(MultipartFile.class), anyString())).thenReturn(expectedPath);
        when(repository.save(any(Category.class))).thenAnswer(i -> i.getArguments()[0]);

        String result = service.createCategory("Electronics", file);

        assertEquals(Constants.CATEGORY_CREATED, result);
        verify(fileService).save(file, "category");
        verify(repository).save(any(Category.class));
    }

    @Test
    public void testGetCategories() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("id").descending());
        Page<Category> mockPage = new PageImpl<>(Arrays.asList(new Category()), pageable, 1);
        when(repository.findAll(any(Pageable.class))).thenReturn(mockPage);
        when(deviceTypeService.getByCategoryId(anyString())).thenReturn(Arrays.asList(new DeviceType()));

        CategoryResponse response = service.getCategories(new SearchParam(0, 10, null, null));

        assertNotNull(response);
        assertEquals(1, response.getTotalPages());
        verify(repository).findAll(pageable);
    }

    @Test
    public void testFindById() throws EntityNotFoundException {
        Category category = new Category();
        category.setName("Electronics");
        
        when(repository.findById("1")).thenReturn(Optional.of(category));

        Optional<Category> found = service.findById("1");

        assertTrue(found.isPresent());
        assertEquals("Electronics", found.get().getName());
        verify(repository).findById("1");
    }

    @Test
    public void testFindByName() {
        Category category = new Category();
        category.setName("Electronics");
        
        when(repository.findByName("Electronics")).thenReturn(Optional.of(category));

        Optional<Category> found = service.findByName("Electronics");
        
        assertTrue(found.isPresent());
        assertEquals("Electronics", found.get().getName());
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
