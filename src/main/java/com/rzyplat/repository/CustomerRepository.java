package com.rzyplat.repository;

import com.rzyplat.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer , String> {
	
}
