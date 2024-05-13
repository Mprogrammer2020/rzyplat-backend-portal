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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.rzyplat.dto.CategoryDTO;
import com.rzyplat.response.CategoryResponse;
import com.rzyplat.service.CategoryService;

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService categoryService;

	@Test
	public void testCreateCategory() throws Exception {
		MockMultipartFile file = new MockMultipartFile("image", "filename.txt", "text/plain", "some xml".getBytes());
		String categoryName = "Electronics";
		String expectedResponse = "Category created successfully";

		when(categoryService.createCategory(categoryName, file)).thenReturn(expectedResponse);

		mockMvc.perform(MockMvcRequestBuilders.multipart("/categories").file(file).param("name", categoryName)
				.contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
				.andExpect(status().isCreated())
				.andExpect(content().string(expectedResponse));
	}
	
	   
    @Test
    public void testBasicGetCategories() throws Exception {
        List<CategoryDTO> categories = new ArrayList<>();

        when(categoryService.getCategories()).thenReturn(categories);

        mockMvc.perform(get("/categories/basic"))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$").isArray());
    }

	@Test
	public void testGetCategories() throws Exception {
		CategoryResponse categoryResponse = new CategoryResponse(0, 10, 1, 100L, new ArrayList<>());

		when(categoryService.searchCategory(0,10,"name","desc")).thenReturn(categoryResponse);

		mockMvc.perform(get("/categories")
				.param("page", "0")
				.param("size", "10")
				.param("orderBy", "name")
				.param("direction", "desc"))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.pageNumber").value(0))
		        .andExpect(jsonPath("$.pageSize").value(10))
		        .andExpect(jsonPath("$.totalPages").value(1))
		        .andExpect(jsonPath("$.totalElements").value(100))
		        .andExpect(jsonPath("$.list").isArray());
	}

}
