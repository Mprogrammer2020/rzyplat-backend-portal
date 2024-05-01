package com.rzyplat.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.service.FileService;
import com.rzyplat.repository.CategoryRepository;
import com.rzyplat.repository.DeviceTypeRepository;
import com.rzyplat.response.DeviceTypeResponse;
import com.rzyplat.service.DeviceTypeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeviceTypeServiceImpl implements DeviceTypeService{
	
	private final FileService fileService;
	private final DeviceTypeRepository repository;
	private final CategoryRepository categoryRepository;
	
    @Override
	public String createDeviceType(String categoryId,String type, MultipartFile image) throws EntityNotFoundException, IOException {
		Category category = categoryRepository.findById(categoryId).get();	
		
		DeviceType deviceType = new DeviceType();
		deviceType.setCategory(category);
		deviceType.setCreatedOn(LocalDateTime.now());
		deviceType.setType(type);
		deviceType.setCount(0);
		deviceType.setImagePath(fileService.save(image, "device_type"));
		repository.save(deviceType);
		
		return Constants.DEVICETYPE_CREATED;
    }
    
    @Override
	public DeviceTypeResponse getDevices(Integer page,Integer size,String categoryId) {
	    Pageable pageable = PageRequest.of(page, size);
	    Page<DeviceType> paged = repository.findByCategoryId(categoryId, pageable);
	    System.out.println("paged..."+paged);
        return new DeviceTypeResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),paged.getContent());
	}
    
    @Override
    public Optional<DeviceType> findById(String deviceId) throws EntityNotFoundException{
		Optional<DeviceType> deviceType = repository.findById(deviceId);
		if(!deviceType.isPresent()) {
			 throw new EntityNotFoundException(Constants.DEVICE, deviceId);
		 }
		return deviceType;
	}
    
    @Override
	public Optional<DeviceType> findByTypeAndCategoryId(String type,String categoryId) {
    	return repository.findByTypeAndCategoryId(type,categoryId);
    }

    
    @Override
	public List<DeviceType> getByCategoryId(String categoryId){
    	return repository.getByCategoryId(categoryId);
    }
    
    @Override
	public void save(DeviceType deviceType) {
    	repository.save(deviceType);
    }
    
    @Override
    public void saveAll(List<DeviceType> deviceTypes) {
    	repository.saveAll(deviceTypes);
    }

}
