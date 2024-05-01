package com.rzyplat.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {
	
	private Integer page;
	private Integer size;
	private Integer totalPages;
	private Long totalElements;
	private List<CategoryDeviceTypes> list;
}
