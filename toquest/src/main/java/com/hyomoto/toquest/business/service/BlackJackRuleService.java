package com.hyomoto.toquest.business.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyomoto.toquest.business.model.TOQuestCardMasterModel;
import com.hyomoto.toquest.business.model.TOQuestCardsModel;
import com.hyomoto.toquest.business.model.TOQuestPlayerModel;
import com.hyomoto.toquest.common.model.TOQuestBaseModel;
import com.hyomoto.toquest.common.service.TOQuestBaseService;
import com.hyomoto.toquest.common.util.TOQuestConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 21Questによるメインロジッククラス
 * 基本的には、コントローラからこのクラスが呼ばれる。
 */
@Slf4j
@Service
public class BlackJackRuleService extends TOQuestBaseService {

    private final CardManagerService cardManagerService;
    private final TOQuestPlayerModel toQuestPlayerModel;
    private final TOQuestCardMasterModel toQuestCardMasterModel;

    @Autowired
    public BlackJackRuleService(CardManagerService cardManagerService,
                                 TOQuestPlayerModel toQuestPlayerModel,
                                 TOQuestCardMasterModel toQuestCardMasterModel){
        this.cardManagerService = cardManagerService;
        this.toQuestPlayerModel = toQuestPlayerModel;
        this.toQuestCardMasterModel = toQuestCardMasterModel;

    }

    /**
     * 
     * @return List<TOQuestBaseModel> プレイヤーとゲームマスターモデルのリスト
     */
    public List<TOQuestBaseModel> cardsDistribute(){
        // ゲーム開始に先立ち、カードデッキからカードを２枚ドロー
        List<TOQuestCardsModel> toQuestCardsModels_Player = cardManagerService.distributeCards(2);
        List<TOQuestCardsModel> toQuestCardsModels_Master = cardManagerService.distributeCards(2);
        // 引いたカードをプレイヤーの手札に加える
        toQuestPlayerModel.setHandOfCards(toQuestCardsModels_Player);

        // ゲームマスターの2枚目のカードについて裏側設定をONにする
        toQuestCardsModels_Master.get(1).setBack(true);
        // ゲームマスターのカードについて、裏返し対応を行う。（裏返し＝ダミーカードをセット）
        cardManagerService.flipCards(toQuestCardsModels_Master,toQuestCardMasterModel.toString(),true);        
        // ゲームマスターの手札に処理したカードを加える
        toQuestCardMasterModel.setHandOfCards(toQuestCardsModels_Master);

        // 戻り値生成
        List<TOQuestBaseModel> toQuestBaseModels = new ArrayList<TOQuestBaseModel>(Arrays.asList(toQuestPlayerModel,toQuestCardMasterModel));

        return toQuestBaseModels;

    }

    /**
     * 
     * @return List<TOQuestBaseModel> プレイヤーとゲームマスターモデルのリスト
     */
    public List<TOQuestBaseModel> cardsRedistribute(){
        // プレイヤーからのカード再配布要求に伴いカードをデッキから一枚引く
        List<TOQuestCardsModel> toQuestCardsModels_Player = cardManagerService.distributeCards(1);
        // 引いたカードをプレイヤーの手札に加える
        toQuestPlayerModel.addCard(toQuestCardsModels_Player);
        // 現時点でカードの合計ポイントを出す
        int playerPoint =cardsPointTally(toQuestPlayerModel.getHandOfCards());

        // カードの合計ポイントがバースト値（=合計値＞21）の場合、バースト処理
        if ( isBurst(playerPoint) ){
            
            // 所持金の計算
            moneyCalc(toQuestPlayerModel);
            // 戻り値生成
            List<TOQuestBaseModel> toQuestBaseModels = new ArrayList<TOQuestBaseModel>(Arrays.asList(toQuestPlayerModel,toQuestCardMasterModel));

            return toQuestBaseModels;
        }
        // ゲームマスターについては、まずカードを引く・引かないの判断をこの時点でのカード合計ポイントで行う。
        // そのため、まずダミーに変更したカードを元に戻し、合計ポイントを算出する。
        cardManagerService.flipCards(toQuestCardMasterModel.getHandOfCards(),toQuestCardMasterModel.toString(),false);  
        int masterPoint =cardsPointTally(toQuestCardMasterModel.getHandOfCards());

        // 合計ポイントが、カードを引く基準値以上である場合、カードを引く
        if ( masterPoint < 16 ) {
            // 一枚カードを引く
            List<TOQuestCardsModel> toQuestCardsModels_Master = cardManagerService.distributeCards(1);
            // 引いたカードを手札に加える
            toQuestCardMasterModel.addCard(toQuestCardsModels_Master);
            // 現時点でのカード合計値を出す
            masterPoint = cardsPointTally(toQuestCardsModels_Master);

            // ゲームマスターがバーストしていた場合、プレイヤーの勝利処理
            if ( isBurst(masterPoint) ){

                toQuestPlayerModel.setWin(true);   
                // 所持金計算
                moneyCalc(toQuestPlayerModel);
                // 戻り値生成
                List<TOQuestBaseModel> toQuestBaseModels = new ArrayList<TOQuestBaseModel>(Arrays.asList(toQuestPlayerModel,toQuestCardMasterModel));
    
                return toQuestBaseModels;
            }
            // カードマスターの追加カードについて、裏返し設定を行う
            toQuestCardMasterModel.getHandOfCards().get(2).setBack(true);
            // カードマスターのカードについて、裏返し処理を行う
            cardManagerService.flipCards(toQuestCardMasterModel.getHandOfCards(),toQuestCardMasterModel.toString(),true);  
        }

        List<TOQuestBaseModel> toQuestBaseModels = new ArrayList<TOQuestBaseModel>(Arrays.asList(toQuestPlayerModel,toQuestCardMasterModel));
        return toQuestBaseModels;
        
    }

