package com.qa.demo.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.qa.demo.persistence.domain.Card;
import com.qa.demo.persistence.repo.CardRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
class CardServiceUnitTest {
	
	
	@MockBean
	CardRepo repo;
	
	@Autowired
	CardService cardService;
	
	
	@Test
	void testCreate() {
		Card card = new Card("7 of spades", "White", "0.69");
		when(repo.save(card)).thenReturn(card);
		
		assertEquals(cardService.create(card),card);
		
		verify(repo, Mockito.times(1)).save(card);
	}
		
	@Test
	void testReadId() {
//		Optional <Card> card = Optional.of(new Card("7 of spades", "White", "0.69"));
		Card card = new Card("7 of spades", "White", "0.69");
		Optional <Card> optionalCard = Optional.ofNullable(card);
		when(repo.findById((long) 1)).thenReturn(optionalCard);
		assertEquals(cardService.read((long) 1), card); 
	}
		
	
	

}
