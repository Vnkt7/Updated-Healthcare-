package org.eureka.registry.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.eureka.registry.entities.Enrollee;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EnrolleeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void saveEnrollee() throws Exception {
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("name", "Enrollee");
		params.add("activationStatus", "true");
		params.add("birthDate", "2020/09/30");
		params.add("phoneNumber", "57898765");
		MvcResult mvcResult = this.mockMvc.perform(post("/enrollee/v1").params(params)).andDo(print()).andExpect(status().isCreated()).andReturn();
		Assert.assertNotNull(mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testGetEnrollees() throws Exception {
		saveEnrollee();
		MvcResult mvcResult = this.mockMvc.perform(get("/enrollee/all")).andDo(print()).andExpect(status().isOk()).andReturn();
		List<Enrollee> participantJsonList = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), 
				new TypeReference<List<Enrollee>>(){});
		Assert.assertFalse(participantJsonList.isEmpty());
		Assert.assertEquals("Enrollee", participantJsonList.get(0).getName());
	}
	
	@Test
	public void testGetEnrolleById() throws Exception {
		saveEnrollee();
		Enrollee enrollee = getEnrolleeById(1l);
		Assert.assertNotNull(enrollee);
		Assert.assertEquals(1l, enrollee.getId());
	}

	private Enrollee getEnrolleeById(long id)
			throws Exception, JsonProcessingException, JsonMappingException, UnsupportedEncodingException {
		MvcResult mvcResult = this.mockMvc.perform(get("/enrollee/v1").param("enrolleeId", String.valueOf(id))).andDo(print()).andExpect(status().isOk()).andReturn();
		Enrollee enrollee = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), 
				Enrollee.class);
		return enrollee;
	}
	
	@Test
	public void testUpdateEnrolleById() throws Exception {
		saveEnrollee();
		LinkedMultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("enrolleeId", "1");
		params.add("name", "New Enrollee");
		params.add("activationStatus", "false");
		MvcResult mvcResult = this.mockMvc.perform(put("/enrollee").params(params)).andDo(print()).andExpect(status().isOk()).andReturn();
		Integer updatedCount = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), 
				Integer.class);
		Assert.assertNotNull(updatedCount);
		Assert.assertEquals(Integer.valueOf(1), updatedCount);
		Enrollee updatedEnrollee = getEnrolleeById(1l);
		Assert.assertEquals("New Enrollee", updatedEnrollee.getName());
		Assert.assertEquals(false, updatedEnrollee.isActivationStatus());
	}
	
	@Test
	public void testeleteEnrollee() throws Exception {
		saveEnrollee();
		this.mockMvc.perform(delete("/enrollee").param("enrolleeId", "1")).andDo(print()).andExpect(status().isOk());
		this.mockMvc.perform(get("/enrollee/v1").param("enrolleeId", "1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$").doesNotExist());

	}
}
