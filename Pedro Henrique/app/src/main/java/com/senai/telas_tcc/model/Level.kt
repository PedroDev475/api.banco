package com.senai.telas_tcc.model

class Level {
    enum class LevelStatus {
        LOCKED,    // Bloqueado (Cinza)
        ACTIVE,    // Atual (Vermelho + Mascote)
        COMPLETED, // Feito (Vermelho Escuro)
        CHEST      // Baú de recompensa
    }

    data class Level(
        val id: Int,
        val status: LevelStatus
    )
}