    /**
     * 
     * @return List<TOQuestBaseModel> プレイヤーとゲームマスターモデルのリスト
     */
    public List<TOQuestBaseModel> cardOpen(){
        // プレイヤー手札のポイント取得
        int playerPoint = cardsPointTally(toQuestPlayerModel.getHandOfCards());
        // ゲームマスターの手札について裏返していたものを元に戻す
        cardManagerService.flipCards(toQuestCardMasterModel.getHandOfCards(),toQuestCardMasterModel.toString(),false);          
        // ゲームマスター手札のポイント取得
        int masterPoint = cardsPointTally(toQuestCardMasterModel.getHandOfCards());
        
        log.info(String.format("%s_%s", playerPoint,masterPoint));
        // プレイヤー勝利判定
        toQuestPlayerModel.setWin(isWinner(playerPoint,masterPoint));
        log.info(String.format("%s", toQuestPlayerModel.isWin()));

        // プレイヤーの所持金処理
        moneyCalc(toQuestPlayerModel);
        
        List<TOQuestBaseModel> toQuestBaseModels = new ArrayList<TOQuestBaseModel>(Arrays.asList(toQuestPlayerModel,toQuestCardMasterModel));
        return toQuestBaseModels;
    }

    /**
     * カードの合計ポイント計算
     * @param toQuestCardsModels
     * @return
     */
    public int cardsPointTally(List<TOQuestCardsModel> toQuestCardsModels){
        int sum = 0;
        for (TOQuestCardsModel toQuestCardsModel : toQuestCardsModels) {
            sum += toQuestCardsModel.getPoint();
        }
        return sum;
        
    }

    /**
     * バーストしているかどうかの判断
     * @param sumPoint
     * @return
     */
    public boolean isBurst(int sumPoint){
        if ( sumPoint > TOQuestConstants.BLACK_JACK ) {
            return true;
        }
        return false;
        
    }

    /**
     * 誰が買ったか判断
     * @param playerPoint
     * @param masterPoint
     * @return
     */
    public boolean isWinner(int playerPoint,int masterPoint){
        if (playerPoint > masterPoint) {
            return true;
        }
        return false;
        
    }

    /**
     * お金の計算
     * @param toQuestPlayerModel
     */
    public void moneyCalc(TOQuestPlayerModel toQuestPlayerModel){
        int calcedMoney = 0;
        // プレイヤーが勝っていた場合、プレイヤーの所持金を増やす
        if ( toQuestPlayerModel.isWin() ) {
            calcedMoney = toQuestPlayerModel.getMoneyInPossession() + TOQuestConstants.STAKE;
        // プレイヤーが負けていた場合、プレイヤーの所持金を減らす
        } else {
            calcedMoney = toQuestPlayerModel.getMoneyInPossession() - TOQuestConstants.STAKE;
        }
        toQuestPlayerModel.setMoneyInPossession(calcedMoney);
    }
}
