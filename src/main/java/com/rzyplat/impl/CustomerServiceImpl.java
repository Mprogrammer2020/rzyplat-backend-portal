package com.rzyplat.impl;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Customer;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchParam;
import com.rzyplat.response.CustomerSearchResponse;
import com.rzyplat.service.CustomerService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {
	
	private final ObjectMapper objectMapper;
	private final CustomerRepository repository;

	@Override
	public String createCustomer(CreateCustomerRequest customerCreateRequest) {
		Customer customer=objectMapper.convertValue(customerCreateRequest, Customer.class);
		
		customer.setCreatedDate(LocalDateTime.now());
		customer=repository.save(customer);
		return Constants.CUSTOMER_CREATED;
	}

	@Override
	public String deleteCustomerById(String customerId) throws Exception {
		 Optional<Customer> customerOptional=repository.findById(customerId);
		 if(!customerOptional.isPresent()) {
			 throw new EntityNotFoundException(Constants.CUSTOMER, customerId);
		 }
		 
		repository.deleteById(customerId);
		return Constants.CUSTOMER_DELETED;
	}

	@Override
	public CustomerSearchResponse searchCustomers(SearchParam search) {
		Sort sort=Sort.by(Constants.ID).descending();
        
        if(search.getOrderBy() != null) {
        	sort=Sort.by(search.getOrderBy()).ascending();
        	 
        	if (search.getDirection()!=null && Constants.DESC.equals(search.getDirection())) {
        		sort=Sort.by(search.getOrderBy()).descending();
             }
        }
        
        Page<Customer> paged=repository.findAll(PageRequest.of(search.getPage(), search.getSize(), sort));
        return new CustomerSearchResponse(paged.getNumber(),paged.getSize(),paged.getTotalPages(),paged.getTotalElements(),paged.getContent());
	}
}
