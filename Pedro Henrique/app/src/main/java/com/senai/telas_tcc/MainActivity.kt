package com.senai.telas_tcc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
// CORREÇÃO 1: Importar o nome correto do tema (conforme seu arquivo Theme.kt)
import com.senai.telas_tcc.ui.theme.Telas_TCCTheme
import com.senai.telas_tcc.ui.theme.screens.SenaiGameScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // CORREÇÃO 2: Usar o nome exato da função que está no Theme.kt Theme {
            Telas_TCCTheme {
                SenaiGameScreen()
            }
        }
    }
}