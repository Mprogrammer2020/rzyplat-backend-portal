package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.rzyplat.entity.User;

public interface UserRepository extends MongoRepository<User , String>{

}
