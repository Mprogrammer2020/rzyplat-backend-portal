package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.rzyplat.constant.Action;
import com.rzyplat.entity.Customer;
import com.rzyplat.repository.CustomerRepository;
import com.rzyplat.request.CustomerSearchParam;
import com.rzyplat.response.GenericResponse;
import com.rzyplat.response.SearchResponse;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:application.properties")
public class CustomerServiceTest {

	@MockBean
	private CustomerRepository repository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private CustomerService customerService;

	@Test
	public void testCreateCustomer() {
		Customer inputCustomer = new Customer();
		Customer savedCustomer = new Customer();
		
		when(repository.save(inputCustomer)).thenReturn(savedCustomer);

		GenericResponse<Customer> response = customerService.createCustomer(inputCustomer);

		verify(repository, times(1)).save(inputCustomer);
		assertEquals(Action.CREATED, response.getAction());
		assertEquals(savedCustomer, response.getData());
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

	@Test
	public void testSearchCustomers() {
		CustomerSearchParam searchParam = new CustomerSearchParam(0, 5,null,null); 
		
		Query expectedQuery = new Query(); 
		Sort sort = Sort.by(Sort.Direction.ASC, "propertyName");
		expectedQuery.with(sort);

		Pageable pageable = PageRequest.of(searchParam.getPage(), searchParam.getSize());
		expectedQuery.with(pageable);

		SearchResponse<Customer> response = customerService.searchCustomers(searchParam);

		assertNotNull(response.getList());
	}
}
