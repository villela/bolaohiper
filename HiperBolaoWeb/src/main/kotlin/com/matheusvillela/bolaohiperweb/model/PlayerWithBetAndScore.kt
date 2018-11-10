package com.matheusvillela.bolaohiperweb.model

data class PlayerWithBetAndScore(val player: Player,
                                 val bet: Bet,
                                 val score: Int? = null)