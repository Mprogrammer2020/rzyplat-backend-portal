package com.rzyplat.service;

import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.CustomerSearchResponse;

public interface CustomerService  {
		       
	String createCustomer(CreateCustomerRequest customerCreateRequest);

	String deleteCustomerById(String customerId) throws Exception;

	CustomerSearchResponse searchCustomers(SearchCustomerParam param);
}
