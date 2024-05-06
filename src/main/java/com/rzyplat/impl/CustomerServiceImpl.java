package com.rzyplat.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.CustomerDTO;
import com.rzyplat.entity.Customer;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CreateCustomerRequest;
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
		repository.findById(customerId).orElseThrow(() -> new EntityNotFoundException(Constants.CUSTOMER,Constants.ID,customerId));
			repository.deleteById(customerId);
		return Constants.CUSTOMER_DELETED;
	}

	
	@Override
	public CustomerSearchResponse searchCustomers(Integer pageNumber, Integer pageSize, String orderBy, String direction) {
		Sort sort=Sort.by(Constants.ID).descending();
        
        if(Objects.nonNull(orderBy)) {
        	sort=Sort.by(orderBy).ascending();
        	 
        	if (Objects.nonNull(direction) && Constants.DESC.equals(direction)) {
        		sort=Sort.by(direction).descending();
             }
        }
        
        Page<Customer> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<CustomerDTO> customers=paged.getContent().stream().map(customer -> objectMapper.convertValue(customer, CustomerDTO.class)).collect(Collectors.toList());
        return new CustomerSearchResponse(paged.getNumber(), paged.getSize(), paged.getTotalPages(), paged.getTotalElements(), customers);
	}
}
