package com.example.democursorandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.example.democursorandroid.navigation.AppNavigation
import com.example.democursorandroid.ui.theme.DarkBackground
import com.example.democursorandroid.ui.theme.DemoCursorAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        
        // Android固有実装: ステータスバー背景を透明化（仕様書準拠）
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            // ダークテーマ強制設定（仕様書: ダークテーマ専用設計）
            DemoCursorAndroidTheme(darkTheme = true, dynamicColor = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = DarkBackground
                ) {
                    AppNavigation()
                }
            }
        }
    }
}