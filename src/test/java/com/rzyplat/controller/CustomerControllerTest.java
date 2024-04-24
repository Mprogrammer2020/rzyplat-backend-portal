package com.rzyplat.controller;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Action;
import com.rzyplat.entity.Customer;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.GenericResponse;
import com.rzyplat.response.SearchResponse;
import com.rzyplat.service.CustomerService;
import com.rzyplat.util.Messages;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
    private Messages messages;
	
	@Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;
    
    @Test
    public void testCreateCustomer() throws Exception {
        String message="customer created successfully.";
    	CreateCustomerRequest createCustomerRequest = new CreateCustomerRequest();
    	createCustomerRequest.setName("John Wick");
    	createCustomerRequest.setEmail("john@wick.com");
    	createCustomerRequest.setPhone("+915478548689");
    	createCustomerRequest.setRole("OWNER");
    	createCustomerRequest.setProperty(Arrays.asList("Property 1","Property 2"));
    	
    	Customer customer=objectMapper.convertValue(createCustomerRequest, Customer.class);
        
        GenericResponse<Customer> response = new GenericResponse<>(Action.CREATED, message, customer);

        when(customerService.createCustomer(any(CreateCustomerRequest.class))).thenReturn(response);
        when(messages.get("customer.created")).thenReturn(message);
        
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))) 
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.name").value("John Wick"))
            .andExpect(jsonPath("$.data.email").value("john@wick.com"))
            .andExpect(jsonPath("$.action").value("CREATED"))
            .andExpect(jsonPath("$.message").value(message));

        verify(customerService).createCustomer(any(CreateCustomerRequest.class));
    }
    
    @Test
    public void testSearchCustomer() throws Exception {
        SearchResponse<Customer> response = new SearchResponse<Customer>(100L, Arrays.asList(new Customer()));
        
        when(customerService.searchCustomers(any(SearchCustomerParam.class))).thenReturn(response);

        mockMvc.perform(get("/customers/search"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.totalCount").value(100))
            .andExpect(jsonPath("$.list").isArray());

        verify(customerService).searchCustomers(any(SearchCustomerParam.class));
    }
    
    
    @Test
    public void testDeleteCustomer() throws Exception {
    	String message="customer deleted successfully.";
    	Customer customer = getCustomer();
        GenericResponse<Customer> response = new GenericResponse<>(Action.DELETED, message, customer);

        when(customerService.deleteCustomerById("123")).thenReturn(response);
        when(messages.get("customer.deleted")).thenReturn(message);
        
        mockMvc.perform(delete("/customers/123"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.action").value("DELETED"))
            .andExpect(jsonPath("$.message").value(message));

        verify(customerService).deleteCustomerById("123");
    }

    
    private Customer getCustomer() {
    	Customer customer = new Customer(); 
        customer.setName("John Wick");
        customer.setEmail("john@wick.com");
        
        return customer;
    }

}
