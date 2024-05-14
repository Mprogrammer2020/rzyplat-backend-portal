package com.rzyplat.service;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.rzyplat.dto.CategoryDTO;
import com.rzyplat.entity.Category;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.response.CategoryResponse;

public interface CategoryService {

	String createCategory(String name, MultipartFile image) throws IOException;

	List<CategoryDTO> getCategories();
	
	CategoryResponse searchCategory(Integer pageNumber, Integer pageSize, String orderBy, String direction);

	Category findById(String categoryId) throws EntityNotFoundException;
	
	CategoryDTO findDTOById(String categoryId) throws EntityNotFoundException;
	
	Category findByName(String categoryName) throws EntityNotFoundException;

	String save(Category category);
	
	String saveAll(List<Category> categories);


}
