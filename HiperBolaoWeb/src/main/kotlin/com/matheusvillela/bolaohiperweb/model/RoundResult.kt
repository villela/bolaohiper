package com.matheusvillela.bolaohiperweb.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.matheusvillela.bolaohiperweb.util.LocalDateTimeSerializer
import java.time.LocalDateTime

data class RoundResult(@field:JsonSerialize(using = LocalDateTimeSerializer::class)
                       val start: LocalDateTime,
                       val points: Int,
                       val bets: List<BetWithPoints>)