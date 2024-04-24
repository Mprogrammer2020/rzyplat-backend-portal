package com.rzyplat.service;

import java.util.Optional;
import com.rzyplat.entity.Customer;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.SearchResponse;
import com.rzyplat.response.GenericResponse;

public interface CustomerService  {
		       
	GenericResponse<Customer> createCustomer(CreateCustomerRequest customerCreateRequest);

	Optional<Customer> getCustomerById(String customerId);

	GenericResponse<Customer> deleteCustomerById(String customerId) throws Exception;

	SearchResponse<Customer> searchCustomers(SearchCustomerParam param);
}
