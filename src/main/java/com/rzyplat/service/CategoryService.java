package com.rzyplat.service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.entity.Category;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.request.SearchParam;
import com.rzyplat.response.CategoryResponse;

public interface CategoryService {

	String createCategory(String name, MultipartFile image) throws IOException;

	CategoryResponse getCategories(SearchParam search);

	Optional<Category> findById(String categoryId) throws EntityNotFoundException;
	
	Optional<Category> findByName(String deviceName);

	void save(Category category);
	
	void saveAll(List<Category> categories);


}
