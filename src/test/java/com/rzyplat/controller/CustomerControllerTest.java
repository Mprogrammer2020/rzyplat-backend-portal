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
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Customer;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.request.CreateCustomerRequest;
import com.rzyplat.request.SearchCustomerParam;
import com.rzyplat.response.CustomerSearchResponse;
import com.rzyplat.service.CustomerService;

@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
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
        
        when(customerService.createCustomer(any(CreateCustomerRequest.class))).thenReturn(Constants.CUSTOMER_CREATED);
        
        mockMvc.perform(post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(customer))) 
            .andExpect(status().isCreated())
            .andExpect(content().string(message));

        verify(customerService).createCustomer(any(CreateCustomerRequest.class));
    }
    
    @Test
    public void testSearchCustomer() throws Exception {
        CustomerSearchResponse response = new CustomerSearchResponse(0, 10, 10, 100l, Arrays.asList(new Customer()));
        
        when(customerService.searchCustomers(any(SearchCustomerParam.class))).thenReturn(response);

        mockMvc.perform(get("/customers/search"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.page").value(0))
            .andExpect(jsonPath("$.size").value(10))
            .andExpect(jsonPath("$.totalPages").value(10))
            .andExpect(jsonPath("$.totalElements").value(100))
            .andExpect(jsonPath("$.list").isArray());

        verify(customerService).searchCustomers(any(SearchCustomerParam.class));
    }
    
    @Test
    public void testDeleteCustomer() throws Exception {
        when(customerService.deleteCustomerById("123")).thenReturn(Constants.CUSTOMER_DELETED);
        
        mockMvc.perform(delete("/customers/123"))
            .andExpect(status().isOk())
            .andExpect(content().string(Constants.CUSTOMER_DELETED));

        verify(customerService).deleteCustomerById("123");
    }
    
    @Test
    public void testDeleteInavlidCustomer() throws Exception {
        when(customerService.deleteCustomerById("1x2x")).thenThrow(new EntityNotFoundException("customer", "1x2x"));
        
        mockMvc.perform(delete("/customers/1x2x"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No customer with id 1x2x was found!"));

        verify(customerService).deleteCustomerById("1x2x");
    }

}
