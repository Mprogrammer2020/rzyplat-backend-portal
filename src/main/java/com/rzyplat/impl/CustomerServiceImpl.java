package com.rzyplat.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.rzyplat.constant.Action;
import com.rzyplat.entity.Customer;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CustomerSearchParam;
import com.rzyplat.response.SearchResponse;
import com.rzyplat.response.GenericResponse;
import com.rzyplat.service.CustomerService;
import com.rzyplat.util.Messages;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private Messages messages;
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private CustomerRepository repository;

	@Override
	public GenericResponse<Customer> createCustomer(Customer customer) {
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
			 throw new Exception("");
		 }
	}

	@Override
	public SearchResponse<Customer> searchCustomers(CustomerSearchParam search) {
		Query query = new Query();
        
        String property = search.getSortBy();
        String order = search.getOrderBy();
        
        if(property != null && order != null) {
        	 if (order.equalsIgnoreCase("ASC")) {
                 query.with(Sort.by(Sort.Direction.ASC, property));
             } else if (order.equalsIgnoreCase("DESC")) {
                 query.with(Sort.by(Sort.Direction.DESC, property));
             }
        }
        
        Long totalCount=mongoTemplate.count(query, Customer.class);
        
        Pageable pageable = PageRequest.of(search.getPage(), search.getSize());
        List<Customer> customers=mongoTemplate.find(query.with(pageable), Customer.class);
        
        return new SearchResponse<Customer>(totalCount,customers);
	}
}
