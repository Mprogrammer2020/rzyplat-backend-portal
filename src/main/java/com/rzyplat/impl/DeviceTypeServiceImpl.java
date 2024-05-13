package com.rzyplat.impl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.DeviceTypeDTO;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.repository.DeviceTypeRepository;
import com.rzyplat.response.DeviceTypeResponse;
import com.rzyplat.service.DeviceTypeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeviceTypeServiceImpl implements DeviceTypeService{
	
	private final ObjectMapper objectMapper;
	private final DeviceTypeRepository repository;
	private final CategoryRepository categoryRepository;
	
    @Override
	public String createDeviceType(String categoryId,String type, MultipartFile image) throws EntityNotFoundException, IOException {
		Category category = categoryRepository.findById(categoryId).get();	
		
		DeviceType deviceType = new DeviceType();
		deviceType.setCategory(category);
		deviceType.setType(type);
		deviceType.setCount(0);
		deviceType.setImageContentType(image.getContentType());
		deviceType.setImageContent(image.getBytes());
		deviceType.setCreatedBy(Constants.SYSTEM);
		deviceType.setUpdatedBy(Constants.SYSTEM);
		
		return save(deviceType);
    }

	@Override
	public List<DeviceTypeDTO> getDeviceTypes(String categoryId) {
		return repository.getByCategoryId(categoryId).stream()
			.map(device -> {
				DeviceTypeDTO dto=new DeviceTypeDTO();
				dto.setId(device.getId());
				dto.setType(device.getType());
				return dto;
			}).collect(Collectors.toList());
	}
    
    @Override
	public DeviceTypeResponse getDeviceTypes(Integer pageNumber,Integer pageSize,String categoryId) {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    Page<DeviceType> paged = repository.findByCategoryId(categoryId, pageable);
	    List<DeviceTypeDTO> deviceTypes=paged.getContent().stream()
	    		.map(deviceType -> objectMapper.convertValue(deviceType, DeviceTypeDTO.class))
	    		.collect(Collectors.toList());
        return new DeviceTypeResponse(paged.getNumber(), paged.getSize(), paged.getTotalPages(), paged.getTotalElements(), deviceTypes);
	}
    
    @Override
    public DeviceType findById(String deviceId) throws EntityNotFoundException{
		return repository.findById(deviceId).orElseThrow(() -> new EntityNotFoundException(Constants.DEVICE,Constants.ID,deviceId));
	}
    
    @Override
	public DeviceType findByTypeAndCategoryId(String type,String categoryId) throws EntityNotFoundException {
    	return repository.findByTypeAndCategoryId(type,categoryId)
    			.orElseThrow(() -> new EntityNotFoundException(String.format(Constants.NO_DEVICE_FOUND_BY_TYPE_CATEGORY, type, categoryId)));
    }

    @Override
	public List<DeviceType> getByCategoryId(String categoryId){
    	return repository.getByCategoryId(categoryId);
    }
    
    @Override
	public String save(DeviceType deviceType) {
    	repository.save(deviceType);
    	return Constants.DEVICE_TYPE_CREATED;
    }
    
    @Override
    public String saveAll(List<DeviceType> deviceTypes) {
    	repository.saveAll(deviceTypes);
    	return Constants.DEVICE_TYPES_CREATED;
    }
}
