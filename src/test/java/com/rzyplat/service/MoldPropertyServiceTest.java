package com.rzyplat.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import com.google.gson.reflect.TypeToken;
import com.rzyplat.impl.MoldPropertyServiceImpl;
import com.rzyplat.repository.MoldPropertyRepository;
import com.rzyplat.response.MoldPropertyResponse;

@SpringBootTest
public class MoldPropertyServiceTest {
	
	@Mock
	private ModelMapper mapper;
	@Mock
	private MoldPropertyRepository repository;
	@InjectMocks
	private MoldPropertyServiceImpl service;
	
	@Test
	public void testGetProperties() {
		List<MoldPropertyResponse> expected=List.of(new MoldPropertyResponse(),new MoldPropertyResponse(),new MoldPropertyResponse());
		
		when(mapper.map(anyList(), eq(new TypeToken<List<MoldPropertyResponse>>() {}.getType()))).thenReturn(expected);
		
		List<MoldPropertyResponse> actual=service.getProperties();
		
		assertNotNull(actual);
		assertEquals(actual.size(), 3);
	}
}
