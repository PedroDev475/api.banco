package com.senai.telas_tcc.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.sin
// Importe suas cores e modelos

val SenaiRed = Color(0xFFD32F2F)
val SenaiDarkRed = Color(0xFFB71C1C)
val PathGrey = Color(0xFFB0BEC5)

data class Level(val id: Int, val status: LevelStatus)
enum class LevelStatus { COMPLETED, ACTIVE, LOCKED, CHEST }
@Composable
fun SenaiGameScreen() {
    // Lista simulada de níveis
    val levels = List(5) { index ->
        when (index) {
            0 -> Level(index, LevelStatus.COMPLETED)
            1 -> Level(index, LevelStatus.ACTIVE) // Nível atual
            4 -> Level(index, LevelStatus.CHEST)
            else -> Level(index, LevelStatus.LOCKED)
        }
    }

    Scaffold(
        topBar = { TopHeader() },
        bottomBar = { BottomNavBar() }
    ) { paddingValues ->

        // COLUNA PRINCIPAL (Empilha Banner e Lista)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Padding do Scaffold
        ) {
            // 1. BANNER FIXO NO TOPO
            BannerCard()

            // 2. CAMINHO ROLÁVEL (Ocupa o resto do espaço)
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Importante: Faz a lista ocupar o espaço restante
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item { Spacer(modifier = Modifier.height(20.dp)) }

                itemsIndexed(levels) { index, level ->
                    LevelItem(level = level, index = index)
                }

                item { Spacer(modifier = Modifier.height(20.dp)) }
            }
        }
    }
}
@Composable
fun LevelItem(level: Level, index: Int) { // Corrigido o tipo para Level
    // Lógica do Zig-Zag (Matemática simples usando Seno)
    val isChest = level.status == LevelStatus.CHEST
    val horizontalOffset = if (isChest) 0.dp else (sin(index * 0.8) * 80).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Espaçamento vertical entre botões
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier.offset(x = horizontalOffset),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // MASCOTE (Aparece apenas no nível ATIVO e se NÃO for o baú)
            if (level.status == LevelStatus.ACTIVE) {
                Text("🐦", fontSize = 60.sp) // Seu mascote aqui
                Spacer(modifier = Modifier.width(8.dp))
            }

            // --- AQUI MUDA O FORMATO ---
            if (isChest) {
                // 1. VISUAL DO BOTÃO FINAL (Retangular e Cinza)
                Box(
                    modifier = Modifier
                        .width(100.dp)   // Mais largo
                        .height(60.dp)   // Altura retangular
                        .clip(RoundedCornerShape(12.dp)) // Bordas arredondadas
                        .background(Color(0xFFE0E0E0))   // Cinza claro
                        .border(4.dp, Color(0xFFBDBDBD), RoundedCornerShape(12.dp)), // Borda cinza mais escura
                    contentAlignment = Alignment.Center
                ) {
                    // Ícone de coração/finalização
                    Icon(
                        painter = painterResource(android.R.drawable.star_on), // Placeholder (Troque pelo seu ícone)
                        contentDescription = "Final",
                        tint = Color.Gray,
                        modifier = Modifier.size(32.dp)
                    )
                }
            } else {
                // 2. VISUAL DOS NÍVEIS NORMAIS (Bolinhas Vermelhas/Cinzas)
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .background(
                            when (level.status) {
                                LevelStatus.COMPLETED -> SenaiDarkRed
                                LevelStatus.ACTIVE -> SenaiRed
                                else -> PathGrey
                            }
                        )
                        // Borda branca extra se for o ativo
                        .then(if (level.status == LevelStatus.ACTIVE) Modifier.border(4.dp, Color.White, CircleShape) else Modifier)
                        // Sombra simulada (padding interno)
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("</>", color = Color.White, fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
                }
            }
        }
    }
}
@Composable
fun BannerCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = SenaiRed),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text("PROGRAMAÇÃO BACK-END", color = Color.White, fontWeight = FontWeight.Black, fontSize = 30.sp)
                Text("Treine sua lógica...", color = Color.White, fontSize = 25.sp)
            }
            Icon(painterResource(android.R.drawable.ic_media_play), contentDescription = null, tint = Color.White)
        }
    }
}
@Composable
fun TopHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("SENAI", color = SenaiRed, fontSize = 30.sp, fontWeight = FontWeight.Black) // Substituir por logo
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("💎 500", color = SenaiRed, fontWeight = FontWeight.Bold)
        }
    }
}
@Composable
fun BottomNavBar() {
    NavigationBar(containerColor = SenaiDarkRed) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home", tint = Color.White) },
            selected = true,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Star, contentDescription = "Ranking", tint = Color.Gray) },
            selected = false,
            onClick = { }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Person, contentDescription = "Perfil", tint = Color.Gray) },
            selected = false,
            onClick = { }
        )
    }
}