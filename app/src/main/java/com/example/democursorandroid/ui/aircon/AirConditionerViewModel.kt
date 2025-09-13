package com.example.democursorandroid.ui.aircon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * エアコン画面のViewModel
 * 既存のビジネスロジックをViewModelに移行
 */
class AirConditionerViewModel : ViewModel() {
    
    // 画面状態の管理
    private val _currentState = MutableStateFlow(AirConState.INITIAL)
    val currentState: StateFlow<AirConState> = _currentState.asStateFlow()
    
    // 残り時間の管理
    private val _remainingTime = MutableStateFlow(9)
    val remainingTime: StateFlow<Int> = _remainingTime.asStateFlow()
    
    /**
     * エアコンの起動を開始する
     */
    fun startAirConditioner() {
        _currentState.value = AirConState.REQUESTING
        
        // リクエスト処理をシミュレート（4秒後に完了画面へ遷移）
        viewModelScope.launch {
            delay(4000)
            _currentState.value = AirConState.COMPLETED
            startCountdownTimer()
        }
    }
    
    /**
     * エアコンを停止する
     */
    fun stopAirConditioner() {
        _currentState.value = AirConState.INITIAL
        _remainingTime.value = 9
    }
    
    /**
     * カウントダウンタイマーを開始する
     */
    private fun startCountdownTimer() {
        viewModelScope.launch {
            while (_currentState.value == AirConState.COMPLETED && _remainingTime.value > 0) {
                delay(60000) // 1分間隔で更新
                val newTime = _remainingTime.value - 1
                _remainingTime.value = newTime
                
                if (newTime == 0) {
                    _currentState.value = AirConState.INITIAL
                    _remainingTime.value = 9
                }
            }
        }
    }
}
