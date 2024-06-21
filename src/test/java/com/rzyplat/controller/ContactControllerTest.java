package com.rzyplat.controller;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Contact;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.ContactServiceImpl;
import com.rzyplat.request.ContactPaginateResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;
import com.rzyplat.response.ContactResponse;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
    private ContactServiceImpl contactService;
    
	private static final String name="John Wick";
	private static final String email="john@wick.com";
	private static final String phone="+915478548689";
	private static final String role="OWNER";
	
    @Test
    public void testCreateContact() throws Exception {
        String message="contact created successfully.";
    	CreateContactRequest createContactRequest = new CreateContactRequest();
    	createContactRequest.setName(name);
    	createContactRequest.setEmail(email);
    	createContactRequest.setPhone(phone);
    	createContactRequest.setRole(role);
    	
    	Contact contact=objectMapper.convertValue(createContactRequest, Contact.class);
        
        when(contactService.createContact(any(CreateContactRequest.class))).thenReturn(Constants.CONTACT_CREATED);
        
        mockMvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contact))) 
            .andExpect(status().isCreated())
            .andExpect(content().string(message));

        verify(contactService).createContact(any(CreateContactRequest.class));
    }
    
    @Test
    public void testCreateContactValidation() throws Exception {
    	CreateContactRequest createContactRequest = new CreateContactRequest();
    	createContactRequest.setName(null);
    	createContactRequest.setEmail(null);
    	createContactRequest.setPhone(null);
    	createContactRequest.setRole(null);
    	
    	Contact contact=objectMapper.convertValue(createContactRequest, Contact.class);
        
        mockMvc.perform(post("/contacts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(contact))) 
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testUpdateContact() throws Exception {
        String message="contact updated successfully.";
    	UpdateContactRequest updateContactRequest = new UpdateContactRequest();
    	updateContactRequest.setName(name);
    	updateContactRequest.setEmail(email);
    	updateContactRequest.setPhone(phone);
    	updateContactRequest.setRole(role);
        
        when(contactService.updateContact(any(String.class),any(UpdateContactRequest.class))).thenReturn(Constants.CONTACT_UPDATED);
        
        mockMvc.perform(put("/contacts/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateContactRequest))) 
            .andExpect(status().isOk())
            .andExpect(content().string(message));

        verify(contactService).updateContact(any(String.class),any(UpdateContactRequest.class));
    }
    
    @Test
    public void testUpdateContactValidation() throws Exception {
    	UpdateContactRequest updateContactRequest = new UpdateContactRequest();
    	updateContactRequest.setName(null);
    	updateContactRequest.setEmail(null);
    	updateContactRequest.setPhone(null);
    	updateContactRequest.setRole(null);
        
        mockMvc.perform(put("/contacts/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateContactRequest))) 
            .andExpect(status().isBadRequest());
    }
    
    @Test
    public void testGetContacts() throws Exception {
    	ContactPaginateResponse response = new ContactPaginateResponse(0, 10, 10, 100l, Arrays.asList(new ContactResponse()));
        
        when(contactService.getContacts(0, 10)).thenReturn(response);
        
        mockMvc.perform(get("/contacts"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageNumber").exists())
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.totalPages").value(10))
            .andExpect(jsonPath("$.totalElements").value(100))
            .andExpect(jsonPath("$.list").isArray());

        verify(contactService).getContacts(0, 10);
    }
    
    @Test
    public void testDeleteContact() throws Exception {
        when(contactService.deleteContactById("123")).thenReturn(Constants.CONTACT_DELETED);
        
        mockMvc.perform(delete("/contacts/123"))
            .andExpect(status().isOk())
            .andExpect(content().string(Constants.CONTACT_DELETED));

        verify(contactService).deleteContactById("123");
    }
    
    @Test
    public void testDeleteInvalidContact() throws Exception {
    	when(contactService.deleteContactById("1x2x")).thenThrow(new EntityNotFoundException(Constants.CONTACT ,Constants.ID, "1x2x"));
        
        mockMvc.perform(delete("/contacts/1x2x"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No contact with id '1x2x' was found!"));

        verify(contactService).deleteContactById("1x2x");
    }

}
