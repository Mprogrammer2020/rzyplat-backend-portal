package com.rzyplat.service;

import com.rzyplat.request.ContactPaginateResponse;
import com.rzyplat.request.CreateContactRequest;
import com.rzyplat.request.UpdateContactRequest;

public interface ContactService {

	String createContact(CreateContactRequest contactCreateRequest);
	
	String updateContact(String contactId, UpdateContactRequest contactUpdateRequest) throws Exception;

	String deleteContactById(String contactId) throws Exception;

	ContactPaginateResponse getContacts(Integer pageNumber, Integer pageSize);
}
