package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.Device;

public interface DeviceRepository extends MongoRepository<Device , String>{

}
