package com.rzyplat.response;

import java.util.List;
import com.rzyplat.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CustomerSearchResponse {
	
	private Integer pageNumber;
	private Integer pageSize;
	private Integer totalPages;
	private Long totalElements;
	private List<CustomerDTO> list;

}
