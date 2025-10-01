package com.example.democursorandroid.ui.airconditioner

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.democursorandroid.R
import com.example.democursorandroid.ui.theme.DemoCursorAndroidTheme

/**
 * エアコン画面
 * 3つの状態（初期表示、リクエスト中、完了）を管理
 */
@Composable
fun AirConditionerScreen(
    onNavigateBack: () -> Unit,
    viewModel: AirConditionerViewModel = viewModel()
) {
    val currentState by viewModel.currentState.collectAsState()
    val startupTime by viewModel.startupTimeMinutes.collectAsState()
    val remainingTime by viewModel.remainingTimeMinutes.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1A001A)) // ダークテーマ背景色
    ) {
        // ナビゲーションバー
        AirConditionerTopBar(
            onNavigateBack = onNavigateBack,
            onHelpClick = { /* ヘルプ機能 */ }
        )
        
        // メインコンテンツ
        when (currentState) {
            AirConditionerState.INITIAL_DISPLAY -> {
                InitialDisplayContent(
                    startupTime = startupTime,
                    onStartAirConditioner = { viewModel.startAirConditioner() },
                    onChangeSettings = { viewModel.navigateToSettings() }
                )
            }
            AirConditionerState.REQUESTING -> {
                RequestingContent(
                    startupTime = startupTime,
                    onStop = { viewModel.stopAirConditioner() },
                    onNotificationSettings = { viewModel.navigateToNotificationSettings() }
                )
            }
            AirConditionerState.COMPLETED -> {
                CompletedContent(
                    remainingTime = remainingTime,
                    startupTime = startupTime,
                    onStop = { viewModel.stopAirConditioner() },
                    onNotificationSettings = { viewModel.navigateToNotificationSettings() }
                )
            }
        }
        
        // エラーメッセージ表示
        errorMessage?.let { message ->
            LaunchedEffect(message) {
                // エラーメッセージを3秒後にクリア
                kotlinx.coroutines.delay(3000)
                viewModel.clearError()
            }
            
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            ) {
                Text(
                    text = message,
                    color = Color.White,
                    modifier = Modifier.padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

/**
 * ナビゲーションバー
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirConditionerTopBar(
    onNavigateBack: () -> Unit,
    onHelpClick: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "エアコン",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "戻る",
                    tint = Color.White
                )
            }
        },
        actions = {
            IconButton(onClick = onHelpClick) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "ヘルプ",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFF1A001A)
        )
    )
}

/**
 * 初期表示状態のコンテンツ
 */
@Composable
fun InitialDisplayContent(
    startupTime: Int,
    onStartAirConditioner: () -> Unit,
    onChangeSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // 確認メッセージ
        Text(
            text = "下記の設定でエアコンを起動します。よろしいですか?",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 設定セクションヘッダー
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "エアコンの設定",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            TextButton(onClick = onChangeSettings) {
                Text(
                    text = "変更する",
                    color = Color(0xFFE53935), // 赤色
                    fontSize = 16.sp
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 設定表示セクション
        SettingDisplaySection(startupTime = startupTime)
        
        Spacer(modifier = Modifier.weight(1f))
        
        // プライマリアクションボタン
        Button(
            onClick = onStartAirConditioner,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "この設定で起動する",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * 設定表示セクション
 */
@Composable
fun SettingDisplaySection(startupTime: Int) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        // 上側のセパレーター
        Divider(
            color = Color(0xFF333333),
            thickness = 1.dp
        )
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // 起動時間ラベル
        Text(
            text = "起動時間",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // 起動時間値表示
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = startupTime.toString(),
                color = Color.White,
                fontSize = 60.sp,
                fontWeight = FontWeight.Light
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "分間",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
        }
        
        Spacer(modifier = Modifier.height(20.dp))
        
        // 下側のセパレーター
        Divider(
            color = Color(0xFF333333),
            thickness = 1.dp
        )
    }
}

/**
 * リクエスト中状態のコンテンツ
 */
@Composable
fun RequestingContent(
    startupTime: Int,
    onStop: () -> Unit,
    onNotificationSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        // リクエストステータス
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "リクエスト中",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // ローディングインジケーター
            LoadingIndicator()
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // セパレーター
        Divider(
            color = Color(0xFF333333),
            thickness = 1.dp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 操作詳細
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "この設定で動作中",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "起動時間",
                color = Color(0xFFCCCCCC),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "--",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // セパレーター
        Divider(
            color = Color(0xFF333333),
            thickness = 1.dp
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // 停止ボタン
        Button(
            onClick = onStop,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF33334A)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "停止する",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 通知設定セクション
        NotificationSettingsSection(onNotificationSettings = onNotificationSettings)
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * 完了状態のコンテンツ
 */
@Composable
fun CompletedContent(
    remainingTime: Int,
    startupTime: Int,
    onStop: () -> Unit,
    onNotificationSettings: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        
        // エアコン停止タイマー
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "エアコン停止まで",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = remainingTime.toString(),
                    color = Color.White,
                    fontSize = 60.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "分",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // 操作詳細
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "この設定で動作中",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "起動時間",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = startupTime.toString(),
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "分間",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // 停止ボタン
        Button(
            onClick = onStop,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFE53935)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "停止する",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // 通知設定セクション
        NotificationSettingsSection(onNotificationSettings = onNotificationSettings)
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * ローディングインジケーター
 */
@Composable
fun LoadingIndicator() {
    val infiniteTransition = rememberInfiniteTransition(label = "loading")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Icon(
        imageVector = Icons.Default.Refresh,
        contentDescription = "読み込み中",
        tint = Color(0xFFFF4500), // 赤オレンジ色
        modifier = Modifier
            .size(48.dp)
            .rotate(rotation)
    )
}

/**
 * 通知設定セクション
 */
@Composable
fun NotificationSettingsSection(
    onNotificationSettings: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF28283D)
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Text(
                text = "通知設定",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = "クルマの操作関連通知をONにすると、リモート操作完了時にお知らせします。",
                color = Color(0xFFCCCCCC),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "設定",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                TextButton(onClick = onNotificationSettings) {
                    Text(
                        text = "アプリの通知を設定する",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirConditionerScreenPreview() {
    DemoCursorAndroidTheme {
        AirConditionerScreen(
            onNavigateBack = { }
        )
    }
}
