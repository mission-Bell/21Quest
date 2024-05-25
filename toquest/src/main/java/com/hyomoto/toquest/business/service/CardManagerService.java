package com.hyomoto.toquest.business.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hyomoto.toquest.business.model.TOQuestCardsModel;
import com.hyomoto.toquest.common.service.TOQuestBaseService;
import com.hyomoto.toquest.common.util.TOQuestConstants;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * カード管理クラス
 */
@Service
@Slf4j
public class CardManagerService extends TOQuestBaseService {

    @Getter
    private Iterator<TOQuestCardsModel> cardsDeck;

    @Getter
    @Setter
    private Map<String,TOQuestCardsModel> escapeCards;

    @Getter
    private TOQuestCardsModel dummyCardsModel;

    /**
     * インスタンス初期値の設定。主にダミーカード
     */
    public CardManagerService(){
        this.dummyCardsModel = new TOQuestCardsModel("***", 0, 0);
        dummyCardsModel.setBack(true);

    }

    /**
     * カードデッキ生成メソッド
     */
    public void createCardDeck(){
        // カード数のカードリストを用意
        List<TOQuestCardsModel> tOQuestCardsModels = new ArrayList<TOQuestCardsModel>(TOQuestConstants.CARD_MAX_NUMBER - 1);
        // トランプの枚数（ジョーカぬき）のカードについて初期設定を行う
        for(int rank = 1; rank <= TOQuestConstants.CARD_MAX_RANK; rank++){
            
            int point;
            // rankが10を超える場合、すべてpointを10にする
            if (rank > 10) {
                point = 10;
            } else {
            point = rank; 

            }
            // 各suit毎にカードを作成
            tOQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_SPADE, rank,point));
            tOQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_CLUB, rank,point));
            tOQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_HEART, rank,point));
            tOQuestCardsModels.add(new TOQuestCardsModel(TOQuestConstants.SUIT_DIAMOND, rank,point));
        }
        // カードが全て生成できたら、シャッフルする
        Collections.shuffle(tOQuestCardsModels);
        // イテレータとしてカードデッキを格納
        cardsDeck = tOQuestCardsModels.iterator();
    }

    /**
     * 
     * @param numberCardRequested 要求カード数
     * @return List<TOQuestCardsModel> カードデッキから引いたカード
     */
    public List<TOQuestCardsModel> distributeCards (int numberCardRequested) {
        List<TOQuestCardsModel> tOQuestCardsModels = new ArrayList<TOQuestCardsModel>();
        for (int i = 0 ; i < numberCardRequested; i++) {
            tOQuestCardsModels.add(cardsDeck.next());           
        } 
        return tOQuestCardsModels;
    }

    // 
    /**
     * 裏返し要求のカードについてダミーカードと差し替える。
     * @param toQuestCardsModels
     * @param targetIndex
     * @param targetName
     */
    public void changeFromTargetCardToDummyCard(List<TOQuestCardsModel> toQuestCardsModels,int targetIndex, String targetName){
        escapeCards.put(targetName, toQuestCardsModels.get(targetIndex));
        toQuestCardsModels.set(targetIndex, dummyCardsModel);

    }

    /**
     * 裏返し要求のカードについてダミーカードを元のカードに戻す
     * @param toQuestCardsModels
     * @param targetIndex
     * @param targetName
     */
    public void changeFromDummyCardToTargetCard(List<TOQuestCardsModel> toQuestCardsModels,int targetIndex,String targetName){
        toQuestCardsModels.set(targetIndex, escapeCards.get(targetName));

    }

    /**
     * カードの裏返し要求を受け付ける
     * @param toQuestCardsModels
     * @param instalnceID
     * @param doDummy
     */
    public void flipCards(List<TOQuestCardsModel> toQuestCardsModels,String instalnceID,boolean doDummy){
        int i = 0;
        // ダミーカードにするかどうか判断
        if (doDummy){
            // ダミーカードと差し替えるカードについては、一時的に退避
            escapeCards = new HashMap<String,TOQuestCardsModel>();
            for (TOQuestCardsModel toQuestCardsModel : toQuestCardsModels){
                // 裏返しフラグがONのカードの場合、裏返し実施
                if (toQuestCardsModel.isBack()) {
                    // 退避カードについては、インスタンスID＋位置情報の名札を付与
                    String targetName = String.format("%s_%s",instalnceID,i);
                    // 裏返し処理実施
                    changeFromTargetCardToDummyCard(toQuestCardsModels, i, targetName);                    
                }
                i++;
            }
        } else {
            for (TOQuestCardsModel toQuestCardsModel : toQuestCardsModels){

                // 裏返しフラグがONの場合、元に戻す処理実施
                if (toQuestCardsModel.isBack()) {
                    String targetName = String.format("%s_%s",instalnceID,i);
                    // 元に戻す処理実施
                    changeFromDummyCardToTargetCard(toQuestCardsModels, i, targetName);                    
                }
                i++;
            }
            escapeCards = null;
        }
    }

}
