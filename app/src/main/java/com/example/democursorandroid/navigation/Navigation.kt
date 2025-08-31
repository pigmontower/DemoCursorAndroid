package com.example.democursorandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.democursorandroid.ui.aircon.AirConScreen
import com.example.democursorandroid.ui.home.HomeScreen

/**
 * ナビゲーション画面の定義
 */
sealed class Screen(val route: String) {
    object Home : Screen("home")
    object AirCon : Screen("aircon")
}

/**
 * アプリのメインナビゲーション
 */
@Composable
fun AppNavigation(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // ホーム画面
        composable(Screen.Home.route) {
            HomeScreen(
                onAirConClick = {
                    navController.navigate(Screen.AirCon.route)
                }
            )
        }
        
        // エアコン画面
        composable(Screen.AirCon.route) {
            AirConScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onHelpClick = {
                    // TODO: ヘルプ画面への遷移を実装
                },
                onSettingsClick = {
                    // TODO: 設定画面への遷移を実装
                },
                onNotificationSettingsClick = {
                    // TODO: 通知設定画面への遷移を実装
                }
            )
        }
    }
}
