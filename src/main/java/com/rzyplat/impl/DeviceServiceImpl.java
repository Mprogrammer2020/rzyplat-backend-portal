package com.rzyplat.impl;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Category;
import com.rzyplat.entity.Device;
import com.rzyplat.entity.DeviceType;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.DeviceRepository;
import com.rzyplat.request.CreateDeviceRequest;
import com.rzyplat.request.DeviceSearchParam;
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
		Category category = categoryService.findById(createDeviceRequest.getCategoryId()).get();
		DeviceType deviceType = deviceTypeService.findById(createDeviceRequest.getDeviceTypeId()).get();
		
		Device device=objectMapper.convertValue(createDeviceRequest, Device.class);		
		device.setCategory(category);
		device.setDeviceType(deviceType);
		device.setCreatedDate(LocalDateTime.now());
		device.setManufacturer("Manufacturer Name");
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
		String fileName = bulkUpload.getOriginalFilename();

        if (fileName.endsWith(Constants.XLS) || fileName.endsWith(Constants.XLSX)) {
        	return handleExcelUpload(bulkUpload);
        } else if (fileName.endsWith(Constants.CSV)) {
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
	
	private String uploadBulkDevice(List<CreateDeviceFromFileRequest> devices) throws Exception {
		List<Device> devicesToSave=new ArrayList<Device>();
		List<Category> categories=new ArrayList<Category>();
		List<DeviceType> deviceTypes=new ArrayList<DeviceType>();
		
		for(CreateDeviceFromFileRequest deviceRequest:devices) {
			System.out.println(deviceRequest.getCategory() +" "+deviceRequest.getType()+" "+deviceRequest.getSerialNumber()+" "+deviceRequest.getSku());
			
			Optional<Category> categoryOptional = categoryService.findByName(deviceRequest.getCategory());
			
			if(categoryOptional.isPresent()) {
				Category category=categoryOptional.get();
				
				Optional<DeviceType> deviceTypeOptional = deviceTypeService.findByTypeAndCategoryId(deviceRequest.getType(),category.getId());
				if(deviceTypeOptional.isPresent()) {
					DeviceType deviceType=deviceTypeOptional.get();
					
					Device device=objectMapper.convertValue(deviceRequest, Device.class);		
					device.setCategory(category);
					device.setDeviceType(deviceType);
					device.setCreatedDate(LocalDateTime.now());
					device.setManufacturer("Manufacturer Name");
					
					category.setCount(category.getCount() + 1);
					deviceType.setCount(deviceType.getCount() + 1);
					
					categories.add(category);
					deviceTypes.add(deviceType);
					devicesToSave.add(device);
				} else {
					System.out.println(deviceRequest.getType()+" not found "+category.getId());
				}
			}	
		}
		
		categoryService.saveAll(categories);
		deviceTypeService.saveAll(deviceTypes);
		repository.saveAll(devicesToSave);
		
		return Constants.DEVICE_CREATED;
	}

	@Override
	public DeviceResponse getDevices(DeviceSearchParam search) {
	    Pageable pageable = PageRequest.of(search.getPage(), search.getSize());
	    Page<Device> paged;
	    if(search.getCategoryId()!=null) {
		     paged = repository.findByCategoryId(search.getCategoryId(), pageable);
	    } else {
	         paged = repository.findByDeviceTypeId(search.getDeviceTypeId(),pageable);
	    }
        return new DeviceResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),paged.getContent());
	}
	
	@Override
	public String deleteDeviceById(String deviceId) throws Exception {
		 Optional<Device> deviceOptional=repository.findById(deviceId);
		 if(!deviceOptional.isPresent()) {
			 throw new EntityNotFoundException(Constants.DEVICE, deviceId);
		 }
		repository.deleteById(deviceId);
		return Constants.DEVICE_DELETED;
	}
 

}
