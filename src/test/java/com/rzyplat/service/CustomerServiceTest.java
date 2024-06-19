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
import com.rzyplat.entity.Contact;
import com.rzyplat.repository.ContactRepository;
import com.rzyplat.request.ContactSearchResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;

@SpringBootTest
public class CustomerServiceTest {
	 
	@MockBean
	private ContactRepository repository;

	@Autowired
	private ContactService contactService;

	@Test
	public void testCreateContact() {
		CreateContactRequest contactCreateRequest= new CreateContactRequest();
		Contact contact = new Contact();
		
		when(repository.save(any(Contact.class))).thenReturn(contact);

		String message = contactService.createContact(contactCreateRequest);
		verify(repository, times(1)).save(any(Contact.class));
		assertEquals(message, Constants.CONTACT_CREATED);
	}
	
	@Test
	public void testUpdateContact() throws Exception {
		String contactId="123";
		Optional<Contact> contactOptional = Optional.of(new Contact());
		
		UpdateContactRequest contactUpdateRequest= new UpdateContactRequest();
		Contact contact = new Contact();
		
		when(repository.findById(contactId)).thenReturn(contactOptional);
		when(repository.save(any(Contact.class))).thenReturn(contact);

		String message = contactService.updateContact(contactId, contactUpdateRequest);
		verify(repository, times(1)).save(any(Contact.class));
		assertEquals(message, Constants.CONTACT_UPDATED);
	}

	@Test
	public void testDeleteContactById() throws Exception {
		String contactId = "123";
		Contact contact = new Contact();
		Optional<Contact> contactOptional = Optional.of(contact);

		when(repository.findById(contactId)).thenReturn(contactOptional);

		String message = contactService.deleteContactById(contactId);
		verify(repository, times(1)).delete(contact);
		assertEquals(message, Constants.CONTACT_DELETED);
	}
	
	@Test
	public void testSearchContact() throws Exception {
		Page<Contact> page = new PageImpl<Contact>(Arrays.asList(new Contact()), PageRequest.of(0, 10), 100);

		when(repository.findAll(any(Pageable.class))).thenReturn(page);

		ContactSearchResponse searchResponse = contactService.searchContact(0, 10);
		
		verify(repository, times(1)).findAll(any(PageRequest.class));
		
		assertAll(
			() -> assertEquals(searchResponse.getTotalPages(), page.getTotalPages()),
			() -> assertEquals(searchResponse.getTotalElements(), page.getTotalElements()),
			() -> assertNotNull(page.getContent())
		);
	}
}
