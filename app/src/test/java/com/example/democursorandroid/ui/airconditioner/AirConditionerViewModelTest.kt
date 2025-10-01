package com.example.democursorandroid.ui.airconditioner

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * AirConditionerViewModelの単体テスト
 * エアコン画面の状態遷移とビジネスロジックをテスト
 */
class AirConditionerViewModelTest {

    private lateinit var viewModel: AirConditionerViewModel

    @Before
    fun setUp() {
        viewModel = AirConditionerViewModel()
    }

    // ===== 1. 初期状態テスト =====

    /**
     * テストケース: 1.1 初期状態の確認
     * ViewModel初期化時の状態確認
     */
    @Test
    fun `初期化時にINITIAL_DISPLAY状態であること`() {
        // Then
        assertEquals(AirConditionerState.INITIAL_DISPLAY, viewModel.currentState.value)
    }

    /**
     * テストケース: 1.1 初期状態の確認
     * 初期設定値（起動時間10分間）が正しく設定されていること
     */
    @Test
    fun `初期設定値が正しく設定されていること`() {
        // Then
        assertEquals(10, viewModel.startupTimeMinutes.value)
        assertEquals(9, viewModel.remainingTimeMinutes.value)
        assertNull(viewModel.errorMessage.value)
    }

    // ===== 2. エラーハンドリングテスト =====

    /**
     * テストケース: 2.1 エラー処理
     * エラーメッセージのクリア
     */
    @Test
    fun `エラーメッセージがクリアされること`() {
        // When
        viewModel.clearError()

        // Then
        assertNull(viewModel.errorMessage.value)
    }

    // ===== 3. 設定値変更テスト =====

    /**
     * テストケース: 3.1 設定値の変更
     * 起動時間の変更が正しく反映されること
     */
    @Test
    fun `起動時間の変更が正しく反映されること`() {
        // When
        viewModel.updateStartupTime(15)

        // Then
        assertEquals(15, viewModel.startupTimeMinutes.value)
        assertEquals(14, viewModel.remainingTimeMinutes.value)
    }

    /**
     * テストケース: 3.2 無効な設定値
     * 無効な設定値の場合は変更されないこと
     */
    @Test
    fun `無効な設定値の場合は変更されないこと`() {
        // Given
        val initialStartupTime = viewModel.startupTimeMinutes.value

        // When
        viewModel.updateStartupTime(-1)
        viewModel.updateStartupTime(100)

        // Then
        assertEquals(initialStartupTime, viewModel.startupTimeMinutes.value)
    }
}