package com.example.democursorandroid.ui.airconditioner

/**
 * エアコン画面の状態を管理するEnum
 */
enum class AirConditionerState {
    /**
     * 初期表示状態
     * エアコン設定の確認画面
     */
    INITIAL_DISPLAY,
    
    /**
     * リクエスト中状態
     * エアコン起動リクエスト処理中
     */
    REQUESTING,
    
    /**
     * 完了状態
     * エアコンが正常に起動し、動作中
     */
    COMPLETED
}
