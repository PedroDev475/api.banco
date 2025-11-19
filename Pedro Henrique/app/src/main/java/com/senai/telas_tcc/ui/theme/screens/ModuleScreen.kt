package com.senai.telas_tcc.ui.theme.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.datatransport.runtime.dagger.Module


data class ModuleData(
    val title: String,
    val subtitle: String,
    val imageRes: Int
)
@Composable
fun ModuleScreen(){

    val module = listOf(
        ModuleData(
            "PROGRAMAÇÃO BACK-END",
            "Treine sua lógica e entenda algoritimos",
            android.R.drawable.ic_menu_gallery
        ),
        ModuleData(
            "PROGRAMAÇÃO FRONT-END",
            "Entenda como a WEB ganha forma",
            android.R.drawable.ic_menu_camera
        ),
        ModuleData(
            "PROJETO DE SOFTWARE",
            "Aprenda a organizar e planejar sistemas",
            android.R.drawable.ic_dialog_map
        )
    )
    Scaffold (
        topBar = { TopHeader() },
        bottomBar = { BottomNavBar() }
    ){ paddingValues ->
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
                .padding(horizontal = 16.dp),// Padding Lateral para os cards
                verticalArrangement = Arrangement.spacedBy(16.dp),//Espaçamento entre os cards
                contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp)// Padding no topo e base da lista
    ){
            items(module){ module ->
                ModuleCard(module = module){

                    println("Clicou no módulo: ${module.title}")
                }
                }
        }
        }
    }
@Composable
fun ModuleCard(module: ModuleData, onClick:() -> Unit){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column {
                // 1. CORREÇÃO: Adicionado module.imageRes e ContentScale
                Image(
                    painter = painterResource(id = module.imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop // Importante para a imagem não distorcer
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SenaiRed)
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = module.title,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        // 2. CORREÇÃO: Trocado 'module' por 'module.subtitle'
                        Text(
                            text = module.subtitle,
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                    Icon(
                        painter = painterResource(android.R.drawable.ic_media_play),
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
        }
    }
}