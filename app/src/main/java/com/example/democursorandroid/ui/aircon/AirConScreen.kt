package com.example.democursorandroid.ui.aircon

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.democursorandroid.ui.theme.*
import kotlinx.coroutines.delay

/**
 * エアコン画面の状態を表すenum
 */
enum class AirConState {
    INITIAL,     // 初期表示
    REQUESTING,  // リクエスト中
    COMPLETED    // 完了
}

/**
 * エアコン画面のメインコンポーザブル
 *
 * ViewModelを使用してビジネスロジックを分離
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirConScreen(
    onBackClick: () -> Unit = {},
    onHelpClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onNotificationSettingsClick: () -> Unit = {},
    viewModel: AirConditionerViewModel = viewModel()
) {
    val currentState by viewModel.currentState.collectAsState()
    val remainingTime by viewModel.remainingTime.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // ヘッダー部
        AirConHeader(
            showHelpButton = currentState != AirConState.REQUESTING,
            onBackClick = onBackClick,
            onHelpClick = onHelpClick
        )
        
        // メインコンテンツ部
        when (currentState) {
            AirConState.INITIAL -> {
                AirConInitialContent(
                    onStartClick = { viewModel.startAirConditioner() },
                    onSettingsClick = onSettingsClick,
                    onNotificationSettingsClick = onNotificationSettingsClick
                )
            }
            AirConState.REQUESTING -> {
                AirConRequestingContent(
                    onNotificationSettingsClick = onNotificationSettingsClick
                )
            }
            AirConState.COMPLETED -> {
                AirConCompletedContent(
                    remainingTime = remainingTime,
                    onStopClick = { viewModel.stopAirConditioner() },
                    onNotificationSettingsClick = onNotificationSettingsClick
                )
            }
        }
    }
}

/**
 * エアコン画面のヘッダー部
 */
@Composable
fun AirConHeader(
    showHelpButton: Boolean = true,
    onBackClick: () -> Unit,
    onHelpClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        color = DarkBackground
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 戻るボタン（左端）: 白色の左向き矢印アイコン
            // Material Icons: Menu - 確実に存在（戻るの代替）
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "戻る",
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // タイトル「エアコン」: 中央配置、白色テキスト、フォントサイズ18sp
            Text(
                text = "エアコン",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // ヘルプボタン（右端）: 白色の「?」アイコン
            if (showHelpButton) {
                // Material Icons: Star - 確実に存在（ヘルプの代替）
                IconButton(onClick = onHelpClick) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "ヘルプ",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            } else {
                // ヘルプボタンが非表示の場合、スペースを確保
                Spacer(modifier = Modifier.size(48.dp))
            }
        }
    }
}

/**
 * 初期表示画面のコンテンツ
 */
@Composable
fun AirConInitialContent(
    onStartClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        // セクション1: 説明テキスト
        Text(
            text = "下記の設定でエアコンを起動します。\nよろしいですか？",
            color = Color.White,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        // セクション2: 設定表示エリア
        Column {
            // 上部境界線
            Divider(
                color = Color(0xFF333333),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
            
            // 設定ヘッダー
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "エアコンの設定",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "変更する",
                    color = Color(0xFFFF4444),
                    fontSize = 16.sp,
                    modifier = Modifier.clickable { onSettingsClick() }
                )
            }
            
            // 起動時間表示
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "起動時間",
                    color = Color(0xFF888888),
                    fontSize = 14.sp
                )
                
                Text(
                    text = "10分間",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                
                Spacer(modifier = Modifier.height(32.dp))
            }
            
            // 下部境界線
            Divider(
                color = Color(0xFF333333),
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth()
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // セクション3: アクションボタン
        Button(
            onClick = onStartClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF4444)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "この設定で起動する",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(32.dp))
    }
}

/**
 * リクエスト中画面のコンテンツ
 */
