package com.matheusvillela.hiperbolao.model

data class Standing(val player: Player,
                    val position: Int,
                    val positionsEarned: Int,
                    val lastRoundResult: RoundResult)