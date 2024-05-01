package com.rzyplat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.Device;

public interface DeviceRepository extends MongoRepository<Device , String>{

	Page<Device> findByCategoryId(String categoryId, Pageable pageable);

	Page<Device> findByDeviceTypeId(String deviceTypeId, Pageable pageable);

}
