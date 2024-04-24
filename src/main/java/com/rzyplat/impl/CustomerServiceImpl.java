package com.rzyplat.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Action;
import com.rzyplat.entity.Customer;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.SearchResponse;
import com.rzyplat.response.GenericResponse;
import com.rzyplat.service.CustomerService;
import com.rzyplat.util.Messages;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private Messages messages;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public GenericResponse<Customer> createCustomer(CreateCustomerRequest customerCreateRequest) {
		Customer customer=objectMapper.convertValue(customerCreateRequest, Customer.class);
		
		customer.setCreatedDate(LocalDateTime.now());
		customer=repository.save(customer);
		return new GenericResponse<Customer>(Action.CREATED, messages.get("customer.created"), customer);
	}

	@Override
	public Optional<Customer> getCustomerById(String customerId) {
		return repository.findById(customerId);
	}

	@Override
	public GenericResponse<Customer> deleteCustomerById(String customerId) throws Exception {
		 Optional<Customer> customerOptional=getCustomerById(customerId);
		 
		 if(customerOptional.isPresent()) {
			 repository.deleteById(customerId);
			 return new GenericResponse<Customer>(Action.DELETED, messages.get("customer.deleted"), customerOptional.get());
		 } else {
			 throw new EntityNotFoundException("customer", customerId);
		 }
	}

	@Override
	public SearchResponse<Customer> searchCustomers(SearchCustomerParam search) {
		Sort sort=Sort.by("id").descending();
        
        if(search.getOrderBy() != null) {
        	sort=Sort.by(search.getOrderBy()).ascending();
        	 
        	if (search.getDirection()!=null && "DESC".equals(search.getDirection())) {
        		sort=Sort.by(search.getOrderBy()).descending();
             }
        }
        
        Page<Customer> paged=repository.findAll(PageRequest.of(search.getPage(), search.getSize(), sort));
       
        return new SearchResponse<Customer>(paged.getTotalElements(),paged.getContent());
	}
}
