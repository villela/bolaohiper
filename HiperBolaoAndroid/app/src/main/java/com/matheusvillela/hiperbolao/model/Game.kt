package com.matheusvillela.hiperbolao.model
import org.threeten.bp.LocalDateTime

data class Game(val id: Int,
                val start: LocalDateTime,
                val homeTeam: Team,
                val awayTeam: Team,
                val score: Score?)