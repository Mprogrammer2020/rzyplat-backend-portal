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
import com.rzyplat.dto.ContactDTO;
import com.rzyplat.entity.Contact;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.impl.ContactServiceImpl;
import com.rzyplat.request.ContactSearchResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
    private ContactServiceImpl contactService;
    
    @Test
    public void testCreateContact() throws Exception {
        String message="contact created successfully.";
    	CreateContactRequest createContactRequest = new CreateContactRequest();
    	createContactRequest.setName("John Wick");
    	createContactRequest.setEmail("john@wick.com");
    	createContactRequest.setPhone("+915478548689");
    	createContactRequest.setRole("OWNER");
    	
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
    public void testUpdateContact() throws Exception {
        String message="contact updated successfully.";
    	UpdateContactRequest updateContactRequest = new UpdateContactRequest();
    	updateContactRequest.setName("John Wick");
    	updateContactRequest.setEmail("john@wick.com");
    	updateContactRequest.setPhone("+915478548689");
    	updateContactRequest.setRole("Maintenance");
        
        when(contactService.updateContact(any(String.class),any(UpdateContactRequest.class))).thenReturn(Constants.CONTACT_UPDATED);
        
        mockMvc.perform(put("/contacts/12345")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateContactRequest))) 
            .andExpect(status().isOk())
            .andExpect(content().string(message));

        verify(contactService).updateContact(any(String.class),any(UpdateContactRequest.class));
    }
    
    @Test
    public void testSearchContact() throws Exception {
        ContactSearchResponse response = new ContactSearchResponse(0, 10, 10, 100l, Arrays.asList(new ContactDTO()));
        
        when(contactService.searchContact(0, 10)).thenReturn(response);
        
        mockMvc.perform(get("/contacts/search"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.pageNumber").exists())
            .andExpect(jsonPath("$.pageSize").value(10))
            .andExpect(jsonPath("$.totalPages").value(10))
            .andExpect(jsonPath("$.totalElements").value(100))
            .andExpect(jsonPath("$.list").isArray());

        verify(contactService).searchContact(0, 10);
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
    public void testDeleteInavlidContact() throws Exception {
    	when(contactService.deleteContactById("1x2x")).thenThrow(new EntityNotFoundException(Constants.CONTACT ,Constants.ID, "1x2x"));
        
        mockMvc.perform(delete("/contacts/1x2x"))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("No contact with id '1x2x' was found!"));

        verify(contactService).deleteContactById("1x2x");
    }

}
