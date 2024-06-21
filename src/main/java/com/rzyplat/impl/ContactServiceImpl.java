package com.rzyplat.impl;

import java.time.LocalDate;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.rzyplat.constant.Constants;
import com.rzyplat.entity.Contact;
import com.rzyplat.exception.EntityNotFoundException;
import com.rzyplat.repository.ContactRepository;
import com.rzyplat.request.ContactPaginateResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;
import com.rzyplat.response.ContactResponse;
import com.rzyplat.service.ContactService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ContactServiceImpl implements ContactService {

	private final ModelMapper modelMapper;
	private final ContactRepository repository;

	@Override
	public String createContact(CreateContactRequest contactCreateRequest) {
		Contact contact=modelMapper.map(contactCreateRequest, Contact.class);
		
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
		modelMapper.map(contact, contactUpdateRequest);
		repository.save(contact);
		
		return Constants.CONTACT_UPDATED;
	}
	
	@Override
	public String deleteContactById(String contactId) throws EntityNotFoundException {
		repository.delete(getById(contactId));
		return Constants.CONTACT_DELETED;
	}
	
	@Override
	public ContactPaginateResponse getContacts(Integer pageNumber, Integer pageSize) {
		Sort sort=Sort.by(Constants.ID).descending();
        Page<Contact> paged=repository.findAll(PageRequest.of(pageNumber, pageSize, sort));
        
        List<ContactResponse> contacts=modelMapper.map(paged.getContent(), List.class);
        return new ContactPaginateResponse(paged.getNumber(), paged.getSize(), paged.getTotalPages(), paged.getTotalElements(), contacts);
	}
}
