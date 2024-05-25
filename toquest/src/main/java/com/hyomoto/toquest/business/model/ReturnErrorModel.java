package com.hyomoto.toquest.business.model;

import com.hyomoto.toquest.common.model.TOQuestBaseModel;

import lombok.Getter;
import lombok.Setter;

/**
 * クライアントへエラーを返すためのクラス
 */
public class ReturnErrorModel extends TOQuestBaseModel {

    @Getter
    @Setter
    private int errorCode;
    @Getter
    @Setter
    private String errorMessage;

    public ReturnErrorModel(){

    }

    /**
     * 
     * @param errorCode エラーコード
     * @param errorMessage エラーメッセージ
     */
    public ReturnErrorModel(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;        
    }
}
