package com.example.democursorandroid.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.democursorandroid.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAirConClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        // ヘッダーセクション
        HeaderSection()
        
        // 車両画像セクション
        VehicleImageSection()
        
        // 車両情報セクション
        VehicleInfoSection()
        
        // 通知セクション
        NotificationSection()
        
        // アクションボタンセクション
        ActionButtonsSection(onAirConClick = onAirConClick)
        
        Spacer(modifier = Modifier.weight(1f))
        
        // フッターセクション
        FooterSection()
    }
}

@Composable
fun HeaderSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp),
        color = DarkSurface
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ブランドロゴ（仕様書: 車両メーカーのロゴアイコン）
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(CarRed, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "T",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 車種表示（仕様書: ハリアー ハイブリッド）
            Text(
                text = "ハリアー ハイブリッド",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // メニューボタン（仕様書: ハンバーガーメニューアイコン（3本線））
            // 基本アイコン: Icons.Default.Menu - 確実に存在
            IconButton(
                onClick = { /* メニューを開く */ }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "メニュー",
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun VehicleImageSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(GradientTop, GradientBottom)
                )
            )
            .clickable { /* 車両詳細画面へ */ },
        contentAlignment = Alignment.Center
    ) {
        // 車両画像のプレースホルダー
        Box(
            modifier = Modifier
                .size(320.dp, 180.dp)
                .background(
                    Color.Gray.copy(alpha = 0.3f),
                    RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            // 仕様書: 車両シルエット → 基本アイコン: Icons.Default.Star（確実に存在）
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "車両画像",
                tint = White.copy(alpha = 0.7f),
                modifier = Modifier.size(80.dp)
            )
        }
        
        // 画像拡大ボタン（仕様書: 拡大アイコン（四角形に矢印4方向））
        // 基本アイコン: Icons.Default.Add - 確実に存在（拡大の意味）
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
                .size(32.dp)
                .background(
                    Color.Black.copy(alpha = 0.6f),
                    CircleShape
                )
                .clickable { /* 画像拡大 */ },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "拡大",
                tint = White,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
fun VehicleInfoSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = DarkBackground
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 燃費情報
            Column {
                Text(
                    text = "給油時燃費記録",
                    color = LightGray,
                    fontSize = 12.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "146",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "km",
                        color = White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                    // 仕様書: 給油ポンプアイコン
                    // 基本アイコン: Icons.Default.Menu - 確実に存在
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "燃料設定",
                        tint = White,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(24.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // 総走行距離情報
            Column {
                Text(
                    text = "総走行距離",
                    color = LightGray,
                    fontSize = 12.sp
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "6,395",
                        color = White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "km",
                        color = White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationSection() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = DarkBackground
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clickable { /* 通知詳細 */ },
            colors = CardDefaults.cardColors(
                containerColor = Orange.copy(alpha = 0.2f)
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 仕様書: 三角形警告アイコン → 基本アイコン: Icons.Default.Warning
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "警告",
                    tint = Orange,
                    modifier = Modifier.size(20.dp)
                )
                
                Text(
                    text = "オートアラームがOFFになっています",
                    color = White,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp)
                )
                
                // 仕様書: 右向き矢印 → 基本アイコン: Icons.Default.Add
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "詳細",
                    tint = LightGray,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun ActionButtonsSection(
    onAirConClick: () -> Unit = {}
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        color = DarkBackground
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 仕様書: 三角形警告アイコン → 基本アイコン: Icons.Default.Warning
            ActionButton(
                icon = Icons.Default.Warning,
                label = "ハザード",
                onClick = { /* ハザード操作 */ }
            )
            
            // 仕様書: エアコンファンアイコン（プロペラ形状）
            // 基本アイコン: Icons.Default.Add - 確実に存在
            ActionButton(
                icon = Icons.Default.Add,
                label = "エアコン",
                onClick = onAirConClick
            )
            
            // 仕様書: 車両位置アイコン（車に電波マーク）
            // 基本アイコン: Icons.Default.Star - 確実に存在
            ActionButton(
                icon = Icons.Default.Star,
                label = "カーファインダー",
                onClick = { /* カーファインダー */ }
            )
        }
    }
}

@Composable
fun ActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(1.dp, DarkBorder, CircleShape)
                .background(DarkSurface, CircleShape)
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = White,
                modifier = Modifier.size(32.dp)
            )
        }
        
        Text(
            text = label,
            color = White,
            fontSize = 12.sp,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun FooterSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = DarkBackground
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "リモートサービス",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
            
            Row(
                modifier = Modifier.padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                // 仕様書: 車両シルエット → 基本アイコン: Icons.Default.Star（確実に存在）
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "車両サービス",
                    tint = CarRed,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* 車両サービス */ }
                )
                
                // 仕様書: 封筒アイコン → 基本アイコン: Icons.Default.Add
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "メール",
                    tint = White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* メール */ }
                )
                
                // 仕様書: お知らせベルアイコン → 基本アイコン: Icons.Default.Menu
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "お知らせ",
                    tint = White,
                    modifier = Modifier
                        .size(24.dp)
                        .clickable { /* お知らせ */ }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    DemoCursorAndroidTheme {
        HomeScreen()
    }
}