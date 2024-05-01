package com.rzyplat.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.Category;

public interface CategoryRepository extends MongoRepository<Category , String>{

	Optional<Category> findByName(String categoryName);

}
