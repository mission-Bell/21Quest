package com.hyomoto.toquest.business.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.hyomoto.toquest.common.model.TOQuestBaseModel;
import com.hyomoto.toquest.common.model.TOQuestBaseUserModel;
import com.hyomoto.toquest.common.util.TOQuestConstants;

import lombok.Getter;
import lombok.Setter;

/**
 * 21Questにおけるプレイヤーモデル
 * ユーザに関連するものを引き受ける。
 */
@Component
public class TOQuestPlayerModel extends TOQuestBaseUserModel{
    
    @Getter
    @Setter
    private int moneyInPossession;
    @Getter
    @Setter
    private boolean isWin;

    /**
     * インスタンスの初期値を設定する。
     */
    public TOQuestPlayerModel(){
        this.moneyInPossession = TOQuestConstants.INITIAL_MONEY;
        this.isWin = false;
    }
}
