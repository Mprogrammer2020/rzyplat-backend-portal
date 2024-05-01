package com.rzyplat.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Category;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.request.SearchParam;
import com.rzyplat.response.CategoryDeviceTypes;
import com.rzyplat.response.CategoryResponse;
import com.rzyplat.service.CategoryService;
import com.rzyplat.service.DeviceTypeService;
import com.rzyplat.service.FileService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	
	private final FileService fileService;
	private final CategoryRepository repository;
	private final DeviceTypeService deviceTypeService;

	@Override
	public String createCategory(String name,MultipartFile image) throws IOException {	
		Category category = new Category();
		category.setName(name);
		category.setCreatedOn(LocalDateTime.now());
		category.setCount(0);
		category.setImagePath(fileService.save(image,"category"));
		repository.save(category);
		return Constants.CATEGORY_CREATED;
	}
	
	@Override
	public CategoryResponse getCategories(SearchParam search) {
        Sort sort=Sort.by(Constants.ID).descending();
        
        if(search.getOrderBy() != null) {
        	sort=Sort.by(search.getOrderBy()).ascending();
        	 
        	if (search.getDirection()!=null && Constants.DESC.equals(search.getDirection())) {
        		sort=Sort.by(search.getOrderBy()).descending();
             }
        }        
        
        
        Page<Category> paged=repository.findAll(PageRequest.of(search.getPage(), search.getSize(), sort));
        
        List<CategoryDeviceTypes> categoryDevices = paged.getContent().stream().map(category -> {
        	CategoryDeviceTypes devices = new CategoryDeviceTypes();
        	devices.setCategory(category);
        	devices.setDeviceTypes(deviceTypeService.getByCategoryId(category.getId()));
        	return devices;
        }).collect(Collectors.toList());
        
        return new CategoryResponse(paged.getNumber(), paged.getSize(),
        		paged.getTotalPages(), paged.getTotalElements(), categoryDevices);
	}
	
	@Override
    public Optional<Category> findById(String categoryId) throws EntityNotFoundException{
		Optional<Category> category = repository.findById(categoryId);
		if(!category.isPresent()) {
			 throw new EntityNotFoundException(Constants.Category, categoryId);
		 }
		return category;
	}
	
	@Override
	public Optional<Category> findByName(String categoryName) {
		return repository.findByName(categoryName);
	}
	
	@Override
	public void save(Category category) {
		repository.save(category);
	}
	
	@Override
	public void saveAll(List<Category> categories) {
		repository.saveAll(categories);
	}
}
