package com.hyomoto.toquest.common.model;

import java.util.List;

import com.hyomoto.toquest.business.model.TOQuestCardsModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 21Questのユーザ基底モデルクラス
 */
public class TOQuestBaseUserModel extends TOQuestBaseModel{
    
    @Getter
    @Setter
    private List<TOQuestCardsModel> handOfCards;

    /**
     * カードを一枚手札に加える
     * @param toQuestCardsModel
     */
    public void addCard(TOQuestCardsModel toQuestCardsModel){
        this.handOfCards.add(toQuestCardsModel);
    }

    /**
     * カードを複数枚手札に加える
     * @param toQuestCardsModel
     */
    public void addCard(List<TOQuestCardsModel> toQuestCardsModels){
        toQuestCardsModels.forEach( toQuestCardsModel -> {
            this.addCard(toQuestCardsModel);

        });
    } 

}
