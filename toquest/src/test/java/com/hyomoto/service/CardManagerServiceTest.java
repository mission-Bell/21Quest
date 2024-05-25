package com.hyomoto.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hyomoto.toquest.ToquestApplication;
import com.hyomoto.toquest.business.model.TOQuestCardsModel;
import com.hyomoto.toquest.business.service.CardManagerService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest(classes = ToquestApplication.class)
public class CardManagerServiceTest {

    @Autowired
    CardManagerService cardManagerService;

    @Test
    public void createCardDeck(){
        cardManagerService.createCardDeck();
        Iterator<TOQuestCardsModel> tOQuestCardsModels = cardManagerService.getCardsDeck();

        tOQuestCardsModels.forEachRemaining((card) -> {            
            String str= String.format("suit: %s rank: %s point: %s", card.getSuit(),card.getRank(),card.getPoint());
            log.info(str);
        });
    }

    @Test
    public void distributeCards(){
        cardManagerService.createCardDeck();
        List<TOQuestCardsModel> toQuestCardsModels_2= cardManagerService.distributeCards(2);
        String str = String.format("size: %s",toQuestCardsModels_2.size());
        log.info(str);

        List<TOQuestCardsModel> toQuestCardsModels_1= cardManagerService.distributeCards(1);
        str = String.format("size: %s",toQuestCardsModels_1.size());
        log.info(str);

        str = String.format("size: %s",returnCardDeckList().size());
        log.info(str);

    }

    private List<TOQuestCardsModel> returnCardDeckList(){
        List<TOQuestCardsModel> toQuestCardsModels = new ArrayList<TOQuestCardsModel>();
        
        cardManagerService.getCardsDeck().forEachRemaining( card ->{
            toQuestCardsModels.add(card);
        });;
        return toQuestCardsModels; 

    }

}
