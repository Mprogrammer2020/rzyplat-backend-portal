package com.rzyplat.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.DeviceType;

public interface DeviceTypeRepository extends MongoRepository<DeviceType , String>{

	Page<DeviceType> findByCategoryId(String categoryId, Pageable pageable);

	List<DeviceType> getByCategoryId(String categoryId);

	Optional<DeviceType> findByTypeAndCategoryId(String type,String categoryId);

}
