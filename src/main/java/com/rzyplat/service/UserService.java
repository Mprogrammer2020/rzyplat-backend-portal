package com.rzyplat.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.rzyplat.entity.User;
import com.rzyplat.param.UserSearchParam;
import com.rzyplat.repository.UserRepository;
import com.rzyplat.util.UserSpecification;
@Service
public class UserService implements UserSpecification{
	private UserRepository repository;
    private final MongoTemplate mongoTemplate;

	public UserService(UserRepository repository, MongoTemplate mongoTemplate) {
		 this.repository = repository;
	     this.mongoTemplate = mongoTemplate;
	    }
	       
	public User save(User user) {
		user.setCreatedDate(LocalDateTime.now());
		return repository.save(user);
	}

	public Optional<User> getById(String id) {
		return repository.findById(id);
	}

	public void deleteById(String id) {
		 repository.deleteById(id);	
	}

	public List<User> search(UserSearchParam param) {
		
		Pageable pageable = PageRequest.of(param.getPage(), param.getSize());
        Query query = specification(param);
        long count = mongoTemplate.count(query, User.class);
        
        return mongoTemplate.find(query.with(pageable), User.class);
	}
    
     public Long count(UserSearchParam param) {
		
		Pageable pageable = PageRequest.of(param.getPage(), param.getSize());
        Query query = specification(param);
        long count = mongoTemplate.count(query, User.class);
        
        return count;
	}
}
