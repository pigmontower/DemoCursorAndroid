package com.example.democursorandroid.ui.airconditioner

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * エアコン画面のViewModel
 * エアコン操作の状態管理とビジネスロジックを担当
 */
class AirConditionerViewModel : ViewModel() {
    
    // 現在の状態
    private val _currentState = MutableStateFlow(AirConditionerState.INITIAL_DISPLAY)
    val currentState: StateFlow<AirConditionerState> = _currentState.asStateFlow()
    
    // エアコン設定値
    private val _startupTimeMinutes = MutableStateFlow(10)
    val startupTimeMinutes: StateFlow<Int> = _startupTimeMinutes.asStateFlow()
    
    // 残り時間（完了状態でのみ使用）
    private val _remainingTimeMinutes = MutableStateFlow(9)
    val remainingTimeMinutes: StateFlow<Int> = _remainingTimeMinutes.asStateFlow()
    
    // エラー状態
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()
    
    /**
     * エアコン起動を開始
     * 初期表示状態からリクエスト中状態に遷移
     */
    fun startAirConditioner() {
        if (_currentState.value == AirConditionerState.INITIAL_DISPLAY) {
            _currentState.value = AirConditionerState.REQUESTING
            _errorMessage.value = null
            
            // リクエスト処理をシミュレート
            viewModelScope.launch {
                try {
                    // 3-5秒のリクエスト処理をシミュレート
                    delay(3000)
                    
                    // 成功時は完了状態に遷移
                    _currentState.value = AirConditionerState.COMPLETED
                    
                } catch (e: Exception) {
                    // エラー時は初期状態に戻る
                    _currentState.value = AirConditionerState.INITIAL_DISPLAY
                    _errorMessage.value = "エアコンの起動に失敗しました"
                }
            }
        }
    }
    
    /**
     * エアコンを停止
     * リクエスト中または完了状態から初期表示状態に遷移
     */
    fun stopAirConditioner() {
        when (_currentState.value) {
            AirConditionerState.REQUESTING,
            AirConditionerState.COMPLETED -> {
                _currentState.value = AirConditionerState.INITIAL_DISPLAY
                _errorMessage.value = null
                // 残り時間をリセット
                _remainingTimeMinutes.value = _startupTimeMinutes.value - 1
            }
            else -> {
                // 初期表示状態では何もしない
            }
        }
    }
    
    /**
     * エアコン設定を変更
     * @param minutes 起動時間（分）
     */
    fun updateStartupTime(minutes: Int) {
        if (minutes > 0 && minutes <= 60) {
            _startupTimeMinutes.value = minutes
            // 残り時間も更新
            _remainingTimeMinutes.value = minutes - 1
        }
    }
    
    /**
     * エラーメッセージをクリア
     */
    fun clearError() {
        _errorMessage.value = null
    }
    
    /**
     * 設定変更画面への遷移
     */
    fun navigateToSettings() {
        // 設定変更画面への遷移ロジック
        // 現在は実装しない（将来の拡張用）
    }
    
    /**
     * 通知設定画面への遷移
     */
    fun navigateToNotificationSettings() {
        // 通知設定画面への遷移ロジック
        // 現在は実装しない（将来の拡張用）
    }
}
