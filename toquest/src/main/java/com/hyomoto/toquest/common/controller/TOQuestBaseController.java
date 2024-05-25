package com.hyomoto.toquest.common.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyomoto.toquest.business.model.ReturnErrorModel;

import lombok.extern.slf4j.Slf4j;

/**
 * 共通的な処理を行うコントローラクラス
 */
@Slf4j
@RestController
public abstract class TOQuestBaseController {

    /**
     * 
     * @return ReturnErrorModel 
     */
    @GetMapping("/health")
    public ReturnErrorModel healthCheck (){

        // 異常系の場合処理をインターセプトするようにする必要あり。（4XX ,5XX）
        return new ReturnErrorModel(0,"");

    }
}
