package com.hyomoto.toquest.business.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyomoto.toquest.business.model.TOQuestPlayerModel;
import com.hyomoto.toquest.business.service.BlackJackRuleService;
import com.hyomoto.toquest.business.service.CardManagerService;
import com.hyomoto.toquest.common.controller.TOQuestBaseController;
import com.hyomoto.toquest.common.model.TOQuestBaseModel;
import com.hyomoto.toquest.common.util.TOQuestConstants;

/**
 * 
 * 21Questのリクエストを受け付けるクラス
 * 基本は、このクラスを経由してサービスへ繋ぐ
 * 
 */
@RestController
public class TOQusetReceptionController extends TOQuestBaseController {

    private final TOQuestPlayerModel toQuestPlayerModel;
    private final BlackJackRuleService blackJackRuleService;
    private final CardManagerService cardManagerService;

    @Autowired
    public TOQusetReceptionController(TOQuestPlayerModel toQuestPlayerModel,
                                      BlackJackRuleService blackJackRuleService,
                                      CardManagerService cardManagerService){
        this.toQuestPlayerModel = toQuestPlayerModel;
        this.blackJackRuleService = blackJackRuleService;
        this.cardManagerService = cardManagerService;

    }

    /**
     * 
     * @return toQuestPlayerModel プレイヤーモデルクラス
     */
    @GetMapping("/game/beginning")
    public TOQuestPlayerModel gameStart(){
        cardManagerService.createCardDeck();
        return toQuestPlayerModel;
        
    }

     /**
     * 
     * @return toQuestPlayerModel プレイヤーモデルクラス
     */
    @GetMapping("/game/continuation")
    public TOQuestPlayerModel gameContinue(){
        cardManagerService.createCardDeck();
        return toQuestPlayerModel;
        
    }

     /**
     * 
     * @return FINISH_MESSAGE 終了メッセージ
     */
    @GetMapping("/game/ending")
    public String gamefinish(){
        return TOQuestConstants.FINISH_MESSAGE;
        
    }

    /**
     * 
     * @return List<TOQuestBaseModel> プレイヤーとゲームマスターモデルのリスト
     */
    @GetMapping("/cards/disclosure")
    public List<TOQuestBaseModel> cardsOpen(){
        
        return blackJackRuleService.cardOpen();
        
    }

    /**
     * 
     * @return List<TOQuestBaseModel> プレイヤーとゲームマスターモデルのリスト
     */
    @GetMapping("/cards/distribution")
    public List<TOQuestBaseModel> cardsDistribute(){
        return blackJackRuleService.cardsDistribute();
        
    }

     /**
     * 
     * @return List<TOQuestBaseModel> プレイヤーとゲームマスターモデルのリスト
     */
    @GetMapping("/cards/redistribution")
    public List<TOQuestBaseModel> cardsRedistribute(){
        return blackJackRuleService.cardsRedistribute();
        
    }
}
