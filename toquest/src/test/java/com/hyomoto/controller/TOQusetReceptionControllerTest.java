package com.hyomoto.controller;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyomoto.toquest.ToquestApplication;
import com.hyomoto.toquest.business.controller.TOQusetReceptionController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@SpringBootTest(classes = ToquestApplication.class)
public class TOQusetReceptionControllerTest {

    @Autowired
    MockMvc mockMvc;

	ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void gameStart() throws UnsupportedEncodingException, Exception{
        String responseString = getResponse("/game/beginning");
        log.info(responseString);
    }

    @Test
    public void gameContinue()throws UnsupportedEncodingException, Exception{
        String responseString = getResponse("/game/continuation");
        log.info(responseString);
    }
    @Test
    public void gamefinish()throws UnsupportedEncodingException, Exception{
        String responseString = getResponse("/game/ending");
        log.info(responseString);
    }
    @Test
    public void cardsOpen()throws UnsupportedEncodingException, Exception{
        String responseString = getResponse("/game/beginning");
        responseString = getResponse("/cards/distribution");
        responseString = getResponse("/cards/disclosure");
        log.info(responseString);
    }
    @Test
    public void cardsDistribute()throws UnsupportedEncodingException, Exception{
        String responseString = getResponse("/game/beginning");
        responseString = getResponse("/cards/distribution");
        log.info(responseString);
    }
    @Test
    public void cardsRedistribute()throws UnsupportedEncodingException, Exception{
        String responseString = getResponse("/game/beginning");
        responseString = getResponse("/cards/distribution");
        responseString = getResponse("/cards/redistribution");
        log.info(responseString);
    }
    @Test
    private String getResponse(String url) throws UnsupportedEncodingException, Exception{
        return mockMvc.perform(MockMvcRequestBuilders.get(url))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString();

    }
    





}
