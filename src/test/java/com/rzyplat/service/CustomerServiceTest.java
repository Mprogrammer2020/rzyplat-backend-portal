package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Customer;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.CustomerSearchResponse;

@SpringBootTest
public class CustomerServiceTest {
	 
	@MockBean
	private CustomerRepository repository;

	@Autowired
	private CustomerService customerService;

	@Test
	public void testCreateCustomer() {
		CreateCustomerRequest customerCreateRequest= new CreateCustomerRequest();
		Customer customer = new Customer();
		
		when(repository.save(any(Customer.class))).thenReturn(customer);

		String message = customerService.createCustomer(customerCreateRequest);
		verify(repository, times(1)).save(any(Customer.class));
		assertEquals(message, Constants.CUSTOMER_CREATED);
	}

	@Test
	public void testDeleteCustomerById() throws Exception {
		String customerId = "123";
		Optional<Customer> customerOptional = Optional.of(new Customer());

		when(repository.findById(customerId)).thenReturn(customerOptional);

		String message = customerService.deleteCustomerById(customerId);
		verify(repository, times(1)).deleteById(customerId);
		assertEquals(message, Constants.CUSTOMER_DELETED);
	}
	
	@Test
	public void testSearchCustomers() throws Exception {
		Page<Customer> page = new PageImpl<Customer>(Arrays.asList(new Customer()), PageRequest.of(0, 10), 100);

		when(repository.findAll(any(Pageable.class))).thenReturn(page);

		SearchCustomerParam searchParam=new SearchCustomerParam(0, 10, null, null);
		CustomerSearchResponse searchResponse = customerService.searchCustomers(searchParam);
		
		verify(repository, times(1)).findAll(any(PageRequest.class));
		
		assertAll(
			() -> assertEquals(searchResponse.getTotalPages(), page.getTotalPages()),
			() -> assertEquals(searchResponse.getTotalElements(), page.getTotalElements()),
			() -> assertNotNull(page.getContent())
		);
	}
}
