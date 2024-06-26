package com.rzyplat.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import com.rzyplat.response.MoldPropertyResponse;
import com.rzyplat.service.MoldPropertyService;

@SpringBootTest
@AutoConfigureMockMvc
public class MoldPropertyControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Mock
	private MoldPropertyService service;
	
	@Test
	public void testGetProperties() throws Exception {
		when(service.getProperties()).thenReturn(List.of(new MoldPropertyResponse()));
		
		mockMvc.perform(get("/mold/properties"))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$").isArray());
	}
}
