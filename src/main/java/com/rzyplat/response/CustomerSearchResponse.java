package com.rzyplat.response;

import java.util.List;
import com.rzyplat.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CustomerSearchResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<Customer> list;

}
