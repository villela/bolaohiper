package com.matheusvillela.hiperbolao.model

data class PlayerWithBetAndScore(val player: Player,
                                 val bet: Bet,
                                 val score: Int? = null)