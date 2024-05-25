package com.hyomoto.toquest;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hyomoto.toquest.business.model.TOQuestCardsModel;
import com.hyomoto.toquest.business.service.CardManagerService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class ToquestApplicationTests {

	@Autowired
	CardManagerService cardManagerService;

	@Test
	void contextLoads() {
	}

}