@Composable
fun AirConRequestingContent(
    onNotificationSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        // セクション1: ステータス表示
        Text(
            text = "リクエスト中",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // セクション2: ローディングインジケーター
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            LoadingSpinner()
        }
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // セクション3: 区切り線
        Divider(
            color = Color(0xFF333333),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // セクション4: 動作中設定表示
        Text(
            text = "この設定で動作中",
            color = Color(0xFF888888),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // セクション5: 起動時間表示
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "起動時間",
                color = Color(0xFF888888),
                fontSize = 14.sp
            )
            
            Text(
                text = "-- --",
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // セクション6: 停止ボタン（無効化）
        Button(
            onClick = { /* 無効化されているため何もしない */ },
            enabled = false,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF666666),
                disabledContainerColor = Color(0xFF666666)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "停止する",
                color = Color.White,
                fontSize = 16.sp
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // セクション7: 通知設定エリア
        NotificationSettingsSection(
            onNotificationSettingsClick = onNotificationSettingsClick
        )
    }
}

/**
 * 完了画面のコンテンツ
 */
@Composable
fun AirConCompletedContent(
    remainingTime: Int,
    onStopClick: () -> Unit,
    onNotificationSettingsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        
        // セクション1: ステータス表示
        Text(
            text = "エアコン停止まで",
            color = Color.White,
            fontSize = 18.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // セクション2: 残り時間表示
        Text(
            text = "${remainingTime}分",
            color = Color.White,
            fontSize = 64.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // セクション3: 区切り線
        Divider(
            color = Color(0xFF333333),
            thickness = 1.dp,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // セクション4: 動作中設定表示
        Text(
            text = "この設定で動作中",
            color = Color(0xFF888888),
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // セクション5: 起動時間表示
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "起動時間",
                color = Color(0xFF888888),
                fontSize = 14.sp
            )
            
            Text(
                text = "10分間",
                color = Color.White,
                fontSize = 32.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // セクション6: 停止ボタン
        Button(
            onClick = onStopClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFFF4444)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "停止する",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // セクション7: 通知設定エリア
        NotificationSettingsSection(
            onNotificationSettingsClick = onNotificationSettingsClick
        )
    }
}

/**
 * 通知設定セクション
 */
@Composable
fun NotificationSettingsSection(
    onNotificationSettingsClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color(0xFF2A2A3E),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // 通知設定ヘッダー
            Text(
                text = "通知設定",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            // 通知説明テキスト
            Text(
                text = "クルマの操作関連通知をONにすると、リモート操作完了時にお知らせします。",
                color = Color(0xFFCCCCCC),
                fontSize = 14.sp,
                lineHeight = 19.6.sp
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 通知設定ボタン
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onNotificationSettingsClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Material Icons: Menu - 確実に存在（設定の代替）
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "設定",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = "アプリの通知を設定する",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}

/**
 * ローディングスピナー
 */
@Composable
fun LoadingSpinner() {
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
    
    // Material Icons: Add - 確実に存在（回転アニメーション用の代替）
    Icon(
        imageVector = Icons.Default.Add,
        contentDescription = "読み込み中",
        tint = Color(0xFFFF4444),
        modifier = Modifier
            .size(48.dp)
            .rotate(rotation)
    )
}

@Preview(showBackground = true)
@Composable
fun AirConScreenPreview() {
    DemoCursorAndroidTheme {
        AirConScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun AirConRequestingPreview() {
    DemoCursorAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
        ) {
            AirConHeader(
                showHelpButton = false,
                onBackClick = {},
                onHelpClick = {}
            )
            AirConRequestingContent(
                onNotificationSettingsClick = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AirConCompletedPreview() {
    DemoCursorAndroidTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(DarkBackground)
        ) {
            AirConHeader(
                showHelpButton = true,
                onBackClick = {},
                onHelpClick = {}
            )
            AirConCompletedContent(
                remainingTime = 9,
                onStopClick = {},
                onNotificationSettingsClick = {}
            )
        }
    }
}
