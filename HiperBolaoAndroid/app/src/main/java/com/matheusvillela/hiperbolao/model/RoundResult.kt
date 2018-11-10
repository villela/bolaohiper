package com.matheusvillela.hiperbolao.model

import org.threeten.bp.LocalDateTime

data class RoundResult(val start: LocalDateTime,
                       val points: Int,
                       val bets: List<BetWithPoints>)