package com.rzyplat.impl;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.constant.CreateDeviceAllowedTypes;
import com.rzyplat.dto.DeviceDTO;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.Device;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.exception.InvalidDataFormatException;
import com.rzyplat.repository.DeviceRepository;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.request.CreateDeviceFromFileRequest;
import com.rzyplat.response.DeviceResponse;
import com.rzyplat.service.CategoryService;
import com.rzyplat.service.DeviceService;
import com.rzyplat.service.DeviceTypeService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DeviceServiceImpl implements DeviceService{
	private final DeviceRepository repository;
	private final ObjectMapper objectMapper;
	private final CategoryService categoryService;	
	private final DeviceTypeService deviceTypeService;
	
	@Override
	public String createDevice(CreateDeviceRequest createDeviceRequest) throws EntityNotFoundException {
		Category category = categoryService.findById(createDeviceRequest.getCategoryId());
		DeviceType deviceType = deviceTypeService.findById(createDeviceRequest.getDeviceTypeId());
		
		Device device=objectMapper.convertValue(createDeviceRequest, Device.class);		
		device.setCategory(category);
		device.setDeviceType(deviceType);
		device.setCreatedBy(Constants.SYSTEM);
		device.setUpdatedBy(Constants.SYSTEM); 
		device.setManufacturer(Constants.MANUFACTURER);
		repository.save(device);
		
		//update category count
		category.setCount(category.getCount() + 1);
		categoryService.save(category);
		
		//update device type count
		deviceType.setCount(deviceType.getCount() + 1);
		deviceTypeService.save(deviceType);
		
		return Constants.DEVICE_CREATED;
	}
	
	@Override
	public String createBulkDevices(MultipartFile bulkUpload) throws Exception {
        if (bulkUpload.getContentType().equals(CreateDeviceAllowedTypes.XLS) || bulkUpload.getContentType().equals(CreateDeviceAllowedTypes.XLSX)) {
        	return handleExcelUpload(bulkUpload);
        } else if (bulkUpload.getContentType().equals(CreateDeviceAllowedTypes.CSV)) {
        	return handleCsvUpload(bulkUpload);
        }	
        return Constants.UNSUPPORTED_FILE;
	}

	public String handleExcelUpload(MultipartFile bulkUpload) throws Exception {
		Workbook workbook = WorkbookFactory.create(bulkUpload.getInputStream());
	    Sheet sheet = workbook.getSheetAt(0); 
	    
	    List<CreateDeviceFromFileRequest> devices=new ArrayList<CreateDeviceFromFileRequest>();
	    
	    for (Row row : sheet) {
	    	if(row.getCell(0)==null) break;
	    	CreateDeviceFromFileRequest device = new CreateDeviceFromFileRequest(
	    			row.getCell(0).getStringCellValue(),
	    			row.getCell(1).getStringCellValue(),
	    			row.getCell(2).getStringCellValue(),
	    			row.getCell(3).getStringCellValue()
	    			);
	    	
	    	devices.add(device);
	    }
	    
	    return uploadBulkDevice(devices);
	}

	public String handleCsvUpload(MultipartFile bulkUpload) throws Exception {
		Reader reader = new InputStreamReader(bulkUpload.getInputStream());
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        
        List<CreateDeviceFromFileRequest> devices=new ArrayList<CreateDeviceFromFileRequest>();
        for (CSVRecord record : csvParser) {
	    	CreateDeviceFromFileRequest device = new CreateDeviceFromFileRequest(
	    			record.get(0),
	    			record.get(1),
	    			record.get(2),
	    			record.get(3)
	    			);
	    	devices.add(device);
        }
        return uploadBulkDevice(devices);
	}
	
	public String uploadBulkDevice(List<CreateDeviceFromFileRequest> devices) throws Exception {
		List<Device> devicesToSave=new ArrayList<Device>();
		List<Category> categories=new ArrayList<Category>();
		List<DeviceType> deviceTypes=new ArrayList<DeviceType>();
		
		System.out.println("devices::"+devices);
		for(CreateDeviceFromFileRequest deviceRequest:devices) {
			Category category = categoryService.findByName(deviceRequest.getCategory());
			DeviceType deviceType = deviceTypeService.findByTypeAndCategoryId(deviceRequest.getType(),category.getId());
				
			Device device=objectMapper.convertValue(deviceRequest, Device.class);		
			device.setCategory(category);
			device.setDeviceType(deviceType);
			device.setCreatedDate(LocalDateTime.now());
			device.setManufacturer(Constants.MANUFACTURER);
			device.setCreatedBy(Constants.SYSTEM);
			device.setUpdatedBy(Constants.SYSTEM);
			
			category.setCount(category.getCount() + 1);
			deviceType.setCount(deviceType.getCount() + 1);
			
			categories.add(category);
			deviceTypes.add(deviceType);
			devicesToSave.add(device);
		}
		
		if(devicesToSave.isEmpty()) {
			throw new InvalidDataFormatException(Constants.DEVICE_FILE_INVALID_FORMAT);
		}
		
		categoryService.saveAll(categories);
		deviceTypeService.saveAll(deviceTypes);
		repository.saveAll(devicesToSave);
		
		return Constants.DEVICE_CREATED;
	}

	
	@Override
	public DeviceResponse searchDevice(Integer pageNumber, Integer pageSize, String categoryId, String deviceTypeId) throws EntityNotFoundException {
	    Pageable pageable = PageRequest.of(pageNumber, pageSize);
	    ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.EXACT);

	    Device exampleDevice = new Device();
	    if(Objects.nonNull(categoryId)) {
	    	exampleDevice.setCategory(categoryService.findById(categoryId));
	    }
	    if(Objects.nonNull(deviceTypeId)) {
	    	exampleDevice.setDeviceType(deviceTypeService.findById(deviceTypeId));
	    }

        Example<Device> example = Example.of(exampleDevice, matcher);
	    Page<Device> paged = repository.findAll(example, pageable);
	    
	    List<DeviceDTO> devices=paged.getContent().stream()
	    		.map(device ->  { 
	    			DeviceDTO dto=objectMapper.convertValue(device, DeviceDTO.class); 
	    			dto.setCategoryId(device.getCategory().getId());
	    			dto.setCategoryName(device.getCategory().getName());
	    			dto.setDeviceTypeId(device.getDeviceType().getId());
	    			dto.setDeviceTypeLabel(device.getDeviceType().getType());
	    			return dto;
	    		})
	    		.collect(Collectors.toList());
        return new DeviceResponse(paged.getNumber(), paged.getSize(), paged.getTotalPages(), paged.getTotalElements(), devices);
	}
	
	@Override
	public String deleteDeviceById(String deviceId) throws Exception {
		repository.findById(deviceId).orElseThrow(() -> new EntityNotFoundException(Constants.DEVICE, Constants.ID, deviceId));
			repository.deleteById(deviceId);
		return Constants.DEVICE_DELETED;
	}
 

}
