package com.hyomoto.toquest.business.model;

import com.hyomoto.toquest.common.model.TOQuestBaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * 21Questで使うカードモデルクラス
 * 1インスタンス＝カード1枚
 */
public class TOQuestCardsModel extends TOQuestBaseModel{

    @Getter
    private String suit;
    @Getter
    private int rank;
    @Getter
    private int point;
    @Getter
    @Setter
    private boolean isBack;

    /**
     * 
     * @param suit ダイヤ、クローバー、ハート、スペード
     * @param rank 1〜13
     * @param point 21Questにおける各カードごとのポイント。最大10
     */
    public TOQuestCardsModel(String suit,int rank,int point){
        this.suit = suit;
        this.rank = rank;
        this.point = point;
        this.isBack = false;

    }    

}
