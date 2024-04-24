package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Action;
import com.rzyplat.entity.Customer;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.response.GenericResponse;

@SpringBootTest
public class CustomerServiceTest {

	@Mock
	private ObjectMapper objectMapper;
	 
	@MockBean
	private CustomerRepository repository;

	@Autowired
	private CustomerService customerService;

	@Test
	public void testCreateCustomer() {
		CreateCustomerRequest customerCreateRequest= new CreateCustomerRequest();
		
		Customer customer = new Customer();
		
		when(objectMapper.convertValue(customerCreateRequest, Customer.class)).thenReturn(customer);
		when(repository.save(any(Customer.class))).thenReturn(customer);

		GenericResponse<Customer> response = customerService.createCustomer(customerCreateRequest);

		verify(repository, times(1)).save(any(Customer.class));
		assertEquals(Action.CREATED, response.getAction());
		assertEquals(customer, response.getData());
	}

	@Test
	public void testGetCustomerById() {
		String customerId = "123";
		Optional<Customer> expectedCustomer = Optional.of(new Customer());

		when(repository.findById(customerId)).thenReturn(expectedCustomer);

		Optional<Customer> actualCustomer = customerService.getCustomerById(customerId);

		verify(repository, times(1)).findById(customerId);
		assertEquals(expectedCustomer, actualCustomer);
	}

	@Test
	public void testDeleteCustomerById() throws Exception {
		String customerId = "123";
		Optional<Customer> customerOptional = Optional.of(new Customer());

		when(repository.findById(customerId)).thenReturn(customerOptional);

		GenericResponse<Customer> response = customerService.deleteCustomerById(customerId);

		verify(repository, times(1)).deleteById(customerId);
		assertEquals(Action.DELETED, response.getAction());
		assertEquals(customerOptional.get(), response.getData());
	}
}
