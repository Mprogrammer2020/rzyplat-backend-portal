package com.rzyplat.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzyplat.constant.Constants;
import com.rzyplat.dto.ContactDTO;
import com.rzyplat.entity.Contact;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.ContactRepository;
import com.rzyplat.request.ContactSearchResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;
import com.rzyplat.service.ContactService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

	private final ObjectMapper objectMapper;
	private final ContactRepository repository;

	@Override
	public String createContact(CreateContactRequest contactCreateRequest) {
		Contact contact=objectMapper.convertValue(contactCreateRequest, Contact.class);
		
		contact.setJoiningDate(LocalDate.now());
		repository.save(contact);
		return Constants.CONTACT_CREATED;
	}
	
	private Contact getById(String contactId) throws EntityNotFoundException {
		return repository.findById(contactId).orElseThrow(() -> new EntityNotFoundException(Constants.CONTACT,Constants.ID,contactId));
	}

	@Override
	public String updateContact(String contactId, UpdateContactRequest contactUpdateRequest) throws Exception {
		Contact contact=getById(contactId);
		objectMapper.updateValue(contact, contactUpdateRequest);
		repository.save(contact);
		
		return Constants.CONTACT_UPDATED;
	}
	
	@Override
	public String deleteContactById(String contactId) throws EntityNotFoundException {
		repository.delete(getById(contactId));
		return Constants.CONTACT_DELETED;
	}
	
	@Override
	public ContactSearchResponse searchContact(Integer pageNumber, Integer pageSize) {
		Sort sort=Sort.by(Constants.ID).descending();
        
        Page<Contact> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        List<ContactDTO> contacts=paged.getContent().stream().map(contact -> objectMapper.convertValue(contact, ContactDTO.class)).collect(Collectors.toList());
        return new ContactSearchResponse(paged.getNumber(), paged.getSize(), paged.getTotalPages(), paged.getTotalElements(), contacts);
	}
}
