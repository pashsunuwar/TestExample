package com.qa.demo.rest;

import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.demo.persistence.domain.Card;
import com.qa.demo.persistence.repo.CardRepo;
import com.qa.demo.service.CardService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CardControllerIntegrationTest {
	
	@Autowired
	private MockMvc mock;
	
	@Autowired
	private CardRepo repo;
	
	@Autowired
	private CardService cardService;
	
	@Autowired
	private ObjectMapper mapper = new ObjectMapper();
	
	private Card testCard;
	
	private Card testCardWithID;
	
	
	@Before
	public void init() {
		this.repo.deleteAll();
		this.testCard = new Card("7 of spades", "White", "0.69");
		this.testCardWithID = this.repo.save(this.testCard);
		
	}
	
	@Test
	void test() throws Exception{
		this.testCard = new Card("7 of spades", "White", "0.69");
		this.testCardWithID = this.repo.save(this.testCard);
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.POST, "/card/create");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(testCard))
				.accept(MediaType.APPLICATION_JSON);
		
		//need to tell this test what http is getting pinged at it(for e.g json, other metadata)
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isCreated();
		
		// check the content we are getting back
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(testCardWithID));
	
		//checking status = header and checking content = body
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	void getIDtest() throws Exception{
		this.testCard = new Card("8 of spades", "White", "0.69");
		this.testCardWithID = this.repo.save(this.testCard);
		
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.GET, "/card/get/2");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
		.content(this.mapper.writeValueAsString(testCard))
			.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(testCardWithID));
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);

	}
	
	@Test
	void getAllTest() throws Exception {
		List <Card> found = cardService.readAll();
//		this.testCard = new Card("7 of spades", "White", "0.69");
//		this.testCardWithID = this.repo.save(this.testCard); 
//		
		
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.GET, "/card/getAll");
		
		mockRequest.accept(MediaType.APPLICATION_JSON);
		
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isOk();
		
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(found));
		
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}
	
	@Test
	void updateTest() throws Exception {
//		this.testCard1 = new Card("King of Clubs", "Black", "2.69");
//		this.testCardWithID2 = this.repo.save(this.testCard);
		MockHttpServletRequestBuilder mockRequest = 
				MockMvcRequestBuilders.request(HttpMethod.POST, "/card/update/1");
		
		mockRequest.contentType(MediaType.APPLICATION_JSON)
			.content(this.mapper.writeValueAsString(testCard))
				.accept(MediaType.APPLICATION_JSON);
		
		//need to tell this test what http is getting pinged at it(for e.g json, other metadata)
		ResultMatcher matchStatus = MockMvcResultMatchers.status().isAccepted();
		
		// check the content we are getting back
		ResultMatcher matchContent = MockMvcResultMatchers.content()
				.json(this.mapper.writeValueAsString(testCardWithID));
	
		//checking status = header and checking content = body
		this.mock.perform(mockRequest).andExpect(matchStatus).andExpect(matchContent);
	}

}
