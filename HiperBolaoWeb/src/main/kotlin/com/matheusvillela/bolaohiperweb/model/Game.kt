package com.matheusvillela.bolaohiperweb.model

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.matheusvillela.bolaohiperweb.util.LocalDateTimeSerializer
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "games")
data class Game(@Id val id: Int,
                @field:JsonSerialize(using = LocalDateTimeSerializer::class)
                val start: LocalDateTime,
                val homeTeam: Team,
                val awayTeam: Team,
                val score: Score?)