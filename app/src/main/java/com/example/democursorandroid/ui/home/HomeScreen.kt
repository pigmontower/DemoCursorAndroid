package com.example.democursorandroid.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.democursorandroid.R
import com.example.democursorandroid.ui.theme.DemoCursorAndroidTheme

@Composable
fun HomeScreen(
    onNavigateToAirConditioner: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_gray))
    ) {
        // アプリヘッダー
        AppHeader()
        
        // 車両画像表示エリア
        VehicleImageArea()
        
        // 車両ステータス情報
        VehicleStatusInfo()
        
        // 車両情報ボタン
        VehicleInfoButton()
        
        // 警告メッセージ
        WarningMessage()
        
        // リモートサービス
        RemoteServices(
            onNavigateToAirConditioner = onNavigateToAirConditioner
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        // ボトムナビゲーション
        BottomNavigation()
    }
}

@Composable
fun AppHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colorResource(id = R.color.dark_gray))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // トヨタロゴ（赤色円形、白いトヨタエンブレム）
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(colorResource(id = R.color.toyota_red)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "T",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        // 車種選択ボタン
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ハリアー ハイブリッド",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "車種選択",
                tint = Color.White,
                modifier = Modifier.size(20.dp)
            )
        }
        
        // ハンバーガーメニューアイコン
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "メニュー",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun VehicleImageArea() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1.67f) // 画面幅の60%の高さ
            .background(colorResource(id = R.color.dark_gray))
    ) {
        // 車両画像のプレースホルダー
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "🚗",
                fontSize = 120.sp,
                textAlign = TextAlign.Center
            )
        }
        
        // 画像ギャラリーアイコン
        IconButton(
            onClick = { /* 画像ギャラリー表示 */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "画像ギャラリー",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun VehicleStatusInfo() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(colorResource(id = R.color.dark_gray))
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 航続可能距離
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "航続可能距離",
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "146km",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        
        // 燃料ゲージ
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.size(80.dp),
                contentAlignment = Alignment.Center
            ) {
                // 半円形ゲージのプレースホルダー
                Text(
                    text = "⛽",
                    fontSize = 32.sp
                )
            }
            Row(
                modifier = Modifier.width(80.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "E",
                    color = Color.White,
                    fontSize = 12.sp
                )
                Text(
                    text = "F",
                    color = Color.White,
                    fontSize = 12.sp
                )
            }
        }
        
        // 総走行距離
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "総走行距離",
                color = Color.White,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "6,395km",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun VehicleInfoButton() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .background(colorResource(id = R.color.dark_gray))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "① クルマの情報",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "車両情報",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun WarningMessage() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(colorResource(id = R.color.dark_gray))
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "警告",
            tint = colorResource(id = R.color.warning_yellow),
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "オートアラームがOFFになっています",
            color = Color.White,
            fontSize = 14.sp,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "詳細",
            tint = Color.White,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun RemoteServices(
    onNavigateToAirConditioner: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(colorResource(id = R.color.dark_gray))
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "リモートサービス",
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // ハザードボタン
            RemoteServiceButton(
                icon = Icons.Default.Add,
                label = "ハザード",
                onClick = { /* ハザード制御 */ }
            )
            
            // エアコンボタン
            RemoteServiceButton(
                icon = Icons.Default.Add,
                label = "エアコン",
                onClick = onNavigateToAirConditioner
            )
            
            // カーファインダーボタン
            RemoteServiceButton(
                icon = Icons.Default.Add,
                label = "カーファインダー",
                onClick = { /* カーファインダー */ }
            )
        }
    }
}

@Composable
fun RemoteServiceButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        IconButton(
            onClick = onClick,
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.Transparent)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = Color.White,
                modifier = Modifier.size(32.dp)
            )
        }
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun BottomNavigation() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(colorResource(id = R.color.dark_gray))
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // クルマタブ（アクティブ）
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "クルマ",
                tint = colorResource(id = R.color.toyota_red),
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "クルマ",
                color = colorResource(id = R.color.toyota_red),
                fontSize = 12.sp
            )
        }
        
        // お知らせタブ（非アクティブ）
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "お知らせ",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
            Text(
                text = "お知らせ",
                color = Color.White,
                fontSize = 12.sp
            )
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
