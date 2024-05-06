package com.rzyplat.impl;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.CategoryDTO;
import com.rzyplat.dto.DeviceTypeDTO;
import com.rzyplat.entity.Category;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.response.CategoryResponse;
import com.rzyplat.service.CategoryService;
import com.rzyplat.service.DeviceTypeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final ObjectMapper objectMapper;
	private final CategoryRepository repository;
	private final DeviceTypeService deviceTypeService;

	@Override
	public String createCategory(String name,MultipartFile image) throws IOException {	
		Category category = new Category();
		category.setName(name);
		category.setCount(0);
		category.setImageContentType(image.getContentType());
		category.setImageContent(image.getBytes());
		category.setCreatedBy(Constants.SYSTEM);
		category.setUpdatedBy(Constants.SYSTEM);
		return save(category);
	}
	
	
	@Override
	public CategoryResponse searchCategory(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		Sort sort=Sort.by(Constants.ID).descending();
        
        if(Objects.nonNull(orderBy)) {
        	sort=Sort.by(orderBy).ascending();
        	if (Objects.nonNull(direction) && Constants.DESC.equals(direction)) {
        		sort=Sort.by(orderBy).descending();
             }
        }        
        
        Page<Category> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<CategoryDTO> categories = paged.getContent().stream().map(category -> {
        	CategoryDTO categoryDto = objectMapper.convertValue(category, CategoryDTO.class);
        	
        	List<DeviceTypeDTO> deviceTypes=deviceTypeService.getByCategoryId(category.getId())
        			.stream().map(deviceType -> objectMapper.convertValue(deviceType, DeviceTypeDTO.class)).collect(Collectors.toList());
        	
        	categoryDto.setDeviceTypes(deviceTypes);
        	return categoryDto;
        }).collect(Collectors.toList());
        
        return new CategoryResponse(paged.getNumber(), paged.getSize(),
        		paged.getTotalPages(), paged.getTotalElements(), categories);
	}
	
	@Override
    public Category findById(String categoryId) throws EntityNotFoundException {
		return repository.findById(categoryId)
				.orElseThrow(() -> new EntityNotFoundException(Constants.CATEGORY, Constants.ID, categoryId));
	}
	
	@Override
	public Category findByName(String categoryName) throws EntityNotFoundException {
		return repository.findByName(categoryName)
				.orElseThrow(() -> new EntityNotFoundException(Constants.CATEGORY, Constants.NAME, categoryName)) ;
	}
	
	@Override
	public String save(Category category) {
		repository.save(category);
		return Constants.CATEGORY_CREATED;
	}
	
	@Override
	public String saveAll(List<Category> categories) {
		repository.saveAll(categories);
		return Constants.CATEGORIES_CREATED;
	}
}
