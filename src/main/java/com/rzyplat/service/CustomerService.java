package com.rzyplat.service;

import java.util.Optional;
import com.rzyplat.entity.Customer;
import com.rzyplat.request.CustomerSearchParam;
import com.rzyplat.response.SearchResponse;
import com.rzyplat.response.GenericResponse;

public interface CustomerService  {
		       
	GenericResponse<Customer> createCustomer(Customer customer);

	Optional<Customer> getCustomerById(String customerId);

	GenericResponse<Customer> deleteCustomerById(String customerId) throws Exception;

	SearchResponse<Customer> searchCustomers(CustomerSearchParam param);
}
