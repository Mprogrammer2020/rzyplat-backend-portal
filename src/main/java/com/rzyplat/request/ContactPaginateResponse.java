package com.rzyplat.request;

import java.util.List;

import com.rzyplat.response.ContactResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class ContactPaginateResponse {

	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalElements;
	private List<ContactResponse> list;
}
