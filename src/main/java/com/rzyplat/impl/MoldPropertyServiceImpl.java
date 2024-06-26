package com.rzyplat.impl;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.repository.MoldPropertyRepository;
import com.rzyplat.response.MoldPropertyResponse;
import com.rzyplat.service.MoldPropertyService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MoldPropertyServiceImpl implements MoldPropertyService {

	private final ModelMapper mapper;
	private final MoldPropertyRepository repository;
	
	@Override
	public List<MoldPropertyResponse> getProperties() {
		return mapper.map(repository.findAll(), new TypeToken<List<MoldPropertyResponse>>() {}.getType());
	}

}
