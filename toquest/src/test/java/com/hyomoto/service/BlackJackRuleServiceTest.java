package com.hyomoto.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyomoto.toquest.ToquestApplication;
import com.hyomoto.toquest.business.model.TOQuestCardsModel;
import com.hyomoto.toquest.business.model.TOQuestPlayerModel;
import com.hyomoto.toquest.business.service.BlackJackRuleService;
import com.hyomoto.toquest.business.service.CardManagerService;
import com.hyomoto.toquest.common.model.TOQuestBaseModel;
import com.hyomoto.toquest.common.util.TOQuestConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = ToquestApplication.class)
public class BlackJackRuleServiceTest {

    @Autowired
    BlackJackRuleService blackJackRuleService;

    @Autowired
    CardManagerService cardManagerService;

    @Autowired
    TOQuestPlayerModel toQuestPlayerModel;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void cardsDistribute () {
        cardManagerService.createCardDeck();
        List<TOQuestBaseModel> toQuestBaseModels = blackJackRuleService.cardsDistribute();

        toQuestBaseModels.forEach(( baseModel) -> {
            String str;
            try {
                str = objectMapper.writeValueAsString(toQuestBaseModels);
                System.out.println(str);

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

    }
    @Test
    public void cardsRedistribute (){
        cardManagerService.createCardDeck();
        blackJackRuleService.cardsDistribute();
        

        List<TOQuestBaseModel> toQuestBaseModels = blackJackRuleService.cardsRedistribute();;

        toQuestBaseModels.forEach(( baseModel) -> {
            String str;
            try {
                str = objectMapper.writeValueAsString(toQuestBaseModels);
                System.out.println(str);

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

    }
    @Test
    public void cardOpen_cardsRedistribute (){
        cardManagerService.createCardDeck();
        blackJackRuleService.cardsDistribute();
        blackJackRuleService.cardsRedistribute();

        List<TOQuestBaseModel> toQuestBaseModels = blackJackRuleService.cardOpen();

        toQuestBaseModels.forEach(( baseModel) -> {
            String str;
            try {
                str = objectMapper.writeValueAsString(toQuestBaseModels);
                System.out.println(str);

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });

    }

    @Test
    public void cardOpen_not_cardsRedistribute (){
        cardManagerService.createCardDeck();
        blackJackRuleService.cardsDistribute();
        
        List<TOQuestBaseModel> toQuestBaseModels = blackJackRuleService.cardOpen();

        toQuestBaseModels.forEach(( baseModel) -> {
            String str;
            try {
                str = objectMapper.writeValueAsString(toQuestBaseModels);
                System.out.println(str);

            } catch (JsonProcessingException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        });
    }

    @Test
    public void cardsPointTally (){
        int i = blackJackRuleService.cardsPointTally(createCardsList());
        log.info(Integer.toString(i));

    }
    @Test
    public void isBurst (){
        blackJackRuleService.isBurst(100);

    }
    @Test
    public void isWinner (){
        log.info(Boolean.toString(blackJackRuleService.isWinner(12, 11)));
        

    }
    @Test
    public void moneyCalc (){
        blackJackRuleService.moneyCalc(toQuestPlayerModel);

    }


    private List<TOQuestCardsModel> createCardsList (){
        List<TOQuestCardsModel> toQuestCardsModels = new ArrayList<TOQuestCardsModel>();
        toQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_CLUB, 1, 1));
        toQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_DIAMOND, 5, 5));
        toQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_HEART, 10, 10));
        toQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_SPADE, 11, 10));
        toQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_CLUB, 13, 10));        
        return toQuestCardsModels;
    }

}
