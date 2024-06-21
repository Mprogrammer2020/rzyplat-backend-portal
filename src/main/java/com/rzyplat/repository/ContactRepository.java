package com.rzyplat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.rzyplat.entity.Contact;

public interface ContactRepository extends MongoRepository<Contact , String> {

}
