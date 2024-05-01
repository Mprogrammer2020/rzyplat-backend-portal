package com.rzyplat.controller;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.rzyplat.request.SearchParam;
import com.rzyplat.response.CategoryResponse;
import com.rzyplat.service.CategoryService;

@WebMvcTest(CategoryController.class)
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
	public void testGetCategories() throws Exception {
		CategoryResponse categoryResponse = new CategoryResponse(0, 10, 1, 100L, new ArrayList<>());
		SearchParam searchParam = new SearchParam(0, 10, "name", "desc");

		when(categoryService.getCategories(any(SearchParam.class))).thenReturn(categoryResponse);

		mockMvc.perform(get("/categories")
				.param("page", "0")
				.param("size", "10")
				.param("orderBy", "name")
				.param("direction", "desc"))
				.andExpect(status().isOk())
		        .andExpect(jsonPath("$.page").value(0))
		        .andExpect(jsonPath("$.size").value(10))
		        .andExpect(jsonPath("$.totalPages").value(1))
		        .andExpect(jsonPath("$.totalElements").value(100))
		        .andExpect(jsonPath("$.list").isArray());
	}

}
